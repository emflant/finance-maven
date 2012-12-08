package com.emflant.accounting.business.bean;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.business.remote.CodemasterRemote;
import com.emflant.accounting.dto.table.CodeMasterDTO;
import com.emflant.common.EntException;
import com.emflant.common.EntHashList;
import com.emflant.common.EntTransaction;

public class CodeMasterBean implements CodemasterRemote {
	
	private EntTransaction transaction;
	
	public CodeMasterBean(EntTransaction transaction){
		this.transaction = transaction;
	}	
	
	public void selectEntHashList() throws EntException {
		
		try {
			StringBuilder sb = new StringBuilder(1024);
			sb.append("SELECT code_type AS code, ");
			sb.append("code_type_name AS code_name ");
			sb.append("FROM code_master ");
			sb.append("where use_yn = 'Y' ");
			
			EntHashList list = this.transaction.selectCodeToHashMap(sb);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	
	
	@SuppressWarnings("unchecked")
	public List<CodeMasterDTO> selectAllToList(){
		StringBuffer sb = new StringBuffer(1024);
		sb.append("SELECT * ");
		sb.append("FROM code_master");
		List<CodeMasterDTO> list = null; //this.entQuery.select(CodeMaster.class, sb.toString());
		
		return list;
	}
	
	public void selectAll() throws EntException {
		
		try {
			StringBuilder sb = new StringBuilder(1024);
			sb.append("SELECT * FROM code_master");
			DefaultTableModel list = this.transaction.selectToTableModel(sb);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	
	public void insert(CodeMasterDTO inputDTO) throws EntException {
		
		try {
			transaction.insert(inputDTO);
			transaction.setSuccessMessage("정상적으로 등록되었습니다.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	

	
	
}
