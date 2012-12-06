package com.emflant.zexecute;

import com.emflant.common.EntLogger;
import com.emflant.interest.dto.InterestCalculateInputDTO;
import com.emflant.interest.split.Interest;

/**
 * 
 * 
 * @author nuri
 * @version 1.1
 * @created 09-3-2011 오후 4:43:09
 */
public class ZInterestExecute {

	/**
	 * main 함수
	 */
	public static void main(String[] args){
		
		EntLogger.debug("이자계산에 필요한 입력값 넣는다.");
		
		InterestCalculateInputDTO interestCalculateInputDTO = new InterestCalculateInputDTO();
		interestCalculateInputDTO.setAccount("01131234567");
		interestCalculateInputDTO.setCalculateData("20110301");
		Interest interest = new Interest();
		interest.execute(interestCalculateInputDTO);
		
	}
	/*
	 * 1.1 
	 * - MakeTableAccount, TableAccountDTO 클래스 추가
	 * 
	 * 
	 * 
	 */

}