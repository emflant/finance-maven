package com.emflant.accounting.dto.table;

import com.emflant.accounting.adapter.table.TransactionLogDetail;
import com.emflant.common.EntTransaction;

public class TransactionLogDetailDTO extends TransactionLogDetail {
	
	public TransactionLogDetailDTO(EntTransaction transaction){
		this.userId = transaction.getUserId();
		this.systemDateTime = transaction.getBusinessBaseDataTimeMillis();
		this.transactionSequence = transaction.getTransactionSequence();
		this.tradeCode = transaction.getTradeCode();
		this.startDateTime = transaction.getSystemDateTimeMillis();
		this.endDateTime = "0";
	}
}
