package com.emflant.ifrs.arrange;

import java.math.BigDecimal;
import java.util.HashMap;


/**
 * ��ȿ�������� ���� ���ڼ����ν� �� �̿��δ���ͻ� ��ȹǥ��<br/>
 * ���ڵ� �����͸� ������ Ŭ����.
 *
 * @author nuri
 *
 */
class ArrangedCashFlowDTO extends CashFlowDTO {
	
	/**
	 * ��ȿ������
	 */
	private BigDecimal effectiveInterestRate;
	
	/**
	 * ��������
	 */
	private BigDecimal balBeforeAmortization;
	
	/**
	 * ���Ŀ���
	 */
	private BigDecimal balAfterAmortization;
	
	/**
	 * ��ȿ����
	 */
	private BigDecimal effectiveInterest;
	
	/**
	 * �󰢾�
	 */
	private BigDecimal amortizationAmount;
	
	/**
	 * ������
	 * @param bal
	 * @param interest
	 * @param fee
	 */
	public ArrangedCashFlowDTO(CashFlowDTO cashFlowDTO){
		super(cashFlowDTO.getBal(), cashFlowDTO.getInterest(), cashFlowDTO.getFee());
	}

	public BigDecimal getEffectiveInterestRate() {
		return effectiveInterestRate;
	}

	public void setEffectiveInterestRate(BigDecimal effectiveInterestRate) {
		this.effectiveInterestRate = effectiveInterestRate;
	}

	public BigDecimal getBalBeforeAmortization() {
		return balBeforeAmortization;
	}

	public void setBalBeforeAmortization(BigDecimal balBeforeAmortization) {
		this.balBeforeAmortization = balBeforeAmortization;
	}

	public BigDecimal getBalAfterAmortization() {
		return balAfterAmortization;
	}

	public void setBalAfterAmortization(BigDecimal balAfterAmortization) {
		this.balAfterAmortization = balAfterAmortization;
	}

	public BigDecimal getEffectiveInterest() {
		return effectiveInterest;
	}

	public void setEffectiveInterest(BigDecimal effectiveInterest) {
		this.effectiveInterest = effectiveInterest;
	}

	public BigDecimal getAmortizationAmount() {
		return amortizationAmount;
	}

	public void setAmortizationAmount(BigDecimal amortizationAmount) {
		this.amortizationAmount = amortizationAmount;
	}
	
	public void calculate(){
		this.effectiveInterest = this.balBeforeAmortization.multiply(this.effectiveInterestRate).setScale(0,BigDecimal.ROUND_DOWN);
		this.amortizationAmount = this.effectiveInterest.subtract(super.interest);
		this.balAfterAmortization = this.balBeforeAmortization.add(this.amortizationAmount);
	}
	
	public String display(){
		return balBeforeAmortization+", "+effectiveInterestRate+", "+effectiveInterest+
			", "+interest+", "+amortizationAmount+", "+balAfterAmortization;
	}
	
	public HashMap<String, Object> transferToHashMap(){
		
		HashMap<String, Object> hmResult = super.transferToHashMap();
		
		hmResult.put("effectiveInterestRate"	, getEffectiveInterestRate());
		hmResult.put("balBeforeAmortization"	, getBalBeforeAmortization());
		hmResult.put("balAfterAmortization"		, getBalAfterAmortization());
		hmResult.put("effectiveInterest"		, getEffectiveInterest());
		hmResult.put("amortizationAmount"		, getAmortizationAmount());
		
		return hmResult;
	}
	
}
