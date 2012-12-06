package com.emflant.interest.data;

import com.emflant.common.EntDate;
import com.emflant.interest.dto.InterestCalculateResultDTO;

public class TableCustomerDTO implements TableSplitDTO {
	
	private String account;
	private String customer;
	private EntDate fromDate;
	private EntDate toDate;
	private String taxClassification;
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public EntDate getFromDate() {
		// TODO Auto-generated method stub
		return fromDate;
	}
	public void setFromDate(EntDate fromDate) {
		this.fromDate = fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = new EntDate(fromDate);
	}
	public EntDate getToDate() {
		// TODO Auto-generated method stub
		return toDate;
	}
	public void setToDate(EntDate toDate) {
		this.toDate = toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = new EntDate(toDate);
	}
	public String getTaxClassification() {
		return taxClassification;
	}
	public void setTaxClassification(String taxClassification) {
		this.taxClassification = taxClassification;
	}
	
	public String display() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void display(String pattern) {
		// TODO Auto-generated method stub
		
	}


	public boolean put(InterestCalculateResultDTO interestCalculateDetailDTO) {
		// TODO Auto-generated method stub
		interestCalculateDetailDTO.setCustomer(getCustomer());
		interestCalculateDetailDTO.setTaxClassification(getTaxClassification());
		return true;
	}

}
