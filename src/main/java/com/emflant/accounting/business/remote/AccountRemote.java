package com.emflant.accounting.business.remote;

import com.emflant.common.EntException;


public interface AccountRemote {
	void selectEntHashListNewAccount() throws EntException;
	void selectEntHashListByBsPlDetailType(String input) throws EntException;
	void selectEntHashList() throws EntException;
}
