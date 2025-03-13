package com.tml.AIP_POSITION_JDG_TRANS.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Set;

import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponse;
import com.tml.AIP_POSITION_JDG_TRANS.exceptions.BlogNotFoundException;

public interface JdgService {
	
	 boolean put(List<PositionUIResponse> PositionUIResponseList) throws Exception;

	    

	    /*
	     * Put all data of time period
	     */
	    boolean putAll(String OrderId) throws Exception;

	    /*
	     * Get All Account extract Data
	     */
	    List<PositionUIResponse> getAll() throws Exception;
	   

	    /*
	     * Update or add value to index map
	     */
	    boolean delete(String OrderId) throws Exception;
	    
	   Set<PositionUIResponse> search(HashMap<String, String> paramMap) throws Exception;
	    //Set<PositionUIResponse> search(String PAR_ROW_ID) throws Exception;



	List<PositionUIResponse> getAll(String div_name);



	boolean deleteById(String par_row_id) throws Exception;

}