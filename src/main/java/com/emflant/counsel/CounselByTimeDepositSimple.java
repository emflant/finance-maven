package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntLogger;

public class CounselByTimeDepositSimple extends CounselInterestMethod {
	
	public CounselByTimeDepositSimple(CounselDTO counselDTO){
		super(counselDTO);
	}
	
	public void display() {
		// TODO Auto-generated method stub
		EntLogger.info("���⿹�� �ܸ����ڰ��");
	}

	@Override
	public BigDecimal calculateTotalGiveInterest() {
		// TODO Auto-generated method stub
		EntLogger.debug("���⿹�� �ܸ����ڰ�� ������� ����.");
		
		
		//���� X �ش�����(��) X ����/12
		BigDecimal interestMonths = counselDTO.getAmount().
			multiply(counselDTO.getGiveInterestRate()).
			multiply(BigDecimal.valueOf(counselDTO.getTermMonths())).
			divide(BigDecimal.valueOf(12),0,BigDecimal.ROUND_DOWN);
		
		EntLogger.debug("[�ܸ�]������� = "+interestMonths);
		
		//���� X �ش�����(��) X �ϼ�/365
		BigDecimal interestDays = counselDTO.getAmount().
			multiply(counselDTO.getGiveInterestRate()).
			multiply(BigDecimal.valueOf(counselDTO.getTermDays())).
			divide(BigDecimal.valueOf(365),0,BigDecimal.ROUND_DOWN);
		
		EntLogger.debug("[�ܸ�]�ϼ���� = "+interestDays);
		
		EntLogger.debug("���������� = "+interestMonths.add(interestDays));
		
		return interestMonths.add(interestDays);
	}

}
