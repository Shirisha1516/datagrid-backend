package com.tml.AIP_POSITION_JDG_TRANS.esb;

public class StudentDetails {

	private int user_id;
	private String user_name;
	private String email_id;
	private int mobile_no;
	//private int Lab_Id;
	private String lab_name;
	//private int Book_Id;
	private String book_name;
	private String author;
	private Double price;
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public int getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(int mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getLab_name() {
		return lab_name;
	}
	public void setLab_name(String lab_name) {
		this.lab_name = lab_name;
	}
	public String getBook_name() {
		return book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "StudentDetails [user_id=" + user_id + ", user_name=" + user_name + ", email_id=" + email_id
				+ ", mobile_no=" + mobile_no + ", lab_name=" + lab_name + ", book_name=" + book_name + ", author="
				+ author + ", price=" + price + "]";
	}
	
}
