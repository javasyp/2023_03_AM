package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.util.Util;
import com.KoreaIT.java.AM.controller.MemberController;

public class ArticleController extends Controller {
	List<Article> articles_board;
	
	private Scanner sc;
	private String command;
	private String actionMethodName;

	public ArticleController(Scanner sc) {
		this.articles_board = new ArrayList<>();;
		this.sc = sc;
	}
	
	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "write":
			if (isLogined() == false) {
				System.out.println("로그인 상태가 아닙니다");
				break;
			}
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			if (isLogined() == false) {
				System.out.println("로그인 상태가 아닙니다");
				break;
			}
			doModify();
			break;
		case "delete":
			if (isLogined() == false) {
				System.out.println("로그인 상태가 아닙니다");
				break;
			}
			doDelete();
			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다.");
			break;
		}
	}
	
	int lastArticleId = 3;
	
	// 입력
	public void doWrite() {
		int id = lastArticleId + 1;

		System.out.print("제목 : ");
		String title = sc.nextLine();
		
		System.out.print("내용 : ");
		String content = sc.nextLine();
		
		String regDate = Util.getNowDateTimeStr();

		Article article = new Article(id, loginedMember.ID, regDate, regDate, title, content);
		articles_board.add(article);

		System.out.println(id + "번 글이 생성되었습니다.");
		lastArticleId++;
			
	}

	// 목록
	public void showList() {
		if (articles_board.size() == 0) {
			
			System.out.println("게시글이 없습니다.");
//			continue; // 반복문의 제어 continue (에러) -> 리턴을 써준다.
			return;
		}
		
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
//				continue;
				return;
			}
		}
		
		System.out.println("번호 / 제목 / 조회");	// 검색어가 없는 경우
		
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			
			Article articles_list = forPrintArticles.get(i); // 값 읽어와서 변수에 저장
			System.out.println(" " + articles_list.ID + "  / " + articles_list.Title + " / " + articles_list.Count);
		}
	}
	
	
	// 세부사항
	public void showDetail() {
		String[] cmdDiv = command.split(" ");
		
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 확인해 주세요.");
			return;
		}
		
		int num = Integer.parseInt(cmdDiv[2]);
		
		Article foundArticle = getArticleId(num);
		
		if (foundArticle == null) {
			System.out.println(num + "번 게시물은 존재하지 않습니다.");
			return;
		}
		
		foundArticle.Count++;
		
		System.out.println("번호 : " + foundArticle.ID);
		System.out.println("작성날짜 : " + foundArticle.RegDate);
		System.out.println("수정날짜 : " + foundArticle.UpdateDate);
		System.out.println("제목 : " + foundArticle.Title);
		System.out.println("내용 : " + foundArticle.Content);
		System.out.println("조회수 : " + foundArticle.Count);
	}
	
	// 수정
	public void doModify() {
		String[] cmdDiv = command.split(" ");
		
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 확인해 주세요.");
			return;
		}
		
		int num = Integer.parseInt(cmdDiv[2]);
		
		Article foundArticle = getArticleId(num);
		
		if (foundArticle == null) {
			System.out.println(num + "번 게시물은 존재하지 않습니다.");
			return;
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
	
	
	// 삭제
	public void doDelete() {
		String[] cmdDiv = command.split(" ");
		
		if (cmdDiv.length < 3) {
			System.out.println("명령어를 확인해 주세요.");
			return;
		}
		
		int num = Integer.parseInt(cmdDiv[2]);

		int foundIndex = getArticleIndex(num);
		
		if (foundIndex == -1) {
			System.out.println(num + "번 게시물은 존재하지 않습니다.");
			return;
		}
		
		articles_board.remove(foundIndex); 
		
		System.out.println(num + "번 글이 삭제되었습니다.");
		
	}
	
	private Article getArticleId(int num) {
		int index = getArticleIndex(num);
		
		if (index != -1) {
			return articles_board.get(index);
		}
		
		return null;
	}
	
	private int getArticleIndex(int num) {
		int i = 0;
		for (Article articles_find : articles_board) {
			if (articles_find.ID == num) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	// 테스트 데이터
	public void makeTestData() {		// static -> public
		System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
		articles_board.add(new Article(1, 3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));
		articles_board.add(new Article(2, 2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));
		articles_board.add(new Article(3, 2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));
	}
}
