package com.emflant.counsel;

import java.math.BigDecimal;

import com.emflant.common.EntLogger;

public abstract class CounselInterestMethod {
	
	protected final int RATE_SCALE = 4;
	protected final int AMOUNT_SCALE = 0;
	
	protected CounselDTO counselDTO;
	
	public CounselInterestMethod(CounselDTO counselDTO){
		this.counselDTO = counselDTO;
		display();
		getRate();
	}

	public final void result(){
		counselDTO.setGiveInterest(calculateTotalGiveInterest());
	}
	
	private final void getRate(){
		EntLogger.debug("해당 지급이율을 가져온다.");
		Rate r = new Rate();
		counselDTO.setGiveInterestRate(r.getGiveRate(counselDTO.getProduct()
							 , counselDTO.getNewDate()
							 , counselDTO.getTermMonths()));
	}
	
	protected abstract BigDecimal calculateTotalGiveInterest();
	protected abstract void display();
	
}
