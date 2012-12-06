package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntLogger;

public class ZExecute {
	
	public static void main(String[] args){
		EntLogger.debug("상담시 입력값 설정한다.");
		
		//TD:정기예금, PD:정기적금
		String strProduct = "PD";
		BigDecimal bdAmount = new BigDecimal("16000000");
		int nTermMonths = 12;
		int nTermDays = 0;
		boolean spcp = false;
		
		CounselDTO counselDTO = null;
		
		if("PD".equals(strProduct)){
			counselDTO = new PeriodicalDepositDTO();
			counselDTO.setAmount(bdAmount);
			counselDTO.setTermMonths(nTermMonths);
		}
		
		else if("TD".equals(strProduct)){
			counselDTO = new TimeDepositDTO();
			counselDTO.setAmount(bdAmount);
			counselDTO.setTermMonths(nTermMonths);
			counselDTO.setTermDays(nTermDays);
			counselDTO.setCompoundInterest(spcp);
		}

		Counsel cc = new Counsel();
		cc.execute(counselDTO);
		counselDTO.displayResult();
		EntLogger.debug("상담결과 조회끝");
	}

}
