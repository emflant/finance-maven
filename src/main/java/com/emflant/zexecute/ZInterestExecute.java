package com.emflant.zexecute;

import com.emflant.common.EntLogger;
import com.emflant.interest.dto.InterestCalculateInputDTO;
import com.emflant.interest.split.Interest;

/**
 * 
 * 
 * @author nuri
 * @version 1.1
 * @created 09-3-2011 ���� 4:43:09
 */
public class ZInterestExecute {

	/**
	 * main �Լ�
	 */
	public static void main(String[] args){
		
		EntLogger.debug("���ڰ�꿡 �ʿ��� �Է°� �ִ´�.");
		
		InterestCalculateInputDTO interestCalculateInputDTO = new InterestCalculateInputDTO();
		interestCalculateInputDTO.setAccount("01131234567");
		interestCalculateInputDTO.setCalculateData("20110301");
		Interest interest = new Interest();
		interest.execute(interestCalculateInputDTO);
		
	}
	/*
	 * 1.1 
	 * - MakeTableAccount, TableAccountDTO Ŭ���� �߰�
	 * 
	 * 
	 * 
	 */

}