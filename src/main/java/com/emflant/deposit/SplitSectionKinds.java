package com.emflant.deposit;

import java.util.List;

/**
 * ������ ������.
 * �������������� ���������� ���������Ѵ�.
 * @author nuri
 * @version 1.0
 * @updated 25-2-2011 ���� 5:25:31
 */
public interface SplitSectionKinds {

	/**
	 * 
	 * @param splitSectionDTO
	 */
	public List split(SplitSectionDTO splitSectionDTO);

	/**
	 * 
	 * @param accountInfo
	 */
	public List split(AccountDTO accountInfo);

}