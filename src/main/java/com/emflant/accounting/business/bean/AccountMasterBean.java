package com.emflant.accounting.business.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.business.remote.AccountmasterRemote;
import com.emflant.accounting.dto.screen.A01AccountMasterMainInsert01DTO;
import com.emflant.accounting.dto.table.AccountDetailDTO;
import com.emflant.accounting.dto.table.AccountMasterDTO;
import com.emflant.accounting.dto.table.SlipMasterDTO;
import com.emflant.common.EntCommon;
import com.emflant.common.EntException;
import com.emflant.common.EntHashList;
import com.emflant.common.EntLogger;
import com.emflant.common.EntTransaction;

public class AccountMasterBean implements AccountmasterRemote {
	private EntTransaction transaction;
	public AccountMasterBean(EntTransaction transaction){
		this.transaction = transaction;
	}
	
	public void selectByPK(String accountNo) throws EntException{
		
		try {
			AccountMasterDTO accountMaster = selectByAccountNo(accountNo);
			transaction.addResult(accountMaster);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	
	public AccountMasterDTO selectByAccountNo(String accountNo) throws EntException{
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT * ");
		sb.append("FROM account_master WHERE account_no = ");
		sb.append(EntCommon.convertQuery(accountNo));
		
		List result = this.transaction.select(AccountMasterDTO.class, sb);

		if(result.isEmpty()){
			throw new EntException("해당계좌번호가 존재하지 않습니다.");
		}
		
		return (AccountMasterDTO)result.get(0);
	}
	
	public void selectEntHashListByUserId(String userId) throws EntException{
		
		try {
			//String strUserId = (String)transaction.getMethodParam();
			StringBuilder sb = new StringBuilder(1024);
			sb.append("SELECT account_no AS code, ");
			sb.append("CONCAT('[',(SELECT account_type_name FROM account A ");
			sb.append("WHERE A.account_type = T.account_type)");
			sb.append(", '] ', IFNULL(remarks,'')) AS code_name ");
			sb.append("FROM account_master T WHERE user_id = ");
			sb.append(EntCommon.convertQuery(userId));
			
			
			EntHashList ehlResult = this.transaction.selectCodeToHashMap(sb);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	

	/**
	 * 출금가능계좌조회
	 * @param transaction
	 * @throws EntException
	 */
	public void selectEntHashWithdrawListByUserId(String userId) throws EntException{
		
		try {
			EntHashList ehlResult = queryEntHashWithdrawListByUserId(userId);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	
	public EntHashList queryEntHashWithdrawListByUserId(String userId) throws EntException {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT account_no AS code, ");
		sb.append("CONCAT('[',(SELECT A.account_type_name FROM account A ");
		sb.append("WHERE A.account_type = T.account_type)");
		sb.append(", '] ', IFNULL(remarks,'')) AS code_name ");
		sb.append("FROM account_master T INNER JOIN account S ");
		sb.append("ON (T.account_type = S.account_type and S.bs_pl_detail_type = '1' ");
		sb.append("and S.withdraw_yn = 'Y') WHERE T.user_id = ");	
		sb.append(EntCommon.convertQuery(userId));
		
		return this.transaction.selectCodeToHashMap(sb);
	}
	
	

	/**
	 * 입금가능계좌조회
	 * @param transaction
	 * @throws EntException
	 */
	public void selectEntHashDepositListByUserId(String userId) throws EntException{
		
		try {
			EntHashList ehlResult = queryEntHashDepositListByUserId(userId);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	
	public EntHashList queryEntHashDepositListByUserId(String userId) throws EntException {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT account_no AS code, ");
		sb.append("CONCAT('[',(SELECT A.account_type_name FROM account A ");
		sb.append("WHERE A.account_type = T.account_type)");
		sb.append(", '] ', IFNULL(remarks,'')) AS code_name ");
		sb.append("FROM account_master T INNER JOIN account S ");
		sb.append("ON (T.account_type = S.account_type and S.bs_pl_detail_type = '1' ");
		sb.append("and S.deposit_yn = 'Y') WHERE T.user_id = ");	
		sb.append(EntCommon.convertQuery(userId));
		
		return this.transaction.selectCodeToHashMap(sb);
	}
	
	
	
	/**
	 * 해당 사용자의 신용카드, 체크카드내역을 조회한다.
	 * @param userId
	 * @return
	 * @throws EntException
	 */
	public void selectEntHashCreditCardListByUserId() throws EntException{
		
		try {
			
			String strUserId = this.transaction.getUserId();
			EntHashList ehlResult = queryEntHashCreditCardListByUserId(strUserId);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	

	/**
	 * 해당 사용자의 신용카드, 체크카드내역을 조회한다.
	 * @param userId
	 * @return
	 * @throws EntException
	 */
	public EntHashList queryEntHashCreditCardListByUserId(String userId) throws EntException {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT account_no AS code, ");
		sb.append("CONCAT('[',(SELECT account_type_name FROM account A ");
		sb.append("WHERE A.account_type = T.account_type) ");
		sb.append(", '] ', IFNULL(remarks,'')) AS code_name ");
		sb.append("FROM account_master T WHERE user_id = ");
		sb.append(EntCommon.convertQuery(userId));
		sb.append(" AND account_type in ('21','22') ");
		
		return this.transaction.selectCodeToHashMap(sb);
	}
	

	/**
	 * 해당 사용자의 예금계좌 내역을 조회한다.
	 * @param userId
	 * @return
	 * @throws EntException
	 */
	public void selectEntHashAssetByUserId() throws EntException{
		
		try {
			String strUserId = (String)transaction.getMethodParam();
			EntHashList ehlResult = queryEntHashAssetByUserId(strUserId);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	

	/**
	 * 해당 사용자의 예금계좌 내역을 조회한다.
	 * @param userId
	 * @return
	 * @throws EntException
	 */
	public EntHashList queryEntHashAssetByUserId(String userId) throws EntException {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT CONCAT(account_type, LPAD(account_sequence,2,'0')) AS code, ");
		sb.append("CONCAT('[',(SELECT account_type_name FROM account A ");
		sb.append("WHERE A.account_type = T.account_type) ");
		sb.append(", '] ', IFNULL(remarks,'')) AS code_name ");
		sb.append("FROM account_master T WHERE user_id = ");
		sb.append(EntCommon.convertQuery(userId));
		sb.append(" AND account_type in ('02','03','04') ");
		
		return this.transaction.selectCodeToHashMap(sb);
	}
	
	/**
	 * 신규계좌번호 채번
	 * @param sequenceType
	 * @return
	 * @throws EntException
	 */
	public String createNewAccountNoByAccountType(String sequenceType) throws EntException{
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT IFNULL(MAX(sequence),0) + 1");
		sb.append(" FROM sequence_master WHERE sequence_type = ");
		sb.append(EntCommon.convertQuery(sequenceType));
		
		DefaultTableModel result = this.transaction.selectToTableModel(sb);
		String strNextSequence = result.getValueAt(0, 0).toString();
		
		 sb = new StringBuilder(1024);
		 sb.append("UPDATE SEQUENCE_MASTER SET sequence = ");
		 sb.append(strNextSequence);
		 sb.append(" WHERE sequence_type = ");
		 sb.append(EntCommon.convertQuery(sequenceType));
		
		this.transaction.update(sb);
		
		return String.format("%s%08d", sequenceType, Integer.parseInt(strNextSequence));
	}
	
	
	public void selectByUserId(String userId) throws EntException{
		
		try {

			StringBuilder sb = new StringBuilder(1024);
			sb.append("SELECT");
			sb.append(" (SELECT account_type_name FROM account A WHERE A.account_type = T.account_type) as account_type_nm ");
			sb.append(", (SELECT code_name FROM code_detail WHERE code_type = '04' and code = account_status) as account_status_nm ");
			sb.append(", user_id, account_type, account_status, format(balance,0) as format_balance, balance, remarks, new_date, DATE_FORMAT(new_date,'%Y-%m-%d') AS format_new_date ");
			sb.append("FROM account_master T WHERE user_id = ");
			sb.append(EntCommon.convertQuery(userId));
			sb.append(" UNION ALL ");
			sb.append("SELECT '총액', NULL, user_id, '99' AS account_type, NULL AS account_status");
			sb.append(", format(SUM(balance),0) as format_balance, SUM(balance) as balance, NULL, NULL, NULL ");
			sb.append(" FROM account_master WHERE user_id = ");
			sb.append(EntCommon.convertQuery(userId));
			sb.append(" GROUP BY user_id");
			
			DefaultTableModel list = this.transaction.selectToTableModel(sb);
			
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	

	
	/**
	 * 계좌신규등록
	 * @param transaction
	 * @throws EntException
	 */
	public void insert(A01AccountMasterMainInsert01DTO inputDTO) throws EntException{
		
		try {

			//A01AccountMasterMainInsert01DTO inputDTO = (A01AccountMasterMainInsert01DTO)transaction.getMethodParam();
			
			//입력값체크
			EntCommon.isNull(inputDTO);
			
			String strNewAccountNo = createNewAccountNoByAccountType(inputDTO.getAccountType());
			int nLastTradeSequence = 0;
			
			//거래내역 등록
			AccountDetailDTO accountDetailDTO = new AccountDetailDTO(this.transaction);
			accountDetailDTO.setAccountDetailDTO(inputDTO);
			accountDetailDTO.setAccountNo(strNewAccountNo);
			accountDetailDTO.setTradeSequence(++nLastTradeSequence);
			accountDetailDTO.setSlipNo(this.transaction.getSystemDate()+"0000000");
			accountDetailDTO.setSlipSeq(0);
			accountDetailDTO.setInoutType("0");
			accountDetailDTO.setTradeType("01");
			accountDetailDTO.setTradeAmount(BigDecimal.ZERO);
			accountDetailDTO.setCashAmount(BigDecimal.ZERO);
			accountDetailDTO.setNonCashAmount(BigDecimal.ZERO);
			accountDetailDTO.setAfterTradeBalance(BigDecimal.ZERO);
			accountDetailDTO.setAfterReckonBalance(BigDecimal.ZERO);
			accountDetailDTO.setRemarks("계좌신규");
			
			this.transaction.insert(accountDetailDTO);
			
			if(inputDTO.getTotalAmount().compareTo(BigDecimal.ZERO) == 1){
				
				//전표번호 채번
				SlipBean slipBean = new SlipBean(this.transaction);
				String strSlipNo = slipBean.getSlipNo();
				int nSlipSeq = transaction.getSlipSeq();
				
				//전표생성
				SlipMasterDTO slipMasterDTO = new SlipMasterDTO(this.transaction);
				slipMasterDTO.setSlipMasterDTO(inputDTO);
				
				slipMasterDTO.setSlipNo(strSlipNo);
				slipMasterDTO.setSlipSequence(nSlipSeq);
				
				//전표내역
				//C:차변 자산계정(보통예금)
				slipMasterDTO.addDebtorAmount(inputDTO.getAccountType(), inputDTO.getTotalAmount(), inputDTO.getCashAmount());
				
				//회계처리한다.
				slipBean.execute(slipMasterDTO);
				
				//입금거래내역 만든다.
				AccountDetailDTO accountDetailDTO2 = new AccountDetailDTO(this.transaction);
				accountDetailDTO2.setAccountDetailDTO(inputDTO);
				
				accountDetailDTO2.setAccountNo(strNewAccountNo);
				accountDetailDTO2.setTradeSequence(++nLastTradeSequence);
				accountDetailDTO2.setSlipNo(strSlipNo);
				accountDetailDTO2.setSlipSeq(nSlipSeq);
				accountDetailDTO2.setInoutType("1");
				accountDetailDTO2.setTradeType("02");
				accountDetailDTO2.setTradeAmount(inputDTO.getTotalAmount());
				accountDetailDTO2.setCashAmount(inputDTO.getCashAmount());
				accountDetailDTO2.setNonCashAmount(inputDTO.getTotalAmount().subtract(inputDTO.getCashAmount()));
				accountDetailDTO2.setAfterTradeBalance(inputDTO.getTotalAmount());
				accountDetailDTO2.setAfterReckonBalance(inputDTO.getTotalAmount());
				accountDetailDTO2.setRemarks("계좌신규");
				
				this.transaction.insert(accountDetailDTO2);
				
			}
			
			//계좌원장 등록
			AccountMasterDTO accountMasterDTO = new AccountMasterDTO(this.transaction);
			accountMasterDTO.setAccountMasterDTO(inputDTO);
			accountMasterDTO.setAccountNo(strNewAccountNo);
			accountMasterDTO.setLastTradeSequence(nLastTradeSequence);
			transaction.insert(accountMasterDTO);
			
			transaction.setSuccessMessage("정상적으로 등록 되었습니다.");
			
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
		

	}	

	public void update(AccountMasterDTO accountMaster) throws EntException  {
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("UPDATE account_master ");
		sb.append("SET balance = ");
		sb.append(accountMaster.getBalance());
		sb.append(", last_trade_sequence = ");
		sb.append(accountMaster.getLastTradeSequence());
		sb.append(", last_register_user_id = ");
		sb.append(EntCommon.convertQuery(accountMaster.getLastRegisterUserId()));
		sb.append(", last_register_date_time = ");
		sb.append(EntCommon.convertQuery(accountMaster.getLastRegisterDateTime()));
		sb.append(" WHERE account_no = ");
		sb.append(EntCommon.convertQuery(accountMaster.getAccountNo()));
		int result = transaction.update(sb);
		EntLogger.debug(result + "건 수정했습니다.");
	}

	
	public void updateLastTradeDate(AccountMasterDTO accountMaster) throws EntException  {
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("UPDATE account_master ");
		sb.append("SET balance = ");
		sb.append(accountMaster.getBalance());
		sb.append(", last_trade_sequence = ");
		sb.append(accountMaster.getLastTradeSequence());
		sb.append(", last_trade_date = ");
		sb.append(accountMaster.getLastTradeDate());
		sb.append(", last_register_user_id = ");
		sb.append(EntCommon.convertQuery(accountMaster.getLastRegisterUserId()));
		sb.append(", last_register_date_time = ");
		sb.append(EntCommon.convertQuery(accountMaster.getLastRegisterDateTime()));
		sb.append(" WHERE account_no = ");
		sb.append(EntCommon.convertQuery(accountMaster.getAccountNo()));
		int result = this.transaction.update(sb);
		EntLogger.debug(result + "건 수정했습니다.");
	}
}
