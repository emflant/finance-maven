package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntDate;

public abstract class CounselDTO {
	
	
	//�Է°�
	private String product;				//��ǰ
	private BigDecimal amount;			//���� - ���⿹��(��ġ��), ��������(�Ѻ��Ա�)
	private BigDecimal payAmount;		//�����Աݾ�
	private int termMonths;				//���Ⱓ(��)
	private int termDays;				//���Ⱓ(��)
	private EntDate newDate;			//�ű�����
	private boolean compoundInterest;	//�ܺ�������

	//�����
	private BigDecimal giveInterest;
	private BigDecimal incomeTax;
	private BigDecimal residentTax;
	private BigDecimal giveInterestRate;
	private BigDecimal incomeTaxRate;
	private BigDecimal residentTaxRate;
	
	public String getProduct() {
		return product;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
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
	public EntDate getNewDate() {
		return newDate;
	}
	public void setNewDate(EntDate newDate) {
		this.newDate = newDate;
	}
	
	/**
	 * �ܺ�������
	 * �����̸� true �ܸ��̸� false
	 * @return
	 */
	public boolean isCompoundInterest() {
		return compoundInterest;
	}
	
	/**
	 * �ܺ�������
	 * �����̸� true �ܸ��̸� false
	 * @param compoundInterest
	 */
	public void setCompoundInterest(boolean compoundInterest) {
		this.compoundInterest = compoundInterest;
	}
	
	public BigDecimal getGiveInterest() {
		return giveInterest;
	}
	public void setGiveInterest(BigDecimal giveInterest) {
		this.giveInterest = giveInterest;
	}
	public BigDecimal getIncomeTax() {
		return incomeTax;
	}
	public void setIncomeTax(BigDecimal incomeTax) {
		this.incomeTax = incomeTax;
	}
	public BigDecimal getResidentTax() {
		return residentTax;
	}
	public void setResidentTax(BigDecimal residentTax) {
		this.residentTax = residentTax;
	}
	public BigDecimal getGiveInterestRate() {
		return giveInterestRate;
	}
	public void setGiveInterestRate(BigDecimal giveInterestRate) {
		this.giveInterestRate = giveInterestRate;
	}
	public BigDecimal getIncomeTaxRate() {
		return incomeTaxRate;
	}
	public void setIncomeTaxRate(BigDecimal incomeTaxRate) {
		this.incomeTaxRate = incomeTaxRate;
	}
	public BigDecimal getResidentTaxRate() {
		return residentTaxRate;
	}
	public void setResidentTaxRate(BigDecimal residentTaxRate) {
		this.residentTaxRate = residentTaxRate;
	}	
	public BigDecimal getTotalTax(){
		return incomeTax.add(residentTax);
	}
	
	
	public BigDecimal getBfTaxTotalAmount(){
		return getAmount().add(giveInterest);
	}	
	public BigDecimal getAfTaxTotalAmount(){
		return getBfTaxTotalAmount().subtract(getTotalTax());
	}
	
	
	/**
	 * ���ݾ�(��ǥ�ݾ�)���� �ԷµǾ����� true
	 * �����Ա����� �ԷµǾ����� false
	 * 
	 * @return
	 */
	public boolean isAmount(){
		boolean t = false;
		if(amount != null) t = true;
		return t;
	}
	
	
	abstract public void displayResult();
	
}
