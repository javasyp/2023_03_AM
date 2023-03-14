package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberController extends Controller {
	List<Member> members_board;
	
	private Scanner sc;
	private String command;
	private String actionMethodName;
	
	private Member loginedMember;	// 로그인 상태인지 확인하는 변수
	// 다른 데서도 사용하고, 수명을 길게 하기 위해 전역변수로 사용

	public MemberController(Scanner sc) {
		this.members_board = new ArrayList<>();
		this.sc = sc;
	}
	
	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			doLogin();
			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다.");
			break;
		}
	}

	private void doLogin() {
		System.out.print("로그인 아이디 : ");
		String loginId = sc.nextLine();
		
		System.out.print("로그인 비밀번호 : ");
		String loginPw = sc.nextLine();
		
		// 사용자가 입력한 아이디와 일치하는 회원이 우리한테 있나?
	
		Member member = getMemberByLoginId(loginId);
		
		if (member == null) {
			System.out.println("일치하는 회원이 없습니다.");
			return;
		}
		
		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		
		loginedMember = member;
		System.out.printf("로그인 성공! %s님 반갑습니다.\n", loginedMember.name);
		
	}

	int lastMemberId = 0;
	
	private void doJoin() {
		int id = lastMemberId + 1;
		String regDate = Util.getNowDateTimeStr();
		
		String joinId = null;		// while문에서 돌아가기 때문에 초기화 해줘야함.
		
		// 아이디 중복 검사
		while (true) {
			System.out.print("아이디 : ");
			joinId = sc.nextLine();
			
			if (isJoinableId(joinId) == false) {
				System.out.println("이미 사용중인 아이디입니다.");
				continue;
			}
			break;
		}
		
		// 비밀번호 확인
		String joinPw = null;
		String joinPwCk = null;
		
		while (true) {
			System.out.print("비밀번호 : ");
			joinPw = sc.nextLine();
			System.out.print("비밀번호 확인 : ");
			joinPwCk = sc.nextLine();
			
			if (joinPw.equals(joinPwCk) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		
		System.out.print("이름 : ");
		String joinName = sc.nextLine();
		
		Member members = new Member(id, regDate, regDate, joinId, joinPw, joinName);
		members_board.add(members);
		
		System.out.println(id + "번 회원이 가입되었습니다.");
		lastMemberId++;
	}
	
	// 사용자가 입력한 아이디와 일치하는 회원 있는지 확인
	private Member getMemberByLoginId(String loginId) {
		int index = getMemberIndex(loginId);

		if (index == -1) {
			return null;
		}

		return members_board.get(index);
	}

	// 중복 검사
	private boolean isJoinableId(String joinId) {
		int index = getMemberIndex(joinId);
		
		if (index == -1) {
			return true;
		}
		return false;
	}
	
	private int getMemberIndex(String joinId) {
		int i = 0;
		for (Member member : members_board) {
			if (member.loginId.equals(joinId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

}
