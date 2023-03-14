package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberController extends Controller {
	List<Member> members_board;
	
	private Scanner sc;
	private String command;
	private String actionMethodName;

	public MemberController(List<Member> members, Scanner sc) {
		this.members_board = members;
		this.sc = sc;
	}
	
	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		}
	}
	
	int lastMemberId = 0;
	
	public void doJoin() {
		int id = lastMemberId + 1;
		String regdate = Util.getNowDateTimeStr();
		
		String join_id = null;		// while문에서 돌아가기 때문에 초기화 해줘야함.
		
		// 중복 검사
		while (true) {
			System.out.print("아이디 : ");
			join_id = sc.nextLine();
			
			if (isJoinableId(join_id) == false) {
				System.out.println("이미 사용중인 아이디입니다.");
				continue;
			}
			break;
		}
		
		// 비밀번호 확인
		String join_pw = null;
		String join_pw_ck = null;
		
		while (true) {
			System.out.print("비밀번호 : ");
			join_pw = sc.nextLine();
			System.out.print("비밀번호 확인 : ");
			join_pw_ck = sc.nextLine();
			
			if (join_pw.equals(join_pw_ck) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		
		System.out.print("이름 : ");
		String join_name = sc.nextLine();
		
		Member members = new Member(id, regdate, regdate, join_id, join_pw, join_name);
		members_board.add(members);
		
		System.out.println(id + "번 회원이 가입되었습니다.");
		lastMemberId++;
	}
	
	//중복 검사
	private boolean isJoinableId(String loginId) {
		int index = getMemberIndex(loginId);
		
		if (index == -1) {
			return true;
		}
		return false;
	}
	
	private int getMemberIndex(String loginId) {
		int i = 0;
		for (Member member : members_board) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

}
