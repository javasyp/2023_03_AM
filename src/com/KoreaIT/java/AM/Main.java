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
					for (int j = 0; j < articles_list.size(); j++) {
						
						Article articles_txt = articles_list.get(j);
						System.out.println(" " + articles_txt.ID + "  / " + articles_txt.Title);
					}
				}
			} else if (command.equals("article write")) {
				System.out.print("제목 : ");
				String title = sc.nextLine();
				
				System.out.print("내용 : ");
				String content = sc.nextLine();
				
				articles_list.add(new Article(lastId, title, content));
				
				lastId++;
				System.out.println(lastId + "번 글이 생성되었습니다.");
				
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
	
	Article(int 번호, String 제목, String 내용) {
		this.ID = 번호;
		this.Title = 제목;
		this.Content = 내용;
	}
	
}