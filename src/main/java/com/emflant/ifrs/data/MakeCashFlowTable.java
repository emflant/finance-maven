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
 * �����帧ǥ�� �����.
 *
 * @author emflant
 *
 */
public class MakeCashFlowTable {
	
	private double rate;
	private List<HashMap<String, Object>> listCashflow;
	
	/**
	 * ������ �̿��Ͽ� �����帧ǥ�� �о�´�.
	 * 
	 * @return
	 */
	public void read(){
		
		File file = new File("./CashFlowTable.xlsx");
		XSSFWorkbook wb = null;
		
		//���� ���� ����
		try {
			wb = new XSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		listCashflow = new ArrayList<HashMap<String, Object>>();
		
		
        for(Row row : wb.getSheetAt(0)){
        	
        	//���������
        	if(row.getRowNum() == 1){
        		this.rate = row.getCell(2).getNumericCellValue();
        		continue;
        	}
        	
        	//���� 3���� �ǳʶڴ�.(������ĸ��� �ٸ���)
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
	 * �����帧ǥ�� �����Ѵ�.
	 * @return
	 */
	public List<HashMap<String, Object>> getCashFlowTable(){
		return listCashflow;
	}
	
	/**
	 * ����������� �����Ѵ�.
	 * @return
	 */
	public double getRate(){
		return rate;
	}
	

	/**
	 * �׽��� �����͸� �����.
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
