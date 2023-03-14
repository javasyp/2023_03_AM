package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Member;

public abstract class Controller {

	public abstract void doAction(String actionMethodName, String command);
	
	public void makeTestData() {
		
	}
	
	protected static Member loginedMember = null;

	public static boolean isLogined() {	// App에서 사용하기 위해 static 추가
		return loginedMember != null;
	}
}
