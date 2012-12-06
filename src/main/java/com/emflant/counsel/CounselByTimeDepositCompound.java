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
		EntLogger.info("정기예금 복리이자계산");
	}

	@Override
	public BigDecimal calculateTotalGiveInterest() {
		// TODO Auto-generated method stub
		EntLogger.debug("정기예금복리이자계산 방법으로 선택.");
				

		//원금 X {(1+연이율/12)^n - 1}
		//로직 계산순서 --> {(연이율/12+1)^n - 1} x 원금
		BigDecimal interestMonths = 
			counselDTO.getGiveInterestRate().divide(BigDecimal.valueOf(12),RATE_SCALE,BigDecimal.ROUND_DOWN).
			add(BigDecimal.valueOf(1)).
			pow(counselDTO.getTermMonths()).
			setScale(RATE_SCALE, BigDecimal.ROUND_DOWN).
			subtract(BigDecimal.valueOf(1)).
			multiply(counselDTO.getAmount()).
			setScale(AMOUNT_SCALE, BigDecimal.ROUND_DOWN);
		
		EntLogger.debug("[복리]월수계산 = "+interestMonths);
		
		// ( 원금 + 월복리이자 ) x 연이율 x 경과일수 /365
		BigDecimal interestDays = counselDTO.getAmount().
			add(interestMonths).
			multiply(counselDTO.getGiveInterestRate()).
			multiply(BigDecimal.valueOf(counselDTO.getTermDays())).
			divide(BigDecimal.valueOf(365),AMOUNT_SCALE,BigDecimal.ROUND_DOWN);
		
		EntLogger.debug("[복리]일수계산 = "+interestDays);
		EntLogger.debug("총이자 = "+interestMonths.add(interestDays));
		
		return interestMonths.add(interestDays);
	}

}
