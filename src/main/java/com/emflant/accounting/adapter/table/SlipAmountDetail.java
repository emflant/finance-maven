package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class SlipAmountDetail implements EntTable {

	@EntNotNull
	protected String slipNo;
	@EntNotNull
	protected Integer slipSequence;
	@EntNotNull
	protected String debtorCreditType;
	@EntNotNull
	protected Integer debtorCreditSequence;
	@EntNotNull
	protected String accountType;
	@EntNotNull
	protected BigDecimal amount;
	@EntNotNull
	protected String registerUserId;
	@EntNotNull
	protected String registerDateTime;
	@EntNotNull
	protected String lastRegisterUserId;
	@EntNotNull
	protected String lastRegisterDateTime;

	public SlipAmountDetail(){
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
	public void setDebtorCreditType(String debtorCreditType){
		this.debtorCreditType = debtorCreditType;
	}
	public String getDebtorCreditType(){
		return this.debtorCreditType;
	}
	public void setDebtorCreditSequence(Integer debtorCreditSequence){
		this.debtorCreditSequence = debtorCreditSequence;
	}
	public Integer getDebtorCreditSequence(){
		return this.debtorCreditSequence;
	}
	public void setAccountType(String accountType){
		this.accountType = accountType;
	}
	public String getAccountType(){
		return this.accountType;
	}
	public void setAmount(BigDecimal amount){
		this.amount = amount;
	}
	public BigDecimal getAmount(){
		return this.amount;
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
		sb.append("INSERT INTO slip_amount_detail (");
		sb.append("slip_no, ");
		sb.append("slip_sequence, ");
		sb.append("debtor_credit_type, ");
		sb.append("debtor_credit_sequence, ");
		sb.append("account_type, ");
		sb.append("amount, ");
		sb.append("register_user_id, ");
		sb.append("register_date_time, ");
		sb.append("last_register_user_id, ");
		sb.append("last_register_date_time");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.slipNo));
		sb.append(", ");
		sb.append(this.slipSequence);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.debtorCreditType));
		sb.append(", ");
		sb.append(this.debtorCreditSequence);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.accountType));
		sb.append(", ");
		sb.append(this.amount);
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
