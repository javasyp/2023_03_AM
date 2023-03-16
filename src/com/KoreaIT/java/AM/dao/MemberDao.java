package com.KoreaIT.java.AM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;

public class MemberDao extends Dao {
	public List<Member> members;
	
	public MemberDao() {
		members = new ArrayList<>();
	}
	
	public void add(Member member) {
		members.add(member);
		lastId++;
	}
	
	// 오버라이딩
	public int getLastId() {
		return lastId;
	}

	public int setNewId() {
		return lastId + 1;
	}
	
	public List<Member> getMembers() {
		return members;
	}

	// 사용자가 입력한 아이디와 일치하는 회원 있는지 확인
	public Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return null;
		}

		return members.get(index);
	}

	// 중복 검사
	public boolean isJoinableLoginId(String joinId) {
		int index = getMemberIndexByLoginId(joinId);
		
		if (index == -1) {
			return true;
		}
		return false;
	}
		
	private int getMemberIndexByLoginId(String joinId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(joinId)) {
				return i;
			}
			i++;
		}
		return -1;
	}
}