package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.controller.ArticleController;
import com.KoreaIT.java.AM.controller.MemberController;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;

public class App {
	
		List<Article> articles_board;
		List<Member> members_board;
		
		public App() {
			articles_board = new ArrayList<>();
			members_board = new ArrayList<>();
		}
		
		public void start() {
			System.out.println("=== 프로그램 시작 ===");
			
			Scanner sc = new Scanner(System.in);
			
			// 멤버 컨트롤러 생성
			MemberController memberControl = new MemberController(members_board, sc);
			
			// 아티클 컨트롤러 생성
			ArticleController articleControl = new ArticleController(articles_board, sc);
			articleControl.makeTestData();

			while (true) {
				System.out.print("명령어 > ");
				String command = sc.nextLine().trim();

				if (command.length() == 0) {
					System.out.println("명령어를 입력해 주세요.");
					continue;
				}
				
				if (command.equals("exit")) {
					break;
				}
				
				// 1. 게시판 목록 출력하기
				if (command.startsWith("article list")) {
					articleControl.showList(command);
				}
				
				// 2. 게시글 입력하기
				else if (command.equals("article write")) {
					articleControl.doWrite();
				}
				
				// 3. 게시글의 세부사항 출력하기
				else if (command.startsWith("article detail")) {
					articleControl.showDetail(command);
				}
				
				// 4. 게시글 삭제하기
				else if (command.startsWith("article delete")) {
					articleControl.doDelete(command);
				}
				
				// 5. 게시글 수정하기
				else if (command.startsWith("article modify")) {
					articleControl.doModify(command);
				}
				
				// 6. 회원가입
				else if (command.equals("member join")) {
					memberControl.doJoin();
				}
				
				else {
					System.out.println("존재하지 않는 명령어입니다.");
				}
			}

			System.out.println("=== 프로그램 종료 ===");

			sc.close();
		}
}