package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class TradeMaster implements EntTable {

	@EntNotNull
	protected String tradeCode;
	@EntNotNull
	protected String tradeName;
	@EntNotNull
	protected String beanType;
	@EntNotNull
	protected String methodType;
	protected String parameterType;
	@EntNotNull
	protected String tradeKindsCode;
	@EntNotNull
	protected String tradeYn;
	protected String cancelTradeCode;

	public TradeMaster(){
	}

	public void setTradeCode(String tradeCode){
		this.tradeCode = tradeCode;
	}
	public String getTradeCode(){
		return this.tradeCode;
	}
	public void setTradeName(String tradeName){
		this.tradeName = tradeName;
	}
	public String getTradeName(){
		return this.tradeName;
	}
	public void setBeanType(String beanType){
		this.beanType = beanType;
	}
	public String getBeanType(){
		return this.beanType;
	}
	public void setMethodType(String methodType){
		this.methodType = methodType;
	}
	public String getMethodType(){
		return this.methodType;
	}
	public void setParameterType(String parameterType){
		this.parameterType = parameterType;
	}
	public String getParameterType(){
		return this.parameterType;
	}
	public void setTradeKindsCode(String tradeKindsCode){
		this.tradeKindsCode = tradeKindsCode;
	}
	public String getTradeKindsCode(){
		return this.tradeKindsCode;
	}
	public void setTradeYn(String tradeYn){
		this.tradeYn = tradeYn;
	}
	public String getTradeYn(){
		return this.tradeYn;
	}
	public void setCancelTradeCode(String cancelTradeCode){
		this.cancelTradeCode = cancelTradeCode;
	}
	public String getCancelTradeCode(){
		return this.cancelTradeCode;
	}
	public StringBuilder insert(){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("INSERT INTO trade_master (");
		sb.append("trade_code, ");
		sb.append("trade_name, ");
		sb.append("bean_type, ");
		sb.append("method_type, ");
		sb.append("parameter_type, ");
		sb.append("trade_kinds_code, ");
		sb.append("trade_yn, ");
		sb.append("cancel_trade_code");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.tradeCode));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.tradeName));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.beanType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.methodType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.parameterType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.tradeKindsCode));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.tradeYn));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.cancelTradeCode));
		sb.append(")");
		return sb;
	}

}
