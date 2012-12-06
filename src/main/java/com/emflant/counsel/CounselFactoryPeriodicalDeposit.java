package com.emflant.counsel;

import com.emflant.common.EntLogger;

public class CounselFactoryPeriodicalDeposit implements CounselFactory {
	
	public CounselInterestMethod create(CounselDTO counselDTO){

		EntLogger.debug("�������� ��ǰ�� ������� ��Ī�Ѵ�.");
		CounselInterestMethod method = null;
		
		method = new CounselByPeriodicalDeposit(counselDTO);
		
		return method;

	}
}
