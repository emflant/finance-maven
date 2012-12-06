package com.emflant.accounting.dto.table;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.adapter.table.EntTable;
import com.emflant.accounting.print.MySqlConnection;
import com.emflant.common.EntCommon;
import com.emflant.common.EntHashList;
import com.emflant.common.EntException;
import com.emflant.common.EntLogger;

/**
 * 
 * @author home
 *
 */
public class EntQuery {
	
	private Connection conn;
	private String schema;

	public EntQuery(String ip, String schema){
		EntLogger.info("DB connection IP :: "+ip);
		EntLogger.info("DB schema : "+schema);
		this.schema = schema;
		this.conn = MySqlConnection.getConnection(ip);
	}
	
	public String getSchema(){
		return this.schema;
	}
	
	public void setConnection(Connection conn){
		this.conn = conn;
	}
	
	public void closeConnection(){
        try {
        	if(this.conn != null) this.conn.close();
        	EntLogger.debug("Connection close success.");
        } catch (SQLException sqlEx) {
            EntLogger.debug("Connection close fail.");
        }
	}
	
	public void commit() throws SQLException {
		this.conn.commit();
	}
	public void rollback() throws SQLException {
		this.conn.rollback();
	}
	
	@SuppressWarnings("unchecked")
	public List select(Class clazz, StringBuilder sbQuery) throws EntException{
		String strQuery = sbQuery.toString();
		EntLogger.query(sbQuery.insert(0,this.schema+" : ").toString());
		
		Statement stmt = null;
		ResultSet rs = null;
		List lResult = new ArrayList<Object>();
		
		try {
			stmt = this.conn.createStatement();

			stmt.execute("USE "+this.schema);
			
		    if (stmt.execute(strQuery)) {
		        rs = stmt.getResultSet();
		    }
		    
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int nColumnCnt = rsmd.getColumnCount();
		    
		    while(rs != null && rs.next()){
		    	
		    	Object obj = clazz.newInstance();
		    	
		    	for(int i=1;i<=nColumnCnt;i++){
		    		
		    		String strColumnLabel = rsmd.getColumnLabel(i);
		    		//Logger.debug(rsmd.getColumnClassName(i)+":"+toCamelString(strColumnLabel) + ":" + rs.getObject(i));
		    		
		    		Class columnClass = Class.forName(rsmd.getColumnClassName(i));
		    		Class[] paramTypes = { columnClass };
		    		Method method = clazz.getMethod(toCamelString(strColumnLabel), paramTypes);
		    		
					Object[] parameters = { rs.getObject(i) };
					method.invoke(obj, parameters);
		    	}
		    	
		    	lResult.add(obj);
		    }
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EntException(e.getMessage());
		} 
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		return lResult;
	}


	
	public DefaultTableModel selectToTableModel(StringBuilder sbQuery) throws EntException{
		//sbQuery.insert(0, "USE finance_test;");
		String strQuery = sbQuery.toString();
		EntLogger.query(sbQuery.insert(0,this.schema+" : ").toString());
		//EntLogger.query(strQuery);
		Statement stmt = null;
		ResultSet rs = null;
		DefaultTableModel lResult = new DefaultTableModel();
		
		try {
			stmt = this.conn.createStatement();

			stmt.execute("USE "+this.schema);
			
		    if (stmt.execute(strQuery)) {
		        rs = stmt.getResultSet();
		    }
		    
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int nColumnCnt = rsmd.getColumnCount();
		    
		    String[] columns = new String[nColumnCnt];
		    
		    //헤더정보 셋팅한다.
	    	for(int i=1;i<=nColumnCnt;i++){
    			String strColumnLabel = rsmd.getColumnLabel(i);
    			columns[i-1] = strColumnLabel;
	    	}
	    	lResult.setColumnIdentifiers(columns);
		    
		    
	    	//데이터정보를 셋팅한다.
		    String[] row = null;
		    
		    while(rs != null && rs.next()){
		    	
		    	row = new String[nColumnCnt];

		    	for(int i=1;i<=nColumnCnt;i++){
		    		
		    		row[i-1] = rs.getString(i);
		    	}
		    	
		    	lResult.addRow(row);
		    	
		    }
			
			EntLogger.debug("조회결과 건수 : "+lResult.getRowCount());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EntException(e.getMessage());
		} 
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		return lResult;
	}
	
