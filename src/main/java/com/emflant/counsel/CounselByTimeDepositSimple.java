package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntLogger;

public class CounselByTimeDepositSimple extends CounselInterestMethod {
	
	public CounselByTimeDepositSimple(CounselDTO counselDTO){
		super(counselDTO);
	}
	
	public void display() {
		// TODO Auto-generated method stub
		EntLogger.info("정기예금 단리이자계산");
	}

	@Override
	public BigDecimal calculateTotalGiveInterest() {
		// TODO Auto-generated method stub
		EntLogger.debug("정기예금 단리이자계산 방법으로 선택.");
		
		
		//원금 X 해당이율(연) X 월수/12
		BigDecimal interestMonths = counselDTO.getAmount().
			multiply(counselDTO.getGiveInterestRate()).
			multiply(BigDecimal.valueOf(counselDTO.getTermMonths())).
			divide(BigDecimal.valueOf(12),0,BigDecimal.ROUND_DOWN);
		
		EntLogger.debug("[단리]월수계산 = "+interestMonths);
		
		//원금 X 해당이율(연) X 일수/365
		BigDecimal interestDays = counselDTO.getAmount().
			multiply(counselDTO.getGiveInterestRate()).
			multiply(BigDecimal.valueOf(counselDTO.getTermDays())).
			divide(BigDecimal.valueOf(365),0,BigDecimal.ROUND_DOWN);
		
		EntLogger.debug("[단리]일수계산 = "+interestDays);
		
		EntLogger.debug("총지급이자 = "+interestMonths.add(interestDays));
		
		return interestMonths.add(interestDays);
	}

}
