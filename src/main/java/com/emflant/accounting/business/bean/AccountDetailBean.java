package com.emflant.accounting.business.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.business.remote.AccountdetailRemote;
import com.emflant.accounting.dto.screen.A03CreditCardPaymentMainInsert01DTO;
import com.emflant.accounting.dto.screen.A04DepositAccountMainInsert01DTO;
import com.emflant.accounting.dto.screen.A05WithdrawAccountMainInsert01DTO;
import com.emflant.accounting.dto.screen.A06CreditCardRepaymentMainInsert01DTO;
import com.emflant.accounting.dto.screen.A06CreditCardRepaymentMainSelect01DTO;
import com.emflant.accounting.dto.screen.A10CashInOutTradeMainInsert01DTO;
import com.emflant.accounting.dto.table.AccountDetailDTO;
import com.emflant.accounting.dto.table.AccountMasterDTO;
import com.emflant.accounting.dto.table.SlipMasterDTO;
import com.emflant.accounting.orm.AccountDetailQuery;
import com.emflant.common.EntCommon;
import com.emflant.common.EntException;
import com.emflant.common.EntTransaction;
import com.emflant.url.HttpUrlConnection;

public class AccountDetailBean implements AccountdetailRemote {
	private EntTransaction transaction;
	private AccountDetailQuery accountDetailQuery;

	
	public AccountDetailBean(EntTransaction transaction){
		this.transaction = transaction;
		this.accountDetailQuery = new AccountDetailQuery();
	}
	
	
	public void select(String strAccountNo) throws EntException{
		
		try {
			StringBuilder query = this.accountDetailQuery.select(strAccountNo);
			DefaultTableModel list = this.transaction.selectToTableModel(query);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	

	public AccountDetailDTO selectAccountDetailByKey(String account, int tradeSeqeunce) throws EntException {
		
		StringBuilder query = this.accountDetailQuery.selectAccountDetailByKey(account, tradeSeqeunce);
		List list = this.transaction.select(AccountDetailDTO.class, query);
		
		if(list.isEmpty()){
			throw new EntException("�ش� �ŷ������� �������� �ʽ��ϴ�.");
		}
		
		return (AccountDetailDTO)list.get(0);
	}	
	
	public AccountDetailDTO selectAccountDetailByRegisterDateTime(String registerUserId, String registerDateTime) throws EntException {
		
		StringBuilder query = this.accountDetailQuery.selectAccountDetailByRegisterDateTime(registerUserId, registerDateTime);
		List list = this.transaction.select(AccountDetailDTO.class, query);
		
		if(list.isEmpty()){
			throw new EntException("�ش� �ŷ������� �������� �ʽ��ϴ�.");
		}
		
		return (AccountDetailDTO)list.get(0);
	}	
	
	

	/**
	 * ���� �Ա� ���
	 * @param transaction
	 * @throws EntException
	 */
	public void deposit(A04DepositAccountMainInsert01DTO inputDTO) throws EntException{

		try {
			//A04DepositAccountMainInsert01DTO inputDTO = (A04DepositAccountMainInsert01DTO)transaction.getMethodParam();
			
			
			//���¿��� ��ȸ
			AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
			AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(inputDTO.getAccountNo());
			BigDecimal bdAfterBalance = accountMasterDTO.getBalance().add(inputDTO.getTotalAmount());
			
			//�����ŷ����ں��� �����ڷ� ����� �ŷ��Ұ�.
			if(accountMasterDTO.getLastTradeDate().compareTo(inputDTO.getReckonDate()) >= 0){
				throw new EntException("�����ŷ��Ϻ��� �������ڷ� �ŷ��Ұ� �մϴ�.");
			}
			
			//��ǥ��ȣ ä��
			SlipBean slipBean = new SlipBean(this.transaction);
			String strSlipNo = slipBean.getSlipNo();
			
			//�ش����ϱ����� ������ �ŷ������� ��ȸ�Ѵ�.
			AccountDetailDTO accountDetailOfReckonDate = 
				getLastAccountDetailOfReckonDate(inputDTO.getAccountNo(), inputDTO.getReckonDate());
			
			BigDecimal bdAfterReckonBalance = accountDetailOfReckonDate.getAfterReckonBalance()
					.add(inputDTO.getTotalAmount());
			
			//�ŷ����� ���
			AccountDetailDTO accountDetail = new AccountDetailDTO(this.transaction);
			accountDetail.setAccountDetailDTO(inputDTO);
			
			accountDetail.setSlipNo(strSlipNo);
			accountDetail.setSlipSeq(transaction.getSlipSeq());
			accountDetail.setTradeSequence(accountMasterDTO.getLastTradeSequence()+1);
			accountDetail.setAfterTradeBalance(bdAfterBalance);
			accountDetail.setAfterReckonBalance(bdAfterReckonBalance);
			this.transaction.insert(accountDetail);
			
			//�ŷ����� ����
			updateAccountDetailByReckonDate(accountDetail);
			
			//���¿��� ����
			AccountMasterDTO updateAccountMaster = new AccountMasterDTO(this.transaction);
			updateAccountMaster.setAccountNo(accountDetail.getAccountNo());
			updateAccountMaster.setBalance(accountDetail.getAfterTradeBalance());
			updateAccountMaster.setLastTradeSequence(accountDetail.getTradeSequence());
			accountMasterBean.update(updateAccountMaster);
			
			
			//ȸ��ó��
			SlipMasterDTO slipMaster = new SlipMasterDTO(this.transaction);
			slipMaster.setSlipNo(strSlipNo);
			slipMaster.setSlipSequence(transaction.getSlipSeq());
			slipMaster.setSlipAmount(accountDetail.getTradeAmount());
			slipMaster.setDebtorAmount(accountDetail.getTradeAmount());
			slipMaster.setCreditAmount(BigDecimal.ZERO);
			
			//��ǥ����
			//D:���� �ڻ����(��������)
			slipMaster.addDebtorAmount(accountMasterDTO.getAccountType()
					, accountDetail.getTradeAmount(), accountDetail.getCashAmount());
			
			//ȸ��ó���Ѵ�.
			slipBean.execute(slipMaster);
			
			transaction.setSuccessMessage("���������� ��ϵǾ����ϴ�.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	


	/**
	 * ���� ��� ���
	 * @param transaction
	 * @throws EntException
	 */
	public void withdraw(A05WithdrawAccountMainInsert01DTO inputDTO) throws EntException{

		try {
			//A05WithdrawAccountMainInsert01DTO inputDTO = (A05WithdrawAccountMainInsert01DTO)transaction.getMethodParam();
			
			
			//���¿��� ��ȸ
			AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
			AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(inputDTO.getAccountNo());
			
			BigDecimal bdAfterBalance = accountMasterDTO.getBalance().subtract(inputDTO.getTotalAmount());
			
			if(bdAfterBalance.compareTo(BigDecimal.ZERO) == -1){
				throw new EntException("����� �� ���� �ݾ��Դϴ�.");
			}
			
			//�����ŷ����ں��� �����ڷ� ����� �ŷ��Ұ�.
			if(accountMasterDTO.getLastTradeDate().compareTo(inputDTO.getReckonDate()) >= 0){
				throw new EntException("�����ŷ��Ϻ��� �������ڷ� �ŷ��Ұ� �մϴ�.");
			}
			
			//��ǥ��ȣ ä��
			SlipBean slipBean = new SlipBean(this.transaction);
			String strSlipNo = slipBean.getSlipNo();
			
			
			//�ش����ϱ����� ������ �ŷ������� ��ȸ�Ѵ�.
			AccountDetailDTO accountDetailOfReckonDate = 
				getLastAccountDetailOfReckonDate(inputDTO.getAccountNo(), inputDTO.getReckonDate());
			
			BigDecimal bdAfterReckonBalance = accountDetailOfReckonDate.getAfterReckonBalance()
			.subtract(inputDTO.getTotalAmount());
			
			
			//�ŷ����� ���
			AccountDetailDTO accountDetail = new AccountDetailDTO(this.transaction);
			accountDetail.setAccountDetailDTO(inputDTO);
			accountDetail.setSlipNo(strSlipNo);
			accountDetail.setSlipSeq(transaction.getSlipSeq());
			accountDetail.setTradeSequence(accountMasterDTO.getLastTradeSequence()+1);
			accountDetail.setAfterTradeBalance(bdAfterBalance);
			accountDetail.setAfterReckonBalance(bdAfterReckonBalance);
			this.transaction.insert(accountDetail);
			
			//�ŷ����� ����
			updateAccountDetailByReckonDate(accountDetail);
			
			//���¿��� ����
			AccountMasterDTO updateAccountMaster = new AccountMasterDTO(this.transaction);
			updateAccountMaster.setAccountNo(accountDetail.getAccountNo());
			updateAccountMaster.setBalance(accountDetail.getAfterTradeBalance());
			updateAccountMaster.setLastTradeSequence(accountDetail.getTradeSequence());
			
			accountMasterBean.update(updateAccountMaster);
			
			
			//ȸ��ó��
			//��ǥ����
			SlipMasterDTO slipMaster = new SlipMasterDTO(this.transaction);
			slipMaster.setSlipNo(strSlipNo);
			slipMaster.setSlipSequence(transaction.getSlipSeq());
			slipMaster.setSlipAmount(accountDetail.getTradeAmount());
			slipMaster.setDebtorAmount(BigDecimal.ZERO);
			slipMaster.setCreditAmount(accountDetail.getTradeAmount());
			
			//��ǥ����
			//C:�뺯 �ڻ����(��������)
			slipMaster.addCreditAmount(accountMasterDTO.getAccountType(), accountDetail.getTradeAmount(), accountDetail.getCashAmount());
			
			//ȸ��ó���Ѵ�.
			slipBean.execute(slipMaster);
			
			transaction.setSuccessMessage("���������� ��ϵǾ����ϴ�.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	
	/**
	 * �ſ�ī�� ���� ó��
	 * @param transaction
	 * @throws EntException
	 */
	public void creditCardPayment(A03CreditCardPaymentMainInsert01DTO inputDTO) throws EntException  {
		
		try {
			
			//A03CreditCardPaymentMainInsert01DTO inputDTO = (A03CreditCardPaymentMainInsert01DTO)transaction.getMethodParam();
			
			//���¿��� ��ȸ
			AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
			AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(inputDTO.getAccountNo());
			
			//ȸ��ó��
			SlipMasterDTO slipMasterDTO = new SlipMasterDTO(this.transaction);
			slipMasterDTO.setSlipMasterDTO(inputDTO);
			SlipBean slipBean = new SlipBean(this.transaction);
			slipBean.execute(slipMasterDTO);
			
			
			//�����������������ŷ��� �����ϰ� �Ѵ�.
			if(accountMasterDTO.getLastTradeDate().compareTo(inputDTO.getReckonDate()) >= 0){
				throw new EntException("������ ������¥�� �ŷ��� �� �����ϴ�.");
			}
			
			//�ش����ϱ����� ������ �ŷ������� ��ȸ�Ѵ�.
			AccountDetailDTO accountDetailOfReckonDate = 
				getLastAccountDetailOfReckonDate(inputDTO.getAccountNo(), inputDTO.getReckonDate());
			
			BigDecimal bdAfterReckonBalance = accountDetailOfReckonDate.getAfterReckonBalance()
				.subtract(inputDTO.getTotalAmount());
			
			BigDecimal bdAfterBalance = accountMasterDTO.getBalance().subtract(inputDTO.getTotalAmount());
			
			
			//�ŷ����� ���
			AccountDetailDTO accountNewDetail = new AccountDetailDTO(this.transaction);
			accountNewDetail.setAccountDetailDTO(inputDTO);
			accountNewDetail.setSlipNo(slipMasterDTO.getSlipNo());
			accountNewDetail.setSlipSeq(slipMasterDTO.getSlipSequence());
			accountNewDetail.setTradeSequence(accountMasterDTO.getLastTradeSequence()+1);
			accountNewDetail.setAfterTradeBalance(bdAfterBalance);
			accountNewDetail.setAfterReckonBalance(bdAfterReckonBalance);
			this.transaction.insert(accountNewDetail);
			
			
			//�ŷ����� ����
			updateAccountDetailByReckonDate(accountNewDetail);
			
			
						
			//���¿��� ����
			AccountMasterDTO updateAccountMaster = new AccountMasterDTO(this.transaction);
			updateAccountMaster.setBalance(accountNewDetail.getAfterTradeBalance());
			updateAccountMaster.setLastTradeSequence(accountNewDetail.getTradeSequence());
			updateAccountMaster.setAccountNo(accountNewDetail.getAccountNo());
			accountMasterBean.update(updateAccountMaster);
			
			
			
			transaction.setSuccessMessage("���������� ��ϵǾ����ϴ�.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	public void updateAccountDetailByReckonDate(AccountDetailDTO accountDetailDTO) throws EntException{

		StringBuilder sbQuery = new StringBuilder(1024);
		sbQuery.append(" select *  ");
		sbQuery.append(" from account_detail                  ");
		sbQuery.append(" where account_no =                           ");
		sbQuery.append(EntCommon.convertQuery(accountDetailDTO.getAccountNo()));
		sbQuery.append(" and reckon_date > ");
		sbQuery.append(EntCommon.convertQuery(accountDetailDTO.getReckonDate()));
		//sbQuery.append(" and trade_sequence <> ");
		//sbQuery.append(accountDetailDTO.getTradeSequence());
		sbQuery.append(" order by reckon_date, trade_sequence ");
		
		List lResult2 = this.transaction.select(AccountDetailDTO.class, sbQuery);
		
		for(int i=0;i<lResult2.size();i++){
			
			AccountDetailDTO tempDTO = (AccountDetailDTO)lResult2.get(i);
			sbQuery = new StringBuilder(1024);
			sbQuery.append(" update account_detail  ");
			sbQuery.append(" set after_reckon_balance =     ");
			
			if(accountDetailDTO.getInoutType().equals("2")){
				sbQuery.append(tempDTO.getAfterReckonBalance().subtract(accountDetailDTO.getTradeAmount()));
			} else {
				sbQuery.append(tempDTO.getAfterReckonBalance().add(accountDetailDTO.getTradeAmount()));
			}
			
			sbQuery.append("   , last_register_user_id =    ");
			sbQuery.append(EntCommon.convertQuery(accountDetailDTO.getLastRegisterUserId()));
			sbQuery.append("   , last_register_date_time =       ");
			sbQuery.append(EntCommon.convertQuery(accountDetailDTO.getLastRegisterDateTime()));
			sbQuery.append(" where account_no =             ");
			sbQuery.append(EntCommon.convertQuery(accountDetailDTO.getAccountNo()));
			sbQuery.append(" and trade_sequence =           ");
			sbQuery.append(tempDTO.getTradeSequence());
			this.transaction.update(sbQuery);
		}
		
	}
	
	public AccountDetailDTO getLastAccountDetailOfReckonDate(String strAccountNo, String strReckonDate) throws EntException{
		
		//�ش� ������� �������ŷ��� �ŷ��� �ܾ��� ���Ѵ�.
		StringBuilder sbQuery = new StringBuilder(1024);
		
		sbQuery.append(" select X.*                                   ");
		sbQuery.append(" from account_detail X                ");
		sbQuery.append(" inner join                                   ");
		sbQuery.append(" (select B.account_no, B.reckon_date          ");
		sbQuery.append(" , max(B.trade_sequence) max_trade_sequence   ");
		sbQuery.append(" from (select account_no                      ");
		sbQuery.append(" , max(reckon_date) max_reckon_date           ");
		sbQuery.append(" from account_detail                  ");
		sbQuery.append(" where account_no =                           ");
		sbQuery.append(EntCommon.convertQuery(strAccountNo));
		sbQuery.append(" and reckon_date <=                           ");
		sbQuery.append(EntCommon.convertQuery(strReckonDate));
		sbQuery.append(" and cancel_type = '0'                        ");
		sbQuery.append(" ) A inner join account_detail B      ");
		sbQuery.append(" on (A.account_no = B.account_no              ");
		sbQuery.append(" and A.max_reckon_date = B.reckon_date)       ");
		sbQuery.append(" group by B.account_no, B.reckon_date         ");
		sbQuery.append(" ) Y                                          ");
		sbQuery.append(" on (X.account_no = Y.account_no              ");
		sbQuery.append(" and X.trade_sequence = Y.max_trade_sequence) ");
		
		List lResult = this.transaction.select(AccountDetailDTO.class, sbQuery);
		
		if(lResult.isEmpty()){
			throw new EntException("��� �ϳ��� �ŷ��� �����ؾ� �մϴ�.<br/> �����͸� Ȯ���ϼ���.");
		}

		return (AccountDetailDTO)lResult.get(0);
	}

	
	/**
	 * �ſ�ī�� �ϰ����� ó��
	 * @param transaction
	 * @throws EntException
	 */
	public void httpUrlUpdateMoneyStatus(String key) throws EntException  {
		
		try {
			//String strKey = (String)transaction.getMethodParam();
			
			HttpUrlConnection httpUrl = new HttpUrlConnection();
			httpUrl.updateOneMoney(key, "1");
			
			transaction.setSuccessMessage("���������� ��ϵǾ����ϴ�.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	

	/**
	 * �ſ�ī�� �ϰ����� ó��
	 * @param transaction
	 * @throws EntException
	 */
	public void httpUrlUpdateMoneyStatus2(String key) throws EntException  {
		
		try {
			//String strKey = (String)transaction.getMethodParam();
			
			HttpUrlConnection httpUrl = new HttpUrlConnection();
			httpUrl.updateOneMoney(key, "0");
			
			transaction.setSuccessMessage("���������� ��ϵǾ����ϴ�.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	

	/**
	 * �ſ�ī�� �̿��� ����(��ȯ) ó��
	 * @param transaction
	 * @throws EntException
	 */
	public void creditCardRepayment(A06CreditCardRepaymentMainInsert01DTO inputDTO) throws EntException  {
		
		try {
			//��ǥ��ȣ ä��
			SlipBean slipBean = new SlipBean(this.transaction);
			String strSlipNo = slipBean.getSlipNo();
			
			//�Է°� ����
			//A06CreditCardRepaymentMainInsert01DTO inputDTO = (A06CreditCardRepaymentMainInsert01DTO)transaction.getMethodParam();
			
			StringBuilder sbQuery = new StringBuilder(256);
			sbQuery.append(" select CONCAT(                  ");
			sbQuery.append(EntCommon.convertQuery(inputDTO.getRepaymentYm()));
			sbQuery.append(" , payment_end_day)              ");
			sbQuery.append(" from creditcard_payment ");
			sbQuery.append(" where account_no =              ");
			sbQuery.append(EntCommon.convertQuery(inputDTO.getAccountNo()));
			
			String strMinRepaymentDate = (String)this.transaction.selectToTableModel(sbQuery).getValueAt(0, 0);
			
			if(strMinRepaymentDate.compareTo(inputDTO.getReckonDate()) >= 0){
				throw new EntException(strMinRepaymentDate+"���� ���Ŀ� �̿��� ���� �����մϴ�.");
			}
			
			//���¿��� ��ȸ
			AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
			AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(inputDTO.getAccountNo());
			BigDecimal bdAfterBalance = accountMasterDTO.getBalance().add(inputDTO.getTotalAmount());
			
			//�ش����ϱ����� ������ �ŷ������� ��ȸ�Ѵ�.
			AccountDetailDTO accountDetailOfReckonDate = 
				getLastAccountDetailOfReckonDate(inputDTO.getAccountNo(), inputDTO.getReckonDate());
			
			BigDecimal bdAfterReckonBalance = accountDetailOfReckonDate.getAfterReckonBalance()
				.add(inputDTO.getTotalAmount());
			
			//�ŷ����� ���
			AccountDetailDTO accountDetail = new AccountDetailDTO(this.transaction);
			accountDetail.setAccountDetailDTO(inputDTO);
			accountDetail.setSlipNo(strSlipNo);
			accountDetail.setSlipSeq(transaction.getSlipSeq());
			accountDetail.setTradeSequence(accountMasterDTO.getLastTradeSequence()+1);
			accountDetail.setAfterTradeBalance(bdAfterBalance);
			accountDetail.setAfterReckonBalance(accountDetailOfReckonDate.getAfterReckonBalance());
			transaction.insert(accountDetail);
			
			//�ŷ����� ����!!
			updateAccountDetailByReckonDate(accountDetail);
			
			
			//���¿����� �����Ѵ�.
			AccountMasterDTO updateAccountMaster = new AccountMasterDTO(this.transaction);
			updateAccountMaster.setAccountNo(accountDetail.getAccountNo());
			updateAccountMaster.setBalance(accountDetail.getAfterTradeBalance());
			updateAccountMaster.setLastTradeSequence(accountDetail.getTradeSequence());
			updateAccountMaster.setLastTradeDate(strMinRepaymentDate);
			
			accountMasterBean.updateLastTradeDate(updateAccountMaster);


			//ȸ��ó��
			//��ǥ����
			SlipMasterDTO slipMaster = new SlipMasterDTO(this.transaction);
			slipMaster.setSlipNo(strSlipNo);
			slipMaster.setSlipSequence(transaction.getSlipSeq());
			slipMaster.setSlipAmount(inputDTO.getTotalAmount());
			slipMaster.setDebtorAmount(inputDTO.getTotalAmount());
			slipMaster.setCreditAmount(BigDecimal.ZERO);
			
			
			//��ǥ����
			//C:�뺯 ��ä����(�ſ�ī��)
			slipMaster.addDebtorAmount(accountMasterDTO.getAccountType()
					, inputDTO.getTotalAmount(), inputDTO.getCashAmount());
			
			
			//ȸ��ó���Ѵ�.
			slipBean.execute(slipMaster);
			
			transaction.setSuccessMessage("���������� ��ϵǾ����ϴ�.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	

	/**
	 * �ſ�ī�� ����(��ȯ) ��� ��ȸ
	 * @param transaction
	 * @throws EntException
	 */
	public void selectCreditCardRepayment(A06CreditCardRepaymentMainSelect01DTO inputDTO) throws EntException  {
		
		try {
			
			//�Է°� ����
			//A06CreditCardRepaymentMainSelect01DTO inputDTO = (A06CreditCardRepaymentMainSelect01DTO)transaction.getMethodParam();
			
			//���¿��� ��ȸ
			AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
			AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(inputDTO.getAccountNo());
			
			StringBuilder sbQuery = new StringBuilder(1024);
			sbQuery.append("SELECT (SELECT code_name FROM code_detail WHERE code_type = '01' and code = trade_type) as trade_type_name ");
			sbQuery.append(", (SELECT code_name FROM code_detail WHERE code_type = '02' and code = inout_type) as inout_type_name ");
			sbQuery.append(", (SELECT code_name FROM code_detail WHERE code_type = '11' and code = cancel_type) as cancel_type_name ");
			sbQuery.append(", DATE_FORMAT(reckon_date,'%Y-%m-%d') AS format_reckon_date, format(trade_amount,0) as format_trade_amount, format(after_trade_balance,0) as format_after_balance, format(after_reckon_balance,0) as format_after_reckon_balance, trade_amount, trade_sequence, remarks, trade_date, DATE_FORMAT(trade_date,'%Y-%m-%d') AS format_trade_date ");
			sbQuery.append(" FROM account_detail      ");
			sbQuery.append(" where account_no =                        ");
			sbQuery.append(EntCommon.convertQuery(inputDTO.getAccountNo()));
			sbQuery.append(" and reckon_date >                         ");
			sbQuery.append(EntCommon.convertQuery(accountMasterDTO.getLastTradeDate()));
			sbQuery.append(" and reckon_date <= (select CONCAT(        ");
			sbQuery.append(EntCommon.convertQuery(inputDTO.getRepaymentYm()));
			sbQuery.append(" , payment_end_day) from creditcard_payment ");
			sbQuery.append(" where account_no =                        ");
			sbQuery.append(EntCommon.convertQuery(inputDTO.getAccountNo()));
			sbQuery.append(") and trade_type = '04'                     ");
			//sbQuery.append(" and cancel_type = '0'                     ");
			sbQuery.append(" ORDER BY reckon_date, trade_sequence");
			DefaultTableModel list = this.transaction.selectToTableModel(sbQuery);
			
			

			sbQuery = new StringBuilder(1024);
			sbQuery.append("SELECT ifnull(sum(trade_amount),0) AS sum_trade_amount ");
			sbQuery.append(" FROM account_detail      ");
			sbQuery.append(" where account_no =                        ");
			sbQuery.append(EntCommon.convertQuery(inputDTO.getAccountNo()));
			sbQuery.append(" and reckon_date >                         ");
			sbQuery.append(EntCommon.convertQuery(accountMasterDTO.getLastTradeDate()));
			sbQuery.append(" and reckon_date <= (select CONCAT(        ");
			sbQuery.append(EntCommon.convertQuery(inputDTO.getRepaymentYm()));
			sbQuery.append(" , payment_end_day) from creditcard_payment ");
			sbQuery.append(" where account_no =                        ");
			sbQuery.append(EntCommon.convertQuery(inputDTO.getAccountNo()));
			sbQuery.append(") and trade_type = '04'                     ");
			sbQuery.append(" and cancel_type = '0'                     ");
			sbQuery.append(" ORDER BY reckon_date, trade_sequence");
			DefaultTableModel list2 = this.transaction.selectToTableModel(sbQuery);
			
			transaction.addResult(list);
			transaction.addResult((String)list2.getValueAt(0, 0));
			//transaction.setSuccessMessage("���������� ��ϵǾ����ϴ�.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	
	/**
	 * ���� ����� ���
	 * @param inputDTO
	 * @throws EntException
	 */
	public void cashInOutTrade(A10CashInOutTradeMainInsert01DTO inputDTO) throws EntException {
		try {

			SlipMasterDTO slipMaster = new SlipMasterDTO();
			slipMaster.setDefaultField(transaction);
			slipMaster.setSlipAmount(inputDTO.getTotalAmount());
			
			//���Ͱ����̸� �뺯�� �ŷ��ݾ� ����
			if(inputDTO.getInOutType().equals("1")){
				slipMaster.setDebtorAmount(BigDecimal.ZERO);
				slipMaster.setCreditAmount(inputDTO.getTotalAmount());
				slipMaster.addCreditAmount(inputDTO.getTradeType()
						, inputDTO.getTotalAmount(), inputDTO.getTotalAmount());
				
			} 
			//�������̸� ������ �ŷ��ݾ� ����
			else {
				slipMaster.setDebtorAmount(inputDTO.getTotalAmount());
				slipMaster.setCreditAmount(BigDecimal.ZERO);
				slipMaster.addDebtorAmount(inputDTO.getTradeType()
						, inputDTO.getTotalAmount(), inputDTO.getTotalAmount());
				
			}

			slipMaster.setUserId(this.transaction.getUserId());
			slipMaster.setRegisterUserId(this.transaction.getUserId());
			slipMaster.setLastRegisterUserId(this.transaction.getUserId());
			

			SlipBean slipBean = new SlipBean(this.transaction);
			slipBean.execute(slipMaster);
			
			this.transaction.setSuccessMessage("���������� ��ϵǾ����ϴ�.");
			
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
}
