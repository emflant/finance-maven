package com.emflant.interest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emflant.common.EntLogger;

public class MakeTableAccount implements MakeTable<TableAccountDTO> {

	private List<TableAccountDTO> list;
	

	/**
	 * 기본적으로 DB에 저장된 실제 데이터로 이자계산할때 사용한다.
	 */
	public List<TableAccountDTO> make(){
		return null;
	}
	
	/**
	 * 테스트로 엑셀에 값을 저장후 이자계산할때 사용한다.
	 */
	public List<TableAccountDTO> make(XSSFWorkbook wb) {
		// TODO Auto-generated method stub
		EntLogger.debug("계좌원장 데이터  만든다.");
		list = new ArrayList<TableAccountDTO>();
		
		for(Row row : wb.getSheetAt(3)){
        	
        	//위의 3줄은 건너뛴다.(엑셀양식마다 다르게)
        	if(row.getRowNum() < 3){
        		continue;
        	}
        	
        	TableAccountDTO tableAccountDTO = new TableAccountDTO();
        	tableAccountDTO.setAccount((row.getCell(1)).getStringCellValue());
        	tableAccountDTO.setCustomer((row.getCell(2)).getStringCellValue());
        	tableAccountDTO.setLastCalculateDate((row.getCell(3)).getStringCellValue());
        	tableAccountDTO.setTaxClassification((row.getCell(4)).getStringCellValue());
        	list.add(tableAccountDTO);
        }
		return list;
	}

	public List<TableAccountDTO> make(List<HashMap<String, Object>> list) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void display(){
		
		String pattern = "%15s%10s%15s";
		EntLogger.info("───────────────────────────────────────────────────────────────────────");
		EntLogger.info(String.format(pattern
				,"account","customer","last_clc_date"));
		EntLogger.info("───────────────────────────────────────────────────────────────────────");
		
		for(TableAccountDTO tableAccountDTO : list){
			tableAccountDTO.display(pattern);
		}	
		
		EntLogger.info("───────────────────────────────────────────────────────────────────────");
	}

	public List<TableAccountDTO> getList() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	






}
