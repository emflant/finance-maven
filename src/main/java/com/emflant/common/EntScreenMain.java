package com.emflant.common;

import java.util.List;

import javax.swing.JFrame;

import com.emflant.common.EntMessage.EntMessageType;


public abstract class EntScreenMain {

	protected String transactionKinds;
	private EntMessage entMessage;

	protected String userId;
	protected JFrame frame;
	protected EntBean entBean;
	protected String screenName;

	public EntScreenMain(){
		this.entMessage = new EntMessage();
	}
	
	public void execute(String screenName){
		this.screenName = screenName;
		
		initScreen();
		this.frame.setVisible(true);
		this.frame.transferFocus();
		bindComboBox();
		search();				//조회한다.
	}

	public void setTitle(String title){
		this.frame.setTitle(this.entBean.getSchema()+" "+title);
	}

	/**
	 * 
	 * @param businessDTO
	 */
	public boolean transactionCommitAtOnce(EntBusiness businessDTO){
		
		businessDTO.setUserId(this.userId);
		businessDTO.setScreenName(this.screenName);
		
		this.entBean.doBusinessOpsOneTransaction(businessDTO);
		boolean result = businessDTO.isSuccessTransaction();
		globalCallback(businessDTO);
		return result;
	}
	
	public void transactionCommitOneByOne(EntBusiness businessDTO){
		businessDTO.setUserId(this.userId);
		businessDTO.setScreenName(this.screenName);
		
		this.entBean.doBusinessOpsEachTransaction(businessDTO);
		globalCallback(businessDTO);
	}
	
	private void showErrorMessage(EntBusiness businessDTO){
		
		List<EntTransaction> transactions = businessDTO.getTransactions();
		StringBuffer sbMessage = new StringBuffer();
		EntMessage entMessage = null;
		
		int nErrorCnt = 0;
		int nSize = transactions.size();
		for(int i=0;i<nSize;i++){
			EntTransaction transaction = transactions.get(i);
			EntMessage message = transaction.getMessage();
			
			if(message != null && message.getMessageType() == EntMessageType.ERROR){
				sbMessage.append(transaction.getMessage().getMessage());
				if(i < nSize-1){
					sbMessage.append("<br/><br/>");
				}
				nErrorCnt++;
			}
		}

		//에러메시지
		if(nErrorCnt > 0){
			entMessage = new EntMessage();
			entMessage.setMessageType(EntMessageType.ERROR);
			entMessage.setMessage(sbMessage.toString());
			entMessage.showMessageDialog(this.frame);
		} 
		
		//정상메시지
		else {
			EntMessage message = transactions.get(0).getMessage();
			if(message != null){
				message.showMessageDialog(this.frame);
			}
		}
	}
	
	private void globalCallback(EntBusiness businessDTO){
		
		showErrorMessage(businessDTO);		
		localCallback(businessDTO);
	}
	
	public void showMessageDialog(String message){
		this.entMessage.setMessageType(EntMessageType.WARNING);
		this.entMessage.setMessage(message);
		this.entMessage.showMessageDialog(this.frame);
	}
	public int showConfirmDialog(String message){
		this.entMessage.setMessageType(EntMessageType.WARNING);
		this.entMessage.setMessage(message);
		return this.entMessage.showConfirmDialog(this.frame);
	}
	public abstract void initScreen();
	public abstract void bindComboBox();
	public abstract void search();
	public abstract void localCallback(EntBusiness businessDTO);
	
	
}
