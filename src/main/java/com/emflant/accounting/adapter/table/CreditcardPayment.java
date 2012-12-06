package com.emflant.accounting.adapter.table;

import java.math.BigDecimal;
import java.math.BigInteger;
import com.emflant.accounting.annotation.EntNotNull;
import com.emflant.common.EntCommon;


public class CreditcardPayment implements EntTable {

	@EntNotNull
	protected String accountNo;
	@EntNotNull
	protected String toDate;
	@EntNotNull
	protected String fromDate;
	@EntNotNull
	protected String repaymentDay;
	@EntNotNull
	protected String paymentEndDay;

	public CreditcardPayment(){
	}

	public void setAccountNo(String accountNo){
		this.accountNo = accountNo;
	}
	public String getAccountNo(){
		return this.accountNo;
	}
	public void setToDate(String toDate){
		this.toDate = toDate;
	}
	public String getToDate(){
		return this.toDate;
	}
	public void setFromDate(String fromDate){
		this.fromDate = fromDate;
	}
	public String getFromDate(){
		return this.fromDate;
	}
	public void setRepaymentDay(String repaymentDay){
		this.repaymentDay = repaymentDay;
	}
	public String getRepaymentDay(){
		return this.repaymentDay;
	}
	public void setPaymentEndDay(String paymentEndDay){
		this.paymentEndDay = paymentEndDay;
	}
	public String getPaymentEndDay(){
		return this.paymentEndDay;
	}
	public StringBuilder insert(){
		StringBuilder sb = new StringBuilder(1024);
		sb.append("INSERT INTO creditcard_payment (");
		sb.append("account_no, ");
		sb.append("to_date, ");
		sb.append("from_date, ");
		sb.append("repayment_day, ");
		sb.append("payment_end_day");
		sb.append(") VALUES (");
		sb.append(EntCommon.convertQuery(this.accountNo));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.toDate));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.fromDate));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.repaymentDay));
		sb.append(", ");
		sb.append(EntCommon.convertQuery(this.paymentEndDay));
		sb.append(")");
		return sb;
	}

}
