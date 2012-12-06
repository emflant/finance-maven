package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntLogger;

public class CounselByPeriodicalDeposit extends CounselInterestMethod {

	private BigDecimal subResult;

	public CounselByPeriodicalDeposit(CounselDTO counselDTO) {
		super(counselDTO);
		subCalculate();
		// TODO Auto-generated constructor stub
	}
	
	public void display() {
		// TODO Auto-generated method stub
		EntLogger.info("�������� ���ڰ��");
	}

	public BigDecimal calculateTotalGiveInterest() {
		// TODO Auto-generated method stub
		EntLogger.debug("�����������ڰ�� ������� ����.");
		
		//���ݾ�(��ǥ�ݾ�)���� �ԷµǾ����� �����Ա��� ���Ѵ�.
		if(counselDTO.isAmount()){
			counselDTO.setPayAmount(calculatePayAmount());
		}

		return subResult.multiply(counselDTO.getPayAmount()).
			setScale(AMOUNT_SCALE, BigDecimal.ROUND_DOWN);
	}
	
	private BigDecimal calculatePayAmount(){
		EntLogger.debug("���ݸ�ǥ�ݾ����κ��� ���αݾ��� ���Ѵ�.");
		
		return counselDTO.getAmount().
			divide(subResult.add(BigDecimal.valueOf(12))
					,AMOUNT_SCALE
					,BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * ������ x (������+1)/2 x ���������� / 12
	 * @return
	 */
	private void subCalculate() {
		// TODO Auto-generated method stub
		BigDecimal bdTermMonths = BigDecimal.valueOf(counselDTO.getTermMonths());
		
		this.subResult = bdTermMonths.add(BigDecimal.valueOf(1)).multiply(bdTermMonths).
			multiply(counselDTO.getGiveInterestRate()).
			divide(BigDecimal.valueOf(24),RATE_SCALE,BigDecimal.ROUND_DOWN);
	}
	
}
