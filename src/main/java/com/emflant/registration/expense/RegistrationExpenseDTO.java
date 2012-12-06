package com.emflant.registration.expense;

import java.math.BigDecimal;
import java.util.HashMap;

public class RegistrationExpenseDTO {
	
	//등기원인
	private String groundsOfRegistration;
	
	//채권최고액
	private BigDecimal bondSommeMaximale;
	
	//등록면허세
	private BigDecimal registrationTax;
	
	//부동산 개수
	private int realEstateCount;

	//지방교육세 = 등록면허세 x 20%
	private BigDecimal localEducationTax;
	
	//등기신청수수료 = 부동산개수 x 14,000원
	private BigDecimal registrationApplyFee;
	
	//인지세
	private BigDecimal stampTax;
	
	/**
	 * hashmap 으로 입력받아 입력값을 셋팅한다.
	 * @param input
	 */
	public RegistrationExpenseDTO(HashMap<String, Object> input){
		setGroundsOfRegistration((String)input.get("type"));
	}
	
	
	public String getGroundsOfRegistration() {
		return groundsOfRegistration;
	}

	public void setGroundsOfRegistration(String groundsOfRegistration) {
		this.groundsOfRegistration = groundsOfRegistration;
	}

	public BigDecimal getBondSommeMaximale() {
		return bondSommeMaximale;
	}

	public void setBondSommeMaximale(BigDecimal bondSommeMaximale) {
		this.bondSommeMaximale = bondSommeMaximale;
	}

	public BigDecimal getRegistrationTax() {
		return registrationTax;
	}

	public void setRegistrationTax(BigDecimal registrationTax) {
		this.registrationTax = registrationTax;
	}

	public int getRealEstateCount() {
		return realEstateCount;
	}

	public void setRealEstateCount(int realEstateCount) {
		this.realEstateCount = realEstateCount;
	}
	
	public BigDecimal getLocalEducationTax() {
		return localEducationTax;
	}

	public void setLocalEducationTax(BigDecimal localEducationTax) {
		this.localEducationTax = localEducationTax;
	}

	public BigDecimal getRegistrationApplyFee() {
		return registrationApplyFee;
	}

	public void setRegistrationApplyFee(BigDecimal registrationApplyFee) {
		this.registrationApplyFee = registrationApplyFee;
	}

	public BigDecimal getStampTax() {
		return stampTax;
	}

	public void setStampTax(BigDecimal stampTax) {
		this.stampTax = stampTax;
	}
	
	public void validate(){
		
	}
	
}
