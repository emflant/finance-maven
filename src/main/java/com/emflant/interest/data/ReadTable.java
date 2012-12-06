package com.emflant.interest.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emflant.common.EntLogger;

public class ReadTable {
	
	private static XSSFWorkbook wb;

	private ReadTable(){
		EntLogger.debug("¿¢¼¿ ÆÄÀÏÀ» ÀÐ´Â´Ù.");
		File file = new File("./InterestCalaulateTable.xlsx");
		
		//¿¢¼¿ ÆÄÀÏ ¿ÀÇÂ
		try {
			wb = new XSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static XSSFWorkbook getConnect(){
		if(wb == null){
			new ReadTable();
		}
		return wb;
	}
}
