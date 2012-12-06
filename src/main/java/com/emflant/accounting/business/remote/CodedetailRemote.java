package com.emflant.accounting.business.remote;

import com.emflant.accounting.dto.table.CodeDetailDTO;
import com.emflant.common.EntException;


public interface CodedetailRemote {
	void selectEntHashListByCodeType(String input) throws EntException;
	void selectEntHashListAddAllByCodeType(String input) throws EntException;
	void selectTableModelByCodeType(String input) throws EntException;
	void insert(CodeDetailDTO input) throws EntException;
}
