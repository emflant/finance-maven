package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class Account implements EntTable {

	@EntNotNull
	protected String accountType;
	@EntNotNull
	protected String accountTypeName;
	@EntNotNull
	protected String bsPlType;
	@EntNotNull
	protected String bsPlDetailType;
	@EntNotNull
	protected BigDecimal debtorAmount;
	@EntNotNull
	protected BigDecimal creditAmount;
	@EntNotNull
	protected String newYn;
	@EntNotNull
	protected String depositYn;
	@EntNotNull
	protected String withdrawYn;

	public Account(){
	}

	public void setAccountType(String accountType){
		this.accountType = accountType;
	}
	public String getAccountType(){
		return this.accountType;
	}
	public void setAccountTypeName(String accountTypeName){
		this.accountTypeName = accountTypeName;
	}
	public String getAccountTypeName(){
		return this.accountTypeName;
	}
	public void setBsPlType(String bsPlType){
		this.bsPlType = bsPlType;
	}
	public String getBsPlType(){
		return this.bsPlType;
	}
	public void setBsPlDetailType(String bsPlDetailType){
		this.bsPlDetailType = bsPlDetailType;
	}
	public String getBsPlDetailType(){
		return this.bsPlDetailType;
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
	public void setNewYn(String newYn){
		this.newYn = newYn;
	}
	public String getNewYn(){
		return this.newYn;
	}
	public void setDepositYn(String depositYn){
		this.depositYn = depositYn;
	}
	public String getDepositYn(){
		return this.depositYn;
	}
	public void setWithdrawYn(String withdrawYn){
		this.withdrawYn = withdrawYn;
	}
	public String getWithdrawYn(){
		return this.withdrawYn;
	}
	public StringBuilder insert(){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("INSERT INTO account (");
		sb.append("account_type, ");
		sb.append("account_type_name, ");
		sb.append("bs_pl_type, ");
		sb.append("bs_pl_detail_type, ");
		sb.append("debtor_amount, ");
		sb.append("credit_amount, ");
		sb.append("new_yn, ");
		sb.append("deposit_yn, ");
		sb.append("withdraw_yn");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.accountType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.accountTypeName));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.bsPlType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.bsPlDetailType));
		sb.append(", ");
		sb.append(this.debtorAmount);
		sb.append(", ");
		sb.append(this.creditAmount);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.newYn));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.depositYn));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.withdrawYn));
		sb.append(")");
		return sb;
	}

}
