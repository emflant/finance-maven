package com.emflant.accounting.business.bean;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.business.remote.SlipRemote;
import com.emflant.accounting.dto.screen.A09ReverseTransactionMainInsert01DTO;
import com.emflant.accounting.dto.table.AccountDetailDTO;
import com.emflant.accounting.dto.table.SequenceMasterDTO;
import com.emflant.accounting.dto.table.SlipDetailDTO;
import com.emflant.accounting.dto.table.SlipMasterDTO;
import com.emflant.common.EntCommon;
import com.emflant.common.EntException;
import com.emflant.common.EntTransaction;

public class SlipBean implements SlipRemote {
	private EntTransaction transaction;
	
	public SlipBean(EntTransaction transaction){
		this.transaction = transaction;
	}

	
	public String getSlipNo() throws EntException {
		
		if(transaction.isOneSlip() && transaction.getSlipNo() != null){
			return transaction.getSlipNo();
		}
		
		List results = this.transaction.select(SequenceMasterDTO.class
				, new StringBuilder("SELECT sequence FROM SEQUENCE_MASTER WHERE sequence_type = '00'"));
		Long nNewSequence = ((SequenceMasterDTO)results.get(0)).getSequence() + 1;
		
		StringBuilder sbQuery = new StringBuilder(256);
		sbQuery.append("UPDATE SEQUENCE_MASTER SET sequence = ");
		sbQuery.append(nNewSequence);
		sbQuery.append(" WHERE sequence_type = '00' ");
		
		this.transaction.update(sbQuery);
		
		String strSlipNo = this.transaction.getSystemDate()+String.format("%07d", nNewSequence);
		transaction.setSlipNo(strSlipNo);
		
		return strSlipNo;
	}
	

	public void insert(SlipMasterDTO slipMasterDTO) throws EntException {
		this.transaction.insert(slipMasterDTO);
	}

	public void insert(SlipDetailDTO slipDetail) throws EntException {
		this.transaction.insert(slipDetail);
	}
	


