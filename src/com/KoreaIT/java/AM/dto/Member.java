package com.KoreaIT.java.AM.dto;

// 실제 데이터의 객체요소 (VO, value object) dto 또는 vo
public class Member extends Dto {
	// 생성자(메소드)와 변수 앞에 public을 붙여줘야 메인 메소드에서 사용 가능하다.
	public String loginId;
	public String loginPw;
	public String name;
	
	public Member(int id, String regDate, String updateDate, String loginid, String loginpw, String name) {
		this.ID = id;
		this.RegDate = regDate;
		this.UpdateDate = updateDate;
		this.loginId = loginid;
		this.loginPw = loginpw;
		this.name = name;
	}
}