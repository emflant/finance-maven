package com.emflant.counsel;

import com.emflant.common.EntLogger;

public class Counsel {
	
	/**
	 * [���� ���� ���ڰ��]
	 * �Ʒ��� ���� ������ ����Ѵ�.
	 * 1.���ڰ��
	 * 2.���ݰ��
	 * @param counselDTO
	 */
	public void execute(CounselDTO counselDTO){
		EntLogger.debug("����� ������.");
		
		//1.���ڰ��
		CalculateInterest ci = new CalculateInterest();
		ci.calculate(counselDTO);
		
		//2.���ݰ��
		tax(counselDTO);
	}
	
	private void tax(CounselDTO counselDTO){
		CalculateTax ct = new CalculateTax();
		ct.calculate(counselDTO);
	}
	
	
}
