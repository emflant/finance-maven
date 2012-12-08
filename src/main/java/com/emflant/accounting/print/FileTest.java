package com.emflant.accounting.print;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import com.emflant.common.EntLogger;

public class FileTest {
	
	private String fileName;
	private String packageName;
	
	public FileTest(String fileName, String packageName){
		this.fileName = fileName;
		this.packageName = packageName;
	}
	
	public void test(String source) {
		
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			
			
			StringReader stringReader = new StringReader(source);
			bufferedReader = new BufferedReader(stringReader);
			
			String line = null;
			String strPackage = this.packageName.replace(".", "\\");
			String pathName = "src\\"+strPackage + "\\" +fileName+".java";
			
			File outputfile = new File(pathName);

			if(outputfile.exists()){
				outputfile.delete();
			}
			outputfile.createNewFile();
			
			
			FileWriter fileWriter = new FileWriter(outputfile, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			
			for(int i=1;(line=bufferedReader.readLine())!=null;i++){
				bufferedWriter.append(line);
				bufferedWriter.newLine();
			}
			
			EntLogger.debug(pathName+" 파일생성 완료.");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(bufferedReader != null) bufferedReader.close();
				if(bufferedWriter != null) bufferedWriter.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
