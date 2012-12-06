package com.emflant.accounting.business.bean;

import com.emflant.accounting.business.remote.AccountRemote;
import com.emflant.common.EntCommon;
import com.emflant.common.EntException;
import com.emflant.common.EntHashList;
import com.emflant.common.EntTransaction;

public class AccountBean implements AccountRemote {
	
	private EntTransaction transaction;
	
	public AccountBean(EntTransaction transaction){
		this.transaction = transaction;
	}
	
	public void selectEntHashList() throws EntException{
		
		try {
			//String psPlType = (String)transaction.getMethodParam();
			StringBuilder sb = new StringBuilder(1024);
			sb.append("SELECT account_type AS code, ");
			sb.append("account_type_name AS code_name ");
			sb.append("FROM account");
			
			EntHashList ehlResult = this.transaction.selectCodeToHashMap(sb);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	


	public void selectEntHashListNewAccount() throws EntException{
		
		try {
			StringBuilder sb = new StringBuilder(1024);
			sb.append("SELECT account_type AS code, ");
			sb.append("account_type_name AS code_name ");
			sb.append("FROM account WHERE new_yn = 'Y'");
			
			EntHashList ehlResult = this.transaction.selectCodeToHashMap(sb);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	

	

	public void selectEntHashListByBsPlType(String psPlType) throws EntException{
		
		try {
			EntHashList ehlResult = queryEntHashListByBsPlType(psPlType);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	
	public EntHashList queryEntHashListByBsPlType(String psPlType) throws EntException {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("SELECT account_type AS code, ");
		sb.append("account_type_name AS code_name ");
		sb.append("FROM account WHERE bs_pl_type = "+EntCommon.convertQuery(psPlType));
		
		return this.transaction.selectCodeToHashMap(sb);
	}
	
	
	public void selectEntHashListByBsPlDetailType(String psPlDetailType) throws EntException{
		
		try {
			StringBuilder sb = new StringBuilder(1024);
			sb.append("SELECT account_type AS code, ");
			sb.append("account_type_name AS code_name ");
			sb.append("FROM account WHERE bs_pl_detail_type IN ");
			sb.append(psPlDetailType);
			
			
			EntHashList ehlResult = this.transaction.selectCodeToHashMap(sb);
			transaction.addResult(ehlResult);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}
	
	
		
}
