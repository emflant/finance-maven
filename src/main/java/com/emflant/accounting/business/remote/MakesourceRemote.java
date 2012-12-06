package com.emflant.accounting.business.remote;

import com.emflant.common.EntException;


public interface MakesourceRemote {
	void selectTables() throws EntException;
	void selectColumnsByTable(String input) throws EntException;
	void selectColumnsByTable2(String input) throws EntException;
	void selectBeans() throws EntException;
	void selectTableModelTradesByBeanType(String input) throws EntException;
	void selectTradeMasterDTOTradesByBeanType(String input) throws EntException;
}
