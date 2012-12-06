package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class TransactionLogDetail implements EntTable {

	@EntNotNull
	protected String userId;
	@EntNotNull
	protected String systemDateTime;
	@EntNotNull
	protected Integer transactionSequence;
	@EntNotNull
	protected String tradeCode;
	protected String parameterValue;
	@EntNotNull
	protected BigDecimal gapTime;
	@EntNotNull
	protected String startDateTime;
	@EntNotNull
	protected String endDateTime;

	public TransactionLogDetail(){
		this.gapTime = BigDecimal.valueOf(0.000);
	}

	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getUserId(){
		return this.userId;
	}
	public void setSystemDateTime(String systemDateTime){
		this.systemDateTime = systemDateTime;
	}
	public String getSystemDateTime(){
		return this.systemDateTime;
	}
	public void setTransactionSequence(Integer transactionSequence){
		this.transactionSequence = transactionSequence;
	}
	public Integer getTransactionSequence(){
		return this.transactionSequence;
	}
	public void setTradeCode(String tradeCode){
		this.tradeCode = tradeCode;
	}
	public String getTradeCode(){
		return this.tradeCode;
	}
	public void setParameterValue(String parameterValue){
		this.parameterValue = parameterValue;
	}
	public String getParameterValue(){
		return this.parameterValue;
	}
	public void setGapTime(BigDecimal gapTime){
		this.gapTime = gapTime;
	}
	public BigDecimal getGapTime(){
		return this.gapTime;
	}
	public void setStartDateTime(String startDateTime){
		this.startDateTime = startDateTime;
	}
	public String getStartDateTime(){
		return this.startDateTime;
	}
	public void setEndDateTime(String endDateTime){
		this.endDateTime = endDateTime;
	}
	public String getEndDateTime(){
		return this.endDateTime;
	}
	public StringBuilder insert(){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("INSERT INTO transaction_log_detail (");
		sb.append("user_id, ");
		sb.append("system_date_time, ");
		sb.append("transaction_sequence, ");
		sb.append("trade_code, ");
		sb.append("parameter_value, ");
		sb.append("gap_time, ");
		sb.append("start_date_time, ");
		sb.append("end_date_time");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.userId));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.systemDateTime));
		sb.append(", ");
		sb.append(this.transactionSequence);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.tradeCode));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.parameterValue));
		sb.append(", ");
		sb.append(this.gapTime);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.startDateTime));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.endDateTime));
		sb.append(")");
		return sb;
	}

}
