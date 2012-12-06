package com.emflant.ifrs.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 현금흐름표를 만든다.
 *
 * @author emflant
 *
 */
public class MakeCashFlowTable {
	
	private double rate;
	private List<HashMap<String, Object>> listCashflow;
	
	/**
	 * 엑셀을 이용하여 현금흐름표를 읽어온다.
	 * 
	 * @return
	 */
	public void read(){
		
		File file = new File("./CashFlowTable.xlsx");
		XSSFWorkbook wb = null;
		
		//엑셀 파일 오픈
		try {
			wb = new XSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listCashflow = new ArrayList<HashMap<String, Object>>();
		
		
        for(Row row : wb.getSheetAt(0)){
        	
        	//명목이자율
        	if(row.getRowNum() == 1){
        		this.rate = row.getCell(2).getNumericCellValue();
        		continue;
        	}
        	
        	//위의 3줄은 건너뛴다.(엑셀양식마다 다르게)
        	if(row.getRowNum() < 3){
        		continue;
        	}
        	
        	HashMap<String, Object> hmCashFlow = new HashMap<String, Object>();
        	hmCashFlow.put("bal"		, new BigDecimal((row.getCell(2)).getNumericCellValue()));
        	hmCashFlow.put("interest"	, new BigDecimal((row.getCell(3)).getNumericCellValue()));
        	hmCashFlow.put("fee"		, new BigDecimal((row.getCell(4)).getNumericCellValue()));
        	
        	//Logger.loop(hmCashFlow.display());
        	listCashflow.add(hmCashFlow);
        	
        }
        
	}
	
	/**
	 * 현금흐름표를 리턴한다.
	 * @return
	 */
	public List<HashMap<String, Object>> getCashFlowTable(){
		return listCashflow;
	}
	
	/**
	 * 명목이자율을 리턴한다.
	 * @return
	 */
	public double getRate(){
		return rate;
	}
	

	/**
	 * 테스터 데이터를 만든다.
	 * @return
	 */
	public List<HashMap<String, Object>> read2(){
		
		listCashflow = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> hmCashFlow1 = new HashMap<String, Object>();
		hmCashFlow1.put("bal", new BigDecimal("-20000000"));
		hmCashFlow1.put("interest", new BigDecimal("0"));
		hmCashFlow1.put("fee", new BigDecimal("100000"));
		listCashflow.add(hmCashFlow1);
		
		HashMap<String, Object> hmCashFlow2 = new HashMap<String, Object>();
		hmCashFlow2.put("bal", new BigDecimal("0"));
		hmCashFlow2.put("interest", new BigDecimal("1600000"));
		hmCashFlow2.put("fee", new BigDecimal("0"));
		listCashflow.add(hmCashFlow2);
		
		HashMap<String, Object> hmCashFlow3 = new HashMap<String, Object>();
		hmCashFlow3.put("bal", new BigDecimal("0"));
		hmCashFlow3.put("interest", new BigDecimal("1600000"));
		hmCashFlow3.put("fee", new BigDecimal("0"));
		listCashflow.add(hmCashFlow3);
		
		HashMap<String, Object> hmCashFlow4 = new HashMap<String, Object>();
		hmCashFlow4.put("bal", new BigDecimal("20000000"));
		hmCashFlow4.put("interest", new BigDecimal("1600000"));
		hmCashFlow4.put("fee", new BigDecimal("0"));
		listCashflow.add(hmCashFlow4);
		
		return listCashflow;
	}

}
