package com.emflant.accounting.business.remote;

import com.emflant.common.EntException;
import com.emflant.accounting.dto.screen.A09ReverseTransactionMainSelect01DTO;
import com.emflant.accounting.dto.screen.A09ReverseTransactionMainInsert01DTO;


public interface CancelRemote {
	void selectLinkTransactions(A09ReverseTransactionMainSelect01DTO input) throws EntException;
	void cancel(A09ReverseTransactionMainInsert01DTO input) throws EntException;
}
