package com.cianmcgovern.android.ShopAndShare;

import java.io.Serializable;

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	private String productName;
	private String price;
	private int rating;
	
	public Item(String product, String price,int rating){
		this.productName=product;
		this.price=price;
		this.rating=rating;
	}
	
	public Item(String product, String price){
		this.productName=product;
		this.price=price;
		this.rating=0;
	}
	
	public String getProduct(){
		return this.productName;
	}
	
	public String getPrice(){
		return this.price;
	}
	
	public int getRating(){
		return this.rating;
	}
	
	public void setProduct(String product){
		this.productName=product;
	}
	
	public void setPrice(String price){
		this.price=price;
	}
	
	public void setRating(int rating){
		this.rating=rating;
	}
}