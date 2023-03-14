package com.KoreaIT.java.AM.controller;

public abstract class Controller {

	public abstract void doAction(String actionMethodName, String command);
	
	public void makeTestData() {
		
	} 
	// public abstract void makeTestData(); 이렇게 써도 됨.
}
