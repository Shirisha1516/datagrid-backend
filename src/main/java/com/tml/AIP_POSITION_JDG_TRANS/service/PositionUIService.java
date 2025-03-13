package com.tml.AIP_POSITION_JDG_TRANS.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponse;

@Component
public interface PositionUIService {
	
	 List<PositionUIResponse> findByOrderId(String OrderId);
		public List<Map> getPositionUIListAsListOfMap(List<PositionUIResponse> PositionUIResponseList);

}