package com.KoreaIT.java.AM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("=== 프로그램 시작 ===");

		Scanner sc = new Scanner(System.in);
		
		// 게시물을 담을 배열 리스트 생성
		// 주의 ※ 반복문 안에 넣으면 계속 초기화 됨!!!
		List<Article> articles_list = new ArrayList<>();

		int lastId = 0;

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
				if (articles_list.size() == 0) {
					
					System.out.println("게시글이 없습니다.");

				} else {
					System.out.println("번호 / 제목");
					
					// 최신순으로 출력하기
					for (int i = articles_list.size() - 1; i >= 0; i--) {
						
						Article articles_body = articles_list.get(i); // 값 읽어와서 변수에 저장
						System.out.println(" " + articles_body.ID + "  / " + articles_body.Title);
					}
				}
			}
			
			// 2. 게시물 작성하기
			// 제목과 내용을 배열 리스트에 저장
			else if (command.equals("article write")) {
				int id = lastId + 1;

				System.out.print("제목 : ");
				String title = sc.nextLine();

				System.out.print("내용 : ");
				String content = sc.nextLine();

//				Article article = new Article(id, title, content);
//				articles_list.add(article);				
				articles_list.add(new Article(id, title, content));

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
				
				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해 주세요.");
					continue;
				}
				
				int num = Integer.parseInt(cmdDiv[2]); 	// String -> Int 형 변환
				
				Article foundArticle = null;
				
				// 게시물 있는 경우
				for (int i = 0; i < articles_list.size(); i++) {
					Article articles_detail = articles_list.get(i);
					
					if (articles_detail.ID == num) {
						foundArticle = articles_detail;
						break;
					}
				}
				
				if (foundArticle == null) {
					System.out.println(num + "번 게시물은 존재하지 않습니다.");
					continue;
				} else {
					System.out.println("번호 : " + foundArticle.ID);
					System.out.println("날짜 : " + LocalDate.now());
					System.out.println("제목 : " + foundArticle.Title);
					System.out.println("내용 : " + foundArticle.Content);
				}
			}
			
			else {
				System.out.println("존재하지 않는 명령어입니다.");
			}
		}

		System.out.println("=== 프로그램 종료 ===");

		sc.close();
	}
}

class Article {
	int ID;
	String Title;
	String Content;

	Article(int id, String title, String content) {
		this.ID = id;
		this.Title = title;
		this.Content = content;
	}

}