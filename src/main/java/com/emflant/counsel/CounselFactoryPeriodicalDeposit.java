package com.emflant.counsel;

import com.emflant.common.EntLogger;

public class CounselFactoryPeriodicalDeposit implements CounselFactory {
	
	public CounselInterestMethod create(CounselDTO counselDTO){

		EntLogger.debug("정기적금 상품별 계산방법을 매칭한다.");
		CounselInterestMethod method = null;
		
		method = new CounselByPeriodicalDeposit(counselDTO);
		
		return method;

	}
}
