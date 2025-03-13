package com.tml.AIP_POSITION_JDG_TRANS.jdgrepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponse;
import com.tml.AIP_POSITION_JDG_TRANS.jdgrepo.JdgPositionUICustRepo;
import com.tml.AIP_POSITION_JDG_TRANS.config.JdgProperties;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdgPositionUICustRepo {
	
	private static Logger logger = LoggerFactory.getLogger(JdgPositionUICustRepo.class);
	@Autowired
	private JdgProperties jdgProperties;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<PositionUIResponse> findAll(String OrderId) {
		logger.info("Entering Method  JdgCustomRepository.findAll");
		String query = jdgProperties.getSelectAccountData();
		//query = query.replace("%?1", OrderId);
		System.out.println("query :::" + query);

		//List result = jdbcTemplate.query(query, new BeanPropertyRowMapper(PositionUIResponse.class));	
		
		@SuppressWarnings("unchecked")
		List<PositionUIResponse> result = null;
		try {
		result = jdbcTemplate.query(query, new BeanPropertyRowMapper<PositionUIResponse>(PositionUIResponse.class));		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		System.out.println("Exiting Method JdgCustomRepository.findAll" + result.toString());

		logger.info("Exiting Method JdgCustomRepository.findAll" + result.toString());
		return result;

	}

	private List getPositionUIResponse() {
		List<PositionUIResponse> list = new ArrayList<>();
		PositionUIResponse PositionUIResponse = null;
		for (int i = 0; i < 20; i++) {
			PositionUIResponse = new PositionUIResponse();
			//PositionUIResponse.setRowId("VJ"+ i); 
			System.out.println(PositionUIResponse);
			list.add(PositionUIResponse);
		}

		return list;
	}

}