package com.emflant.accounting.dto.table;

import com.emflant.accounting.adapter.table.AccountMaster;
import com.emflant.accounting.dto.screen.A01AccountMasterMainInsert01DTO;
import com.emflant.common.EntTransaction;

public class AccountMasterDTO extends AccountMaster {

	AccountMasterDTO(){}
	
	public AccountMasterDTO(EntTransaction transaction){
		this.userId					= transaction.getUserId();
		this.registerUserId			= transaction.getUserId();
		this.registerDateTime 		= transaction.getSystemDateTimeMillis();
		this.lastRegisterUserId		= transaction.getUserId();
		this.lastRegisterDateTime 	= transaction.getSystemDateTimeMillis();
	}
	
	
	public void setAccountMasterDTO(A01AccountMasterMainInsert01DTO a01AccountMasterMainInsert01DTO){
		
		this.accountType = a01AccountMasterMainInsert01DTO.getAccountType();
		this.accountNo = null;
		this.accountStatus = "00";
		this.balance = a01AccountMasterMainInsert01DTO.getTotalAmount();
		this.remarks = a01AccountMasterMainInsert01DTO.getRemarks();
		this.newDate = a01AccountMasterMainInsert01DTO.getNewDate();
		this.lastTradeDate = a01AccountMasterMainInsert01DTO.getNewDate();
		this.lastTradeSequence = 0;
	}
	
	

}
