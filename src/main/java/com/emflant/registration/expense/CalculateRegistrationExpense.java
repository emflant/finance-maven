package com.emflant.registration.expense;

import java.util.List;

public class CalculateRegistrationExpense {
	
	public void execute(RegistrationExpenseDTO registrationExpenseDTO){
		
		//지상권설정인 경우
		if(registrationExpenseDTO.getGroundsOfRegistration().equals("01")){
			CreateSuperficies cs = new CreateSuperficies();
			List<CalculateRegistrationExpenseTypes> list = cs.rule();
			
			for(CalculateRegistrationExpenseTypes type : list){
				type.calculate(registrationExpenseDTO);
			}
		}
		
	}
	
}
