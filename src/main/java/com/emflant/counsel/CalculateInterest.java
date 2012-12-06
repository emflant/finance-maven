package com.emflant.counsel;

import com.emflant.common.EntLogger;

public class CalculateInterest {
	
	public void calculate(CounselDTO counselDTO){
		
		CounselFactory cf = null;
		
		if(counselDTO instanceof TimeDepositDTO){
			EntLogger.debug("���⿹�� ��� ���ڰ�� ����.");
			cf = new CounselFactoryTimeDeposit();
		}
		
		else if(counselDTO instanceof PeriodicalDepositDTO){
			EntLogger.debug("�������� ��� ���ڰ�� ����.");
			cf = new CounselFactoryPeriodicalDeposit();
		}
		
		CounselInterestMethod cm = cf.create(counselDTO);
		
		//�������ڰ�����
		cm.result();
	}
}
