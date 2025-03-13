package com.tml.AIP_POSITION_JDG_TRANS.esb;

import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoField;

import com.fasterxml.jackson.annotation.JsonFormat;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@JsonPropertyOrder({"User_Id"})

//@XmlType(propOrder = {"div_name"}) 
@XmlAccessorType(XmlAccessType.FIELD)

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "Student_APIResponse")

public class StudentResponse {
	
	@XmlElement(name = "User_Id")
	@JsonProperty("User_Id")
	private int user_id;

	@XmlElement(name = "User_Name")
	@JsonProperty("User_Name")
    private String user_name;
	
	@XmlElement(name = "Email_Id")
	@JsonProperty("Email_Id")
    private String email_id;
	
	@XmlElement(name = "Mobile_No")
	@JsonProperty("Mobile_No")
    private int mobile_no;
	
	@XmlElement(name = "Lab_Name")
	@JsonProperty("Lab_Name")
    private String lab_name;
	
	@XmlElement(name = "Book_Name")
	@JsonProperty("Book_Name")
    private String book_name;
	
	@XmlElement(name = "Author")
	@JsonProperty("Author")
    private String author;
	
	@XmlElement(name = "price")
	@JsonProperty("price")
    private Double price;
	
	 @XmlTransient
	    @JsonIgnore
	    private String rowId;
	 
	 @ProtoField(number = 1, required = false , defaultValue = "0")
		public int getUser_id() {
			return user_id;
		}

		public void setUser_id(int user_id) {
			this.user_id = user_id;
		}

		@ProtoField(number = 2, required = false, defaultValue = "null")
		public String getUser_name() {
			return user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		@ProtoField(number = 3, required = false, defaultValue = "null")
		public String getEmail_id() {
			return email_id;
		}

		public void setEmail_id(String email_id) {
			this.email_id = email_id;
		}

		@ProtoField(number = 4, required = false, defaultValue = "0")
		public int getMobile_no() {
			return mobile_no;
		}

		public void setMobile_no(int mobile_no) {
			this.mobile_no = mobile_no;
		}

		@ProtoField(number = 5, required = false, defaultValue = "null")
		public String getLab_name() {
			return lab_name;
		}

		public void setLab_name(String lab_name) {
			this.lab_name = lab_name;
		}

		
		 @ProtoField(number = 6, required = false, defaultValue = "null")
			public String getBook_name() {
				return book_name;
			}

			public void setBook_name(String book_name) {
				this.book_name = book_name;
			}

			 @ProtoField(number = 7, required = false, defaultValue = "null")
				public String getAuthor() {
					return author;
				}

				public void setAuthor(String author) {
					this.author = author;
				}
				
				 @ProtoField(number = 8, required = false, defaultValue = "0")
					public Double getPrice() {
						return price;
					}

					public void setPrice(Double price) {
						this.price = price;
					}


		 @ProtoField(number = 9, required = false)
		public String getRowId() {
			return rowId;
		}

		public void setRowId(String rowId) {
			this.rowId = rowId;
		}    
   
	
}
