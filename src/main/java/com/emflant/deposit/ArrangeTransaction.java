package com.emflant.deposit;

import java.util.List;

/**
 * 이자계산내역을 만든다.
 * 
 * - 이자계산시작일자
 * - 이자계산종료일자
 * - 일수
 * - 잔액
 * - 이율
 * - 이자
 * - 소득세
 * - 주민세
 * - 농특세
 * - 기타세
 * - 세금합계
 * - 소득세율
 * - 주민세율
 * - 농특세율
 * - 기타세율
 * - 차감지급액
 * @author home
 * @version 1.0
 * @updated 25-2-2011 오후 5:25:29
 */
public class ArrangeTransaction {

	private String baseDate;
	private List transaction;

	/**
	 * 거래내역을 만들어서 아예입력값으로 줄때
	 * 거래내역을 조회할필요없을때 사용한다.
	 * 계산종료일자가 필요한이유는 마지막계산내역을 만들기위해.
	 * 
	 * @param transaction
	 * @param toDate
	 */
	public ArrangeTransaction(List transaction, String toDate){

	}

	/**
	 * 계산내역으로 배열한다.
	 */
	public List arrange(){
		return null;
	}

	/**
	 * 이자계산구간을 구한다.
	 */
	public List getIntrestCalculateSection(){
		return null;
	}


}