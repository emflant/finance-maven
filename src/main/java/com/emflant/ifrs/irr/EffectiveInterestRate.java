package com.emflant.ifrs.irr;

import java.math.BigDecimal;
import java.util.List;

import com.emflant.common.EntLogger;

public class EffectiveInterestRate {

	/**
	 * 유효이자율을 실제 계산한다.
	 * 
	 * @param cashFlowTable
	 * @param rate
	 * @param scale
	 * @return
	 */
	public BigDecimal calculate(List<BigDecimal> listCashFlow, int scale, double rate){
		
		
		//오차범위셋팅
		BigDecimal irr = new BigDecimal("1").divide((new BigDecimal("10").pow(scale)));
		EntLogger.debug("오차범위 : "+irr.toPlainString());
		
		BigDecimal inputMax = BigDecimal.ONE;//rate.add(new BigDecimal("0.01"));
		BigDecimal inputMin = BigDecimal.ZERO;//rate;
		
		BigDecimal input = inputMin.add(inputMax).divide(new BigDecimal("2"));
		
		int cnt = 0;
		
		while(true)
		{
			cnt++;
			EntLogger.loop("================= "+cnt+"회");
			EntLogger.loop("유효이자율 : "+input);
			EntLogger.loop("최소값 : "+inputMin);
			EntLogger.loop("최대값 : "+inputMax);
			
			BigDecimal total = this.sum(input, listCashFlow, scale);
			EntLogger.loop(total.toPlainString());
			
			BigDecimal between = inputMax.subtract(inputMin);
			
			//0이 되어야지만.. 오차범위이내면 그만한다.
			if(total.abs().compareTo(irr) == -1){
				EntLogger.info("산출값이 오차범위("+irr.toPlainString()+")이내에 들어왔습니다. 정상종료.");
				break;
			}
			
			else if(between.compareTo(irr)== 0){
				EntLogger.info("최대최소값 범위가 오차범위("+irr.toPlainString()+")이내에 들어왔습니다. 정상종료.");
				break;
			}
			
			else if(cnt > 100){
				EntLogger.info("루프 100번을 초과하였습니다. 강제종료.");
				break;
			}
			
			else {
				
				//결과가 양수이면 IRR 더 커져야한다.
				//최소값 갱신
				if(total.compareTo(BigDecimal.ZERO) == 1){
					inputMin = input;
				}
				//결과가 음수이면 IRR 더 작아져야한다.
				//최대값 갱신
				else if(total.compareTo(BigDecimal.ZERO) == -1){
					inputMax = input;
				}
				input = inputMin.add(inputMax).divide(new BigDecimal("2")
								, scale
								, BigDecimal.ROUND_HALF_UP);
			}
		}
		EntLogger.debug("############# "+cnt+"회 루프종료  #############");
		
		return input;
	}

	
	
	/**
	 * 유효이자율과 현금흐름표를 받아 계산한다.	<br/>
	 * 										<br/>
	 *    회차별 합계(원금+이자+수수료)		<br/>
	 * ∑ -----------------------------		<br/>
	 *           (1 + IRR)ⁿ					<br/>
	 *   
	 * @param input
	 * @param list
	 * @return
	 */
	public BigDecimal sum(BigDecimal irr, List<BigDecimal> listCashFlow, int scale){
		
		BigDecimal total = new BigDecimal("0");
		
		for(int i=0;i<listCashFlow.size();i++){
			BigDecimal bdTotal = listCashFlow.get(i);
			BigDecimal under = (new BigDecimal("1").add(irr)).pow(i);
			BigDecimal temp = bdTotal.divide(under, scale, BigDecimal.ROUND_HALF_UP);
			total = total.add(temp);
		}
		
		return total;
	}
	
	
}
