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
		EntLogger.info("정기적금 이자계산");
	}

	public BigDecimal calculateTotalGiveInterest() {
		// TODO Auto-generated method stub
		EntLogger.debug("정기적금이자계산 방법으로 선택.");
		
		//계약금액(목표금액)으로 입력되었으면 월불입금을 구한다.
		if(counselDTO.isAmount()){
			counselDTO.setPayAmount(calculatePayAmount());
		}

		return subResult.multiply(counselDTO.getPayAmount()).
			setScale(AMOUNT_SCALE, BigDecimal.ROUND_DOWN);
	}
	
	private BigDecimal calculatePayAmount(){
		EntLogger.debug("적금목표금액으로부터 월부금액을 구한다.");
		
		return counselDTO.getAmount().
			divide(subResult.add(BigDecimal.valueOf(12))
					,AMOUNT_SCALE
					,BigDecimal.ROUND_DOWN);
	}
	
	/**
	 * 계약월수 x (계약월수+1)/2 x 약정이자율 / 12
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
