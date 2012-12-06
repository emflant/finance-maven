package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class SequenceMaster implements EntTable {

	@EntNotNull
	protected String sequenceType;
	protected String sequenceTypeName;
	@EntNotNull
	protected Long sequence;

	public SequenceMaster(){
		this.sequence = 0L;
	}

	public void setSequenceType(String sequenceType){
		this.sequenceType = sequenceType;
	}
	public String getSequenceType(){
		return this.sequenceType;
	}
	public void setSequenceTypeName(String sequenceTypeName){
		this.sequenceTypeName = sequenceTypeName;
	}
	public String getSequenceTypeName(){
		return this.sequenceTypeName;
	}
	public void setSequence(Long sequence){
		this.sequence = sequence;
	}
	public Long getSequence(){
		return this.sequence;
	}
	public StringBuilder insert(){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("INSERT INTO sequence_master (");
		sb.append("sequence_type, ");
		sb.append("sequence_type_name, ");
		sb.append("sequence");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.sequenceType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.sequenceTypeName));
		sb.append(", ");
		sb.append(this.sequence);
		sb.append(")");
		return sb;
	}

}
