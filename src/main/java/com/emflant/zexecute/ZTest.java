package com.emflant.zexecute;

import java.math.BigDecimal;

public class ZTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(String.format("%s%04d","01",3));
		System.out.println(System.currentTimeMillis());
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis());
		System.out.println(new BigDecimal(System.nanoTime()).divide(BigDecimal.valueOf(1000000000)).toPlainString());		//1000000000
		System.out.println(new BigDecimal(System.nanoTime()).divide(BigDecimal.valueOf(1000000000)).toPlainString());
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("4123");
		sb.insert(0, "55");
		
		System.out.println(sb);
		
		
		String str=  "12_3-1_-23";
		
		System.out.println(str.matches("[\\d\\_]{4}-[\\d\\_]{2}-[\\d\\_]{2}"));
	}

}
