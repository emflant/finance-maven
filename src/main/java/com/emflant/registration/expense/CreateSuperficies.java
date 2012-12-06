package com.emflant.registration.expense;

import java.util.ArrayList;
import java.util.List;

public class CreateSuperficies {
	
	
	public List<CalculateRegistrationExpenseTypes> rule(){
		List<CalculateRegistrationExpenseTypes> list = new ArrayList<CalculateRegistrationExpenseTypes>();
		
		list.add(new CalculateLocalEducationTax());
		list.add(new CalculateRegistrationApplyFee());
		list.add(new CalculateStampTax());
		
		return list;
	}
	
}
