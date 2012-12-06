package com.emflant.accounting.dto.table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.emflant.accounting.adapter.table.SlipMaster;
import com.emflant.accounting.dto.screen.A01AccountMasterMainInsert01DTO;
import com.emflant.accounting.dto.screen.A03CreditCardPaymentMainInsert01DTO;
import com.emflant.common.EntTransaction;

public class SlipMasterDTO extends SlipMaster {

	private List<SlipDetailDTO> amounts;		//전표상세금액내역
	
	public SlipMasterDTO(){
		this.amounts = new ArrayList<SlipDetailDTO>();
	}
	
	public SlipMasterDTO(EntTransaction transaction){
		this.slipDate 				= transaction.getSystemDate();
		this.slipTime 				= transaction.getSystemTimeMillis();
		this.userId					= transaction.getUserId();
		this.registerUserId			= transaction.getUserId();
		this.registerDateTime 		= transaction.getSystemDateTimeMillis();
		this.lastRegisterUserId		= transaction.getUserId();
		this.lastRegisterDateTime 	= transaction.getSystemDateTimeMillis();
		this.amounts = new ArrayList<SlipDetailDTO>();
	}
	

	public void setDefaultField(EntTransaction transaction){
		this.slipDate 				= transaction.getSystemDate();
		this.slipTime 				= transaction.getSystemTimeMillis();
		this.userId					= transaction.getUserId();
		this.registerUserId			= transaction.getUserId();
		this.registerDateTime 		= transaction.getSystemDateTimeMillis();
		this.lastRegisterUserId		= transaction.getUserId();
		this.lastRegisterDateTime 	= transaction.getSystemDateTimeMillis();
	}
	
	public void setSlipMasterDTO(A01AccountMasterMainInsert01DTO a01AccountMasterMainInsert01DTO){
		
		this.slipNo = null;
		this.slipSequence = null;
		this.cancelType = "0";
		this.slipAmount = a01AccountMasterMainInsert01DTO.getTotalAmount();
		this.debtorAmount = a01AccountMasterMainInsert01DTO.getTotalAmount();
		this.creditAmount = BigDecimal.ZERO;
	
		this.amounts = new ArrayList<SlipDetailDTO>();
	}
	
	public void setSlipMasterDTO(A03CreditCardPaymentMainInsert01DTO a03CreditCardPaymentMainInsert01DTO){
		//BigDecimal bdCostAmount = a03CreditCardPaymentMainInsert01DTO.getTotalAmount()
		//				.subtract(a03CreditCardPaymentMainInsert01DTO.getCashAmount());
		
		this.slipNo = null;
		this.slipSequence = null;
		this.cancelType = "0";
		this.slipAmount = a03CreditCardPaymentMainInsert01DTO.getTotalAmount();
		this.debtorAmount = BigDecimal.ZERO;										//차변
		this.creditAmount = a03CreditCardPaymentMainInsert01DTO.getTotalAmount();	//대변

	
		this.amounts = new ArrayList<SlipDetailDTO>();
		
		addCreditAmount("21", a03CreditCardPaymentMainInsert01DTO.getTotalAmount()
				, a03CreditCardPaymentMainInsert01DTO.getCashAmount());
	}
	
	public SlipMasterDTO(AccountDetailDTO accountDetailDTO){
		
	}

	public List<SlipDetailDTO> getAmounts() {
		return amounts;
	}


	public void setAmounts(List<SlipDetailDTO> amounts) {
		this.amounts = amounts;
	}


	/**
	 * 차변금액내역을 입력한다.
	 * @param accountType
	 * @param amount
	 */
	public void addDebtorAmount(String accountType, BigDecimal totalAmount, BigDecimal cashAmount){
		addAmount("D", accountType, totalAmount, cashAmount);
	}

	/**
	 * 대변금액내역을 입력한다.
	 * @param accountType
	 * @param amount
	 */
	public void addCreditAmount(String accountType, BigDecimal totalAmount, BigDecimal cashAmount){
		addAmount("C", accountType, totalAmount, cashAmount);
	}
	
	private void addAmount(String debtorCreditType, String accountType, BigDecimal totalAmount, BigDecimal cashAmount){
		SlipDetailDTO slipDetail = new SlipDetailDTO();
		slipDetail.setSlipNo(this.slipNo);
		slipDetail.setSlipSequence(this.slipSequence);
		slipDetail.setSequence(this.amounts.size()+1);
		slipDetail.setDebtorCreditType(debtorCreditType);
		slipDetail.setAccountType(accountType);
		slipDetail.setTotalAmount(totalAmount);
		slipDetail.setCashAmount(cashAmount);
		slipDetail.setNonCashAmount(totalAmount.subtract(cashAmount));
		slipDetail.setRegisterUserId(this.registerUserId);
		slipDetail.setRegisterDateTime(this.registerDateTime);
		slipDetail.setLastRegisterUserId(this.lastRegisterUserId);
		slipDetail.setLastRegisterDateTime(this.lastRegisterDateTime);
		
		this.amounts.add(slipDetail);
	}
	
	
	
	
}
