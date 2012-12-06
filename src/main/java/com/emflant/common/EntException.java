package com.emflant.common;

public class EntException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String msgCd;
	
	public EntException(String msgCd){
		this.msgCd = msgCd;
		EntLogger.error(msgCd);
	}
	
	//public void printMessage(){
		//Logger.error(msgCd);
	//}
	
	public String getMessage(){
		return this.msgCd;
	}
	

}
