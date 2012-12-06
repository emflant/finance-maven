package com.emflant.deposit;

/**
 * 해당계좌 정보를 가지고 있는 클래스.
 * 계좌거래내역을 기본적으로 읽어온다.
 * @author home
 * @version 1.0
 * @created 22-2-2011 오후 1:31:43
 */
public class AccountDTO {

	private String customer;
	private String lastIntcDt;
	private int contractTerm;

	/**
	 * 
	 * @param account
	 */
	public AccountDTO(String account){

	}

	public String getCustomer(){
		return "";
	}

	/**
	 * 최종이수일(최종이자계산일)을 구한다.
	 */
	public String getLastIntcDt(){
		return "";
	}

	public int getContractTerm(){
		return 0;
	}

}