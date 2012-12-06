package com.emflant.common;

import java.util.List;

import com.emflant.accounting.dto.table.TableInfomationDTO;
import com.emflant.accounting.dto.table.TradeMasterDTO;
import com.emflant.accounting.print.FileTest;


public class SourceFactory {
	
	private String className;
	private String tableName;
	private String beanType;
	
	public SourceFactory(){
		
	}
	

	
	public void print2(List list, String beanType){
		String newBeanType = beanType.replace("Bean", "");
		print2(list, newBeanType, "com.emflant.accounting.business.remote");
	}
	
	public void print2(List list, String beanType, String packageName){
		
		this.className = toCamelFieldName(null, beanType+"_remote");
		this.beanType = beanType;
		
		StringBuilder sb = new StringBuilder(10240);

		sb.append(createHeader2(packageName, list));
		
		sb.append("\npublic interface ");
		sb.append(this.className);
		sb.append(" {");
		
		
		
		for(int i=0;i<list.size();i++){
			TradeMasterDTO tradeMasterDTO = (TradeMasterDTO)list.get(i);
			
			sb.append("\n\tvoid ");
			sb.append(tradeMasterDTO.getMethodType());
			sb.append("(");
			if(tradeMasterDTO.getParameterType() == null){
				sb.append(") throws EntException;");
			} else {
				sb.append(tradeMasterDTO.getParameterType());
				sb.append(" input) throws EntException;");
			}

			
		}
		
		sb.append("\n");
		
		sb.append("}");
		
		EntLogger.debug(sb.toString());
		
		//파일로 생성한다.
		FileTest ft = new FileTest(this.className, packageName);
		ft.test(sb.toString());
	}
	
	
	
	public void print(List list, String tableName){
		print(list, tableName, "com.emflant.accounting.adapter.table");
	}
	
	public void print(List list, String tableName, String packageName){
		
		this.className = toCamelFieldName(null, tableName);
		this.tableName = tableName;
		setOtherInfo(list);
		
		StringBuilder sb = new StringBuilder(10240);

		sb.append(createHeader(packageName));
		
		sb.append("\npublic class ");
		sb.append(this.className);
		sb.append(" implements EntTable {\n\n");
		
		//필드 생성한다.
		sb.append(createFields(list));
		
		sb.append("\n");
		
		//생성자 생성한다.
		sb.append(createConstructor(list));
		
		sb.append("\n");
		
		//get, set 메소드 생성한다.
		sb.append(createMethods(list));
		
		//insert 메소드 생성한다.
		sb.append(createInsertMethod(list));
		
		sb.append("\n");
		sb.append("}");
		
		//Logger.debug(sb.toString());
		
		//파일로 생성한다.
		FileTest ft = new FileTest(this.className, packageName);
		ft.test(sb.toString());
	}
	
	
	private void setOtherInfo(List list){
		for(int i=0;i<list.size();i++){
			TableInfomationDTO tableDTO = (TableInfomationDTO)list.get(i);
			
			getFieldType(tableDTO);
			
			tableDTO.setFieldName(toCamelFieldName(tableDTO.getColumnName()));
			tableDTO.setSetMethodName(toCamelFieldName("set", tableDTO.getColumnName()));
			tableDTO.setGetMethodName(toCamelFieldName("get", tableDTO.getColumnName()));
			
		}
	}
	
	private void setOtherInfo2(List list){
		for(int i=0;i<list.size();i++){
			TableInfomationDTO tableDTO = (TableInfomationDTO)list.get(i);
			
			getFieldType(tableDTO);
			
			tableDTO.setFieldName(toCamelFieldName(tableDTO.getColumnName()));
			tableDTO.setSetMethodName(toCamelFieldName("set", tableDTO.getColumnName()));
			tableDTO.setGetMethodName(toCamelFieldName("get", tableDTO.getColumnName()));
			
		}
	}
	
	private StringBuilder createHeader(String packageName){
		StringBuilder sb = new StringBuilder(1024);
		
		sb.append("package ");
		sb.append(packageName);
		sb.append(";\n");
		sb.append("\n");
		sb.append("import java.math.BigDecimal;\n");
		sb.append("import java.math.BigInteger;\n");
		sb.append("import com.emflant.accounting.annotation.EntNotNull;\n");
		sb.append("import com.emflant.common.EntCommon;\n");
		sb.append("\n");
		
		return sb;
	}

	private StringBuilder createHeader2(String packageName, List list){
		StringBuilder sb = new StringBuilder(1024);
		
		sb.append("package ");
		sb.append(packageName);
		sb.append(";\n");
		sb.append("\n");
		sb.append("import com.emflant.common.EntException;\n");
		
		for(int i=0;i<list.size();i++){
			TradeMasterDTO tradeMasterDTO = (TradeMasterDTO)list.get(i);
			
			if(tradeMasterDTO.getParameterType() != null || 
					!tradeMasterDTO.getParameterType().equals("String")){
				continue;
			} else {
				sb.append("import com.emflant.accounting.dto.screen.");
				sb.append(tradeMasterDTO.getParameterType());
				sb.append(";\n");
			}
		}
		sb.append("\n");
		
		return sb;
	}
	
	
	private StringBuilder createFields(List list){
		StringBuilder sb = new StringBuilder(1024);

		for(int i=0;i<list.size();i++){
			TableInfomationDTO tableDTO = (TableInfomationDTO)list.get(i);
			
			if(tableDTO.getIsNullable().equals("NO")){
				sb.append("\t@EntNotNull\n");
			}
			
			sb.append("\tprotected ");
			sb.append(tableDTO.getFieldType());
			sb.append(" ");
			sb.append(tableDTO.getFieldName());
			sb.append(";\n");
		}

		return sb;
	}
	
