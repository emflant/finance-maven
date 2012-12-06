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
	 * �⺻������ DB�� ����� ���� �����ͷ� ���ڰ���Ҷ� ����Ѵ�.
	 */
	public List<TableAccountDTO> make(){
		return null;
	}
	
	/**
	 * �׽�Ʈ�� ������ ���� ������ ���ڰ���Ҷ� ����Ѵ�.
	 */
	public List<TableAccountDTO> make(XSSFWorkbook wb) {
		// TODO Auto-generated method stub
		EntLogger.debug("���¿��� ������  �����.");
		list = new ArrayList<TableAccountDTO>();
		
		for(Row row : wb.getSheetAt(3)){
        	
        	//���� 3���� �ǳʶڴ�.(������ĸ��� �ٸ���)
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
		EntLogger.info("����������������������������������������������������������������������������������������������������������������������������������������������");
		EntLogger.info(String.format(pattern
				,"account","customer","last_clc_date"));
		EntLogger.info("����������������������������������������������������������������������������������������������������������������������������������������������");
		
		for(TableAccountDTO tableAccountDTO : list){
			tableAccountDTO.display(pattern);
		}	
		
		EntLogger.info("����������������������������������������������������������������������������������������������������������������������������������������������");
	}

	public List<TableAccountDTO> getList() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	






}
