package com.emflant.deposit;

import java.util.List;


/**
 * @author nuri
 * @version 1.0
 * @created 22-2-2011 ���� 1:31:45
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
	 * ���ڰ�걸�� ������ �ʿ��� ���� �ִ´�.
	 * - ��ȸ��������
	 * - ��ȸ��������
	 * - ��ǰ����
	 * - ���¹�ȣ
	 * 
	 * 
	 * @param accountInfo
	 */
	public List start(AccountDTO accountInfo){
		return null;
	}

}