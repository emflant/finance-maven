package com.emflant.accounting.dto.screen;

import java.math.BigDecimal;

import com.emflant.accounting.annotation.EntNotNull;

public class A06CreditCardRepaymentMainInsert01DTO {

	@EntNotNull
	private String accountNo;
	@EntNotNull
	private String remarks;
	@EntNotNull
	private String repaymentYm;
	@EntNotNull
	private BigDecimal totalAmount;
	@EntNotNull
	private BigDecimal cashAmount;
	@EntNotNull
	private String reckonDate;
	
	
	
	public String getReckonDate() {
		return reckonDate;
	}
	public void setReckonDate(String reckonDate) {
		this.reckonDate = reckonDate;
	}

	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRepaymentYm() {
		return repaymentYm;
	}
	public void setRepaymentYm(String repaymentYm) {
		this.repaymentYm = repaymentYm;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(BigDecimal cashAmount) {
		this.cashAmount = cashAmount;
	}
}
