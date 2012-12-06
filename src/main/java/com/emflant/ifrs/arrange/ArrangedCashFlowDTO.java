package com.emflant.ifrs.arrange;

import java.math.BigDecimal;
import java.util.HashMap;


/**
 * 유효이자율에 의한 이자수익인식 및 이연부대수익상각 계획표의<br/>
 * 레코드 데이터를 저장할 클래스.
 *
 * @author nuri
 *
 */
class ArrangedCashFlowDTO extends CashFlowDTO {
	
	/**
	 * 유효이자율
	 */
	private BigDecimal effectiveInterestRate;
	
	/**
	 * 상각전원가
	 */
	private BigDecimal balBeforeAmortization;
	
	/**
	 * 상각후원가
	 */
	private BigDecimal balAfterAmortization;
	
	/**
	 * 유효이자
	 */
	private BigDecimal effectiveInterest;
	
	/**
	 * 상각액
	 */
	private BigDecimal amortizationAmount;
	
	/**
	 * 생성자
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
