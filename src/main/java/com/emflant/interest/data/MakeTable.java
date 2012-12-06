package com.emflant.interest.data;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface MakeTable<T> {
	
	/**
	 * 기본적으로 DB에 저장된 실제 데이터로 이자계산할때 사용한다.
	 */
	public List<T> make();
	
	
	/**
	 * 테스트로 엑셀에 값을 저장후 이자계산할때 사용한다.
	 */
	public List<T> make(XSSFWorkbook wb);
	
	
	/**
	 * 임의의 데이터로 이자계산 할때 사용한다.
	 */
	public List<T> make(List<HashMap<String, Object>> list);
	
	
	/**
	 * 테이블의 데이터를 Display 한다.
	 */
	public void display();
	
}
