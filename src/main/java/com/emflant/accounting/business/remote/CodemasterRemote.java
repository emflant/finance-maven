package com.emflant.accounting.business.remote;

import com.emflant.accounting.dto.table.CodeMasterDTO;
import com.emflant.common.EntException;

public interface CodemasterRemote {
	void selectAll() throws EntException;
	void insert(CodeMasterDTO input) throws EntException;
	void selectEntHashList() throws EntException;
}
