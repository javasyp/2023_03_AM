package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	static List<Article> articles_board = new ArrayList<>();
	// main과 makeTestData 메소드가 static이기 때문에 static 붙여준다.
	
	public static void main(String[] args) {
		
		System.out.println("=== 프로그램 시작 ===");
		
//		List<Article> articles_board = new ArrayList<>();
		// 현재 지역변수인 articles_board를 전역변수로 빼기
		
		// 테스트할 데이터 미리 생성하기
		makeTestData();

		Scanner sc = new Scanner(System.in);
		
		// 게시물을 담을 배열 리스트 생성
		// 주의 ※ 반복문 안에 넣으면 계속 초기화 됨!!!
		
		// makeTestData() 호출하여 이미 3개가 들어와 있는 상태
		int lastId = 3;
		
		int count = 0;

		while (true) {
			System.out.print("명령어 > ");
			String command = sc.nextLine();

			// 추가
			// 아무것도 입력 안 했을 때
			if (command.length() == 0) {
				System.out.println("명령어를 입력해 주세요.");
				continue;
			}
			// 여기까지
			
			// exit 입력 시 종료
			if (command.equals("exit")) {
				break;
			}
			
			
			// 1. 게시판 목록 출력하기
			// 리스트에 게시글이 있으면 최신순으로 글 내용 출력
			// 없으면 "게시글이 없습니다." 출력
			if (command.equals("article list")) {
				if (articles_board.size() == 0) {
					
					System.out.println("게시글이 없습니다.");

				} else {
					System.out.println("번호 / 제목 / 조회");
					
					// 최신순으로 출력하기
					for (int i = articles_board.size() - 1; i >= 0; i--) {
						
						Article articles_list = articles_board.get(i); // 값 읽어와서 변수에 저장
						System.out.println(" " + articles_list.ID + "  / " + articles_list.Title + " / " + articles_list.Count);
					}
				}
			}
			
			// 2. 게시글 입력하기
			// 제목과 내용을 배열 리스트에 저장
			else if (command.equals("article write")) {
				int id = lastId + 1;

				System.out.print("제목 : ");
				String title = sc.nextLine();
				
				System.out.print("내용 : ");
				String content = sc.nextLine();
				
				// 작성 날짜 저장
				String regDate = Util.getNowDateTimeStr();

//				Article article = new Article(id, title, content);
//				articles_board.add(article);
				articles_board.add(new Article(id, regDate, regDate, title, content, count));

				System.out.println(id + "번 글이 생성되었습니다.");
				lastId++;
				
			}
			
			// 3. 게시글의 세부사항 출력하기
			// "article detail 번호" 입력 시 없으면 "게시물이 존재하지 않습니다."
			// 있으면 번호, 날짜, 제목, 내용 출력
			else if (command.startsWith("article detail")) {				
				// "article" "delete" "1" 로 쪼개어 저장
				String[] cmdDiv = command.split(" ");
				// cmdDiv[0] : article
				// cmdDiv[1] : delete
				// cmdDiv[2] : (숫자 부분)
				
				// 몇 번인지 확인이 안될 때
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				// 문자열을 정수형으로 형 변환
				int num = Integer.parseInt(cmdDiv[2]);
				
				
				Article foundArticle = getArticleId(num);
				
				// 게시물 있는지 판별 (배열 순회하여 비교)
				// 중복 구간 시작!! (중복 코드 -> 메소드 만들기)
//				for (int i = 0; i < articles_board.size(); i++) {
//					Article articles_detail = articles_board.get(i);
//					
//					if (articles_detail.ID == num) {
//						foundArticle = articles_detail;
//						break;		// 찾았으면 그만 찾아~
//					}
//				}
				// 중복 구간 끝!!
				
				if (foundArticle == null) {	// 게시물 없는 경우
					System.out.println(num + "번 게시물은 존재하지 않습니다.");
					continue;
				}
				
				// 상세보기 할 때마다 조회수 증가
				foundArticle.Count++;
				
				System.out.println("번호 : " + foundArticle.ID);
				System.out.println("작성날짜 : " + foundArticle.RegDate);
				System.out.println("수정날짜 : " + foundArticle.UpdateDate);
				System.out.println("제목 : " + foundArticle.Title);
				System.out.println("내용 : " + foundArticle.Content);
				System.out.println("조회수 : " + foundArticle.Count);
				
			}
			
			
			// 4. 게시글 삭제하기
			// "article delete 번호" 입력 시 없으면 "n번 게시물이 존재하지 않습니다."
			// 있으면 삭제 후 "n번 게시물이 삭제되었습니다." 출력
			else if (command.startsWith("article delete")) {
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int num = Integer.parseInt(cmdDiv[2]);
				
				// 값을 삭제하면 삭제한 만큼 인덱스는 하나씩 줄게 된다.
				// 그래서 내가 찾는 글의 번호와 실제 인덱스의 값이 일치하지 않는다.
				// 삭제 후 저장할 때 인덱스는 난 자리부터 채워지기 때문에
				// 반드시 변수를 따로 지정해준다.
				int foundIndex = getArticleIndex(num);
				
				if (foundIndex == -1) {	// 게시물 없는 경우
					System.out.println(num + "번 게시물은 존재하지 않습니다.");
					continue;
				}
				
				articles_board.remove(foundIndex); 
				
				System.out.println(num + "번 글이 삭제되었습니다.");
				
			}
			
			
			// 5. 게시글 수정하기 - 3번 상세보기와 유사함
			// "article modify 번호" 입력 시 없으면 "n번 게시물은 존재하지 않습니다."
			// 있으면 수정된 내용 출력 후 "n번 게시물이 수정되었습니다." 출력
			else if (command.startsWith("article modify")) {
				
				String[] cmdDiv = command.split(" ");
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int num = Integer.parseInt(cmdDiv[2]);
				
				Article foundArticle = getArticleId(num);
				
				if (foundArticle == null) {	// 게시물 없는 경우
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
			
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("=== 프로그램 종료 ===");

		sc.close();
	}
	
	// 4
	private static int getArticleIndex(int num) {
//		for (int i = 0; i < articles_board.size(); i++) {
//			Article articles_find = articles_board.get(i);
//			if (articles_find.ID == num) {
//				return i;
//			}
//		}
//		return -1;
		
		int i = 0;
		for (Article articles_find : articles_board) {
			if (articles_find.ID == num) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	
	// 3, 5 중복 코드
	private static Article getArticleId(int num) {
//		for (int i = 0; i < articles_board.size(); i++) {
//			Article articles_find = articles_board.get(i);
//			
//			if (articles_find.ID == num) {
//				return articles_find;
//			}
//		}
//		return null;
		
//		for (Article articles_find : articles_board) {
//			if (articles_find.ID == num) {
//				return articles_find;
//			}
//		}
//		return null;
		
		int index = getArticleIndex(num);
		
		if (index != -1) {
			return articles_board.get(index);
		}
		
		return null;
		
	}
	


	// 단어 미리 입력하는 함수
	public static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles_board.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));
		articles_board.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));
		articles_board.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));
	}
	
}

class Article {
	int ID;		// 글 번호
	int Count;	// 글 조회수
	String RegDate;		// 입력날짜
	String UpdateDate;	// 수정날짜
	String Title;		// 제목
	String Content;		// 내용
	
	// 조회수 X
	// 메소드 오버로딩
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
