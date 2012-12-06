package com.emflant.accounting.dto.screen;

import com.emflant.accounting.annotation.EntNotNull;

public class A06CreditCardRepaymentMainSelect01DTO {

	@EntNotNull
	private String accountNo;
	@EntNotNull
	private String repaymentYm;
	

	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getRepaymentYm() {
		return repaymentYm;
	}
	public void setRepaymentYm(String repaymentYm) {
		this.repaymentYm = repaymentYm;
	}
	
	
}
