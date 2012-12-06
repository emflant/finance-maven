package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntLogger;

public class CounselByTimeDepositCompound extends CounselInterestMethod {
	
	public CounselByTimeDepositCompound(CounselDTO counselDTO){
		super(counselDTO);
	}
	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		EntLogger.info("���⿹�� �������ڰ��");
	}

	@Override
	public BigDecimal calculateTotalGiveInterest() {
		// TODO Auto-generated method stub
		EntLogger.debug("���⿹�ݺ������ڰ�� ������� ����.");
				

		//���� X {(1+������/12)^n - 1}
		//���� ������ --> {(������/12+1)^n - 1} x ����
		BigDecimal interestMonths = 
			counselDTO.getGiveInterestRate().divide(BigDecimal.valueOf(12),RATE_SCALE,BigDecimal.ROUND_DOWN).
			add(BigDecimal.valueOf(1)).
			pow(counselDTO.getTermMonths()).
			setScale(RATE_SCALE, BigDecimal.ROUND_DOWN).
			subtract(BigDecimal.valueOf(1)).
			multiply(counselDTO.getAmount()).
			setScale(AMOUNT_SCALE, BigDecimal.ROUND_DOWN);
		
		EntLogger.debug("[����]������� = "+interestMonths);
		
		// ( ���� + ���������� ) x ������ x ����ϼ� /365
		BigDecimal interestDays = counselDTO.getAmount().
			add(interestMonths).
			multiply(counselDTO.getGiveInterestRate()).
			multiply(BigDecimal.valueOf(counselDTO.getTermDays())).
			divide(BigDecimal.valueOf(365),AMOUNT_SCALE,BigDecimal.ROUND_DOWN);
		
		EntLogger.debug("[����]�ϼ���� = "+interestDays);
		EntLogger.debug("������ = "+interestMonths.add(interestDays));
		
		return interestMonths.add(interestDays);
	}

}
