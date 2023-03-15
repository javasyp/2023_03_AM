package com.KoreaIT.java.AM.dao;

public abstract class Dao {
	protected int lastId;	// 자식 클래스까지만 사용가능
	
	public Dao() {
		lastId = 0;
	}
	
	public abstract int getLastId();
}
