package com.emflant.accounting.business.remote;

import com.emflant.common.EntException;
import com.emflant.accounting.dto.screen.A09ReverseTransactionMainInsert01DTO;
import com.emflant.accounting.dto.table.SlipMasterDTO;


public interface SlipRemote {
	void selectSlipMaster() throws EntException;
	void selectSlipDetail() throws EntException;
	void selectSlipDetailByAccountType(String input) throws EntException;
	void insertSlip(SlipMasterDTO input) throws EntException;
	void cancel(A09ReverseTransactionMainInsert01DTO input) throws EntException;
}
