package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberController extends Controller {
	List<Member> members_board;
	
	private Scanner sc;
	private String command;
	private String actionMethodName;

	public MemberController(Scanner sc) {
		this.members_board = Container.memberDao.members;
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
		case "profile":
			showProfile();
			break;
		case "logout":
			doLogout();
			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다.");
			break;
		}
	}
	
	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃 되었습니다.");
	}

	private void showProfile() {
		System.out.println("== 현재 로그인한 회원의 정보 ==");
		System.out.println("나의 회원번호 : " + loginedMember.ID);
		System.out.println("로그인 아이디 : " + loginedMember.loginId);
		System.out.println("이름 : " + loginedMember.name);
		
	}

	private void doLogin() {
		Member member = null;
		String loginId = null;
		String loginPw = null;

		while (true) {
			System.out.print("로그인 아이디 : ");
			loginId = sc.nextLine();

			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.print("로그인 비밀번호 : ");
			loginPw = sc.nextLine();

			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
			break;
		}
		
		// 사용자가 입력한 아이디와 일치하는 회원이 우리한테 있나?
		member = getMemberByLoginId(loginId);
		
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
	
	private void doJoin() {
		int id = Container.memberDao.setNewId();
		
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
//		members_board.add(members);
		Container.memberDao.add(members);
		
		System.out.println(id + "번 회원이 가입되었습니다.");
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
	
	// 테스트 데이터
	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		Container.memberDao.add(new Member(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "test1", "test1", "김철수"));
		Container.memberDao.add(new Member(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "test2", "test2", "김영희"));
		Container.memberDao.add(new Member(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "test3", "test3", "홍길동"));
	}

}
