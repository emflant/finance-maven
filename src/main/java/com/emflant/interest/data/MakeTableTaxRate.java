package com.emflant.interest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;

/**
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 오후 4:43:07
 */
public class MakeTableTaxRate implements MakeTable<TableSplitDTO> {

	private List<TableSplitDTO> list;


	public List<TableSplitDTO> make(){
		return null;
		
	}
	
	
	public void display(){
		
		String pattern = "%10s%12s%8s%8s";
		EntLogger.info("───────────────────────────────────────");
		EntLogger.info(String.format(pattern
				,"from","to","inctxrt","rstxrt"));
		EntLogger.info("───────────────────────────────────────");
		
		for(TableSplitDTO tableDTO : list){
			tableDTO.display(pattern);
		}
		
		EntLogger.info("───────────────────────────────────────");
	}



	public List<TableSplitDTO> make(XSSFWorkbook wb) {
		// TODO Auto-generated method stub
		EntLogger.debug("세율변동내역 리스트를 만든다.");
		list = new ArrayList<TableSplitDTO>();

		for(Row row : wb.getSheetAt(2)){
        	
        	//위의 3줄은 건너뛴다.(엑셀양식마다 다르게)
        	if(row.getRowNum() < 3){
        		continue;
        	}
        	if(!(row.getCell(3)).getStringCellValue().equals("1")){
        		//continue;
        	}
        	
        	TableTaxRateDTO tableTaxRateDTO = new TableTaxRateDTO();
        	tableTaxRateDTO.setFromDate(new EntDate((row.getCell(1)).getStringCellValue()));
        	tableTaxRateDTO.setToDate(new EntDate((row.getCell(2)).getStringCellValue()));
        	tableTaxRateDTO.setTaxClassification((row.getCell(3)).getStringCellValue());
        	tableTaxRateDTO.setIncomeTaxRate((row.getCell(4)).getNumericCellValue());
        	tableTaxRateDTO.setResidentTaxRate((row.getCell(5)).getNumericCellValue());
        	list.add(tableTaxRateDTO);
        	
        	
        }
		return list;
	}


	public List<TableSplitDTO> make(List<HashMap<String, Object>> list) {
		// TODO Auto-generated method stub
		return null;
	}

}