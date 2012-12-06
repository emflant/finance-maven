package com.emflant.interest.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;

/**
 * ���ڰ�곻�� DTO.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 ���� 4:43:06
 */
public class InterestCalculateResultDTO {

	/**
	 * ��������
	 */
	private EntDate fromDate;
	/**
	 * ��������
	 */
	private EntDate toDate;
	/**
	 * ����
	 */
	private int termMonths;
	/**
	 * �ϼ�
	 */
	private int termDays;
	
	/**
	 * �ܾ�
	 */
	private BigDecimal balance;
	
	/**
	 * ��������
	 */
	private double interestRate;
	/**
	 * �ҵ漼��
	 */
	private double incomeTaxRate;
	/**
	 * �ֹμ���
	 */
	private double residentTaxRate;
	
	/**
	 * ����ȣ
	 */
	private String customer;
	
	/**
	 * ��뱸��
	 */	
	private String taxClassification;
	
	public EntDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(EntDate fromDate) {
		this.fromDate = fromDate;
	}
	public EntDate getToDate() {
		return toDate;
	}
	public void setToDate(EntDate toDate) {
		this.toDate = toDate;
	}
	public int getTermMonths() {
		return termMonths;
	}
	public void setTermMonths(int termMonths) {
		this.termMonths = termMonths;
	}
	public int getTermDays() {
		return termDays;
	}
	public void setTermDays(int termDays) {
		this.termDays = termDays;
	}
	public double getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	public double getIncomeTaxRate() {
		return incomeTaxRate;
	}
	public void setIncomeTaxRate(double incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}
	public double getResidentTaxRate() {
		return residentTaxRate;
	}
	public void setResidentTaxRate(double residentTaxRate) {
		this.residentTaxRate = residentTaxRate;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getTaxClassification() {
		return taxClassification;
	}
	public void setTaxClassification(String taxClassification) {
		this.taxClassification = taxClassification;
	}
	
	public void display(){
		display("%10s%12s%10s%15s%8s%8s%5s", 1);
	}
	
	public void display(String pattern, int cnt){
		DecimalFormat df1 = new DecimalFormat("#,###");
		DecimalFormat df2 = new DecimalFormat("0.000");
		DecimalFormat df3 = new DecimalFormat("00");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		EntLogger.info(String.format(pattern
				, df3.format(cnt)
				, sdf.format(fromDate.getDate())
				, sdf.format(toDate.getDate())
				, df1.format(termDays)
				, df1.format(balance)
				, df2.format(interestRate)
				, df2.format(incomeTaxRate)
				, df2.format(residentTaxRate)
				, getCustomer()
				, getTaxClassification()
		));
	}



}