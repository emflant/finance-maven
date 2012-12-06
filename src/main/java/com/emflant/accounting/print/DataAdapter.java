package com.emflant.accounting.print;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;



public class DataAdapter {

	
	public void saveProperties(){
		
		
		Properties prop = new Properties();
		//FileWriter fileWriter = null;
		FileReader fileReader = null;
		
		try {
			//fileWriter = new FileWriter("data\\Accounts.txt");
			fileReader = new FileReader("data\\Accounts.txt");
			prop.load(fileReader);
			
			fileReader.close();
			
			//Enumeration<String> keys = (Enumeration<String>) prop.propertyNames();
			
			
	
			
			//prop.store(fileWriter, "Accounts Info");
			//fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void loadProperties(){
		
	}
	
	public void write(String filename, String data){
		File outputfile = new File("data\\"+filename+".txt");
		
		BufferedWriter bufferedWriter = null;
		
		try {
			outputfile.createNewFile();
			FileWriter fileWriter = new FileWriter(outputfile, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			
			bufferedWriter.write("|"+sdf.format(new Date())+data);
			bufferedWriter.newLine();
			bufferedWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	
}
