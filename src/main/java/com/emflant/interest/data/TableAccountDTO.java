package com.emflant.interest.data;

import java.text.SimpleDateFormat;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;
import com.emflant.interest.dto.InterestCalculateResultDTO;

public class TableAccountDTO {
	
	
	/**
	 * ���¹�ȣ
	 */
	private String account;
	
	/**
	 * ����ȣ
	 */
	private String customer;
	
	/**
	 * �������ڰ������
	 */	
	private EntDate lastCalculateDate;
	/**
	 * ��뱸��
	 */
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
	public EntDate getLastCalculateDate() {
		return lastCalculateDate;
	}
	public void setLastCalculateDate(String lastCalculateDate) {
		this.lastCalculateDate = new EntDate(lastCalculateDate);
	}
	public void setLastCalculateDate(EntDate lastCalculateDate) {
		this.lastCalculateDate = lastCalculateDate;
	}
	
	public String getTaxClassification() {
		return taxClassification;
	}
	public void setTaxClassification(String taxClassification) {
		this.taxClassification = taxClassification;
	}
	
	public String display(){
		String pattern = "%15s%10s%15s";
		display(pattern);
		return null;
	}
	

	public void display(String pattern){
		//DecimalFormat df = new DecimalFormat("#,###");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		EntLogger.info(String.format(pattern
				, account
				, customer
				, sdf.format(lastCalculateDate.getDate())
		));
	}
	
	
	public boolean put(InterestCalculateResultDTO interestCalculateResultDTO) {
		// TODO Auto-generated method stub
		interestCalculateResultDTO.setCustomer(getCustomer());
		interestCalculateResultDTO.setTaxClassification(getTaxClassification());
		return true;
	}

	
	
}
