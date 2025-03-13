package com.tml.AIP_POSITION_JDG_TRANS.config;



public enum SearchParameters {
	
	PAR_ROW_ID("par_row_id"),
	PARTNER_NAME("partner_name"),
	DEALER_CODE("dealer_code"),
	CREDIT_MNTH("credit_mnth"),
	CREDIT_YR("credit_yr"),
	INVC_TYPE("invc_type");
	String value;

    public String getValue() {
        return value;
    }

    SearchParameters(String value) {
        this.value = value;
    }

}