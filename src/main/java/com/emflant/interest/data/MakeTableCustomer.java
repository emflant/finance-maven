package com.emflant.interest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emflant.common.EntLogger;

public class MakeTableCustomer implements MakeTable<TableSplitDTO> {

	private List<TableSplitDTO> list;

	public List<TableSplitDTO> make(){
		return null;
	}
	
	public List<TableSplitDTO> make(XSSFWorkbook wb) {
		EntLogger.debug("고객변동내역 리스트를 만든다.");
		list = new ArrayList<TableSplitDTO>();

		for(Row row : wb.getSheetAt(4)){
        	
        	//위의 3줄은 건너뛴다.(엑셀양식마다 다르게)
        	if(row.getRowNum() < 3){
        		continue;
        	}
        	
        	TableCustomerDTO tableCustomerDTO = new TableCustomerDTO();
        	tableCustomerDTO.setAccount((row.getCell(1)).getStringCellValue());
        	tableCustomerDTO.setCustomer((row.getCell(2)).getStringCellValue());
        	tableCustomerDTO.setFromDate((row.getCell(3)).getStringCellValue());
        	tableCustomerDTO.setToDate((row.getCell(4)).getStringCellValue());
        	tableCustomerDTO.setTaxClassification((row.getCell(5)).getStringCellValue());

        	list.add(tableCustomerDTO);
        }
		
		return list;
	}

	public List<TableSplitDTO> make(List<HashMap<String, Object>> list) {
		return null;
	}
	
	public void display(){
		
		String pattern = "%10s%12s%11s";
		EntLogger.info("──────────────────────────────────");
		EntLogger.info(String.format(pattern
				,"account","bf_cust","chg_date", "bf_tax_cf"));
		EntLogger.info("──────────────────────────────────");
		
		for(TableSplitDTO tableDTO : list){
			tableDTO.display(pattern);
		}
		
		EntLogger.info("──────────────────────────────────");
	}

	



}
