package com.emflant.zexecute;

import java.util.HashMap;

import com.emflant.registration.expense.CalculateRegistrationExpense;
import com.emflant.registration.expense.RegistrationExpenseDTO;

public class ZRegistrationExpense {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, Object> input = new HashMap<String, Object>();
		input.put("type", "01");
		
		RegistrationExpenseDTO inputDTO = new RegistrationExpenseDTO(input);
		
		CalculateRegistrationExpense ex = new CalculateRegistrationExpense();
		ex.execute(inputDTO);
	}

}
