package com.emflant.accounting.business.remote;

import com.emflant.common.EntException;
import com.emflant.accounting.dto.screen.A01AccountMasterMainInsert01DTO;


public interface AccountmasterRemote {
	void selectByUserId(String input) throws EntException;
	void insert(A01AccountMasterMainInsert01DTO input) throws EntException;
	void selectEntHashListByUserId(String input) throws EntException;
	void selectByPK(String input) throws EntException;
	void selectEntHashCreditCardListByUserId() throws EntException;
	void selectEntHashDepositListByUserId(String input) throws EntException;
	void selectEntHashWithdrawListByUserId(String input) throws EntException;
}
