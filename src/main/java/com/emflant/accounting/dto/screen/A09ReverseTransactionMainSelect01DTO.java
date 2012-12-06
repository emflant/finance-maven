package com.emflant.accounting.dto.screen;

import com.emflant.accounting.annotation.EntNotNull;

public class A09ReverseTransactionMainSelect01DTO {
	
	@EntNotNull
	private String accountNo;
	@EntNotNull
	private Integer tradeSequence;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public Integer getTradeSequence() {
		return tradeSequence;
	}
	public void setTradeSequence(Integer tradeSequence) {
		this.tradeSequence = tradeSequence;
	}
	
	

}
