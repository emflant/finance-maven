package com.emflant.ifrs.irr;

import java.math.BigDecimal;
import java.util.List;


/**
 * IRR ���ϴ� Ŭ�������� �������̽��� �����Ѵ�.
 * 
 * @author emflant
 *
 */
public class EirCalculate {
	
	
	/**
	 * �����帧ǥ�� �Է¹޾� ��ȿ�������� ���Ѵ�.
	 * �⺻ �Ҽ��� �ڸ� 16�ڸ����� ���������� ���Ѵ�.
	 * @param listCashFlow
	 * @return
	 */
	public BigDecimal calculate(List<BigDecimal> listCashFlow){
		return calculate(listCashFlow, 16);
	}
	
	/**
	 * �����帧ǥ�� �Ҽ����ڸ�(scale)�� �Է¹޾� ��ȿ�������� ���Ѵ�.<br/>
	 * ���� ��� scale = 5 �̸� 0.00001 �������� ��ȿ�������� ���ؿ´�.
	 * 
	 * @param listCashFlow
	 * @param scale
	 * @return
	 */
	public BigDecimal calculate(List<BigDecimal> listCashFlow, int scale){
		return calculate(listCashFlow, scale, 0);
	}
	
	/**
	 * �����帧ǥ�� �Ҽ����ڸ�(scale)�� �Է¹޾� ��ȿ�������� ���Ѵ�.<br/>
	 * ���� ��� scale = 5 �̸� 0.00001 �������� ��ȿ�������� ���ؿ´�.<br/>
	 * ���������(rate)�� �ּҰ����� �����Ѵ�.
	 * 
	 * @param listCashFlow
	 * @param rate
	 * @param scale
	 * @return
	 */
	public BigDecimal calculate(List<BigDecimal> listCashFlow, int scale, double rate){

		EffectiveInterestRate eir = new EffectiveInterestRate();
		BigDecimal irr = eir.calculate(listCashFlow, scale, rate);
		return irr;
	}

}
