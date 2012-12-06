package com.emflant.accounting.dto.screen;

import java.math.BigDecimal;

import com.emflant.accounting.annotation.EntNotNull;

public class A05WithdrawAccountMainInsert01DTO {

	@EntNotNull
	private String accountNo;
	@EntNotNull
	private String remarks;
	@EntNotNull
	private String reckonDate;
	@EntNotNull
	private BigDecimal totalAmount;
	@EntNotNull
	private BigDecimal cashAmount;
	
	


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
	public String getReckonDate() {
		return reckonDate;
	}
	public void setReckonDate(String reckonDate) {
		this.reckonDate = reckonDate;
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
