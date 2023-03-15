package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.service.ArticleService;
import com.KoreaIT.java.AM.util.Util;

public class ArticleController extends Controller {
	private List<Article> articles_board;
	
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ArticleService articleService;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.articleService = Container.articleService;
	}
	
	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다.");
			break;
		}
	}
	
	// 입력
	public void doWrite() {
		// 컨트롤러 -> 서비스 -> dao
		int id = articleService.setNewId();

		System.out.print("제목 : ");
		String title = sc.nextLine();
		
		System.out.print("내용 : ");
		String content = sc.nextLine();
		
		String regDate = Util.getNowDateTimeStr();

		Article article = new Article(id, loginedMember.ID, regDate, regDate, title, content);
//		articles_board.add(article);	// 직접 DB에 추가
//		Container.articleDao.articles.add(article);
//		Container.articleDao.add(article);		// 컨테이너를 통해 창고에 간접 추가
		articleService.add(article);
		
		System.out.println(id + "번 글이 생성되었습니다.");
	}

	// 목록
	public void showList() {
		
		String searchKeyword = command.substring("article list".length()).trim();
		
		List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);
		
		if (forPrintArticles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}
		
		System.out.println("번호 / 제목 / 조회 / 작성자");	// 검색어가 없는 경우
		
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			String writerName = null;

			List<Member> members = Container.memberDao.members;
			
			Article articles_list = forPrintArticles.get(i); // 값 읽어와서 변수에 저장
			
			// 작성자의 이름 출력하기
			for (Member member : members) {
				if (articles_list.MemberId == member.ID) {
					writerName = member.name;
					break;
				}
			}
			
			System.out.println(" " + articles_list.ID + "  / " + articles_list.Title + " / " + articles_list.Count + " / " + writerName);
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
		
		Article foundArticle = articleService.getArticleById(num);
		
		if (foundArticle == null) {
			System.out.println(num + "번 게시물은 존재하지 않습니다.");
			return;
		}
		
		// 작성자의 이름 출력하기
		String writerName = null;
		
		List<Member> members = Container.memberDao.members;
		
		for (Member member : members) {
			if (foundArticle.MemberId == member.ID) {
				writerName = member.name;
				break;
			}
		}
		
		foundArticle.Count++;
		
		System.out.println("번호 : " + foundArticle.ID);
		System.out.println("작성날짜 : " + foundArticle.RegDate);
		System.out.println("수정날짜 : " + foundArticle.UpdateDate);
		System.out.println("작성자 : " + writerName);
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
		
		Article foundArticle = articleService.getArticleById(num);
		
		if (foundArticle == null) {
			System.out.println(num + "번 게시물은 존재하지 않습니다.");
			return;
		}
		
		if (foundArticle.MemberId != loginedMember.ID) {
			System.out.println("권한이 없습니다");
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
		
		// index 대신 객체 사용
		Article foundArticle = articleService.getArticleById(num);
		
		if (foundArticle == null) {
			System.out.println(num + "번 게시물은 존재하지 않습니다.");
			return;
		}
		
		if (foundArticle.MemberId != loginedMember.ID) {
			System.out.println("권한이 없습니다");
			return;
		}
		
		articleService.remove(foundArticle); 
		
		System.out.println(num + "번 글이 삭제되었습니다.");
		
	}
	
	// 테스트 데이터
	public void makeTestData() {		// static -> public
		System.out.println("테스트를 위한 게시글 데이터를 생성합니다.");
		// 1번 김철수, 2번 김영희, 3번 홍길동
		articleService.add(new Article(1, 3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));	// 홍길동
		articleService.add(new Article(2, 2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));	// 김영희
		articleService.add(new Article(3, 2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));	// 김영희
	}
}
