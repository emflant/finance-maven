package com.emflant.accounting.business.bean;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.business.remote.CodedetailRemote;
import com.emflant.accounting.dto.table.CodeDetailDTO;
import com.emflant.accounting.dto.table.EntQuery;
import com.emflant.common.EntCommon;
import com.emflant.common.EntException;
import com.emflant.common.EntHashList;
import com.emflant.common.EntTransaction;

public class CodeDetailBean implements CodedetailRemote {

	private EntTransaction transaction;
	public CodeDetailBean(EntTransaction transaction){
		this.transaction = transaction;
	}
	
	public void selectEntHashListAddAllByCodeType(String codeType) throws EntException{
		
		try {
			EntHashList ehlResult = queryEntHashListAddAllByCodeType(codeType);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	public void selectEntHashListByCodeType(String codeType) throws EntException{
		
		try {
			EntHashList ehlResult = queryEntHashListByCodeType(codeType);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	public EntHashList queryEntHashListAddAllByCodeType(String codeType) throws EntException {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT '*' as code, '전체' as code_name union all ");
		sb.append("SELECT code, code_name ");
		sb.append("FROM code_detail WHERE code_type = "+EntCommon.convertQuery(codeType));
		sb.append(" AND use_yn = 'Y'");
		
		return this.transaction.selectCodeToHashMap(sb);
	}
	
	public EntHashList queryEntHashListByCodeType(String codeType) throws EntException {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT code, code_name ");
		sb.append("FROM code_detail WHERE code_type = "+EntCommon.convertQuery(codeType));
		sb.append(" AND use_yn = 'Y'");
		
		return this.transaction.selectCodeToHashMap(sb);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<CodeDetailDTO> selectListByCodeType(String codeType) {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT code_type, code, code_name, use_yn, remarks ");
		sb.append("FROM code_detail WHERE code_type = "+EntCommon.convertQuery(codeType));
		List<CodeDetailDTO> list = null; //this.entQuery.select(CodeDetail.class, sb.toString());
		
		return list;
	}
	
	public void selectTableModelByCodeType(String codeType) throws EntException{
		
		try {
			//String strCodeType = (String)transaction.getMethodParam();
			DefaultTableModel model = queryTableModelByCodeType(codeType);
			transaction.addResult(model);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	public DefaultTableModel queryTableModelByCodeType(String codeType) throws EntException{
		
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT code, code_name, use_yn, remarks ");
		sb.append("FROM code_detail WHERE code_type = "+EntCommon.convertQuery(codeType));
		DefaultTableModel list = this.transaction.selectToTableModel(sb);
		
		return list;
	}
	
	public void insert(CodeDetailDTO inputDTO) throws EntException {
		
		try {
			//CodeDetailDTO codeDetail = (CodeDetailDTO)transaction.getMethodParam();
			//insert(inputDTO);
			
			transaction.insert(inputDTO);
			transaction.setSuccessMessage("정상적으로 등록되었습니다.");
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	

	public void delete(CodeDetailDTO codeDetail) throws Exception {
				
		StringBuilder sb = new StringBuilder(1024);
		sb.append("DELETE FROM code_detail WHERE code_type = ");
		sb.append(EntCommon.convertQuery(codeDetail.getCodeType()));
		sb.append(" AND code = ");
		sb.append(EntCommon.convertQuery(codeDetail.getCode()));
		transaction.update(sb);
	}
}