	private StringBuilder createConstructor(List list){
		
		StringBuilder sb = new StringBuilder(1024);
		
		sb.append("\tpublic ");
		sb.append(this.className);
		sb.append("(){\n");
		

		for(int i=0;i<list.size();i++){
			TableInfomationDTO tableDTO = (TableInfomationDTO)list.get(i);
			
			if(tableDTO.getColumnDefault() == null){
				continue;
			}
			
			sb.append("\t\tthis.");
			sb.append(tableDTO.getFieldName());
			sb.append(" = ");
			sb.append(tableDTO.getDefaultValue());
			sb.append(";\n");
		}

		sb.append("\t}\n");
		
		return sb;
	}
	
	private StringBuilder createMethods(List list){
		StringBuilder sb = new StringBuilder(1024);


		for(int i=0;i<list.size();i++){
			TableInfomationDTO tableDTO = (TableInfomationDTO)list.get(i);
			
			sb.append("\tpublic void ");
			sb.append(tableDTO.getSetMethodName());
			sb.append("(");
			sb.append(tableDTO.getFieldType());
			sb.append(" ");
			sb.append(tableDTO.getFieldName());
			sb.append("){\n");
			sb.append("\t\tthis.");
			sb.append(tableDTO.getFieldName());
			sb.append(" = ");
			sb.append(tableDTO.getFieldName());
			sb.append(";\n");
			sb.append("\t}\n");
			

			sb.append("\tpublic ");
			sb.append(tableDTO.getFieldType());
			sb.append(" ");
			sb.append(tableDTO.getGetMethodName());
			sb.append("(){\n");

			sb.append("\t\treturn this.");
			sb.append(tableDTO.getFieldName());
			sb.append(";\n");
			sb.append("\t}\n");

		}

		return sb;
	}
	
	private StringBuilder createInsertMethod(List list){
		StringBuilder sb = new StringBuilder(1024);
		
		sb.append("\tpublic StringBuilder insert(){\n");
		sb.append("\t\tStringBuilder sb = new StringBuilder(1024);\n");
		sb.append("\t\tsb.append(\"INSERT INTO ");
		sb.append(this.tableName);
		sb.append(" (\");\n");
		
		for(int i=0;i<list.size();i++){
			TableInfomationDTO tableDTO = (TableInfomationDTO)list.get(i);
			
			sb.append("\t\tsb.append(\"");
			sb.append(tableDTO.getColumnName());
			if(i != list.size()-1){
				sb.append(", ");
			}
			
			sb.append("\");\n");
		}

		sb.append("\t\tsb.append(\") VALUES (\");\n");
		
		for(int i=0;i<list.size();i++){
			TableInfomationDTO tableDTO = (TableInfomationDTO)list.get(i);
			
			sb.append("\t\tsb.append(");
			
			if(tableDTO.getFieldType().equals("String")){
				sb.append("EntCommon.convertQuery(this.");
				sb.append(tableDTO.getFieldName());
				sb.append(")");
			} else {
				sb.append("this.");
				sb.append(tableDTO.getFieldName());
			}

			sb.append(");\n");
			
			if(i != list.size()-1){
				sb.append("\t\tsb.append(\", \");\n");
			}
		}
		
		sb.append("\t\tsb.append(\")\");\n");
		sb.append("\t\treturn sb;\n");
		
		sb.append("\t}\n");
		
		return sb;
	}
	
	public void getFieldType(TableInfomationDTO tableDTO){
		
		String dataType = tableDTO.getDataType();
		String columnDefault = tableDTO.getColumnDefault();
		
		String result = null;
		String strDefaultValue = null;
		if(dataType.equals("varchar")){
			
			if(columnDefault != null){
				strDefaultValue = EntCommon.convertQuery(columnDefault,"\"");
			}
			result = "String";
		} else if(dataType.equals("decimal")){
			if(columnDefault != null){
				strDefaultValue = "BigDecimal.valueOf("+columnDefault+")";
			}
			result = "BigDecimal";
		} else if(dataType.equals("int")){
			if(columnDefault != null){
				strDefaultValue = columnDefault;
			}
			result = "Integer";
		} else if(dataType.equals("bigint")){
			if(columnDefault != null){
				//strDefaultValue = "BigInteger.valueOf("+columnDefault+")";
				strDefaultValue = columnDefault+"L";
			}
			result = "Long";
		} 

		tableDTO.setFieldType(result);
		tableDTO.setDefaultValue(strDefaultValue);
	}

	public String toFirstUpper(String str){
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	public String toFirstLower(String str){
		return str.substring(0,1).toLowerCase() + str.substring(1);
	}
	
	public String toCamelFieldName(String strColumnLabel){
		String strResult = toCamelFieldName(null, strColumnLabel);
		return toFirstLower(strResult);
	}
	
	public String toCamelFieldName(String prefix, String strColumnLabel){
		int fromIndex = 0;
		int toIndex = 0;
		
		StringBuffer sb = new StringBuffer(128);
		if(prefix != null) sb.append(prefix);

		while(true){
			
			toIndex = strColumnLabel.indexOf("_", fromIndex);
			
			if(toIndex == -1){
				sb.append(toFirstUpper(strColumnLabel.substring(fromIndex)));
				break;
			} else {
				sb.append(toFirstUpper(strColumnLabel.substring(fromIndex, toIndex)));
			}
			fromIndex = toIndex + 1;
		}
		
		return sb.toString();
	}
	

	

}
