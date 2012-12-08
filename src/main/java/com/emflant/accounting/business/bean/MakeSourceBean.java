package com.emflant.accounting.business.bean;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.emflant.accounting.business.remote.MakesourceRemote;
import com.emflant.accounting.dto.table.TableInfomationDTO;
import com.emflant.accounting.dto.table.TradeMasterDTO;
import com.emflant.common.EntCommon;
import com.emflant.common.EntException;
import com.emflant.common.EntHashList;
import com.emflant.common.EntTransaction;

public class MakeSourceBean implements MakesourceRemote {
	private EntTransaction transaction;
	
	public MakeSourceBean(EntTransaction transaction){
		this.transaction = transaction;
	}	
	
	public void selectBeans() throws EntException {
		
		try {
			StringBuilder sb = new StringBuilder(1024);
			sb.append(" SELECT bean_type as code ");
			sb.append(" FROM trade_master ");
			sb.append(" group by bean_type ");
			EntHashList list = this.transaction.selectCodeToHashMap(sb);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	/**
	 * 거래코드조회
	 * @throws EntException
	 */
	public void selectTableModelTradesByBeanType(String strInput) throws EntException {
		
		try {
			StringBuilder sb = new StringBuilder(1024);
			sb.append(" SELECT * ");
			sb.append(" FROM trade_master ");
			sb.append(" Where bean_type = ");
			sb.append(EntCommon.convertQuery(strInput));
			sb.append(" order by trade_code ");
			DefaultTableModel list = this.transaction.selectToTableModel(sb);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	

	public void selectTradeMasterDTOTradesByBeanType(String beanType) throws EntException {
		
		try {
			//String strTableName = (String)transaction.getMethodParam();
			List list = queryTradesByBeanType(beanType);
			transaction.addResult(list);

		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	public List queryTradesByBeanType(String beanType) throws EntException  {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(" SELECT * ");
		sb.append(" FROM trade_master ");
		sb.append(" Where bean_type = ");
		sb.append(EntCommon.convertQuery(beanType));
		sb.append(" order by trade_code ");
		return this.transaction.select(TradeMasterDTO.class, sb);
	}
	
	
	public void selectTables() throws EntException {
		
		try {
			StringBuilder sb = new StringBuilder(1024);
			sb.append(" SELECT table_name as code ");
			sb.append(" FROM INFORMATION_SCHEMA.TABLES ");
			sb.append(" WHERE TABLE_SCHEMA = 'finance' ");
			sb.append(" group by table_name ");
			EntHashList list = this.transaction.selectCodeToHashMap(sb);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	


	public void selectColumnsByTable(String tableName) throws EntException {
		
		try {
			//String strTableName = (String)transaction.getMethodParam();
			DefaultTableModel list = queryColumnsByTable(tableName);
			transaction.addResult(list);
		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	public DefaultTableModel queryColumnsByTable(String tableName) throws EntException  {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(" SELECT column_name, ordinal_position, data_type, column_key, is_nullable, column_default ");
		sb.append(" FROM INFORMATION_SCHEMA.COLUMNS ");
		sb.append(" WHERE TABLE_SCHEMA = 'finance' ");
		sb.append(" and table_name = ");
		sb.append(EntCommon.convertQuery(tableName));
		
		return this.transaction.selectToTableModel(sb);
	}
	
	
	
	public void selectColumnsByTable2(String tableName) throws EntException {
		
		try {
			//String strTableName = (String)transaction.getMethodParam();
			List list = queryColumnsByTable2(tableName);
			transaction.addResult(list);

		} catch (EntException e) {
			transaction.setErrorMessage(e);
			throw e;
		}
	}	
	
	public List queryColumnsByTable2(String tableName) throws EntException  {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(" SELECT column_name, ordinal_position, data_type, column_key, is_nullable, column_default ");
		sb.append(" FROM INFORMATION_SCHEMA.COLUMNS ");
		sb.append(" WHERE TABLE_SCHEMA = 'finance' ");
		sb.append(" and table_name = ");
		sb.append(EntCommon.convertQuery(tableName));
		
		return this.transaction.select(TableInfomationDTO.class, sb);
	}
}
