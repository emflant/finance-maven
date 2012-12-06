package com.emflant.ifrs.arrange;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * �����帧ǥ�� ���ڵ带 ������ Ŭ����.
 * ��Ű���������� ����Ѵ�.
 * @author nuri
 *
 */
class CashFlowDTO {
	
	protected BigDecimal bal;
	protected BigDecimal interest;
	protected BigDecimal fee;
	
	public CashFlowDTO(){
		bal = new BigDecimal("0");
		interest = new BigDecimal("0");
		fee = new BigDecimal("0");
	}
	
	public CashFlowDTO(BigDecimal bal, BigDecimal interest, BigDecimal fee){
		this.bal = bal;
		this.interest = interest;
		this.fee = fee;
	}
	
	public CashFlowDTO(HashMap<String, Object> hmInput){
		this((BigDecimal)hmInput.get("bal"),
				(BigDecimal)hmInput.get("interest"),
				(BigDecimal)hmInput.get("fee"));
	}
	
	public BigDecimal getBal() {
		return bal;
	}
	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	public BigDecimal getTotal() {
		return (BigDecimal)bal.add(interest).add(fee);
	}

	public String display(){
		//Logger.say("���� : "+bal+", ���� : "+interest+", ������ : "+fee);
		return bal+", "+interest+", "+fee+", "+getTotal();
	}
	
	public HashMap<String, Object> transferToHashMap(){
		HashMap<String, Object> hmResult = new HashMap<String, Object>();
		hmResult.put("bal"			, getBal());
		hmResult.put("interest"		, getInterest());
		hmResult.put("fee"			, getFee());
		
		return hmResult;
	}
}
