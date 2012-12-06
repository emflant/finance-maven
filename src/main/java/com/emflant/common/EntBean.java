package com.emflant.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.emflant.accounting.dto.table.EntQuery;
import com.emflant.accounting.dto.table.SlipMasterDTO;
import com.emflant.accounting.dto.table.TransactionLogDetailDTO;
import com.emflant.accounting.dto.table.TransactionLogMasterDTO;

public class EntBean {
	
	protected EntQuery entQuery;
	
	public EntBean(){
		this("localhost", "finance_test");
	}
	
	public EntBean(String ip){
		this.entQuery = new EntQuery(ip, "finance");
	}
	
	public EntBean(String ip, String schema){
		this.entQuery = new EntQuery(ip, schema);
	}
	
	public String getSchema(){
		return this.entQuery.getSchema();
	}

	public void closeConnection(){
		this.entQuery.closeConnection();
	}
	

	public void insertBusiness(EntBusiness business){
		TransactionLogMasterDTO transactionLogMasterDTO = new TransactionLogMasterDTO(business);
		try {
			this.entQuery.insert(transactionLogMasterDTO);
		} catch (EntException e) {
			e.printStackTrace();
		}
	}
	
	public void insertTransaction(EntTransaction transaction){
		TransactionLogDetailDTO transactionLogDetailDTO = new TransactionLogDetailDTO(transaction);
		try {
			this.entQuery.insert(transactionLogDetailDTO);
		} catch (EntException e) {
			e.printStackTrace();
		}
	}
	
