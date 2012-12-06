package com.emflant.interest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;

/**
 * �������� ��������Ʈ�� ���Ѵ�.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 ���� 4:43:07
 */
public class MakeTableInterestRate implements MakeTable<TableSplitDTO> {

	private List<TableSplitDTO> list;

	public List<TableSplitDTO> make(){
		return null;
	}
	
	
	public List<TableSplitDTO> make(XSSFWorkbook wb) {
		// TODO Auto-generated method stub
		EntLogger.debug("������������ ����Ʈ�� �����.");
		list = new ArrayList<TableSplitDTO>();

		for(Row row : wb.getSheetAt(1)){
        	
        	//���� 3���� �ǳʶڴ�.(������ĸ��� �ٸ���)
        	if(row.getRowNum() < 3){
        		continue;
        	}
        	
        	TableInterestRateDTO tableInterestRateDTO = new TableInterestRateDTO();
        	tableInterestRateDTO.setFromDate(new EntDate((row.getCell(1)).getStringCellValue()));
        	tableInterestRateDTO.setToDate(new EntDate((row.getCell(2)).getStringCellValue()));
        	tableInterestRateDTO.setRate((row.getCell(3)).getNumericCellValue());

        	list.add(tableInterestRateDTO);
        	
        	
        }
		
		return list;
	}


	public List<TableSplitDTO> make(List<HashMap<String, Object>> list) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void display(){
		
		String pattern = "%10s%12s%8s";
		EntLogger.info("��������������������������������������������������������������������");
		EntLogger.info(String.format(pattern
				,"from","to","rate"));
		EntLogger.info("��������������������������������������������������������������������");
		
		for(TableSplitDTO tableDTO : list){
			tableDTO.display(pattern);
		}	
		
		EntLogger.info("��������������������������������������������������������������������");
	}

}