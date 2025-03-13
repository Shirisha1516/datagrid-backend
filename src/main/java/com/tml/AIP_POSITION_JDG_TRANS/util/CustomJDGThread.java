package com.tml.AIP_POSITION_JDG_TRANS.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponse;
import com.tml.AIP_POSITION_JDG_TRANS.service.JdgService;
import com.tml.AIP_POSITION_JDG_TRANS.service.PositionUIService;
import com.tml.AIP_POSITION_JDG_TRANS.util.CustomJDGThread;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Callable;

public class CustomJDGThread implements Callable<Boolean> {

    private static Logger logger = LoggerFactory.getLogger(CustomJDGThread.class);
    private String orderId;
   
    public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	private JdgService jdgServiceImpl;
    private PositionUIService jdgConnectionServiceImpl;

    private List<PositionUIResponse> PositionUIResponseList;

    public CustomJDGThread(String OrderId, JdgService jdgServiceImpl, PositionUIService jdgConnectionServiceImpl) {
        this.orderId = OrderId;       
        this.jdgServiceImpl = jdgServiceImpl;
        this.jdgConnectionServiceImpl = jdgConnectionServiceImpl;
    }

    

    

    @Override
    public Boolean call() throws Exception {
        logger.info("Entering Method  CustomJDGThread.call");
        String OrderId= this.orderId;       
        PositionUIResponseList= this.jdgConnectionServiceImpl.findByOrderId(OrderId);
        logger.info("Exiting Method  CustomJDGThread.call");
        return this.jdgServiceImpl.put(PositionUIResponseList);
    }

}