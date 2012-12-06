package com.emflant.ifrs.irr;

import java.math.BigDecimal;
import java.util.List;

import com.emflant.common.EntLogger;

public class EffectiveInterestRate {

	/**
	 * ��ȿ�������� ���� ����Ѵ�.
	 * 
	 * @param cashFlowTable
	 * @param rate
	 * @param scale
	 * @return
	 */
	public BigDecimal calculate(List<BigDecimal> listCashFlow, int scale, double rate){
		
		
		//������������
		BigDecimal irr = new BigDecimal("1").divide((new BigDecimal("10").pow(scale)));
		EntLogger.debug("�������� : "+irr.toPlainString());
		
		BigDecimal inputMax = BigDecimal.ONE;//rate.add(new BigDecimal("0.01"));
		BigDecimal inputMin = BigDecimal.ZERO;//rate;
		
		BigDecimal input = inputMin.add(inputMax).divide(new BigDecimal("2"));
		
		int cnt = 0;
		
		while(true)
		{
			cnt++;
			EntLogger.loop("================= "+cnt+"ȸ");
			EntLogger.loop("��ȿ������ : "+input);
			EntLogger.loop("�ּҰ� : "+inputMin);
			EntLogger.loop("�ִ밪 : "+inputMax);
			
			BigDecimal total = this.sum(input, listCashFlow, scale);
			EntLogger.loop(total.toPlainString());
			
			BigDecimal between = inputMax.subtract(inputMin);
			
			//0�� �Ǿ������.. ���������̳��� �׸��Ѵ�.
			if(total.abs().compareTo(irr) == -1){
				EntLogger.info("���Ⱚ�� ��������("+irr.toPlainString()+")�̳��� ���Խ��ϴ�. ��������.");
				break;
			}
			
			else if(between.compareTo(irr)== 0){
				EntLogger.info("�ִ��ּҰ� ������ ��������("+irr.toPlainString()+")�̳��� ���Խ��ϴ�. ��������.");
				break;
			}
			
			else if(cnt > 100){
				EntLogger.info("���� 100���� �ʰ��Ͽ����ϴ�. ��������.");
				break;
			}
			
			else {
				
				//����� ����̸� IRR �� Ŀ�����Ѵ�.
				//�ּҰ� ����
				if(total.compareTo(BigDecimal.ZERO) == 1){
					inputMin = input;
				}
				//����� �����̸� IRR �� �۾������Ѵ�.
				//�ִ밪 ����
				else if(total.compareTo(BigDecimal.ZERO) == -1){
					inputMax = input;
				}
				input = inputMin.add(inputMax).divide(new BigDecimal("2")
								, scale
								, BigDecimal.ROUND_HALF_UP);
			}
		}
		EntLogger.debug("############# "+cnt+"ȸ ��������  #############");
		
		return input;
	}

	
	
	/**
	 * ��ȿ�������� �����帧ǥ�� �޾� ����Ѵ�.	<br/>
	 * 										<br/>
	 *    ȸ���� �հ�(����+����+������)		<br/>
	 * �� -----------------------------		<br/>
	 *           (1 + IRR)��					<br/>
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
