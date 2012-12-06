package com.emflant.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.dto.screen.A03CreditCardPaymentMainInsert01DTO;
import com.emflant.common.EntException;

public class HttpUrlConnection {

	

	/**
	 * 
	 * @return
	 */
	public DefaultTableModel getCardPaymentDetails(String strStatus) {
		// TODO Auto-generated method stub
		DefaultTableModel lResult = new DefaultTableModel();
		
		String[] header = {"key", "status", "trade_type", "cash_amount", "credit_card"
				, "total_amount", "reckon_date", "reckon_time","remarks"};
		lResult.setColumnIdentifiers(header);
		
		URL url;
		try {
			url = new URL("http://emflant02.appspot.com/show.details?status="+strStatus);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDefaultUseCaches(false);
			
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			printWriter.flush();
			printWriter.close();
			
			InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
            String line = null;
            
            boolean start = false;
            
			for(int i=1;(line=bufferedReader.readLine())!=null;i++){
				
				if(line.equals("<body>")){
					start = true;
					continue;
				}
				if(line.equals("</body>")){
					start = false;
					break;
				}
				
				if(start){
					if(line.length() == 0) continue;
					String[] strArray = line.split("\\|");
					
					lResult.addRow(strArray);
				}
			}
			      
			bufferedReader.close();
			inputStreamReader.close();            
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lResult;

	}


	/**
	 * 
	 * @return
	 */
	public void updateOneMoney(String key, String status) throws EntException {

		URL url;
		try {
			url = new URL("http://emflant02.appspot.com/update.details?key="+key+"&status="+status);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDefaultUseCaches(false);
			
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			printWriter.flush();
			printWriter.close();
			
			InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			bufferedReader.close();
			inputStreamReader.close();        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	/**
	 * 
	 * @return
	 */
	List<A03CreditCardPaymentMainInsert01DTO> getCardPaymentDetails2() {
		// TODO Auto-generated method stub
		List<A03CreditCardPaymentMainInsert01DTO> list = new ArrayList<A03CreditCardPaymentMainInsert01DTO>();
		DefaultTableModel lResult = new DefaultTableModel();
		
		URL url;
		try {
			url = new URL("http://emflant02.appspot.com/show.details");
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setDefaultUseCaches(false);
			
			PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
			printWriter.flush();
			printWriter.close();
			
			InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
            //BufferedInputStream in= new BufferedInputStream(httpURLConnection.getInputStream());    
            //new 
            //BufferedReader bufferedReader = new BufferedReader(httpURLConnection.getInputStream());

            String line = null;
            
            boolean start = false;
            
            
            
            A03CreditCardPaymentMainInsert01DTO inputDTO = null;
			for(int i=1;(line=bufferedReader.readLine())!=null;i++){
				
				if(line.equals("<body>")){
					start = true;
					continue;
				}
				if(line.equals("</body>")){
					start = false;
					break;
				}
				
				if(start){
					if(line.length() == 0) continue;
					String[] strArray = line.split("\\|");
					
					
					inputDTO = new A03CreditCardPaymentMainInsert01DTO();
					
					inputDTO.setTradeType(strArray[0]);						// 비용계정
					inputDTO.setCashAmount(new BigDecimal(strArray[1]));	// 현금금액
					
					if(strArray[2].equals("씨티카드")){
						inputDTO.setAccountNo(null);
					} else if(strArray[2].equals("롯데카드")){
						inputDTO.setAccountNo(null);
					}
					
					inputDTO.setTotalAmount(new BigDecimal(strArray[3]));
					inputDTO.setReckonDate(strArray[4]);
					inputDTO.setRemarks(strArray[6]);
					
					list.add(inputDTO);
				}

			}
			      
			bufferedReader.close();
			inputStreamReader.close();            
			
            //System.out.println(sbBufferedWriter.toString());
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;

	}

}
