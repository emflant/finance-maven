package com.emflant.deposit;

import java.util.List;


/**
 * @author nuri
 * @version 1.0
 * @created 22-2-2011 오후 1:31:45
 */
public class SplitSection {

	private List mothods;

	/**
	 * 
	 * @param listener
	 */
	public void addMethod(SplitSection listener){

	}

	/**
	 * 
	 * @param listener
	 */
	public void removeMethod(SplitSection listener){

	}

	/**
	 * 
	 * @param accountInfo
	 * @param transaction
	 */
	public List start(AccountDTO accountInfo, Transaction transaction){
		return null;
	}

	/**
	 * 이자계산구간 나눌때 필요한 값만 넣는다.
	 * - 조회시작일자
	 * - 조회종료일자
	 * - 상품유형
	 * - 계좌번호
	 * 
	 * 
	 * @param accountInfo
	 */
	public List start(AccountDTO accountInfo){
		return null;
	}

}