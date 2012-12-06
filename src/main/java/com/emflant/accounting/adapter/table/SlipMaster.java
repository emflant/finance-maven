package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class SlipMaster implements EntTable {

	@EntNotNull
	protected String slipNo;
	@EntNotNull
	protected Integer slipSequence;
	@EntNotNull
	protected String slipDate;
	@EntNotNull
	protected String slipTime;
	@EntNotNull
	protected String cancelType;
	@EntNotNull
	protected BigDecimal slipAmount;
	@EntNotNull
	protected BigDecimal debtorAmount;
	@EntNotNull
	protected BigDecimal creditAmount;
	@EntNotNull
	protected String userId;
	@EntNotNull
	protected String registerUserId;
	@EntNotNull
	protected String registerDateTime;
	@EntNotNull
	protected String lastRegisterUserId;
	@EntNotNull
	protected String lastRegisterDateTime;

	public SlipMaster(){
		this.cancelType = "0";
		this.slipAmount = BigDecimal.valueOf(0);
		this.debtorAmount = BigDecimal.valueOf(0);
		this.creditAmount = BigDecimal.valueOf(0);
	}

	public void setSlipNo(String slipNo){
		this.slipNo = slipNo;
	}
	public String getSlipNo(){
		return this.slipNo;
	}
	public void setSlipSequence(Integer slipSequence){
		this.slipSequence = slipSequence;
	}
	public Integer getSlipSequence(){
		return this.slipSequence;
	}
	public void setSlipDate(String slipDate){
		this.slipDate = slipDate;
	}
	public String getSlipDate(){
		return this.slipDate;
	}
	public void setSlipTime(String slipTime){
		this.slipTime = slipTime;
	}
	public String getSlipTime(){
		return this.slipTime;
	}
	public void setCancelType(String cancelType){
		this.cancelType = cancelType;
	}
	public String getCancelType(){
		return this.cancelType;
	}
	public void setSlipAmount(BigDecimal slipAmount){
		this.slipAmount = slipAmount;
	}
	public BigDecimal getSlipAmount(){
		return this.slipAmount;
	}
	public void setDebtorAmount(BigDecimal debtorAmount){
		this.debtorAmount = debtorAmount;
	}
	public BigDecimal getDebtorAmount(){
		return this.debtorAmount;
	}
	public void setCreditAmount(BigDecimal creditAmount){
		this.creditAmount = creditAmount;
	}
	public BigDecimal getCreditAmount(){
		return this.creditAmount;
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public String getUserId(){
		return this.userId;
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
		sb.append("INSERT INTO slip_master (");
		sb.append("slip_no, ");
		sb.append("slip_sequence, ");
		sb.append("slip_date, ");
		sb.append("slip_time, ");
		sb.append("cancel_type, ");
		sb.append("slip_amount, ");
		sb.append("debtor_amount, ");
		sb.append("credit_amount, ");
		sb.append("user_id, ");
		sb.append("register_user_id, ");
		sb.append("register_date_time, ");
		sb.append("last_register_user_id, ");
		sb.append("last_register_date_time");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.slipNo));
		sb.append(", ");
		sb.append(this.slipSequence);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.slipDate));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.slipTime));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.cancelType));
		sb.append(", ");
		sb.append(this.slipAmount);
		sb.append(", ");
		sb.append(this.debtorAmount);
		sb.append(", ");
		sb.append(this.creditAmount);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.userId));
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
