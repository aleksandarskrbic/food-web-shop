package com.aki.web.foodorder.domain;

import java.util.UUID;

public class Item {
	
	private String id;

	private Article article;
	
	private int amount;
	
	public Item() {
		id = UUID.randomUUID().toString();
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}	
	
	public String getId() {
		return id;
	}
	
}
