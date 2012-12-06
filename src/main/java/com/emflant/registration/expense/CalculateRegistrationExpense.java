package com.emflant.registration.expense;

import java.util.List;

public class CalculateRegistrationExpense {
	
	public void execute(RegistrationExpenseDTO registrationExpenseDTO){
		
		//����Ǽ����� ���
		if(registrationExpenseDTO.getGroundsOfRegistration().equals("01")){
			CreateSuperficies cs = new CreateSuperficies();
			List<CalculateRegistrationExpenseTypes> list = cs.rule();
			
			for(CalculateRegistrationExpenseTypes type : list){
				type.calculate(registrationExpenseDTO);
			}
		}
		
	}
	
}
