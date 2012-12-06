package com.emflant.counsel;

import com.emflant.common.EntLogger;

public class CalculateInterest {
	
	public void calculate(CounselDTO counselDTO){
		
		CounselFactory cf = null;
		
		if(counselDTO instanceof TimeDepositDTO){
			EntLogger.debug("정기예금 상담 이자계산 시작.");
			cf = new CounselFactoryTimeDeposit();
		}
		
		else if(counselDTO instanceof PeriodicalDepositDTO){
			EntLogger.debug("정기적금 상담 이자계산 시작.");
			cf = new CounselFactoryPeriodicalDeposit();
		}
		
		CounselInterestMethod cm = cf.create(counselDTO);
		
		//지급이자계산실행
		cm.result();
	}
}
