package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntLogger;

public class CalculateTax {
	
	private BigDecimal incomeTaxRate;
	private BigDecimal residentTaxRate;
	
	public CalculateTax(){
		//과세따라 소득세, 주민세 계산방법이 다르다.
		//소득세에 10% 인것도 이자액에 2% 인 주민세.. 기준을 ..
		//우선은 일반과세로 계산한다.
		Rate rate = new Rate();
		this.incomeTaxRate = rate.getIncomeTaxRate();
		this.residentTaxRate = rate.getResidentTaxRate();
	}
	
	public BigDecimal calculateIncomeTax(BigDecimal giveInterest){
		BigDecimal incomeTax = giveInterest.multiply(incomeTaxRate).setScale(-1, BigDecimal.ROUND_DOWN);
		EntLogger.debug("소득세 = "+incomeTax.toPlainString());
		return incomeTax;
	}
	
	public BigDecimal calculateResidentTax(BigDecimal incomeTax){
		BigDecimal residentTax = incomeTax.multiply(residentTaxRate).setScale(-1, BigDecimal.ROUND_DOWN);
		EntLogger.debug("주민세 = "+residentTax.toPlainString());
		return residentTax;
	}
	
	public void calculate(CounselDTO counselDTO){
		
		BigDecimal incomeTax = calculateIncomeTax(counselDTO.getGiveInterest());
		counselDTO.setIncomeTax(incomeTax);
		
		BigDecimal residentTax = calculateResidentTax(incomeTax);
		counselDTO.setResidentTax(residentTax);
	}
	
}
