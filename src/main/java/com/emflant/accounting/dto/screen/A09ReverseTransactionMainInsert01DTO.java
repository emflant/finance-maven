package com.emflant.accounting.dto.screen;

import com.emflant.accounting.annotation.EntNotNull;

public class A09ReverseTransactionMainInsert01DTO {


	@EntNotNull
	private String registerUserId;
	@EntNotNull
	private String registerDateTime;
	
	public String getRegisterUserId() {
		return registerUserId;
	}
	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}
	public String getRegisterDateTime() {
		return registerDateTime;
	}
	public void setRegisterDateTime(String registerDateTime) {
		this.registerDateTime = registerDateTime;
	}
	

	
}
