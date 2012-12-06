package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntDate;

public abstract class CounselDTO {
	
	
	//입력값
	private String product;				//상품
	private BigDecimal amount;			//원금 - 정기예금(예치금), 정기적금(총불입금)
	private BigDecimal payAmount;		//월불입금액
	private int termMonths;				//계약기간(월)
	private int termDays;				//계약기간(일)
	private EntDate newDate;			//신규일자
	private boolean compoundInterest;	//단복리구분

	//결과값
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
	 * 단복리구분
	 * 복리이면 true 단리이면 false
	 * @return
	 */
	public boolean isCompoundInterest() {
		return compoundInterest;
	}
	
	/**
	 * 단복리구분
	 * 복리이면 true 단리이면 false
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
	 * 계약금액(목표금액)으로 입력되었으면 true
	 * 월불입금으로 입력되었으면 false
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
