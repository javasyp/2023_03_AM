package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Member;

public abstract class Controller {

	public abstract void doAction(String actionMethodName, String command);
	
	public void makeTestData() {
		
	}	// public abstract void makeTestData(); 이렇게 써도 됨.
	
	protected static Member loginedMember = null;

	public boolean isLogined() {
		return loginedMember != null;
	}
}
