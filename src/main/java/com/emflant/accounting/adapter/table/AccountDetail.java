package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class AccountDetail implements EntTable {

	@EntNotNull
	protected String accountNo;
	@EntNotNull
	protected Integer tradeSequence;
	@EntNotNull
	protected String slipNo;
	@EntNotNull
	protected Integer slipSeq;
	@EntNotNull
	protected String slipDate;
	@EntNotNull
	protected String tradeDate;
	@EntNotNull
	protected String tradeTime;
	@EntNotNull
	protected String reckonDate;
	@EntNotNull
	protected String inoutType;
	@EntNotNull
	protected String tradeType;
	@EntNotNull
	protected String cancelType;
	@EntNotNull
	protected Integer relatedTradeSequence;
	@EntNotNull
	protected BigDecimal tradeAmount;
	@EntNotNull
	protected BigDecimal cashAmount;
	@EntNotNull
	protected BigDecimal nonCashAmount;
	@EntNotNull
	protected BigDecimal afterTradeBalance;
	@EntNotNull
	protected BigDecimal afterReckonBalance;
	protected String remarks;
	@EntNotNull
	protected String registerUserId;
	@EntNotNull
	protected String registerDateTime;
	@EntNotNull
	protected String lastRegisterUserId;
	@EntNotNull
	protected String lastRegisterDateTime;

	public AccountDetail(){
		this.cancelType = "0";
		this.relatedTradeSequence = 0;
		this.tradeAmount = BigDecimal.valueOf(0);
		this.cashAmount = BigDecimal.valueOf(0);
		this.nonCashAmount = BigDecimal.valueOf(0);
		this.afterTradeBalance = BigDecimal.valueOf(0);
		this.afterReckonBalance = BigDecimal.valueOf(0);
	}

	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}
	public String getAccountNo(){
		return this.accountNo;
	}
	public void setTradeSequence(Integer tradeSequence){
		this.tradeSequence = tradeSequence;
	}
	public Integer getTradeSequence(){
		return this.tradeSequence;
	}
	public void setSlipNo(String slipNo){
		this.slipNo = slipNo;
	}
	public String getSlipNo(){
		return this.slipNo;
	}
	public void setSlipSeq(Integer slipSeq){
		this.slipSeq = slipSeq;
	}
	public Integer getSlipSeq(){
		return this.slipSeq;
	}
	public void setSlipDate(String slipDate){
		this.slipDate = slipDate;
	}
	public String getSlipDate(){
		return this.slipDate;
	}
	public void setTradeDate(String tradeDate){
		this.tradeDate = tradeDate;
	}
	public String getTradeDate(){
		return this.tradeDate;
	}
	public void setTradeTime(String tradeTime){
		this.tradeTime = tradeTime;
	}
	public String getTradeTime(){
		return this.tradeTime;
	}
	public void setReckonDate(String reckonDate){
		this.reckonDate = reckonDate;
	}
	public String getReckonDate(){
		return this.reckonDate;
	}
	public void setInoutType(String inoutType){
		this.inoutType = inoutType;
	}
	public String getInoutType(){
		return this.inoutType;
	}
	public void setTradeType(String tradeType){
		this.tradeType = tradeType;
	}
	public String getTradeType(){
		return this.tradeType;
	}
	public void setCancelType(String cancelType){
		this.cancelType = cancelType;
	}
	public String getCancelType(){
		return this.cancelType;
	}
	public void setRelatedTradeSequence(Integer relatedTradeSequence){
		this.relatedTradeSequence = relatedTradeSequence;
	}
	public Integer getRelatedTradeSequence(){
		return this.relatedTradeSequence;
	}
	public void setTradeAmount(BigDecimal tradeAmount){
		this.tradeAmount = tradeAmount;
	}
	public BigDecimal getTradeAmount(){
		return this.tradeAmount;
	}
	public void setCashAmount(BigDecimal cashAmount){
		this.cashAmount = cashAmount;
	}
	public BigDecimal getCashAmount(){
		return this.cashAmount;
	}
	public void setNonCashAmount(BigDecimal nonCashAmount){
		this.nonCashAmount = nonCashAmount;
	}
	public BigDecimal getNonCashAmount(){
		return this.nonCashAmount;
	}
	public void setAfterTradeBalance(BigDecimal afterTradeBalance){
		this.afterTradeBalance = afterTradeBalance;
	}
	public BigDecimal getAfterTradeBalance(){
		return this.afterTradeBalance;
	}
	public void setAfterReckonBalance(BigDecimal afterReckonBalance){
		this.afterReckonBalance = afterReckonBalance;
	}
	public BigDecimal getAfterReckonBalance(){
		return this.afterReckonBalance;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	public String getRemarks(){
		return this.remarks;
	}
	public void setRegisterUserId(String registerUserId){
		this.registerUserId = registerUserId;
	}
	public String getRegisterUserId(){
		return this.registerUserId;
	}
	public void setRegisterDateTime(String registerDateTime){
		this.registerDateTime = registerDateTime;
	}
	public String getRegisterDateTime(){
		return this.registerDateTime;
	}
	public void setLastRegisterUserId(String lastRegisterUserId){
		this.lastRegisterUserId = lastRegisterUserId;
	}
	public String getLastRegisterUserId(){
		return this.lastRegisterUserId;
	}
	public void setLastRegisterDateTime(String lastRegisterDateTime){
		this.lastRegisterDateTime = lastRegisterDateTime;
	}
	public String getLastRegisterDateTime(){
		return this.lastRegisterDateTime;
	}
	public StringBuilder insert(){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("INSERT INTO account_detail (");
		sb.append("account_no, ");
		sb.append("trade_sequence, ");
		sb.append("slip_no, ");
		sb.append("slip_seq, ");
		sb.append("slip_date, ");
		sb.append("trade_date, ");
		sb.append("trade_time, ");
		sb.append("reckon_date, ");
		sb.append("inout_type, ");
		sb.append("trade_type, ");
		sb.append("cancel_type, ");
		sb.append("related_trade_sequence, ");
		sb.append("trade_amount, ");
		sb.append("cash_amount, ");
		sb.append("non_cash_amount, ");
		sb.append("after_trade_balance, ");
		sb.append("after_reckon_balance, ");
		sb.append("remarks, ");
		sb.append("register_user_id, ");
		sb.append("register_date_time, ");
		sb.append("last_register_user_id, ");
		sb.append("last_register_date_time");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.accountNo));
		sb.append(", ");
		sb.append(this.tradeSequence);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.slipNo));
		sb.append(", ");
		sb.append(this.slipSeq);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.slipDate));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.tradeDate));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.tradeTime));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.reckonDate));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.inoutType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.tradeType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.cancelType));
		sb.append(", ");
		sb.append(this.relatedTradeSequence);
		sb.append(", ");
		sb.append(this.tradeAmount);
		sb.append(", ");
		sb.append(this.cashAmount);
		sb.append(", ");
		sb.append(this.nonCashAmount);
		sb.append(", ");
		sb.append(this.afterTradeBalance);
		sb.append(", ");
		sb.append(this.afterReckonBalance);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.remarks));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.registerUserId));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.registerDateTime));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.lastRegisterUserId));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.lastRegisterDateTime));
		sb.append(")");
		return sb;
	}

}
