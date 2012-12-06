package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class CodeDetail implements EntTable {

	@EntNotNull
	protected String codeType;
	@EntNotNull
	protected String code;
	@EntNotNull
	protected String codeName;
	@EntNotNull
	protected String useYn;
	protected String remarks;

	public CodeDetail(){
		this.useYn = "Y";
	}

	public void setCodeType(String codeType){
		this.codeType = codeType;
	}
	public String getCodeType(){
		return this.codeType;
	}
	public void setCode(String code){
		this.code = code;
	}
	public String getCode(){
		return this.code;
	}
	public void setCodeName(String codeName){
		this.codeName = codeName;
	}
	public String getCodeName(){
		return this.codeName;
	}
	public void setUseYn(String useYn){
		this.useYn = useYn;
	}
	public String getUseYn(){
		return this.useYn;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	public String getRemarks(){
		return this.remarks;
	}
	public StringBuilder insert(){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("INSERT INTO code_detail (");
		sb.append("code_type, ");
		sb.append("code, ");
		sb.append("code_name, ");
		sb.append("use_yn, ");
		sb.append("remarks");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.codeType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.code));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.codeName));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.useYn));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.remarks));
		sb.append(")");
		return sb;
	}

}