	public void execute(SlipMasterDTO slipMasterDTO) throws EntException{
		slipMasterDTO.setSlipNo(getSlipNo());
		slipMasterDTO.setSlipSequence(transaction.getSlipSeq());
		
		//전표원장 등록
		insert(slipMasterDTO);
		
		//전표금액내역 등록
		List<SlipDetailDTO> amounts = slipMasterDTO.getAmounts();
		
		for(SlipDetailDTO amount : amounts){
			amount.setSlipNo(slipMasterDTO.getSlipNo());
			amount.setSlipSequence(slipMasterDTO.getSlipSequence());
			insert(amount);
		}
		
	}
	
	
	/**
	 * 전표 취소/정정 처리를 한다.
	 * @param inputDTO
	 * @throws EntException
	 */
	public void cancel(A09ReverseTransactionMainInsert01DTO inputDTO) throws EntException {
		try {
			//A09ReverseTransactionMainInsert01DTO inputDTO = (A09ReverseTransactionMainInsert01DTO)transaction.getMethodParam();
			
			StringBuilder sb = new StringBuilder(1024);
			sb.append("SELECT * ");
			sb.append(" FROM slip_master WHERE register_user_id = ");
			sb.append(EntCommon.convertQuery(inputDTO.getRegisterUserId()));
			sb.append(" AND register_date_time = ");
			sb.append(EntCommon.convertQuery(inputDTO.getRegisterDateTime()));
			
			List<SlipMasterDTO> lResult = this.transaction.select(SlipMasterDTO.class, sb);
			
			if(lResult.isEmpty()){
				throw new EntException("해당 전표가 존재하지 않습니다.");
			}
			
			SlipMasterDTO slipMasterDTO = lResult.get(0);
			cancel(slipMasterDTO.getSlipNo(), slipMasterDTO.getSlipSequence());
			
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	

	/**
	 * 전표를 취소처리한다.
	 * @param slipNo
	 * @param slipSequence
	 * @throws EntException
	 */
	public void cancel(String slipNo, Integer slipSequence) throws EntException {
		SlipMasterDTO slipMasterDTO = getCancelSlipOfReckonDate(slipNo, slipSequence);
		execute(slipMasterDTO);
	}
	

	public void cancelOfToday(AccountDetailDTO accountDetailDTO) throws EntException{
		//기존전표를 취소로 수정.
		StringBuilder sbQuery = new StringBuilder(256);
		sbQuery.append(" update slip_master    ");
		sbQuery.append(" set cancel_type = '1'         ");
		sbQuery.append("   , last_register_user_id =   ");
		sbQuery.append(EntCommon.convertQuery(accountDetailDTO.getLastRegisterUserId()));
		sbQuery.append("   , last_register_date_time = ");
		sbQuery.append(EntCommon.convertQuery(this.transaction.getSystemDateTimeMillis()));
		sbQuery.append(" where slip_no =               ");
		sbQuery.append(EntCommon.convertQuery(accountDetailDTO.getSlipNo()));
		sbQuery.append(" and slip_sequence =           ");
		sbQuery.append(accountDetailDTO.getSlipSeq());
		
		this.transaction.update(sbQuery);
		
		
		//새로운 취소전표를 생성
		
		
	}
	
	public SlipMasterDTO getCancelSlipOfReckonDate(String slipNo, Integer slipSequence) throws EntException{
		//새로운 정정전표를 생성
		SlipMasterDTO slipMasterDTO = select(slipNo, slipSequence);
		
		slipMasterDTO.setAmounts(selectSlipDetails(slipNo, slipSequence));
		slipMasterDTO.setCancelType("7");
		slipMasterDTO.setSlipDate(this.transaction.getSystemDate());
		slipMasterDTO.setSlipTime(this.transaction.getSystemTimeMillis());
		slipMasterDTO.setRegisterDateTime(this.transaction.getSystemDateTimeMillis());
		slipMasterDTO.setLastRegisterDateTime(this.transaction.getSystemDateTimeMillis());
		
		return slipMasterDTO;
	}
	
	
	public SlipMasterDTO select(String slipNo, Integer slipSequence) throws EntException{
		StringBuilder sbQuery = new StringBuilder(256);
		sbQuery.append(" SELECT                          ");
		sbQuery.append(" slip_no,                        ");
		sbQuery.append(" slip_sequence,                  ");
		sbQuery.append(" slip_date,                      ");
		sbQuery.append(" slip_time,                      ");
		sbQuery.append(" cancel_type,                    ");
		sbQuery.append(" slip_amount,                    ");
		sbQuery.append(" credit_amount AS debtor_amount, ");
		sbQuery.append(" debtor_amount AS credit_amount, ");
		sbQuery.append(" user_id,                        ");
		sbQuery.append(" register_user_id,               ");
		sbQuery.append(" register_date_time,             ");
		sbQuery.append(" last_register_user_id,          ");
		sbQuery.append(" last_register_date_time         ");
		sbQuery.append(" FROM slip_master        ");
		sbQuery.append(" where slip_no =                 ");
		sbQuery.append(EntCommon.convertQuery(slipNo));
		sbQuery.append(" and slip_sequence =             ");
		sbQuery.append(slipSequence);
		sbQuery.append(" order by slip_sequence          ");

		
		List lResult = this.transaction.select(SlipMasterDTO.class, sbQuery);
		
		return (SlipMasterDTO)lResult.get(0);
	}
	
	public List<SlipDetailDTO> selectSlipDetails(String slipNo, Integer slipSequence) throws EntException{
		StringBuilder sbQuery = new StringBuilder(256);
		sbQuery.append(" SELECT                                      ");
		sbQuery.append(" slip_no,                                    ");
		sbQuery.append(" slip_sequence,                              ");
		sbQuery.append(" sequence,                                   ");
		sbQuery.append(" account_type,                               ");
		sbQuery.append(" case when debtor_credit_type = 'D' then 'C' ");
		sbQuery.append(" else 'D' end AS debtor_credit_type,         ");
		sbQuery.append(" total_amount,                               ");
		sbQuery.append(" cash_amount,                                ");
		sbQuery.append(" non_cash_amount,                            ");
		sbQuery.append(" register_user_id,                           ");
		sbQuery.append(" register_date_time,                         ");
		sbQuery.append(" last_register_user_id,                      ");
		sbQuery.append(" last_register_date_time                     ");
		sbQuery.append(" FROM slip_detail                    ");
		sbQuery.append(" where slip_no =                             ");
		sbQuery.append(EntCommon.convertQuery(slipNo));
		sbQuery.append(" and slip_sequence =                         ");
		sbQuery.append(slipSequence);
		sbQuery.append(" order by sequence                           ");

		
		List lResult = this.transaction.select(SlipDetailDTO.class, sbQuery);
		
		return (List<SlipDetailDTO>)lResult;
	}
	
	public void selectSlipMaster() throws EntException{
		
		try {
			
			StringBuilder sbQuery = new StringBuilder(1024);
			sbQuery.append("SELECT slip_no, slip_sequence, format(slip_amount,0) as format_slip_amount ");
			sbQuery.append(", DATE_FORMAT(slip_date,'%Y-%m-%d') AS format_slip_date ");
			sbQuery.append(", format(debtor_amount,0) as format_debtor_amount ");
			sbQuery.append(", format(credit_amount,0) as format_credit_amount ");
			sbQuery.append(" FROM SLIP_MASTER");
			
			
			DefaultTableModel list = this.transaction.selectToTableModel(sbQuery);
			
			
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	
	public void selectSlipDetail() throws EntException{
		
		try {
			SlipDetailDTO slipDetail = (SlipDetailDTO)transaction.getMethodParam();
			DefaultTableModel list = selectSlipDetail(slipDetail);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	public DefaultTableModel selectSlipDetail(SlipDetailDTO slipDetail) throws EntException {
		StringBuilder sbQuery = new StringBuilder(3072);
		
		sbQuery.append(" SELECT sequence as sequence                      ");
		sbQuery.append(" , (SELECT account_type_name FROM account ");
		sbQuery.append("     WHERE account_type = A.account_type)         ");
		sbQuery.append("                AS account_type_name              ");
		sbQuery.append(", (SELECT (SELECT S.code_name FROM code_detail S                  ");
		sbQuery.append("            WHERE S.code_type = '06'                                      ");
		sbQuery.append("              and S.code = T.bs_pl_detail_type) as bs_pl_detail_type_name ");
		sbQuery.append("    FROM account T                                                ");
		sbQuery.append("    WHERE account_type = A.account_type) as bs_pl_detail_type_name ");
		sbQuery.append(" , (SELECT code_name FROM code_detail S   ");
		sbQuery.append("     WHERE S.code_type = '07'                     ");
		sbQuery.append("       AND S.code = A.debtor_credit_type)         ");
		sbQuery.append("                AS debtor_credit_type_name        ");
		sbQuery.append(" , format(total_amount,0) AS total_amount                   ");
		sbQuery.append(" , format(cash_amount,0) AS cash_amount                     ");
		sbQuery.append(" , format(non_cash_amount,0) AS non_cash_amount             ");
		sbQuery.append(" FROM SLIP_DETAIL A                       ");
		sbQuery.append(" WHERE A.slip_no = ");
		sbQuery.append(EntCommon.convertQuery(slipDetail.getSlipNo()));
		sbQuery.append(" AND A.slip_sequence = ");
		sbQuery.append(slipDetail.getSlipSequence());
		sbQuery.append(" ORDER BY A.sequence                              ");

		DefaultTableModel list = this.transaction.selectToTableModel(sbQuery);
		sbQuery.append(EntCommon.convertQuery(slipDetail.getSlipNo()));
		sbQuery.append(slipDetail.getSlipSequence());
		return list;
	}
	
	
	public void selectSlipDetailByAccountType(String accountType) throws EntException{
		
		try {
			//String strAccountType = (String)transaction.getMethodParam();
			DefaultTableModel list = querySlipDetailByAccountType(accountType);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	public DefaultTableModel querySlipDetailByAccountType(String accountType) throws EntException {

		StringBuilder sbQuery = new StringBuilder(4048);
		
		sbQuery.append(" SELECT slip_no, slip_sequence                                                    ");
		sbQuery.append(" , (SELECT account_type_name FROM account                                 ");
		sbQuery.append("     WHERE account_type = A.debtor_account_type) as debtor_account_type_name      ");
		sbQuery.append(" , (SELECT (SELECT S.code_name FROM code_detail S                         ");
		sbQuery.append("             WHERE S.code_type = '06'                                             ");
		sbQuery.append("               and S.code = T.bs_pl_detail_type) as bs_pl_detail_type_name        ");
		sbQuery.append("     FROM account T                                                       ");
		sbQuery.append("     WHERE account_type = A.debtor_account_type) as debtor_bs_pl_detail_type_name ");
		sbQuery.append(" , debtor_amount                                                                  ");
		sbQuery.append(" , (SELECT account_type_name FROM account                                 ");
		sbQuery.append("     WHERE account_type = A.credit_account_type) as credit_account_type_name      ");
		sbQuery.append(" , (SELECT (SELECT S.code_name FROM code_detail S                         ");
		sbQuery.append("             WHERE S.code_type = '06'                                             ");
		sbQuery.append("               and S.code = T.bs_pl_detail_type) as bs_pl_detail_type_name        ");
		sbQuery.append("     FROM account T                                                       ");
		sbQuery.append("     WHERE account_type = A.credit_account_type) as credit_bs_pl_detail_type_name ");
		sbQuery.append(" , credit_amount                                                                  ");
		sbQuery.append(" from (                                                                           ");
		sbQuery.append(" select slip_no, slip_sequence                                                    ");
		sbQuery.append(" , null as debtor_account_type                                                    ");
		sbQuery.append(" , null as debtor_amount                                                          ");
		sbQuery.append(" , account_type as credit_account_type                                            ");
		sbQuery.append(" , total_amount as credit_amount                                                  ");
		sbQuery.append(" from slip_detail                                                         ");
		sbQuery.append(" where account_type =                                                             ");
		sbQuery.append(EntCommon.convertQuery(accountType));
		sbQuery.append(" and debtor_credit_type = 'C'                                                     ");
		sbQuery.append(" union all                                                                        ");
		sbQuery.append(" select slip_no, slip_sequence                                                    ");
		sbQuery.append(" , account_type as debtor_account_type                                            ");
		sbQuery.append(" , total_amount as debtor_amount                                                  ");
		sbQuery.append(" , null as credit_account_type                                                    ");
		sbQuery.append(" , null as credit_amount                                                          ");
		sbQuery.append(" from slip_detail                                                         ");
		sbQuery.append(" where account_type=                                                              ");
		sbQuery.append(EntCommon.convertQuery(accountType));
		sbQuery.append(" and debtor_credit_type = 'D'                                                     ");
		sbQuery.append(" ) A                                                                              ");
		sbQuery.append(" order by A.slip_no, A.slip_sequence                                              ");
		
		
		DefaultTableModel list = this.transaction.selectToTableModel(sbQuery);
		
		return list;
	}	
	
	
	public void insertSlip(SlipMasterDTO slipMasterDTO) throws EntException{
		
		try {
			//SlipMasterDTO slipMasterDTO = (SlipMasterDTO)transaction.getMethodParam();
			slipMasterDTO.setDefaultField(this.transaction);
			String strSlipNo = this.getSlipNo();
			Integer nSlipSeq = transaction.getSlipSeq();
			
			slipMasterDTO.setSlipNo(strSlipNo);
			slipMasterDTO.setSlipSequence(nSlipSeq);
			slipMasterDTO.setRegisterUserId(slipMasterDTO.getUserId());
			slipMasterDTO.setLastRegisterUserId(slipMasterDTO.getUserId());
			
			//전표금액내역 등록
			List<SlipDetailDTO> amounts = slipMasterDTO.getAmounts();
			
			for(SlipDetailDTO amount : amounts){
				amount.setSlipNo(strSlipNo);
				amount.setSlipSequence(nSlipSeq);
				amount.setRegisterUserId(slipMasterDTO.getRegisterUserId());
				amount.setRegisterDateTime(slipMasterDTO.getRegisterDateTime());
				amount.setLastRegisterUserId(slipMasterDTO.getLastRegisterUserId());
				amount.setLastRegisterDateTime(slipMasterDTO.getLastRegisterDateTime());
			}
			
			this.execute(slipMasterDTO);
			
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	
	
	public void selectWholeAccountFromSlipDetail() throws EntException{
		
		try {
			//String strAccountType = (String)transaction.getMethodParam();
			DefaultTableModel list = queryWholeAccountFromSlipDetail();
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	

	public DefaultTableModel queryWholeAccountFromSlipDetail() throws EntException {

		StringBuilder sbQuery = new StringBuilder(4048);
		sbQuery.append(" select A.account_type, A.account_type_name                  ");
		sbQuery.append(" , (SELECT S.code_name FROM code_detail S                    ");
		sbQuery.append("     WHERE S.code_type = '06'                                ");
		sbQuery.append(" and S.code = A.bs_pl_detail_type) as bs_pl_detail_type_name ");
		sbQuery.append(" , format(ifnull(B.debtor_total_amount,0), 0) debtor_total_amount      ");
		sbQuery.append(" , format(ifnull(C.credit_total_amount,0), 0) credit_total_amount      ");
		sbQuery.append(" from account A                                              ");
		sbQuery.append(" left outer join (                                           ");
		sbQuery.append(" select '01' account_type                                    ");
		sbQuery.append(" , 'D' debtor_credit_type                                    ");
		sbQuery.append(" , sum(cash_amount) debtor_total_amount                      ");
		sbQuery.append(" from slip_detail                                            ");
		sbQuery.append(" where debtor_credit_type = 'C') B                           ");
		sbQuery.append(" on (A.account_type = B.account_type)                        ");
		sbQuery.append(" left outer join (                                           ");
		sbQuery.append(" select '01' account_type                                    ");
		sbQuery.append(" , 'C' debtor_credit_type                                    ");
		sbQuery.append(" , sum(cash_amount) credit_total_amount                      ");
		sbQuery.append(" from slip_detail                                            ");
		sbQuery.append(" where debtor_credit_type = 'D') C                           ");
		sbQuery.append(" on (A.account_type = C.account_type)                        ");
		sbQuery.append(" where A.account_type = '01'                                 ");
		sbQuery.append("                                                             ");
		sbQuery.append(" union all                                                   ");
		sbQuery.append("                                                             ");
		sbQuery.append(" select A.account_type, A.account_type_name                  ");
		sbQuery.append(" , (SELECT S.code_name FROM code_detail S                    ");
		sbQuery.append("     WHERE S.code_type = '06'                                ");
		sbQuery.append(" and S.code = A.bs_pl_detail_type) as bs_pl_detail_type_name ");
		sbQuery.append(" , format(ifnull(B.debtor_total_amount,0), 0) debtor_total_amount      ");
		sbQuery.append(" , format(ifnull(C.credit_total_amount,0), 0) credit_total_amount      ");
		sbQuery.append(" from account A                                              ");
		sbQuery.append(" left outer join (                                           ");
		sbQuery.append(" select '02' account_type                                    ");
		sbQuery.append(" , 'D' debtor_credit_type                                    ");
		sbQuery.append(" , sum(non_cash_amount) debtor_total_amount                  ");
		sbQuery.append(" from slip_detail                                            ");
		sbQuery.append(" where debtor_credit_type = 'C') B                           ");
		sbQuery.append(" on (A.account_type = B.account_type)                        ");
		sbQuery.append(" left outer join (                                           ");
		sbQuery.append(" select '02' account_type                                    ");
		sbQuery.append(" , 'C' debtor_credit_type                                    ");
		sbQuery.append(" , sum(non_cash_amount) credit_total_amount                  ");
		sbQuery.append(" from slip_detail                                            ");
		sbQuery.append(" where debtor_credit_type = 'D') C                           ");
		sbQuery.append(" on (A.account_type = C.account_type)                        ");
		sbQuery.append(" where A.account_type = '02'                                 ");
		sbQuery.append("                                                             ");
		sbQuery.append(" union all                                                   ");
		sbQuery.append("                                                             ");
		sbQuery.append(" select A.account_type, A.account_type_name                  ");
		sbQuery.append(" , (SELECT S.code_name FROM code_detail S                    ");
		sbQuery.append("     WHERE S.code_type = '06'                                ");
		sbQuery.append(" and S.code = A.bs_pl_detail_type) as bs_pl_detail_type_name ");
		sbQuery.append(" , format(ifnull(B.debtor_total_amount,0), 0) debtor_total_amount      ");
		sbQuery.append(" , format(ifnull(C.credit_total_amount,0), 0) credit_total_amount      ");
		sbQuery.append(" from account A                                              ");
		sbQuery.append(" left outer join (                                           ");
		sbQuery.append(" select account_type                                         ");
		sbQuery.append(" , debtor_credit_type                                        ");
		sbQuery.append(" , sum(total_amount) debtor_total_amount                     ");
		sbQuery.append(" from slip_detail                                            ");
		sbQuery.append(" where debtor_credit_type = 'D'                              ");
		sbQuery.append(" group by account_type, debtor_credit_type) B                ");
		sbQuery.append(" on (A.account_type = B.account_type)                        ");
		sbQuery.append(" left outer join (                                           ");
		sbQuery.append(" select account_type                                         ");
		sbQuery.append(" , debtor_credit_type                                        ");
		sbQuery.append(" , sum(total_amount) credit_total_amount                     ");
		sbQuery.append(" from slip_detail                                            ");
		sbQuery.append(" where debtor_credit_type = 'C'                              ");
		sbQuery.append(" group by account_type, debtor_credit_type                   ");
		sbQuery.append(" ) C                                                         ");
		sbQuery.append(" on (A.account_type = C.account_type)                        ");
		sbQuery.append(" where A.account_type not in ('00', '01','02')               ");

		DefaultTableModel list = this.transaction.selectToTableModel(sbQuery);
		
		return list;
	}	
	
	

}





/*
	public void execute2(SlipMaster slipMaster) throws EntException{
		
		//전표원장 등록
		insert(slipMaster);
		
		//전표금액내역 등록
		List<SlipAmountDetail> debtorAmounts = slipMaster.getDebtorAmounts();
		for(SlipAmountDetail debtorAmount : debtorAmounts){
			if(debtorAmount.getAmount().compareTo(BigDecimal.ZERO)==0) continue;
			insert(debtorAmount);
		}
		
		List<SlipAmountDetail> creditAmounts = slipMaster.getCreditAmounts();
		for(SlipAmountDetail creditAmount : creditAmounts){
			if(creditAmount.getAmount().compareTo(BigDecimal.ZERO)==0) continue;
			insert(creditAmount);
		}
		
		//전표명세 등록
		int nDebtorAmounts = debtorAmounts.size();
		int nCreditAmounts = creditAmounts.size();
		
		Logger.debug(nDebtorAmounts+", "+nCreditAmounts);
		
		String strDebtorAccountType = null;
		String strCreditAccountType = null;
		BigDecimal bdAmount = null;
		int nSequence = 0;
		
		for(int i=0;i<nDebtorAmounts;i++){
			
			SlipAmountDetail debtorAmount = debtorAmounts.get(i);
			BigDecimal bdDebtorAmount = debtorAmount.getClcAmount();
			
			if(bdDebtorAmount.compareTo(BigDecimal.ZERO)==0){
				continue;
			}
			
			for(int j=0;j<nCreditAmounts;j++){
				
				SlipAmountDetail creditAmount = creditAmounts.get(j);
				BigDecimal bdCreditAmount = creditAmount.getClcAmount();
				
				if(bdCreditAmount.compareTo(BigDecimal.ZERO)==0){
					continue;
				}
				
				//차변금액 >= 대변금액
				if(bdDebtorAmount.compareTo(bdCreditAmount) >= 0){
					strDebtorAccountType = debtorAmount.getAccountType();
					strCreditAccountType = creditAmount.getAccountType();
					bdAmount = bdCreditAmount;
					
					bdDebtorAmount = bdDebtorAmount.subtract(bdCreditAmount);
					bdCreditAmount = BigDecimal.ZERO;
				} 
				//차변금액 < 대변금액
				else {
					strDebtorAccountType = debtorAmount.getAccountType();
					strCreditAccountType = creditAmount.getAccountType();
					bdAmount = bdDebtorAmount;
					
					bdDebtorAmount = BigDecimal.ZERO;
					bdCreditAmount = bdCreditAmount.subtract(bdDebtorAmount);
				}
				
				SlipDetail slipDetail = new SlipDetail();
				slipDetail.setSlipNo(slipMaster.getSlipNo());
				slipDetail.setSlipSequence(slipMaster.getSlipSequence());
				slipDetail.setSequence(++nSequence);
				slipDetail.setDebtorAccountType(strDebtorAccountType);
				slipDetail.setCreditAccountType(strCreditAccountType);
				slipDetail.setAmount(bdAmount);
				slipDetail.setRegisterUserId(slipMaster.getRegisterUserId());
				slipDetail.setRegisterDateTime(slipMaster.getRegisterDateTime());
				slipDetail.setLastRegisterUserId(slipMaster.getLastRegisterUserId());
				slipDetail.setLastRegisterDateTime(slipMaster.getLastRegisterDateTime());
				
				insert(slipDetail);
			}
			
		}
	}
	
*/



/*
	public void insert(SlipDetail slipDetail) throws EntException {
				
		StringBuffer sb = new StringBuffer(1024);
		sb.append("INSERT INTO slip_detail");
		sb.append("( slip_no");
		sb.append(", slip_sequence");
		sb.append(", sequence");
		sb.append(", debtor_account_type");
		sb.append(", credit_account_type");
		sb.append(", amount");
		sb.append(", register_user_id");
		sb.append(", register_date_time");
		sb.append(", last_register_user_id");
		sb.append(", last_register_date_time");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(slipDetail.getSlipNo())).append(" , ");
		sb.append(slipDetail.getSlipSequence()).append(" , ");
		sb.append(slipDetail.getSequence()).append(" , ");
		sb.append(EntCommon.convertQuery(slipDetail.getDebtorAccountType())).append(" , ");
		sb.append(EntCommon.convertQuery(slipDetail.getCreditAccountType())).append(" , ");
		sb.append(slipDetail.getAmount()).append(" , ");
		sb.append(EntCommon.convertQuery(slipDetail.getRegisterUserId())).append(" , ");
		sb.append(EntCommon.convertQuery(slipDetail.getRegisterDateTime())).append(" , ");
		sb.append(EntCommon.convertQuery(slipDetail.getLastRegisterUserId())).append(" , ");
		sb.append(EntCommon.convertQuery(slipDetail.getLastRegisterDateTime())).append(")");

		this.entQuery.insert(sb.toString());
	}
*/	