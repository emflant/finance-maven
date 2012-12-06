package com.emflant.counsel;

import com.emflant.common.EntLogger;

public class CounselFactoryTimeDeposit implements CounselFactory{

	public CounselInterestMethod create(CounselDTO counselDTO){
		EntLogger.debug("���⿹�� ��ǰ�� ������� ��Ī�Ѵ�.");
		CounselInterestMethod method = null;

		//�����϶�
		if(counselDTO.isCompoundInterest()){
			method = new CounselByTimeDepositCompound(counselDTO);
		}
		//�ܸ��϶�
		else {
			method = new CounselByTimeDepositSimple(counselDTO);
		}
		
		return method;

	}
	
}
