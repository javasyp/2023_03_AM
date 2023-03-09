package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("=== 프로그램 시작 ===");

		Scanner sc = new Scanner(System.in);

		List<Article> articles_list = new ArrayList<>();

		int lastId = 0;

		while (true) {
			System.out.print("명령어 > ");
			String command = sc.nextLine();

			// 추가
			if (command.length() == 0) {
				System.out.println("명령어를 입력해 주세요.");
				continue;
			}
			// 여기까지

			if (command.equals("exit")) {
				break;
			}

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
			} else if (command.equals("article write")) {
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

			} else {
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