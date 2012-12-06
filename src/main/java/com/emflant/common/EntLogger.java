package com.emflant.common;

public class EntLogger {
	
	public static void debug(Object message){
		System.out.println("[debug] "+message);
	}
		
	public static void info(Object message){
		System.out.println("[info] "+message);
	}
	
	public static void loop(Object message){
		//System.out.println("[loop] "+message);
	}
	
	public static void message(Object message){
		//System.out.println("[message] "+message);
	}
	
	public static void error(Object message){
		//System.out.println("[error] "+message);
	}
	
	public static void query(Object message){
		System.out.println("[query] "+message);
	}
	
}
