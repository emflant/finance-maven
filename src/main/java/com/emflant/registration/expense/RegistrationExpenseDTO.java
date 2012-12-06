package com.emflant.registration.expense;

import java.math.BigDecimal;
import java.util.HashMap;

public class RegistrationExpenseDTO {
	
	//������
	private String groundsOfRegistration;
	
	//ä���ְ��
	private BigDecimal bondSommeMaximale;
	
	//��ϸ��㼼
	private BigDecimal registrationTax;
	
	//�ε��� ����
	private int realEstateCount;

	//���汳���� = ��ϸ��㼼 x 20%
	private BigDecimal localEducationTax;
	
	//����û������ = �ε��갳�� x 14,000��
	private BigDecimal registrationApplyFee;
	
	//������
	private BigDecimal stampTax;
	
	/**
	 * hashmap ���� �Է¹޾� �Է°��� �����Ѵ�.
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
