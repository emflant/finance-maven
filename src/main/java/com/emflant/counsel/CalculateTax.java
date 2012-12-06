package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntLogger;

public class CalculateTax {
	
	private BigDecimal incomeTaxRate;
	private BigDecimal residentTaxRate;
	
	public CalculateTax(){
		//�������� �ҵ漼, �ֹμ� ������� �ٸ���.
		//�ҵ漼�� 10% �ΰ͵� ���ھ׿� 2% �� �ֹμ�.. ������ ..
		//�켱�� �Ϲݰ����� ����Ѵ�.
		Rate rate = new Rate();
		this.incomeTaxRate = rate.getIncomeTaxRate();
		this.residentTaxRate = rate.getResidentTaxRate();
	}
	
	public BigDecimal calculateIncomeTax(BigDecimal giveInterest){
		BigDecimal incomeTax = giveInterest.multiply(incomeTaxRate).setScale(-1, BigDecimal.ROUND_DOWN);
		EntLogger.debug("�ҵ漼 = "+incomeTax.toPlainString());
		return incomeTax;
	}
	
	public BigDecimal calculateResidentTax(BigDecimal incomeTax){
		BigDecimal residentTax = incomeTax.multiply(residentTaxRate).setScale(-1, BigDecimal.ROUND_DOWN);
		EntLogger.debug("�ֹμ� = "+residentTax.toPlainString());
		return residentTax;
	}
	
	public void calculate(CounselDTO counselDTO){
		
		BigDecimal incomeTax = calculateIncomeTax(counselDTO.getGiveInterest());
		counselDTO.setIncomeTax(incomeTax);
		
		BigDecimal residentTax = calculateResidentTax(incomeTax);
		counselDTO.setResidentTax(residentTax);
	}
	
}
