package com.KoreaIT.java.AM.dto;

public class Article extends Dto {
	public int Count;
	public String Title;
	public String Content;
	
	public Article(int id, String regDate, String updateDate, String title, String content) {
		this(id, regDate, updateDate, title, content, 0);
	}
	
	public Article(int id, String regDate, String updateDate, String title, String content, int count) {
		this.ID = id;
		this.RegDate = regDate;
		this.UpdateDate = updateDate;
		this.Title = title;
		this.Content = content;
		this.Count = count;
	}
}
