package com.emflant.ifrs.irr;

import java.math.BigDecimal;
import java.util.List;


/**
 * IRR 구하는 클래스이자 인터페이스를 제공한다.
 * 
 * @author emflant
 *
 */
public class EirCalculate {
	
	
	/**
	 * 현금흐름표를 입력받아 유효이자율을 구한다.
	 * 기본 소수점 자리 16자리까지 오차범위로 구한다.
	 * @param listCashFlow
	 * @return
	 */
	public BigDecimal calculate(List<BigDecimal> listCashFlow){
		return calculate(listCashFlow, 16);
	}
	
	/**
	 * 현금흐름표와 소수점자리(scale)를 입력받아 유효이자율을 구한다.<br/>
	 * 예를 들어 scale = 5 이면 0.00001 단위까지 유효이자율을 구해온다.
	 * 
	 * @param listCashFlow
	 * @param scale
	 * @return
	 */
	public BigDecimal calculate(List<BigDecimal> listCashFlow, int scale){
		return calculate(listCashFlow, scale, 0);
	}
	
	/**
	 * 현금흐름표와 소수점자리(scale)를 입력받아 유효이자율을 구한다.<br/>
	 * 예를 들어 scale = 5 이면 0.00001 단위까지 유효이자율을 구해온다.<br/>
	 * 명목이자율(rate)는 최소값으로 정의한다.
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
