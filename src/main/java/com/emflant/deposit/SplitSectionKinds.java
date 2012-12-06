package com.emflant.deposit;

import java.util.List;

/**
 * 구간을 나눈다.
 * 옵저버패턴으로 순차적으로 구간적용한다.
 * @author nuri
 * @version 1.0
 * @updated 25-2-2011 오후 5:25:31
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