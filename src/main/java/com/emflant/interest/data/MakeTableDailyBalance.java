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
 * �Ϻ��ܾ� ����Ʈ�� �����.
 * @author nuri
 * @version 1.0
 * @created 09-3-2011 ���� 4:43:07
 */
public class MakeTableDailyBalance implements MakeTable<TableDailyBalanceDTO> {

	private List<TableDailyBalanceDTO> list;

	/**
	 * �⺻������ DB�� ����� ���� �����ͷ� ���ڰ���Ҷ� ����Ѵ�.
	 */
	public List<TableDailyBalanceDTO> make(){
		XSSFWorkbook wb = ReadTable.getConnect();
		return make(wb);
	}

	/**
	 * �������� �о�� ����Ʈ�� �����.
	 */
	public List<TableDailyBalanceDTO> make(XSSFWorkbook wb) {
		// TODO Auto-generated method stub
		EntLogger.debug("�Ϻ��ܾ׸� ����Ʈ�� �����.");
		list = new ArrayList<TableDailyBalanceDTO>();

		for(Row row : wb.getSheetAt(0)){
        	
        	//���� 3���� �ǳʶڴ�.(������ĸ��� �ٸ���)
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
		EntLogger.info("����������������������������������������������������������������������������������������������������������������������������������������������");
		EntLogger.info(String.format(pattern
				,"date","open","close","input","output"));
		EntLogger.info("����������������������������������������������������������������������������������������������������������������������������������������������");
		
		for(TableDailyBalanceDTO tableDailyBalanceDTO : list){
			tableDailyBalanceDTO.display(pattern);
		}	
		
		EntLogger.info("����������������������������������������������������������������������������������������������������������������������������������������������");
	}
	



}