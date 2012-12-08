package com.emflant.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.annotation.EntDisplay;
import com.emflant.accounting.annotation.EntNotNull;

public class EntCommon {
	
	public static String convertQuery(String str){
		return convertQuery(str, "'");
	}
	
	public static String convertQuery(String str, String strFlag){
		
		if(str == null || str.trim().length() == 0){
			return "NULL";
		} else {
			return strFlag+str+strFlag;
		}
	}
	
	public static String convertFieldToSetMethod(String fieldName){
		StringBuffer sb = new StringBuffer(128);
		sb.append("set");
		sb.append(toFirstUpper(fieldName));
		return sb.toString();
	}
	public static String convertFieldToGetMethod(String fieldName){
		StringBuffer sb = new StringBuffer(128);
		sb.append("get");
		sb.append(toFirstUpper(fieldName));
		return sb.toString();
	}
	public static String toFirstUpper(String str){
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	

	@SuppressWarnings("unchecked")
	public static void isNull(Object obj) throws EntException {
		
		Class clazz = obj.getClass();
		
		//상속받는 DTO 는 직접적으로 field 선언이 없으므로, super class 의 정보를 들고와 체크를 한다.
		if(clazz.getName().indexOf("com.emflant.accounting.dto.table") != -1){
			try {
				clazz = Class.forName("com.emflant.accounting.adapter.table."+clazz.getSimpleName().replace("DTO", ""));
			} catch (ClassNotFoundException e) {
				throw new EntException("table dto 패키지 경로를 확인해주세요.");
			}
		}
		
		//Logger.debug("isNull :" + clazz.getDeclaredFields().length);
		for(Field field : clazz.getDeclaredFields()){
			//Logger.debug("field :" +field.getName());
			Annotation annotation = field.getAnnotation(EntNotNull.class);
			
			if(annotation == null) continue;
			
			String strMethodName = EntCommon.convertFieldToGetMethod(field.getName());
			try {
				Class[] paramTypes = { };
				Object[] paramObjs = { };
				
				Method method = clazz.getMethod(strMethodName, paramTypes);
				Object object = method.invoke(obj, paramObjs);
				
				//Logger.debug(object.toString());
				if(object == null){
					throw new EntException("["+clazz.getSimpleName()+"] "+field.getName()+ " 필드에 값이 NULL 입니다.");
				} else if(object.toString().length() == 0){
					throw new EntException("["+clazz.getSimpleName()+"] "+field.getName()+ " 필드에 길이가 0 입니다.");
				}
				
			} 
			catch (EntException e){
				throw e;
			}
			
			catch (Exception e) {
				throw new EntException("["+e.getClass().getSimpleName()+"] "+e.getMessage());
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static String display(Object obj) {
		
		Class clazz = obj.getClass();
		StringBuilder sbResult = new StringBuilder(1024);
		
		sbResult.append("business input value : ");
		
		//Logger.debug("isNull :" + clazz.getDeclaredFields().length);
		for(Field field : clazz.getDeclaredFields()){

			Annotation annotation = field.getAnnotation(EntDisplay.class);
			if(annotation == null) continue;
			
			sbResult.append(field.getName());
			sbResult.append("=");

			String strMethodName = EntCommon.convertFieldToGetMethod(field.getName());
			try {
				Class[] paramTypes = { };
				Object[] paramObjs = { };
				
				Method method = clazz.getMethod(strMethodName, paramTypes);
				Object object = method.invoke(obj, paramObjs);
				
				sbResult.append(object);
				sbResult.append(", ");

			} 

			
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		//sbResult.lastIndexOf(",");
		
		return sbResult.toString();
	}
	
	public static int getColumnIndex2(JTable table, String columnName) throws EntException {
		int result = 0;
		try{
			result = table.getColumnModel().getColumnIndex(columnName);
		} catch (Exception e) {
			throw new EntException(e.getMessage());
		}
		
		
		return result;
	}
	
	
	public static Object getValueSelectedRow2(JTable table, String identifier){
		return getValueAt2(table, identifier, table.getSelectedRow());
	}
	
	public static Object getValueAt2(JTable table, String identifier, int row){
		//Logger.debug("getValueAt : "+identifier+", "+row);
		DefaultTableModel dtm = (DefaultTableModel)table.getModel();
		if(dtm.findColumn(identifier) == -1){
			EntLogger.error("테이블정보에 "+identifier+" 필드가 존재하지 않습니다.");
			return null;
		}
		return dtm.getValueAt(row, dtm.findColumn(identifier));
	}
	
	public static String formatAmount(BigDecimal amount){
		
		DecimalFormat nf = new DecimalFormat("#,##0.##");
		return nf.format(amount);
		
	}
	

}
