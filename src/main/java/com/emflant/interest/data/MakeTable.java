package com.emflant.interest.data;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface MakeTable<T> {
	
	/**
	 * �⺻������ DB�� ����� ���� �����ͷ� ���ڰ���Ҷ� ����Ѵ�.
	 */
	public List<T> make();
	
	
	/**
	 * �׽�Ʈ�� ������ ���� ������ ���ڰ���Ҷ� ����Ѵ�.
	 */
	public List<T> make(XSSFWorkbook wb);
	
	
	/**
	 * ������ �����ͷ� ���ڰ�� �Ҷ� ����Ѵ�.
	 */
	public List<T> make(List<HashMap<String, Object>> list);
	
	
	/**
	 * ���̺��� �����͸� Display �Ѵ�.
	 */
	public void display();
	
}
