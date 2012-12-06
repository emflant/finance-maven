package com.emflant.interest.data;

import com.emflant.common.EntDate;
import com.emflant.interest.dto.InterestCalculateResultDTO;

public interface TableSplitDTO {
	
	/**
	 * ���ڰ�� ��������
	 * @return
	 */
	public EntDate getFromDate();
	
	/**
	 * ���ڰ�� ��������
	 * @return
	 */
	public EntDate getToDate();
	
	/**
	 * ���Ͽ� �´� ������� �����Ѵ�.
	 * @param pattern
	 */
	public void display(String pattern);
	
	/**
	 * ���ڰ�곻���� ���ǰ��� ���� ���氪�� �����Ѵ�.<br/>
	 * ���� ��� �����濡 ���� � Ư�� �ñ��� �����Ը� ���ݿ�븦<br/>
	 * ���ڰ�곻���� �ݿ��Ҷ� �ݿ��Ǹ� true �̹ݿ��� false.
	 * 
	 * @param interestCalculateDetailDTO
	 * @return
	 */
	public boolean put(InterestCalculateResultDTO interestCalculateDetailDTO);
}
