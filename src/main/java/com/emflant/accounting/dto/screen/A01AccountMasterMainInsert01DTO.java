package com.emflant.accounting.dto.screen;

import java.math.BigDecimal;

import com.emflant.accounting.annotation.EntNotNull;

/**
 * 계좌신규 입력값 받는 DTO
 * @author home
 *
 */
public class A01AccountMasterMainInsert01DTO {
	

	@EntNotNull
	private String accountType;
	@EntNotNull
	private String remarks;
	@EntNotNull
	private String newDate;
	@EntNotNull
	private BigDecimal totalAmount;
	@EntNotNull
	private BigDecimal cashAmount;
	

	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getNewDate() {
		return newDate;
	}
	public void setNewDate(String newDate) {
		this.newDate = newDate;
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
