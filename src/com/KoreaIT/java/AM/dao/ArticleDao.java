package com.KoreaIT.java.AM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.AM.dto.Article;

public class ArticleDao extends Dao {
	public List<Article> articles;

	public ArticleDao() {
		articles = new ArrayList<>();
	}
	
	// 저장
	public void add(Article article) {
		articles.add(article);
		lastId++;
	}
	
	//삭제
	public void remove(Article article) {
		articles.add(article);
	}
	
	// 오버라이딩
	public int getLastId() {
		return lastId;
	}

	public int setNewId() {
		return lastId + 1;
	}
	
	// 게시물 번호 찾기, 인덱스 찾기
	public int getArticleIndexById(int num) {
		int i = 0;
		for (Article article : articles) {
			if (article.ID == num) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public Article getArticleById(int num) {
		int index = getArticleIndexById(num);
		
		if (index != -1) {
			return articles.get(index);
		}
		
		return null;
	}

	// 제목 검색하기
	public List<Article> getArticles(String searchKeyword) {
		if (searchKeyword.length() != 0 && searchKeyword != null) {
			System.out.println("searchKeyword : " + searchKeyword);

			List<Article> forPrintArticles = new ArrayList<>();

			if (searchKeyword.length() > 0) {
				for (Article article : articles) {
					if (article.Title.contains(searchKeyword)) {
						forPrintArticles.add(article);
					}
				}
			}

			return forPrintArticles;
		}
		return articles;
	}
}