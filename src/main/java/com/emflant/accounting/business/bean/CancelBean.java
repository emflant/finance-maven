package com.emflant.accounting.business.bean;

import java.math.BigDecimal;
import java.util.List;

import com.emflant.accounting.business.remote.CancelRemote;
import com.emflant.accounting.dto.screen.A09ReverseTransactionMainInsert01DTO;
import com.emflant.accounting.dto.screen.A09ReverseTransactionMainSelect01DTO;
import com.emflant.accounting.dto.table.AccountDetailDTO;
import com.emflant.accounting.dto.table.AccountMasterDTO;
import com.emflant.common.EntCommon;
import com.emflant.common.EntDate;
import com.emflant.common.EntException;
import com.emflant.common.EntTransaction;
import com.emflant.common.EntLogger;

public class CancelBean implements CancelRemote {
	private EntTransaction transaction;
	
	private final String ACCOUNT_DEPOSIT = "02";		//입금
	private final String ACCOUNT_WITHDRAW = "03";		//출금
	private final String CREDITCARD_PAYMENT = "04";		//승인
	private final String CREDITCARD_REPAYMENT = "05";	//결제
	
	public CancelBean(EntTransaction transaction){
		this.transaction = transaction;
	}
	


	public void selectLinkTransactions(A09ReverseTransactionMainSelect01DTO inputDTO) throws EntException {
		try {
			
			//계정처리
			//A09ReverseTransactionMainSelect01DTO inputDTO = (A09ReverseTransactionMainSelect01DTO)transaction.getMethodParam();
			
			//거래내역 조회
			AccountDetailBean accountDetailBean = new AccountDetailBean(this.transaction);
			AccountDetailDTO originalAccountDetailDTO = accountDetailBean.selectAccountDetailByKey(
						inputDTO.getAccountNo(), inputDTO.getTradeSequence());


			
			StringBuilder sbQuery = new StringBuilder(512);

			sbQuery.append(" select A.*, C.bean_type, C.method_type, C.cancel_trade_code ");
			sbQuery.append(" from transaction_log_detail A                       ");
			sbQuery.append(" inner join (select user_id, system_date_time                ");
			sbQuery.append(" from transaction_log_detail                         ");
			sbQuery.append(" where user_id =                                             ");
			sbQuery.append(EntCommon.convertQuery(this.transaction.getUserId()));
			sbQuery.append(" and start_date_time =                                       ");
			sbQuery.append(EntCommon.convertQuery(originalAccountDetailDTO.getRegisterDateTime()));
			sbQuery.append(" ) B on (A.user_id = B.user_id                               ");
			sbQuery.append(" and A.system_date_time = B.system_date_time)                ");
			sbQuery.append(" left outer join trade_master C                      ");
			sbQuery.append(" on (A.trade_code = C.trade_code)                            ");
			sbQuery.append(" left outer join trade_master D                      ");
			sbQuery.append(" on (C.cancel_trade_code = D.trade_code)                     ");

			
			this.transaction.addResult(this.transaction.selectToTableModel(sbQuery));
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	public void cancel(A09ReverseTransactionMainInsert01DTO inputDTO) throws EntException {
		try {
			
			//계정처리
			//A09ReverseTransactionMainInsert01DTO inputDTO = (A09ReverseTransactionMainInsert01DTO)transaction.getMethodParam();
			
			//거래내역 조회
			AccountDetailBean accountDetailBean = new AccountDetailBean(this.transaction);
			AccountDetailDTO originalAccountDetailDTO = accountDetailBean.selectAccountDetailByRegisterDateTime(
						inputDTO.getRegisterUserId(), inputDTO.getRegisterDateTime());
			
			if(!originalAccountDetailDTO.getCancelType().equals("0")){
				throw new EntException("정상거래만 취소/정정이 가능합니다.");
			}
			
			
			if(originalAccountDetailDTO.getTradeType().equals(CREDITCARD_PAYMENT)){
				cancelCreditcardPayment(inputDTO, originalAccountDetailDTO);
			} 
			else if(originalAccountDetailDTO.getTradeType().equals(ACCOUNT_DEPOSIT)){
				cancelAccountDeposit(inputDTO, originalAccountDetailDTO);
			}
			else if(originalAccountDetailDTO.getTradeType().equals(ACCOUNT_WITHDRAW)){
				cancelAccountWithdraw(inputDTO, originalAccountDetailDTO);
			}
			else if(originalAccountDetailDTO.getTradeType().equals(CREDITCARD_REPAYMENT)){
				cancelCreditCardRepayment(inputDTO, originalAccountDetailDTO);
			}
			else {
				throw new EntException("취소가 불가능한 거래유형입니다.");
			}
			
			
			transaction.setSuccessMessage("정상적으로 취소되었습니다.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	public void cancelCreditcardPayment(A09ReverseTransactionMainInsert01DTO inputDTO
			, AccountDetailDTO originalAccountDetailDTO) throws EntException{
		
		//회계처리.
		SlipBean slipBean = new SlipBean(this.transaction);
		slipBean.cancel(originalAccountDetailDTO.getSlipNo(), originalAccountDetailDTO.getSlipSeq());
		
		//계좌원장 조회
		AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
		AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(originalAccountDetailDTO.getAccountNo());
		
		//최종거래일자보다 전일자 거래는 취소 불가.
		if(accountMasterDTO.getLastTradeDate().compareTo(originalAccountDetailDTO.getReckonDate()) >= 0){
			throw new EntException("최종거래일보다 이전일자로 거래취소 불가 합니다.");
		}
		
		BigDecimal bdAfterBalance = accountMasterDTO.getBalance().add(originalAccountDetailDTO.getTradeAmount());
		
		//취소하는 기산일의 마지막거래의 소급잔액을 구해온다.
		AccountDetailBean accountDetailBean = new AccountDetailBean(this.transaction);
		AccountDetailDTO accountDetailOfReckonDate = accountDetailBean.getLastAccountDetailOfReckonDate(
				originalAccountDetailDTO.getAccountNo(),
				originalAccountDetailDTO.getReckonDate());

		//취소하려는 원거래의 거래금액을 더한다.
		BigDecimal bdAfterReckonBalance = accountDetailOfReckonDate.getAfterReckonBalance()
					.add(originalAccountDetailDTO.getTradeAmount());
		
		//새거래내역 등록
		AccountDetailDTO newAccountDetailDTO = new AccountDetailDTO(this.transaction);
		newAccountDetailDTO.setAccountNo(originalAccountDetailDTO.getAccountNo());
		newAccountDetailDTO.setTradeSequence(accountMasterDTO.getLastTradeSequence()+1);
		newAccountDetailDTO.setSlipNo(transaction.getSlipNo());
		newAccountDetailDTO.setSlipSeq(transaction.getSlipSeq());
		newAccountDetailDTO.setReckonDate(originalAccountDetailDTO.getReckonDate());
		newAccountDetailDTO.setInoutType("1");		//입금
		newAccountDetailDTO.setTradeType(originalAccountDetailDTO.getTradeType());		//원거래와 동일하게 셋팅.
		newAccountDetailDTO.setCancelType("8");		//8:정정거래
		newAccountDetailDTO.setRelatedTradeSequence(originalAccountDetailDTO.getTradeSequence());
		newAccountDetailDTO.setTradeAmount(originalAccountDetailDTO.getTradeAmount());
		newAccountDetailDTO.setCashAmount(originalAccountDetailDTO.getCashAmount());
		newAccountDetailDTO.setNonCashAmount(originalAccountDetailDTO.getNonCashAmount());
		newAccountDetailDTO.setAfterTradeBalance(bdAfterBalance);
		newAccountDetailDTO.setAfterReckonBalance(bdAfterReckonBalance);
		//newAccountDetailDTO.setRemarks("신용카드승인거래취소");
		
		this.transaction.insert(newAccountDetailDTO);
		
		//기존거래내역 수정(소급잔액)
		accountDetailBean.updateAccountDetailByReckonDate(newAccountDetailDTO);
		
		//원거래내역 수정
		StringBuilder sbQuery = new StringBuilder(1024);
		sbQuery.append(" update account_detail ");
		sbQuery.append(" set cancel_type = '2'         ");
		sbQuery.append(" , related_trade_sequence =    ");
		sbQuery.append(newAccountDetailDTO.getTradeSequence());
		sbQuery.append(" where account_no =            ");
		sbQuery.append(EntCommon.convertQuery(originalAccountDetailDTO.getAccountNo()));
		sbQuery.append(" and trade_sequence =          ");
		sbQuery.append(originalAccountDetailDTO.getTradeSequence());
		this.transaction.update(sbQuery);
		
		
		//계좌원장 수정
		AccountMasterDTO updateAccountMaster = new AccountMasterDTO(this.transaction);
		updateAccountMaster.setBalance(newAccountDetailDTO.getAfterTradeBalance());
		updateAccountMaster.setLastTradeSequence(newAccountDetailDTO.getTradeSequence());
		updateAccountMaster.setLastRegisterUserId(newAccountDetailDTO.getLastRegisterUserId());
		updateAccountMaster.setLastRegisterDateTime(newAccountDetailDTO.getLastRegisterDateTime());
		updateAccountMaster.setAccountNo(newAccountDetailDTO.getAccountNo());
		accountMasterBean.update(updateAccountMaster);
		
	}
	

	/**
	 * 신용카드 이용대금 결제 취소
	 * @param inputDTO
	 * @param originalAccountDetailDTO
	 * @throws EntException
	 */
	public void cancelCreditCardRepayment(A09ReverseTransactionMainInsert01DTO inputDTO
			, AccountDetailDTO originalAccountDetailDTO) throws EntException{
		
		//회계처리.
		SlipBean slipBean = new SlipBean(this.transaction);
		slipBean.cancel(originalAccountDetailDTO.getSlipNo(), originalAccountDetailDTO.getSlipSeq());
		
		//계좌원장 조회
		AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
		AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(originalAccountDetailDTO.getAccountNo());
		
		//최종거래일자보다 전일자 거래는 취소 불가.
		if(accountMasterDTO.getLastTradeDate().compareTo(originalAccountDetailDTO.getReckonDate()) > 0){
			throw new EntException("최종거래일보다 이전일자로 거래취소 불가 합니다.");
		}
		
		
		BigDecimal bdAfterBalance = accountMasterDTO.getBalance().subtract(originalAccountDetailDTO.getTradeAmount());
		
		//취소하는 기산일의 마지막거래의 소급잔액을 구해온다.
		AccountDetailBean accountDetailBean = new AccountDetailBean(this.transaction);
		AccountDetailDTO accountDetailOfReckonDate = accountDetailBean.getLastAccountDetailOfReckonDate(
				originalAccountDetailDTO.getAccountNo(),
				originalAccountDetailDTO.getReckonDate());

		//취소하려는 원거래의 거래금액을 뺀다.
		BigDecimal bdAfterReckonBalance = accountDetailOfReckonDate.getAfterReckonBalance()
					.subtract(originalAccountDetailDTO.getTradeAmount());
		
		//새거래내역 등록
		AccountDetailDTO newAccountDetailDTO = new AccountDetailDTO(this.transaction);
		newAccountDetailDTO.setAccountNo(originalAccountDetailDTO.getAccountNo());
		newAccountDetailDTO.setTradeSequence(accountMasterDTO.getLastTradeSequence()+1);
		newAccountDetailDTO.setSlipNo(transaction.getSlipNo());
		newAccountDetailDTO.setSlipSeq(transaction.getSlipSeq());
		newAccountDetailDTO.setReckonDate(originalAccountDetailDTO.getReckonDate());
		newAccountDetailDTO.setInoutType("2");		//2:지급
		newAccountDetailDTO.setTradeType(originalAccountDetailDTO.getTradeType());		//원거래와 동일하게 셋팅.
		newAccountDetailDTO.setCancelType("8");		//8:정정거래
		newAccountDetailDTO.setRelatedTradeSequence(originalAccountDetailDTO.getTradeSequence());
		newAccountDetailDTO.setTradeAmount(originalAccountDetailDTO.getTradeAmount());
		newAccountDetailDTO.setCashAmount(originalAccountDetailDTO.getCashAmount());
		newAccountDetailDTO.setNonCashAmount(originalAccountDetailDTO.getNonCashAmount());
		newAccountDetailDTO.setAfterTradeBalance(bdAfterBalance);
		newAccountDetailDTO.setAfterReckonBalance(bdAfterReckonBalance);
		//newAccountDetailDTO.setRemarks("신용카드승인거래취소");
		
		this.transaction.insert(newAccountDetailDTO);
		
		//기존거래내역 수정(소급잔액)
		accountDetailBean.updateAccountDetailByReckonDate(newAccountDetailDTO);
		
		//원거래내역 수정
		StringBuilder sbQuery = new StringBuilder(1024);
		sbQuery.append(" update account_detail ");
		sbQuery.append(" set cancel_type = '2'         ");
		sbQuery.append(" , related_trade_sequence =    ");
		sbQuery.append(newAccountDetailDTO.getTradeSequence());
		sbQuery.append(" where account_no =            ");
		sbQuery.append(EntCommon.convertQuery(originalAccountDetailDTO.getAccountNo()));
		sbQuery.append(" and trade_sequence =          ");
		sbQuery.append(originalAccountDetailDTO.getTradeSequence());
		this.transaction.update(sbQuery);
		
		
/*		sbQuery = new StringBuilder(1024);
		sbQuery.append(" select max(reckon_date) reckon_date     ");
		sbQuery.append(" from account_detail             ");
		sbQuery.append(" where account_no =                      ");
		sbQuery.append(EntCommon.convertQuery(originalAccountDetailDTO.getAccountNo()));
		sbQuery.append(" and trade_type in ('01', '05')          ");
		sbQuery.append(" and cancel_type = '0'                   ");
		List<AccountDetailDTO> lMaxReckonDate = this.transaction.select(AccountDetailDTO.class, sbQuery.toString());*/
		
		String strBeforeLastTradeDate = EntDate.addMonth(accountMasterDTO.getLastTradeDate(), -1);
		EntLogger.debug("1:"+strBeforeLastTradeDate);
		
		if(accountMasterDTO.getNewDate().compareTo(strBeforeLastTradeDate) > 0){
			strBeforeLastTradeDate = accountMasterDTO.getNewDate();
			EntLogger.debug("2:"+strBeforeLastTradeDate);
		}
		
		//계좌원장 수정
		AccountMasterDTO updateAccountMaster = new AccountMasterDTO(this.transaction);
		updateAccountMaster.setBalance(newAccountDetailDTO.getAfterTradeBalance());
		updateAccountMaster.setLastTradeSequence(newAccountDetailDTO.getTradeSequence());
		updateAccountMaster.setLastTradeDate(strBeforeLastTradeDate);
		updateAccountMaster.setLastRegisterUserId(newAccountDetailDTO.getLastRegisterUserId());
		updateAccountMaster.setLastRegisterDateTime(newAccountDetailDTO.getLastRegisterDateTime());
		updateAccountMaster.setAccountNo(newAccountDetailDTO.getAccountNo());
		accountMasterBean.updateLastTradeDate(updateAccountMaster);
		
	}

	
	/**
	 * 입금 취소
	 * @param inputDTO
	 * @param originalAccountDetailDTO
	 * @throws EntException
	 */
	public void cancelAccountDeposit(A09ReverseTransactionMainInsert01DTO inputDTO
			, AccountDetailDTO originalAccountDetailDTO) throws EntException{
		
		//회계처리.
		SlipBean slipBean = new SlipBean(this.transaction);
		slipBean.cancel(originalAccountDetailDTO.getSlipNo(), originalAccountDetailDTO.getSlipSeq());
		
		//계좌원장 조회
		AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
		AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(originalAccountDetailDTO.getAccountNo());
		
		//최종거래일자보다 전일자 거래는 취소 불가.
		if(accountMasterDTO.getLastTradeDate().compareTo(originalAccountDetailDTO.getReckonDate()) >= 0){
			throw new EntException("최종거래일보다 이전일자로 거래취소 불가 합니다.");
		}
		
		
		BigDecimal bdAfterBalance = accountMasterDTO.getBalance().subtract(originalAccountDetailDTO.getTradeAmount());
		
		//취소하는 기산일의 마지막거래의 소급잔액을 구해온다.
		AccountDetailBean accountDetailBean = new AccountDetailBean(this.transaction);
		AccountDetailDTO accountDetailOfReckonDate = accountDetailBean.getLastAccountDetailOfReckonDate(
				originalAccountDetailDTO.getAccountNo(),
				originalAccountDetailDTO.getReckonDate());

		//취소하려는 원거래의 거래금액을 뺀다.
		BigDecimal bdAfterReckonBalance = accountDetailOfReckonDate.getAfterReckonBalance()
					.subtract(originalAccountDetailDTO.getTradeAmount());
		
		//새거래내역 등록
		AccountDetailDTO newAccountDetailDTO = new AccountDetailDTO(this.transaction);
		newAccountDetailDTO.setAccountNo(originalAccountDetailDTO.getAccountNo());
		newAccountDetailDTO.setTradeSequence(accountMasterDTO.getLastTradeSequence()+1);
		newAccountDetailDTO.setSlipNo(transaction.getSlipNo());
		newAccountDetailDTO.setSlipSeq(transaction.getSlipSeq());
		newAccountDetailDTO.setReckonDate(originalAccountDetailDTO.getReckonDate());
		newAccountDetailDTO.setInoutType("2");		//2:지급
		newAccountDetailDTO.setTradeType(originalAccountDetailDTO.getTradeType());		//원거래와 동일하게 셋팅.
		newAccountDetailDTO.setCancelType("8");		//8:정정거래
		newAccountDetailDTO.setRelatedTradeSequence(originalAccountDetailDTO.getTradeSequence());
		newAccountDetailDTO.setTradeAmount(originalAccountDetailDTO.getTradeAmount());
		newAccountDetailDTO.setCashAmount(originalAccountDetailDTO.getCashAmount());
		newAccountDetailDTO.setNonCashAmount(originalAccountDetailDTO.getNonCashAmount());
		newAccountDetailDTO.setAfterTradeBalance(bdAfterBalance);
		newAccountDetailDTO.setAfterReckonBalance(bdAfterReckonBalance);
		//newAccountDetailDTO.setRemarks("신용카드승인거래취소");
		
		this.transaction.insert(newAccountDetailDTO);
		
		//기존거래내역 수정(소급잔액)
		accountDetailBean.updateAccountDetailByReckonDate(newAccountDetailDTO);
		
		//원거래내역 수정
		StringBuilder sbQuery = new StringBuilder(1024);
		sbQuery.append(" update account_detail ");
		sbQuery.append(" set cancel_type = '2'         ");
		sbQuery.append(" , related_trade_sequence =    ");
		sbQuery.append(newAccountDetailDTO.getTradeSequence());
		sbQuery.append(" where account_no =            ");
		sbQuery.append(EntCommon.convertQuery(originalAccountDetailDTO.getAccountNo()));
		sbQuery.append(" and trade_sequence =          ");
		sbQuery.append(originalAccountDetailDTO.getTradeSequence());
		this.transaction.update(sbQuery);
		
		
		//계좌원장 수정
		AccountMasterDTO updateAccountMaster = new AccountMasterDTO(this.transaction);
		updateAccountMaster.setBalance(newAccountDetailDTO.getAfterTradeBalance());
		updateAccountMaster.setLastTradeSequence(newAccountDetailDTO.getTradeSequence());
		updateAccountMaster.setLastRegisterUserId(newAccountDetailDTO.getLastRegisterUserId());
		updateAccountMaster.setLastRegisterDateTime(newAccountDetailDTO.getLastRegisterDateTime());
		updateAccountMaster.setAccountNo(newAccountDetailDTO.getAccountNo());
		accountMasterBean.update(updateAccountMaster);
		
	}

	/**
	 * 출금 취소
	 * @param inputDTO
	 * @param originalAccountDetailDTO
	 * @throws EntException
	 */
	public void cancelAccountWithdraw(A09ReverseTransactionMainInsert01DTO inputDTO
			, AccountDetailDTO originalAccountDetailDTO) throws EntException{
		
		//회계처리.
		SlipBean slipBean = new SlipBean(this.transaction);
		slipBean.cancel(originalAccountDetailDTO.getSlipNo(), originalAccountDetailDTO.getSlipSeq());
		
		//계좌원장 조회
		AccountMasterBean accountMasterBean = new AccountMasterBean(this.transaction);
		AccountMasterDTO accountMasterDTO = accountMasterBean.selectByAccountNo(originalAccountDetailDTO.getAccountNo());
		
		//최종거래일자보다 전일자 거래는 취소 불가.
		if(accountMasterDTO.getLastTradeDate().compareTo(originalAccountDetailDTO.getReckonDate()) >= 0){
			throw new EntException("최종거래일보다 이전일자로 거래취소 불가 합니다.");
		}
		
		
		BigDecimal bdAfterBalance = accountMasterDTO.getBalance().add(originalAccountDetailDTO.getTradeAmount());
		
		//취소하는 기산일의 마지막거래의 소급잔액을 구해온다.
		AccountDetailBean accountDetailBean = new AccountDetailBean(this.transaction);
		AccountDetailDTO accountDetailOfReckonDate = accountDetailBean.getLastAccountDetailOfReckonDate(
				originalAccountDetailDTO.getAccountNo(),
				originalAccountDetailDTO.getReckonDate());

		//취소하려는 원거래의 거래금액을 뺀다.
		BigDecimal bdAfterReckonBalance = accountDetailOfReckonDate.getAfterReckonBalance()
					.add(originalAccountDetailDTO.getTradeAmount());
		
		//새거래내역 등록
		AccountDetailDTO newAccountDetailDTO = new AccountDetailDTO(this.transaction);
		newAccountDetailDTO.setAccountNo(originalAccountDetailDTO.getAccountNo());
		newAccountDetailDTO.setTradeSequence(accountMasterDTO.getLastTradeSequence()+1);
		newAccountDetailDTO.setSlipNo(transaction.getSlipNo());
		newAccountDetailDTO.setSlipSeq(transaction.getSlipSeq());
		newAccountDetailDTO.setReckonDate(originalAccountDetailDTO.getReckonDate());
		newAccountDetailDTO.setInoutType("1");		//1:입금
		newAccountDetailDTO.setTradeType(originalAccountDetailDTO.getTradeType());		//원거래와 동일하게 셋팅.
		newAccountDetailDTO.setCancelType("8");		//8:정정거래
		newAccountDetailDTO.setRelatedTradeSequence(originalAccountDetailDTO.getTradeSequence());
		newAccountDetailDTO.setTradeAmount(originalAccountDetailDTO.getTradeAmount());
		newAccountDetailDTO.setCashAmount(originalAccountDetailDTO.getCashAmount());
		newAccountDetailDTO.setNonCashAmount(originalAccountDetailDTO.getNonCashAmount());
		newAccountDetailDTO.setAfterTradeBalance(bdAfterBalance);
		newAccountDetailDTO.setAfterReckonBalance(bdAfterReckonBalance);
		//newAccountDetailDTO.setRemarks("신용카드승인거래취소");
		
		this.transaction.insert(newAccountDetailDTO);
		
		//기존거래내역 수정(소급잔액)
		accountDetailBean.updateAccountDetailByReckonDate(newAccountDetailDTO);
		
		//원거래내역 수정
		StringBuilder sbQuery = new StringBuilder(1024);
		sbQuery.append(" update account_detail ");
		sbQuery.append(" set cancel_type = '2'         ");
		sbQuery.append(" , related_trade_sequence =    ");
		sbQuery.append(newAccountDetailDTO.getTradeSequence());
		sbQuery.append(" where account_no =            ");
		sbQuery.append(EntCommon.convertQuery(originalAccountDetailDTO.getAccountNo()));
		sbQuery.append(" and trade_sequence =          ");
		sbQuery.append(originalAccountDetailDTO.getTradeSequence());
		this.transaction.update(sbQuery);
		
		
		//계좌원장 수정
		AccountMasterDTO updateAccountMaster = new AccountMasterDTO(this.transaction);
		updateAccountMaster.setBalance(newAccountDetailDTO.getAfterTradeBalance());
		updateAccountMaster.setLastTradeSequence(newAccountDetailDTO.getTradeSequence());
		updateAccountMaster.setLastRegisterUserId(newAccountDetailDTO.getLastRegisterUserId());
		updateAccountMaster.setLastRegisterDateTime(newAccountDetailDTO.getLastRegisterDateTime());
		updateAccountMaster.setAccountNo(newAccountDetailDTO.getAccountNo());
		accountMasterBean.update(updateAccountMaster);
		
	}
	
}
