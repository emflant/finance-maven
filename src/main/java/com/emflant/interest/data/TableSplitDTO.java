package com.emflant.interest.data;

import com.emflant.common.EntDate;
import com.emflant.interest.dto.InterestCalculateResultDTO;

public interface TableSplitDTO {
	
	/**
	 * 이자계산 시작일자
	 * @return
	 */
	public EntDate getFromDate();
	
	/**
	 * 이자계산 종료일자
	 * @return
	 */
	public EntDate getToDate();
	
	/**
	 * 패턴에 맞는 결과값을 리턴한다.
	 * @param pattern
	 */
	public void display(String pattern);
	
	/**
	 * 이자계산내역에 조건값에 따른 변경값을 셋팅한다.<br/>
	 * 예를 들어 고객변경에 따른 어떤 특정 시기의 고객에게만 세금우대를<br/>
	 * 이자계산내역에 반영할때 반영되면 true 미반영은 false.
	 * 
	 * @param interestCalculateDetailDTO
	 * @return
	 */
	public boolean put(InterestCalculateResultDTO interestCalculateDetailDTO);
}
