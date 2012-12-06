package com.emflant.counsel;

import com.emflant.common.EntLogger;

public class CounselFactoryTimeDeposit implements CounselFactory{

	public CounselInterestMethod create(CounselDTO counselDTO){
		EntLogger.debug("정기예금 상품별 계산방법을 매칭한다.");
		CounselInterestMethod method = null;

		//복리일때
		if(counselDTO.isCompoundInterest()){
			method = new CounselByTimeDepositCompound(counselDTO);
		}
		//단리일때
		else {
			method = new CounselByTimeDepositSimple(counselDTO);
		}
		
		return method;

	}
	
}
