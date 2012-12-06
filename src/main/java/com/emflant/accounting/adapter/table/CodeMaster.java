package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class CodeMaster implements EntTable {

	@EntNotNull
	protected String codeType;
	@EntNotNull
	protected String codeTypeName;
	@EntNotNull
	protected String useYn;
	protected String remarks;

	public CodeMaster(){
		this.useYn = "Y";
	}

	public void setCodeType(String codeType){
		this.codeType = codeType;
	}
	public String getCodeType(){
		return this.codeType;
	}
	public void setCodeTypeName(String codeTypeName){
		this.codeTypeName = codeTypeName;
	}
	public String getCodeTypeName(){
		return this.codeTypeName;
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
		sb.append("INSERT INTO code_master (");
		sb.append("code_type, ");
		sb.append("code_type_name, ");
		sb.append("use_yn, ");
		sb.append("remarks");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.codeType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.codeTypeName));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.useYn));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.remarks));
		sb.append(")");
		return sb;
	}

}
