package com.emflant.deposit;

import java.util.List;


/**
 * - 생성자에서 계산일자를 입력받는다.
 * - 이자계산 입력값 유형은 두가지로 나뉜다.
 *   1. 만들어진 일별잔액 리스트를 아예 입력받는 경우.
 *   2. 계좌번호만 입력받는다.
 * 
 * - 로직순서
 *   1. 거래를 횡으로 정열한다.
 *   2. 이자계산구간을 나눈다.
 *   3. 구간별 이자계산/세금계산을 한다.
 * @author home
 * @version 1.0
 * @updated 25-2-2011 오후 5:25:30
 */
public class CalculateInterest {

	/**
	 * 계산기준일자
	 */
	private String account;
	private String baseDate;
	private AccountDTO accountInfo;
	private List transactions;

	/**
	 * 계산기준일자를 입력하는 생성자.
	 * 
	 * @param account
	 */
	public CalculateInterest(String account){

	}

	/**
	 * 계좌번호를 입력값으로 받아 이자계산한다.
	 * 최종이수일자이후로 이자계산할때 사용한다.
	 * 
	 * @param account
	 */
	public double calculate(String account){
		return 0;
	}

	/**
	 * 이자계산한다.
	 * 
	 * @param account
	 * @param transaction
	 */
	public double calculate(String account, List transaction){
		return 0;
	}

}