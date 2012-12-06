package com.emflant.interest.dto;

import com.emflant.common.EntDate;

public class InterestCalculateInputDTO {
	
	private String account;
	private EntDate calculateData;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public EntDate getCalculateData() {
		return calculateData;
	}
	public void setCalculateData(EntDate calculateData) {
		this.calculateData = calculateData;
	}
	public void setCalculateData(String calculateData) {
		this.calculateData = new EntDate(calculateData);
	}
	

}