	public void updateTransaction(EntTransaction transaction){
		//String strEndDateTime = EntDate.getTodayDateTimeMillis()+transaction.getTransactionSequence();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String strEndDateTime = sdf.format(now)+transaction.getTransactionSequence();
		
		long lGapTime = 0L;
		String strGapTime = null;
		
		try {
			Date startDate = sdf.parse(transaction.getSystemDateTimeMillis().substring(0,17));
			lGapTime = now.getTime() - startDate.getTime();
			
			strGapTime = BigDecimal.valueOf(lGapTime).divide(BigDecimal.valueOf(1000)).toPlainString();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//BigDecimal bdEndDateTime = new BigDecimal(strEndDateTime);
		//BigDecimal bdStartDateTime = new BigDecimal(transaction.getSystemDateTimeMillis());
		
		//BigDecimal gapTime = bdEndDateTime.subtract(bdStartDateTime).divide(BigDecimal.valueOf(10000));
		
		StringBuilder sbQuery = new StringBuilder(256);
		sbQuery.append(" update transaction_log_detail ");
		sbQuery.append(" set gap_time = ");
		sbQuery.append(strGapTime);
		sbQuery.append("   , end_date_time = ");
		sbQuery.append(EntCommon.convertQuery(strEndDateTime));
		sbQuery.append(" where user_id = ");
		sbQuery.append(EntCommon.convertQuery(transaction.getUserId()));
		sbQuery.append(" and system_date_time = ");
		sbQuery.append(EntCommon.convertQuery(transaction.getBusinessBaseDataTimeMillis()));
		sbQuery.append(" and transaction_sequence = ");
		sbQuery.append(transaction.getTransactionSequence());
		
		try {
			this.entQuery.update(sbQuery);
		} catch (EntException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * ������ �ŷ��� ������ Ʈ����������  commit, rollback �Ѵ�.
	 * @param transactions
	 */
	public void doBusinessOpsEachTransaction(EntBusiness business){
		business.setupTime();
		EntLogger.info(EntCommon.display(business));
		insertBusiness(business);
		
		
		List<EntTransaction> transactions = business.getTransactions();
		
        int nSize = transactions.size();
        String strSlipNo = null;
        for(int i=0;i<nSize;i++)
        {        	
        	EntTransaction transaction = transactions.get(i);
        	transaction.setTransactionSequence(i+1);
        	transaction.setEntQuery(this.entQuery);
    		transaction.copyCommonInfo(business);
    		
    		transaction.setupTime();
       		
        	if(business.getIsOneSlip()){
        		transaction.setSlipNo(strSlipNo);
        		transaction.setSlipSeq(i+1);
        	}
        	try {
				transaction.getMethodInfoWithTradeCode();
			} catch (EntException e) {
				e.printStackTrace();
				continue;
			}
        	transaction.display();
        	insertTransaction(transaction);
        	
        	doBusinessOpOneTransaction(transaction);
        	
        	if(transaction.isTransactionCompleted()){
        		business.addSuccessCount();
        	}
        	
        	strSlipNo = transaction.getSlipNo();
        }
	}
	
	
	/**
	 * ������ �ŷ��� �ϳ��� Ʈ���������� ���� commit, rollback �Ѵ�.
	 * @param transactions
	 */
	@SuppressWarnings("unchecked")
	public void doBusinessOpsOneTransaction(EntBusiness business){
		business.setupTime();
		EntLogger.info(EntCommon.display(business));
		insertBusiness(business);
		
		List<EntTransaction> transactions = business.getTransactions();
		EntTransaction transaction = null;
		
        try {
            
            int nSize = transactions.size();
            
            String strSlipNo = null;
            
            for(int i=0;i<nSize;i++)
            {
            	transaction = transactions.get(i);
            	transaction.setTransactionSequence(i+1);
            	transaction.setEntQuery(this.entQuery);
            	transaction.copyCommonInfo(business);
            	transaction.setupTime();

            	if(business.getIsOneSlip()){							//
            		transaction.setSlipNo(strSlipNo);
            		transaction.setSlipSeq(i+1);
            	}
            	transaction.getMethodInfoWithTradeCode();
            	transaction.display();									//Ʈ������ info�α�
            	insertTransaction(transaction);
    			
    			invoke(transaction);
    			
    			transaction.setTransactionCompleted(true);
    			strSlipNo = transaction.getSlipNo();
    			
    			if(business.getIsRemoveMessage()){
    				transaction.removeMessage();
    			}
    			
    			business.addSuccessCount();
    			updateTransaction(transaction);
            }

            this.entQuery.commit();
            
        } 
        //EntException �߻��ÿ� �ᱹ InvocationTargetException �� �����Ǳ⿡ �̰����� ������.
        catch (InvocationTargetException e) {

        	//e.printStackTrace();
        	
        	try {
				this.entQuery.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        } 
        
        //�߸��� Ŭ����, �޽���, ���ڰ� �Է½�
        catch (NoSuchMethodException e) {
        	e.printStackTrace();
        	transaction.setErrorMessage("�������� �ʴ� �޼ҵ� �Դϴ�. "+e.getMessage());
        	try {
        		this.entQuery.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	
        } 
        
        //����ġ ���� ����..
        catch (Exception e) {
        	//e.printStackTrace();
        	transaction.setErrorMessage(e);
        	
        	try {
        		this.entQuery.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} 
		
        
	}
	
	
	@SuppressWarnings("unchecked")
	private void invoke(EntTransaction transaction) throws ClassNotFoundException, SecurityException
				, NoSuchMethodException, IllegalArgumentException
				, InstantiationException, IllegalAccessException, InvocationTargetException {
		
        Class clazz = Class.forName("com.emflant.accounting.business.bean."+transaction.getBeanType());
        Class[] constructorTypes = { EntTransaction.class };
        Constructor constructor = clazz.getConstructor(constructorTypes);
        
        Object[] constructorParams = { transaction };
        Object newInstance = constructor.newInstance(constructorParams);
		
        Class[] parameterTypes = null;
        Object[] parameterObjects = null;
        
        if(transaction.getParameterType() ==  null){
        	Class[] tempClazzes = null;
        	Object[] tempObjs = null;
        	parameterTypes = tempClazzes;
        	parameterObjects = tempObjs;
        } else if(transaction.getParameterType().equals("String")){
        	Class[] tempClazzes = { String.class };
        	parameterTypes = tempClazzes;
        	parameterObjects = getDefaultParameterObject(transaction.getMethodParam());
        } else if(transaction.getParameterType().equals("SlipMasterDTO")){
        	Class[] tempClazzes = { SlipMasterDTO.class };
        	parameterTypes = tempClazzes;
        	parameterObjects = getDefaultParameterObject(transaction.getMethodParam());
        } else {
        	Class tempParam = Class.forName("com.emflant.accounting.dto.screen."+transaction.getParameterType());
        	Class[] tempClazzes = { tempParam };
        	parameterTypes = tempClazzes;
        	parameterObjects = getDefaultParameterObject(transaction.getMethodParam());
        }
        
        Method method = clazz.getMethod(transaction.getMethodType(), parameterTypes);
		method.invoke(newInstance, parameterObjects);
        
	}

	private Object[] getDefaultParameterObject(Object obj){
		Object[] objs = { obj };
		return objs;
	}
	
	/**
	 * ���ϰŷ��� �� Ʈ����������  commit, rollback �Ѵ�.
	 * @param transactions
	 */
	@SuppressWarnings("unchecked")
	private void doBusinessOpOneTransaction(EntTransaction transaction) {

        try {
        	invoke(transaction);
        	
			transaction.setTransactionCompleted(true);
			updateTransaction(transaction);
			this.entQuery.commit();
            
        } 
        //EntException �߻��ÿ� �ᱹ InvocationTargetException �� �����Ǳ⿡ �̰����� ������.
        catch (InvocationTargetException e) {

        	e.printStackTrace();
        	
        	try {
        		this.entQuery.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        } 
        
        //�߸��� Ŭ����, �޽���, ���ڰ� �Է½�
        catch (NoSuchMethodException e) {

        	e.printStackTrace();
        	transaction.setErrorMessage("�������� �ʴ� �޼ҵ� �Դϴ�. "+e.getMessage());
        	
        	
        	try {
        		this.entQuery.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        	
        } 
        
        //����ġ ���� ����..
        catch (Exception e) {
        	e.printStackTrace();
        	transaction.setErrorMessage(e);
        	try {
        		this.entQuery.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} 

        
	}
	
}
