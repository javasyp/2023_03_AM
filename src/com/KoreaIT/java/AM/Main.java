package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static List<Article> articles_board = new ArrayList<>();
	static List<Member> members_board = new ArrayList<>();
	
	public static void main(String[] args) {
		
		System.out.println("=== 프로그램 시작 ===");
		
		makeTestData();

		Scanner sc = new Scanner(System.in);
		
		int lastArticleId = 3;
		int lastMemberId = 0;

		while (true) {
			System.out.print("명령어 > ");
			String command = sc.nextLine();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해 주세요.");
				continue;
			}
			
			if (command.equals("exit")) {
				break;
			}
			
			// 1. 게시판 목록 출력하기
			if (command.startsWith("article list")) {
				if (articles_board.size() == 0) {
					
					System.out.println("게시글이 없습니다.");
					continue;

				} 
				
				// article list에서 제목 검색 시
				String searchKeyword = command.substring("article list".length()).trim();
				
				List<Article> forPrintArticles = articles_board;
				
				if (searchKeyword.length() > 0) {	// 검색어가 있는 경우
					System.out.println("검색어 : " + searchKeyword);
					forPrintArticles = new ArrayList<>();
					
					for (Article articles_list : articles_board) {
						if (articles_list.Title.contains(searchKeyword)) {
							forPrintArticles.add(articles_list);
						}
					}
					if (forPrintArticles.size() == 0) {
						System.out.println("검색 결과가 없습니다.");
						continue;
					}
				}
				
				System.out.println("번호 / 제목 / 조회");	// 검색어가 없는 경우
				
				for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
					
					Article articles_list = forPrintArticles.get(i); // 값 읽어와서 변수에 저장
					System.out.println(" " + articles_list.ID + "  / " + articles_list.Title + " / " + articles_list.Count);
				}
			}
			
			// 2. 게시글 입력하기
			else if (command.equals("article write")) {
				int id = lastArticleId + 1;

				System.out.print("제목 : ");
				String title = sc.nextLine();
				
				System.out.print("내용 : ");
				String content = sc.nextLine();
				
				String regDate = Util.getNowDateTimeStr();

				Article article = new Article(id, regDate, regDate, title, content);
				articles_board.add(article);

				System.out.println(id + "번 글이 생성되었습니다.");
				lastArticleId++;
				
			}
			
			// 3. 게시글의 세부사항 출력하기
			else if (command.startsWith("article detail")) {
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int num = Integer.parseInt(cmdDiv[2]);
				
				Article foundArticle = getArticleId(num);
				
				if (foundArticle == null) {
					System.out.println(num + "번 게시물은 존재하지 않습니다.");
					continue;
				}
				
				foundArticle.Count++;
				
				System.out.println("번호 : " + foundArticle.ID);
				System.out.println("작성날짜 : " + foundArticle.RegDate);
				System.out.println("수정날짜 : " + foundArticle.UpdateDate);
				System.out.println("제목 : " + foundArticle.Title);
				System.out.println("내용 : " + foundArticle.Content);
				System.out.println("조회수 : " + foundArticle.Count);
			}
			
			// 4. 게시글 삭제하기
			else if (command.startsWith("article delete")) {
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int num = Integer.parseInt(cmdDiv[2]);

				int foundIndex = getArticleIndex(num);
				
				if (foundIndex == -1) {
					System.out.println(num + "번 게시물은 존재하지 않습니다.");
					continue;
				}
				
				articles_board.remove(foundIndex); 
				
				System.out.println(num + "번 글이 삭제되었습니다.");
			}
			
			// 5. 게시글 수정하기
			else if (command.startsWith("article modify")) {
				
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int num = Integer.parseInt(cmdDiv[2]);
				
				Article foundArticle = getArticleId(num);
				
				if (foundArticle == null) {
					System.out.println(num + "번 게시물은 존재하지 않습니다.");
					continue;
				}
				
				System.out.print("새 제목 : ");
				String new_title = sc.nextLine();
				
				System.out.print("새 내용 : ");
				String new_content = sc.nextLine();
				
				String update_date = Util.getNowDateTimeStr();
				
				foundArticle.Title = new_title;
				foundArticle.Content = new_content;
				foundArticle.UpdateDate = update_date;
				
				System.out.println(num + "번 글이 수정되었습니다.");
			}
			
			// 6. 회원가입
			else if (command.equals("member join")) {
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
			
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("=== 프로그램 종료 ===");

		sc.close();
	}
	
	// 중복체크
	private static boolean isJoinableId(String loginId) {
		int index = getMemberIndex(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	private static int getMemberIndex(String loginId) {
		int i = 0;
		for (Member member : members_board) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	private static int getArticleIndex(int num) {
		int i = 0;
		for (Article articles_find : articles_board) {
			if (articles_find.ID == num) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private static Article getArticleId(int num) {
		int index = getArticleIndex(num);
		
		if (index != -1) {
			return articles_board.get(index);
		}
		
		return null;
	}
	
	private static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles_board.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));
		articles_board.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));
		articles_board.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));
	}
}

class Member {
	int ID;
	String RegDate;
	String UpdateDate;
	String loginId;
	String loginPw;
	String name;
	
	Member(int id, String regDate, String updateDate, String loginid, String loginpw, String name) {
		this.ID = id;
		this.RegDate = regDate;
		this.UpdateDate = updateDate;
		this.loginId = loginid;
		this.loginPw = loginpw;
		this.name = name;
	}
}

class Article {
	int ID;
	int Count;
	String RegDate;
	String UpdateDate;
	String Title;
	String Content;
	
	Article(int id, String regDate, String updateDate, String title, String content) {
		this(id, regDate, updateDate, title, content, 0);
	}
	
	Article(int id, String regDate, String updateDate, String title, String content, int count) {
		this.ID = id;
		this.RegDate = regDate;
		this.UpdateDate = updateDate;
		this.Title = title;
		this.Content = content;
		this.Count = count;
	}
}