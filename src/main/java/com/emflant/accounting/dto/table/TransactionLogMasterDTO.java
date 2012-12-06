package com.emflant.accounting.dto.table;

import com.emflant.accounting.adapter.table.TransactionLogMaster;
import com.emflant.common.EntBusiness;

public class TransactionLogMasterDTO extends TransactionLogMaster {
	
	public TransactionLogMasterDTO(EntBusiness business){
		this.userId = business.getUserId();
		this.systemDateTime = business.getSystemDateTime();
		this.screenName = business.getScreenName();
		
		String strOneSlipYn = null;
		if(business.getIsOneSlip() == true) {
			strOneSlipYn = "1";
		} else {
			strOneSlipYn = "0";
		}
		
		this.oneSlipYn = strOneSlipYn;
		
	}
}
