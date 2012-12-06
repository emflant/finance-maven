package com.emflant.interest.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emflant.common.EntDate;
import com.emflant.common.EntLogger;

/**
 * 일별잔액 리스트를 만든다.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 오후 4:43:07
 */
public class MakeTableDailyBalance implements MakeTable<TableDailyBalanceDTO> {

	private List<TableDailyBalanceDTO> list;

	/**
	 * 기본적으로 DB에 저장된 실제 데이터로 이자계산할때 사용한다.
	 */
	public List<TableDailyBalanceDTO> make(){
		XSSFWorkbook wb = ReadTable.getConnect();
		return make(wb);
	}

	/**
	 * 엑셀에서 읽어와 리스트를 만든다.
	 */
	public List<TableDailyBalanceDTO> make(XSSFWorkbook wb) {
		// TODO Auto-generated method stub
		EntLogger.debug("일별잔액명세 리스트를 만든다.");
		list = new ArrayList<TableDailyBalanceDTO>();

		for(Row row : wb.getSheetAt(0)){
        	
        	//위의 3줄은 건너뛴다.(엑셀양식마다 다르게)
        	if(row.getRowNum() < 3){
        		continue;
        	}
        	
        	TableDailyBalanceDTO tableDailyBalanceDTO = new TableDailyBalanceDTO();
        	tableDailyBalanceDTO.setTradeDate(new EntDate((row.getCell(1)).getStringCellValue()));
        	tableDailyBalanceDTO.setOpenBalance(BigDecimal.valueOf((row.getCell(2)).getNumericCellValue()));
        	tableDailyBalanceDTO.setCloseBalance(BigDecimal.valueOf((row.getCell(3)).getNumericCellValue()));
        	tableDailyBalanceDTO.setInputAmount(BigDecimal.valueOf((row.getCell(4)).getNumericCellValue()));
        	tableDailyBalanceDTO.setOutAmount(BigDecimal.valueOf((row.getCell(5)).getNumericCellValue()));
        	
        	list.add(tableDailyBalanceDTO);
        }
		return list;
	}

	
	public List<TableDailyBalanceDTO> make(List<HashMap<String, Object>> list) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void display(){
		
		String pattern = "%10s%15s%15s%15s%15s";
		EntLogger.info("───────────────────────────────────────────────────────────────────────");
		EntLogger.info(String.format(pattern
				,"date","open","close","input","output"));
		EntLogger.info("───────────────────────────────────────────────────────────────────────");
		
		for(TableDailyBalanceDTO tableDailyBalanceDTO : list){
			tableDailyBalanceDTO.display(pattern);
		}	
		
		EntLogger.info("───────────────────────────────────────────────────────────────────────");
	}
	



}