package com.tml.AIP_POSITION_JDG_TRANS.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jdg")
public class ApplicationProperties {

private String hostname;
	private String hostname1;
	private String hostname2;
	private String cluster1;
	private String cluster2;
	private String port;
	
	private int totalDays;
	private String selectOtcSalesData;
	private int totalValue;
	private String username;
	private String password;
	
	
	private String vmeInvoiceCache;
	
	
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
	public String getVmeInvoiceCache() {
		return vmeInvoiceCache;
	}
	public void setVmeInvoiceCache(String vmeInvoiceCache) {
		this.vmeInvoiceCache = vmeInvoiceCache;
	}
	
	public String getHostname1() {
		return hostname1;
	}
	public void setHostname1(String hostname1) {
		this.hostname1 = hostname1;
	}
	public String getHostname2() {
		return hostname2;
	}
	public void setHostname2(String hostname2) {
		this.hostname2 = hostname2;
	}
	public String getCluster1() {
		return cluster1;
	}
	public void setCluster1(String cluster1) {
		this.cluster1 = cluster1;
	}
	public String getCluster2() {
		return cluster2;
	}
	public void setCluster2(String cluster2) {
		this.cluster2 = cluster2;
	}
	public int getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(int totalDays) {
		this.totalDays = totalDays;
	}
	public int getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(int totalValue) {
		this.totalValue = totalValue;
	}
	public String getSelectOtcSalesData() {
		return selectOtcSalesData;
	}
	public void setSelectOtcSalesData(String selectOtcSalesData) {
		this.selectOtcSalesData = selectOtcSalesData;
	}
	
	
}
