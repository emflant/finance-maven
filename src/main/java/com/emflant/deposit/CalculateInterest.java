package com.emflant.deposit;

import java.util.List;


/**
 * - �����ڿ��� ������ڸ� �Է¹޴´�.
 * - ���ڰ�� �Է°� ������ �ΰ����� ������.
 *   1. ������� �Ϻ��ܾ� ����Ʈ�� �ƿ� �Է¹޴� ���.
 *   2. ���¹�ȣ�� �Է¹޴´�.
 * 
 * - ��������
 *   1. �ŷ��� Ⱦ���� �����Ѵ�.
 *   2. ���ڰ�걸���� ������.
 *   3. ������ ���ڰ��/���ݰ���� �Ѵ�.
 * @author home
 * @version 1.0
 * @updated 25-2-2011 ���� 5:25:30
 */
public class CalculateInterest {

	/**
	 * ����������
	 */
	private String account;
	private String baseDate;
	private AccountDTO accountInfo;
	private List transactions;

	/**
	 * ���������ڸ� �Է��ϴ� ������.
	 * 
	 * @param account
	 */
	public CalculateInterest(String account){

	}

	/**
	 * ���¹�ȣ�� �Է°����� �޾� ���ڰ���Ѵ�.
	 * �����̼��������ķ� ���ڰ���Ҷ� ����Ѵ�.
	 * 
	 * @param account
	 */
	public double calculate(String account){
		return 0;
	}

	/**
	 * ���ڰ���Ѵ�.
	 * 
	 * @param account
	 * @param transaction
	 */
	public double calculate(String account, List transaction){
		return 0;
	}

}