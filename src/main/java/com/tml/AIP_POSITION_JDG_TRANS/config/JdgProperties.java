package com.tml.AIP_POSITION_JDG_TRANS.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("jdg")
public class JdgProperties {
	
	 private String hostname;
	    private String port;
	    private String selectAccountData;
	    private int totalDays;
	    private int totalValue;

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


	    public String getSelectAccountData() {
	        return selectAccountData;
	    }

	    public void setSelectAccountData(String selectAccountData) {
	        this.selectAccountData = selectAccountData;
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
}