	@SuppressWarnings("unchecked")
	public EntHashList selectCodeToHashMap(StringBuilder sbQuery) throws EntException{
		//sbQuery.insert(0, "USE finance_test;");
		String strQuery = sbQuery.toString();
		EntLogger.query(sbQuery.insert(0,this.schema+" : ").toString());
		//EntLogger.query(strQuery);
		Statement stmt = null;
		ResultSet rs = null;
		
		EntHashList entHashList = new EntHashList();
		
		List lResult = entHashList.getList();
		HashMap<String, CodeDetailDTO> hashMap = entHashList.getHashMap();
		
		try {
			stmt = this.conn.createStatement();

			stmt.execute("USE "+this.schema);
		    if (stmt.execute(strQuery)) {
		        rs = stmt.getResultSet();
		    }
		    Class clazz = CodeDetailDTO.class;
		    ResultSetMetaData rsmd = rs.getMetaData();
		    int nColumnCnt = rsmd.getColumnCount();
		    
		    while(rs != null && rs.next()){
		    	
		    	Object obj = clazz.newInstance();
		    	
		    	String code = null;
		    	
		    	for(int i=1;i<=nColumnCnt;i++){
		    		
		    		String strColumnLabel = rsmd.getColumnLabel(i);

		    		if(strColumnLabel.equals("code")){
		    			code = (String)rs.getObject(i);
		    		}
		    		
		    		Class columnClass = Class.forName(rsmd.getColumnClassName(i));
		    		Class[] paramTypes = { columnClass };
		    		Method method = clazz.getMethod(toCamelString(strColumnLabel), paramTypes);
		    		
					Object[] parameters = { rs.getObject(i) };
					method.invoke(obj, parameters);
		    	}
		    	
		    	lResult.add(obj);
		    	hashMap.put(code, (CodeDetailDTO) obj);
		    }
		    EntLogger.debug("조회결과 건수 : "+lResult.size());
		    //entHashList.setHashMap(hashMap);
		    //entHashList.setList(lResult);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EntException(e.getMessage());
		} 
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }
		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		return entHashList;
	}
	
	
	private String toFirstUpper(String str){
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}
	
	private String toCamelString(String strColumnLabel){
		
		StringBuffer sb = new StringBuffer(128);
		sb.append("set");
		
		int fromIndex = 0;
		int toIndex = 0;
		
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
	

	
	public void insert(EntTable entTable) throws EntException {
		//sbQuery.insert(0, "USE finance_test");
		EntCommon.isNull(entTable);
		StringBuilder sb = entTable.insert();
		String query = sb.toString();
		EntLogger.query(sb.insert(0,this.schema+" : ").toString());
		//EntLogger.query(query);
		Statement stmt = null;
		
		try {
			stmt = this.conn.createStatement();
			stmt.execute("USE "+this.schema);
			stmt.execute(query);
			
		}		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EntException(e.getMessage());
		} 
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed
		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
	}
	

	
	public int update(StringBuilder sbQuery) throws EntException {
		//sbQuery.insert(0, "USE finance_test;");
		String strQuery = sbQuery.toString();
		EntLogger.query(sbQuery.insert(0,this.schema+" : ").toString());
		//EntLogger.query(strQuery);
		Statement stmt = null;
		int result = 0;
		
		try {
			
			stmt = this.conn.createStatement();
			stmt.execute("USE "+this.schema);
			result = stmt.executeUpdate(strQuery);
			
		}		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new EntException(e.getMessage());
		} 
		finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed
		    
		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore
		        stmt = null;
		    }
		}
		
		return result;
	}
}
