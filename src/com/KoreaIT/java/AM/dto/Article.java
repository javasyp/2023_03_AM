package com.KoreaIT.java.AM.dto;

public class Article extends Dto {
	public String Title;
	public String Content;
	public int Count;
	public int MemberId;
	
	public Article(int id, int memberId, String regDate, String updateDate, String title, String content) {
		this(id, memberId, regDate, updateDate, title, content, 0);
	}
	
	public Article(int id, int memberId, String regDate, String updateDate, String title, String content, int count) {
		this.ID = id;
		this.RegDate = regDate;
		this.UpdateDate = updateDate;
		this.Title = title;
		this.Content = content;
		this.Count = count;
		this.MemberId = memberId;
	}
}
