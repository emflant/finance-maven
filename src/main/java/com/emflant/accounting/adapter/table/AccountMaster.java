package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class AccountMaster implements EntTable {

	@EntNotNull
	protected String accountNo;
	@EntNotNull
	protected String accountType;
	@EntNotNull
	protected String accountStatus;
	@EntNotNull
	protected BigDecimal balance;
	protected String remarks;
	@EntNotNull
	protected String newDate;
	@EntNotNull
	protected String lastTradeDate;
	@EntNotNull
	protected Integer lastTradeSequence;
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

	public AccountMaster(){
		this.balance = BigDecimal.valueOf(0);
	}

	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}
	public String getAccountNo(){
		return this.accountNo;
	}
	public void setAccountType(String accountType){
		this.accountType = accountType;
	}
	public String getAccountType(){
		return this.accountType;
	}
	public void setAccountStatus(String accountStatus){
		this.accountStatus = accountStatus;
	}
	public String getAccountStatus(){
		return this.accountStatus;
	}
	public void setBalance(BigDecimal balance){
		this.balance = balance;
	}
	public BigDecimal getBalance(){
		return this.balance;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
	}
	public String getRemarks(){
		return this.remarks;
	}
	public void setNewDate(String newDate){
		this.newDate = newDate;
	}
	public String getNewDate(){
		return this.newDate;
	}
	public void setLastTradeDate(String lastTradeDate){
		this.lastTradeDate = lastTradeDate;
	}
	public String getLastTradeDate(){
		return this.lastTradeDate;
	}
	public void setLastTradeSequence(Integer lastTradeSequence){
		this.lastTradeSequence = lastTradeSequence;
	}
	public Integer getLastTradeSequence(){
		return this.lastTradeSequence;
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
		sb.append("INSERT INTO account_master (");
		sb.append("account_no, ");
		sb.append("account_type, ");
		sb.append("account_status, ");
		sb.append("balance, ");
		sb.append("remarks, ");
		sb.append("new_date, ");
		sb.append("last_trade_date, ");
		sb.append("last_trade_sequence, ");
		sb.append("user_id, ");
		sb.append("register_user_id, ");
		sb.append("register_date_time, ");
		sb.append("last_register_user_id, ");
		sb.append("last_register_date_time");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.accountNo));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.accountType));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.accountStatus));
		sb.append(", ");
		sb.append(this.balance);
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.remarks));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.newDate));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.lastTradeDate));
		sb.append(", ");
		sb.append(this.lastTradeSequence);
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
