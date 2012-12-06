package com.emflant.counsel;

import com.emflant.common.EntLogger;

public class Counsel {
	
	/**
	 * [수신 상담시 이자계산]
	 * 아래와 같은 순서로 계산한다.
	 * 1.이자계산
	 * 2.세금계산
	 * @param counselDTO
	 */
	public void execute(CounselDTO counselDTO){
		EntLogger.debug("상담결과 계산시작.");
		
		//1.이자계산
		CalculateInterest ci = new CalculateInterest();
		ci.calculate(counselDTO);
		
		//2.세금계산
		tax(counselDTO);
	}
	
	private void tax(CounselDTO counselDTO){
		CalculateTax ct = new CalculateTax();
		ct.calculate(counselDTO);
	}
	
	
}
