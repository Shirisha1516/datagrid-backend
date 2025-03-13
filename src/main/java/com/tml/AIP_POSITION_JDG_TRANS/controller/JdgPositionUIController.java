package com.tml.AIP_POSITION_JDG_TRANS.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tml.AIP_POSITION_JDG_TRANS.controller.JdgPositionUIController;
import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponse;
import com.tml.AIP_POSITION_JDG_TRANS.exceptions.CustomException;
import com.tml.AIP_POSITION_JDG_TRANS.exceptions.NotFoundException;
import com.tml.AIP_POSITION_JDG_TRANS.service.JdgService;

@RestController
@RequestMapping(path = "/api/crm")
public class JdgPositionUIController {

	private static Logger logger = LoggerFactory.getLogger(JdgPositionUIController.class);
	@Autowired
	JdgService jdgServiceImpl;
	
	
	
	  @CrossOrigin
		@PostMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
		public boolean putAll() throws Exception {
			logger.info("Entering Method  JdgPositionUIController.putAll");
			String orderId = "1";
			
			try {

				jdgServiceImpl.putAll(orderId);

			} catch (DateTimeParseException ex) {
				logger.error("Exception occurred with while parsing Dates startDate {} and endDate {}", orderId);
				throw new Exception("Exception occurred while parsing Dates : ", ex);
			} catch (Exception e) {
				logger.error("Exception occurred with e {}", e);
				throw new Exception("Exception occurred while processing : ", e);
			}
			logger.info("Exiting Method  JdgPositionUIController.putAll");
			return true;
		}

