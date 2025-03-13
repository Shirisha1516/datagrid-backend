package com.tml.AIP_POSITION_JDG_TRANS.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponse;
import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponseMarshaller;
import com.tml.AIP_POSITION_JDG_TRANS.jdgrepo.JdgPositionUICustRepo;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PositionUIServiceImpl implements PositionUIService {
	
	private static Logger logger = LoggerFactory.getLogger(PositionUIResponseMarshaller.class);
	private JdgPositionUICustRepo JdgPositionUICustRepo;
	private ObjectMapper objectMapper;
	public PositionUIServiceImpl(JdgPositionUICustRepo JdgPositionUICustRepo) {
		this.JdgPositionUICustRepo = JdgPositionUICustRepo;
		this.objectMapper=new ObjectMapper();
	}

	@Override
	public List<PositionUIResponse> findByOrderId(String OrderId) {
		logger.info("Entering Method  PositionUIServiceImpl.findByStartDateAndEndDate");
		List PositionUIList=JdgPositionUICustRepo.findAll(OrderId);
		logger.info("Exiting Method  PositionUIServiceImpl.findByStartDateAndEndDate");
	 return	PositionUIList;
	}

	@Override
	public List<Map> getPositionUIListAsListOfMap(List<PositionUIResponse> PositionUIResponseList) {
		List<Map> PositionUIMapList=this.objectMapper.convertValue(PositionUIResponseList, new TypeReference<List<Map>>() {});

		return PositionUIMapList;
	}

}