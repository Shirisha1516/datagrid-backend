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


@JsonPropertyOrder({"DIV_NAME"})

//@XmlType(propOrder = {"div_name"}) 
@XmlAccessorType(XmlAccessType.FIELD)

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "PositionUI_APIResponse")

public class PositionUIResponse {
	
	@XmlElement(name = "Latitude")
	@JsonProperty("Latitude")
	private long latitude;
	
	@XmlElement(name = "Longitude")
	@JsonProperty("Longitude")
    private long longitude;
	
	@XmlElement(name = "DIV_NAME")
	@JsonProperty("DIV_NAME")
    private String div_name;
	
	@XmlElement(name = "X_PREFER_PARTNER_ID")
	@JsonProperty("X_PREFER_PARTNER_ID")
    private String x_prefer_partner_id;
	
	@XmlElement(name = "PAR_ROW_ID")
	@JsonProperty("PAR_ROW_ID")
    private String par_row_id;
	
	
	@XmlElement(name = "PARTNER_NAME")
	@JsonProperty("PARTNER_NAME")
    private String partner_name;
	
	 @XmlTransient
	    @JsonIgnore
	   private String rowId;
	   // private String par_row_id;
	    
	 
	 @ProtoField(number = 1, required = false , defaultValue = "0")
	 public long getLatitude() {
			return latitude;
		}

		public void setLatitude(long latitude) {
			this.latitude = latitude;
		}

		@ProtoField(number = 2, required = false, defaultValue = "0")
		public long getLongitude() {
			return longitude;
		}

		public void setLongitude(long longitude) {
			this.longitude = longitude;
		}

		@ProtoField(number = 3, required = false, defaultValue = "null")
		public String getDiv_name() {
			return div_name;
		}

		public void setDiv_name(String div_name) {
			this.div_name = div_name;
		}

		@ProtoField(number = 4, required = false, defaultValue = "0")
		public String getX_prefer_partner_id() {
			return x_prefer_partner_id;
		}

		public void setX_prefer_partner_id(String x_prefer_partner_id) {
			this.x_prefer_partner_id = x_prefer_partner_id;
		}

		@ProtoField(number = 5, required = false, defaultValue = "0")
		public String getPar_row_id() {
			return par_row_id;
		}

		public void setPar_row_id(String par_row_id) {
			this.par_row_id = par_row_id;
		}

		
		 @ProtoField(number = 6, required = false, defaultValue = "null")
		public String getPartner_name() {
			return partner_name;
		}

		public void setPartner_name(String partner_name) {
			this.partner_name = partner_name;
		}

		 @ProtoField(number = 7, required = false)
		public String getRowId() {
			return rowId;
		}

		public void setRowId(String rowId) {
			this.rowId = rowId;
		}    
   
	
}