package com.emflant.accounting.dto.screen;

import java.math.BigDecimal;

import com.emflant.accounting.annotation.EntNotNull;

public class A10CashInOutTradeMainInsert01DTO {

	@EntNotNull
	private String inOutType;
	@EntNotNull
	private String tradeType;
	@EntNotNull
	private String remarks;
	@EntNotNull
	private BigDecimal totalAmount;
	
	
	public String getInOutType() {
		return inOutType;
	}
	public void setInOutType(String inOutType) {
		this.inOutType = inOutType;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
}