	@CrossOrigin
	@GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PositionUIResponse> getData() throws Exception {
		logger.info("Entering Method  JdgPositionUIController.getData");
		List<PositionUIResponse> PositionUIList = null;
		try {
			
			PositionUIList = jdgServiceImpl.getAll();
		} catch (Exception e) {
			logger.error("Exception occurred with e {}", e);
			// throw new Exception("Exception occurred while processing : ",e);
		}
		logger.info("Exiting Method  JdgPositionUIController.getData");
		return PositionUIList;
	}

	
	  @CrossOrigin
    @PostMapping(path = "/delete" , produces = MediaType.APPLICATION_JSON_VALUE )
    public boolean delete() throws Exception {
        logger.info("Entering Method  JdgPositionUIController.delete");
        String orderId = "1";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			  jdgServiceImpl.delete(orderId);
        }
        catch (DateTimeParseException ex){
            logger.error("Exception occurred with while parsing Dates startDate {} and endDate {}", orderId);
            throw new Exception("Exception occurred while parsing Dates : ",ex);
        }
        catch (Exception e) {
            logger.error("Exception occurred with e {}", e);
            throw new Exception("Exception occurred while processing : ",e);
        }
        logger.info("Exiting Method  JdgPositionUIController.delete");
        return true;
    }
    
    @CrossOrigin
    @PostMapping(path = "/search" , produces = MediaType.APPLICATION_JSON_VALUE )
   public Set<PositionUIResponse> search(@RequestBody HashMap<String, String> paramMap) throws Exception{
   // public Set<PositionUIResponse> search(@RequestParam("PAR_ROW_ID")String PAR_ROW_ID) throws Exception{

   //public Set<PositionUIResponse> search(@RequestBody String PAR_ROW_ID) throws Exception{
        /*logger.info("Entering Method  JdgPositionUIController.search");
        logger.info("Entering Method  JdgPositionUIController.search"+paramMap);
        logger.info("Entering Method  JdgPositionUIController.search"+paramMap.toString());
        logger.info("Entering Method  JdgPositionUIController.search"+paramMap.size());*/
    	//System.out.println(paramMap.size()+"==========="+paramMap);
        Set<PositionUIResponse> searchedList = null;
        try {
            searchedList = jdgServiceImpl.search(paramMap);
        }
       catch (DateTimeParseException ex){
            logger.error("Exception occurred with while parsing Dates startDate and endDate");
            throw new Exception("Exception occurred while parsing Dates : ",ex);
        }
        catch (Exception e) {
            logger.error("Exception occurred with e {}", e);
            throw new Exception("Exception occurred while processing : ",e);
        }
   
        logger.info("Exiting Method  JdgPositionUIController.search");
        return searchedList;

    }
    
    
   // @CrossOrigin
    @PostMapping(path = "/searchJdg" )
   public List<HashMap<String, Object>> searchJdg(@RequestBody HashMap<String, String> Mapparam) {

         logger.info("Entering Method  JdgPositionUIController.search");
        logger.info("Entering Method  JdgPositionUIController.search"+Mapparam.isEmpty());
        logger.info("Entering Method  JdgPositionUIController.search"+Mapparam.toString());

        logger.info("Entering Method  JdgPositionUIController.search"+Mapparam.size());
       // Set<PositionUIResponse> searchedList = null;
        try {
         //   searchedList = jdgServiceImpl.search(Mapparam);
        }catch (DateTimeParseException ex){
            logger.error("Exception occurred with while parsing Dates startDate and endDate");

            //throw new Exception("Exception occurred while parsing Dates : ",ex);
        }
        catch (Exception e) {
            logger.error("Exception occurred with e {}", e);
           // throw new Exception("Exception occurred while processing : ",e);
        }
        logger.info("Exiting Method  JdgPositionUIController.search");

        return null;
    }

    
    // @Scheduled(fixedRate = 1000)
	// @Scheduled(cron = "0 0 13 * * *")
	// @Scheduled(cron = "*/60 * * * * *")
	public boolean putAllTest() throws Exception {
		logger.info("Entering Method  JdgPositionUIController.putAll");
		String orderId = "1";
		try {
    System.out.println("============scheduler called=====insert=====");
         jdgServiceImpl.putAll(orderId);

		} catch (DateTimeParseException ex) {
			logger.error("Exception occurred with while parsing Dates startDate {} and endDate {}", orderId);
			throw new Exception("Exception occurred while parsing Dates : ", ex);
		} catch (Exception e) {
			logger.error("Exception occurred with e {}", e);
			throw new Exception("Exception occurred while processing : ", e);
		}
		logger.info("Exiting Method  JdgPositionUIController.putAll");
		return true;
	}


		// @Scheduled(cron = "0 0 13 * * *")
		// @Scheduled(cron = "*/58 * * * * *")
	    public boolean deleteTest() throws Exception {
	        logger.info("Entering Method  JdgPositionUIController.delete");
	        String orderId = "1";
	        try {
	        	 System.out.println("============scheduler called=====delete=====");
	            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				  jdgServiceImpl.delete(orderId);
	        }
	        catch (DateTimeParseException ex){
	            logger.error("Exception occurred with while parsing Dates startDate {} and endDate {}", orderId);
	            throw new Exception("Exception occurred while parsing Dates : ",ex);
	        }
	        catch (Exception e) {
	            logger.error("Exception occurred with e {}", e);
	            throw new Exception("Exception occurred while processing : ",e);
	        }
	        logger.info("Exiting Method  JdgPositionUIController.delete");
	        return true;
	    }
	    
	   //============================================
	    
	    @CrossOrigin
	    @PostMapping(path = "/deleteById" , produces = MediaType.APPLICATION_JSON_VALUE )
	    public boolean deleteById(@RequestParam("par_row_id")String par_row_id) throws Exception {
	        logger.info("Entering Method  JdgPositionUIController.deleteById");
	     
	        try {
	                jdgServiceImpl.deleteById(par_row_id);
	        }
	        catch (DateTimeParseException ex){
	            logger.error("Exception occurred with while parsing Dates startDate {} and endDate {}", par_row_id);
	            throw new Exception("Exception occurred while parsing Dates : ",ex);
	        }
	        catch (Exception e) {
	            logger.error("Exception occurred with e {}", e);
	            throw new Exception("Exception occurred while processing : ",e);
	        }
	        logger.info("Exiting Method  JdgPositionUIController.deleteById");

	        return true;
	    }
	   //===========================================
}