package com.tml.AIP_POSITION_JDG_TRANS.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tml.AIP_POSITION_JDG_TRANS.esb.PositionUIResponse;
import com.tml.AIP_POSITION_JDG_TRANS.exceptions.CustomException;
import com.tml.AIP_POSITION_JDG_TRANS.service.JdgServiceImpl;
import com.tml.AIP_POSITION_JDG_TRANS.service.PositionUIService;
import com.tml.AIP_POSITION_JDG_TRANS.util.CustomJDGThread;
import com.tml.AIP_POSITION_JDG_TRANS.config.JdgConnection;
import com.tml.AIP_POSITION_JDG_TRANS.config.JdgConstants;
import com.tml.AIP_POSITION_JDG_TRANS.config.JdgProperties;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
import com.tml.AIP_POSITION_JDG_TRANS.config.SearchParameters;

//import org.springframework.exchan
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.Search;
import org.infinispan.query.dsl.*;

@Service
public class JdgServiceImpl implements JdgService {
	
	//@Autowired
	//PositionUIService PositionUIService;

	
	private static Logger logger = LoggerFactory.getLogger(JdgServiceImpl.class);
	private RemoteCache<Integer, Object> cache;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	private ObjectMapper mapper = new ObjectMapper();
	private Integer cacheObjectCounter = 0;
	private int pageLimit = 15;
	private PositionUIService jdgConnectionServiceImpl;

	private JdgConnection jdgConnection;
	private JdgProperties jdgProperties;

	public JdgServiceImpl(PositionUIService jdgConnectionServiceImpl, JdgProperties jdgProperties,JdgConnection jdgConnection) {
	//public JdgServiceImpl(JdgProperties jdgProperties,JdgConnection jdgConnection) {
		logger.info("Creating Bean of class JdgConnectionManagerImpl");
		this.jdgProperties = jdgProperties;
		this.jdgConnectionServiceImpl = jdgConnectionServiceImpl;
		this.jdgConnection = jdgConnection;
		try {
			this.cache = jdgConnection.getCache(JdgConstants.CACHE_NAME);
			logger.info("Cache "+ this.cache);
			this.mapper.setDateFormat(sdf);
			this.mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			this.mapper.configure(Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		} catch (Exception e) {
			logger.error(
					"Exception occurred while creating bean of class JdgConnectionManagerImpl with cache name {} and exception {}",
					JdgConstants.CACHE_NAME, e.toString());
		}
		logger.info("Exiting Constructor of class JdgConnectionManagerImpl");
	}

	@Override
	public boolean put(List<PositionUIResponse> PositionUIResponseList) {
		logger.info("Entering Method  JdgConnectionManagerImpl.put");

		if (PositionUIResponseList != null && PositionUIResponseList.size() != 0) {

			logger.info("Entering for getting cache to put data :");
			try {
				getUpdatedIndexValueFromCache();
			//	cache.clear();
				for (PositionUIResponse PositionUIResponse : PositionUIResponseList) {
				
					deleteById(PositionUIResponse.getPar_row_id());
					
					this.cacheObjectCounter += 1;
					this.cache.put(this.cacheObjectCounter, PositionUIResponse);

					//  int key =(int) PositionUIResponse.getLatitude();
					//	this.cache.put(key, PositionUIResponse);
					
					
				}

			} catch (Exception e) {
				logger.error("Exception occurred while putting data in cache {}", e.toString());
				return false;
			}
		}
		logger.info("Exiting Method JdgConnectionManagerImpl.put");
		return true;
	}

	@Override
	public boolean putAll(String OrderId) throws Exception {
		logger.info("Entering Method  JdgConnectionManagerImpl.putAll");

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Set<Callable<Boolean>> callables = new HashSet<>();
		String orderid  = OrderId;
		
		for (int i = 0; i < jdgProperties.getTotalValue(); i++) {
			callables.add(new CustomJDGThread(OrderId, this, this.jdgConnectionServiceImpl));
		}
		executorService.invokeAll(callables);
		logger.info("Exiting Method JdgConnectionManagerImpl.putAll");
		return false;
	}

	@Override
	public List<PositionUIResponse> getAll() {
		try
		{
		logger.info("Entering Method  JdgConnectionManagerImpl.getAll");

		QueryFactory qf = Search.getQueryFactory(this.cache);
		
		logger.info("Cache Name " + this.cache.getName());
		logger.info("Cache size " + this.cache.size());
		logger.info("QF " + qf);
		Query query = qf.from(PositionUIResponse.class).build();
		logger.info("Query " + query.toString());
		logger.info("Query " + query.getResultSize());		
		List matches = query.list();
		
		logger.info("Exiting Method JdgConnectionManagerImpl.getAll");
		
		return matches;
		} catch (Exception e) {
			logger.error("Exception occurred while getting data in cache {}", e.toString());
			return null;
		}

		
	}

	@Override
	public boolean delete(String OrderId) throws Exception {
		logger.info("Entering Method  JdgConnectionManagerImpl.delete");
		//AtomicReference<Date> startDateReference = new AtomicReference<Date>(startDate);
		//AtomicReference<Date> endDateReference = new AtomicReference<Date>(endDate);

		AtomicReference<Set<Integer>> reference = new AtomicReference<Set<Integer>>(new HashSet<Integer>());

		BiConsumer<Integer, Object> consumerReference = (Integer key, Object value) -> {
			PositionUIResponse accResp = (PositionUIResponse) value;

			/*
			 * if (accResp.getInvcDt().after(startDateReference.get()) &&
			 * accResp.getInvcDt().before(endDateReference.get())) {
			 * reference.get().add(key); }
			 */
			reference.get().add(key);
		};
		logger.info("Cache Size Before Deletion" + this.cache.size());
		logger.info("No. of entries to be removed" + reference.get().size());
		this.cache.forEach(consumerReference);

		for (Integer value : reference.get()) {

			this.cache.remove(value);
		}

		logger.info("Cache Size After Deletion" + this.cache.size());

		logger.info("Exiting Method JdgConnectionManagerImpl.delete");
		return true;
	}

	


	private void getUpdatedIndexValueFromCache() {
		logger.info("Entering Method  JdgConnectionManagerImpl.getUpdatedIndexValueFromCache");
		
		
		this.cacheObjectCounter = this.cache.size();
		logger.info("Cache Name " + this.cache.getName());
		logger.info("Cache size " + this.cache.size());
		logger.info("Entering Method  JdgConnectionManagerImpl.getUpdatedIndexValueFromCache");

		logger.info("Exiting Method JdgConnectionManagerImpl.getUpdatedIndexValueFromCache");
	}
	
	/*
	 * @Override public Set<PositionUIResponse> search(HashMap<String, String>
	 * paramMap) throws Exception { //public Set<PositionUIResponse> search(String
	 * POSTN_ROWID) throws Exception {
	 * 
	 * Map<SearchParameters, String> searchParameters = new
	 * HashMap<SearchParameters, String>(); ObjectMapper mapper = new
	 * ObjectMapper(); //HashMap<String, String> map1=paramMap;
	 * 
	 * String StrJson=""; String key=""; String value="";
	 * 
	 * try {
	 * 
	 * for (Map.Entry map : paramMap.entrySet() ) {
	 * 
	 * key = map.getKey().toString(); value = map.getValue().toString();
	 * 
	 * StrJson="{\""+key+"\":\"" + value + "\"}"; }
	 * 
	 * //StrJson="{\"POSTN_ROWID\":\"" +POSTN_ROWID+ "\"}"; logger.info("StrJson " +
	 * StrJson); // Convert JSON to Map
	 * 
	 * //searchParameters = mapper.readValue(StrJson,HashMap.class);
	 * searchParameters = mapper.readValue(StrJson,new
	 * TypeReference<Map<SearchParameters, String>>() {});
	 * 
	 * } catch (JsonGenerationException e) { e.printStackTrace(); } catch
	 * (JsonMappingException e) { e.printStackTrace(); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * 
	 * logger.info("Entering Method  JdgConnectionManagerImpl.search"+
	 * searchParameters.get(SearchParameters.PAR_ROW_ID)); QueryFactory qf =
	 * Search.getQueryFactory(this.cache); logger.info("qf " + qf); Query query =
	 * null; QueryBuilder queryBuilder = null; FilterConditionContextQueryBuilder
	 * filterConditionContext = null; List<PositionUIResponse> tmpList = null;
	 * 
	 * Set<PositionUIResponse> matches = new HashSet<>(); SearchParameters
	 * searchParametersKey = null; //String searchParametersKey = null; String
	 * searchParameterValue = null;
	 * 
	 * for (Map.Entry<SearchParameters, String> searchParameterEntrySet :
	 * searchParameters.entrySet()) {
	 * 
	 * //for (Map.Entry searchParameterEntrySet : searchParameters.entrySet()) {
	 * searchParametersKey = searchParameterEntrySet.getKey();
	 * logger.info("searchParametersKey" + searchParametersKey);
	 * searchParameterValue = searchParameterEntrySet.getValue();
	 * logger.info("searchParameterValue" + searchParameterValue); if
	 * (searchParameterValue != null && !searchParameterValue.equals("")) {
	 * logger.info("searchParameterValue" + SearchParameters.PAR_ROW_ID); if
	 * (searchParametersKey.equals(SearchParameters.PAR_ROW_ID) ) {
	 * //logger.info("searchParametersKey 11" + searchParametersKey.getValue());
	 * logger.info("searchParameterValue" + searchParameterValue);
	 * filterConditionContext = qf.from(PositionUIResponse.class)
	 * .having(searchParametersKey.getValue()).equal(searchParameterValue);
	 * logger.info("filterConditionContext" + filterConditionContext);
	 * logger.info("filterConditionContext" + filterConditionContext.toString());
	 * 
	 * } } } query = filterConditionContext.build(); logger.info("query" + query);
	 * logger.info("query" + query.toString());
	 * 
	 * tmpList = query.list(); logger.info("tmpList" + tmpList);
	 * logger.info("tmpList" + tmpList.size()); String par_row_id =
	 * searchParameters.get(SearchParameters.PAR_ROW_ID);
	 * logger.info("Exiting Method Matches size .search" + par_row_id); //String
	 * invcEndDate = searchParameters.get(SearchParameters.INVOICE_END_DATE);
	 * 
	 * if ((par_row_id != null && !par_row_id.equals("")) ) {
	 * 
	 * 
	 * tmpList = tmpList.stream().filter((PositionUIResponse accountObject) -> {
	 * return (accountObject.getPar_row_id().equals(par_row_id));
	 * }).collect(Collectors.toList()); logger.info("tmpList" + tmpList.toString());
	 * }
	 * 
	 * matches.addAll(tmpList); logger.info("Exiting Method Matches size .search" +
	 * matches.size());
	 * logger.info("Exiting Method JdgConnectionManagerImpl.search"); return
	 * matches; }
	 */

	@Override
	public List<PositionUIResponse> getAll(String div_name) {

		try
		{
		logger.info("Entering Method  JdgConnectionManagerImpl.getAll");

		QueryFactory qf = Search.getQueryFactory(this.cache);
		
		logger.info("Cache Name " + this.cache.getName());
		logger.info("Cache size " + this.cache.size());
		logger.info("QF " + qf);
		Query query = qf.from(PositionUIResponse.class).build();
		logger.info("Query " + query.toString());
		logger.info("Query " + query.getResultSize());		
		List matches = query.list();
		
		logger.info("Exiting Method JdgConnectionManagerImpl.getAll");
		
		return matches;
		} catch (Exception e) {
			logger.error("Exception occurred while getting data in cache {}", e.toString());
			return null;
		}
	}

	//========================
	
	@Override
    public boolean deleteById(String par_row_id) throws Exception {
                    logger.info("Entering Method  JdgConnectionManagerImpl.delete");
                    //AtomicReference<Date> startDateReference = new AtomicReference<Date>(startDate);
                    //AtomicReference<Date> endDateReference = new AtomicReference<Date>(endDate);

                    AtomicReference<Set<Integer>> reference = new AtomicReference<Set<Integer>>(new HashSet<Integer>());
                    BiConsumer<Integer, Object> consumerReference = (Integer key, Object value) -> {
                                    PositionUIResponse accResp = (PositionUIResponse) value;
                                    if (accResp.getPar_row_id().equals(par_row_id))
                                    {
                                      reference.get().add(key);
                                    }
                                  //  reference.get().add(key);
                    };
                    logger.info("Cache Size Before Deletion" + this.cache.size());
                    logger.info("No. of entries to be removed" + reference.get().size());
                    this.cache.forEach(consumerReference);
                    for (Integer value : reference.get()) {
                                    this.cache.remove(value);
                    }
                    logger.info("Cache Size After Deletion" + this.cache.size());
                    logger.info("Exiting Method JdgConnectionManagerImpl.delete");
                    return true;
	}
	//=========================
	
	@Override
	public Set<PositionUIResponse> search(HashMap<String, String> paramMap) throws Exception {
	//public Set<PositionUIResponse> search(String POSTN_ROWID) throws Exception {
System.out.println("==========size====="+paramMap.size());
		Map<SearchParameters, String> searchParameters = new HashMap<SearchParameters, String>();		
		ObjectMapper mapper = new ObjectMapper();
		//HashMap<String, String> map1=paramMap;
		
		String StrJson="";
		String StrNewJson="";
		 String key="";
		 String value="";
		 
		try {			
			
			for (Map.Entry map : paramMap.entrySet() ) {
				
                 key = map.getKey().toString();
                 value = map.getValue().toString();
                
              //  StrJson="{\""+key+"\":\"" + value + "\"}";
                StrJson="\""+key+"\":\"" + value + "\"";
                logger.info("StrJson inside" + StrJson);
               // StrJson= mapper.writeValueAsString(map);
                StrNewJson = StrNewJson+"," + StrJson;
                
            }
System.out.println("=======StrNewJson======="+StrNewJson.substring(1));
			//StrJson="{\"POSTN_ROWID\":\"" +POSTN_ROWID+ "\"}";
String newJsonStr = "{"+StrNewJson.substring(1)+"}";
			logger.info("StrJson " + StrJson+"-----------"+StrNewJson.substring(1)+"--------#############"+newJsonStr);
			//logger.info("searchParameters 1st" + searchParameters.toString());
			// Convert JSON to Map
			
			//searchParameters = mapper.readValue(StrJson,HashMap.class);
			searchParameters = mapper.readValue(newJsonStr,new TypeReference<Map<SearchParameters, String>>() {});
			logger.info("searchParameters " + searchParameters.toString());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Entering Method  JdgConnectionManagerImpl.search"+ searchParameters.get(SearchParameters.PAR_ROW_ID)+"======"+searchParameters.get(SearchParameters.PARTNER_NAME));
		QueryFactory qf = Search.getQueryFactory(this.cache);
		
		logger.info("qf " + qf);
		Query query = null;
		QueryBuilder queryBuilder = null;
		FilterConditionContextQueryBuilder filterConditionContext = null;
		List<PositionUIResponse> tmpList = null;

		Set<PositionUIResponse> matches = new HashSet<>();
		SearchParameters searchParametersKey = null;
		//String searchParametersKey = null;
		String searchParameterValue = null;
		
		for (Map.Entry<SearchParameters, String> searchParameterEntrySet : searchParameters.entrySet()) {
			
		//for (Map.Entry searchParameterEntrySet : searchParameters.entrySet()) {	
			searchParametersKey = searchParameterEntrySet.getKey();
			logger.info("searchParametersKey11" + searchParametersKey);
			searchParameterValue = searchParameterEntrySet.getValue();
			logger.info("searchParameterValue22" + searchParameterValue);
			if (searchParameterValue != null && !searchParameterValue.equals("")) {
				logger.info("searchParameterValue33" + SearchParameters.PAR_ROW_ID+"-------"+SearchParameters.PARTNER_NAME);
				 if ((searchParametersKey.equals(SearchParameters.PAR_ROW_ID)) || (searchParametersKey.equals(SearchParameters.PARTNER_NAME))) {
					//logger.info("searchParametersKey 11" + searchParametersKey.getValue());
					logger.info("searchParameterValue44" + searchParameterValue);
					filterConditionContext = qf.from(PositionUIResponse.class)
							.having(searchParametersKey.getValue()).equal(searchParameterValue);
					logger.info("filterConditionContext" + filterConditionContext);
					logger.info("filterConditionContext" + filterConditionContext.toString());
					
				}
			}
		}
		query = filterConditionContext.build();
		logger.info("query" + query);
		logger.info("query" + query.toString());
		
		tmpList = query.list();
		logger.info("tmpList" + tmpList);
		logger.info("tmpList" + tmpList.size());
		String par_row_id = searchParameters.get(SearchParameters.PAR_ROW_ID);
		String partner_name = searchParameters.get(SearchParameters.PARTNER_NAME);
		logger.info("Exiting Method Matches size .search" + par_row_id+"==========="+partner_name);
		//String invcEndDate = searchParameters.get(SearchParameters.INVOICE_END_DATE);

		if ((par_row_id != null && !par_row_id.equals("")) &&(partner_name != null && !partner_name.equals(""))) {

			
			tmpList = tmpList.stream().filter((PositionUIResponse accountObject) -> {
				return (accountObject.getPar_row_id().equals(par_row_id) && accountObject.getPartner_name().equals(partner_name));
			}).collect(Collectors.toList());
			logger.info("tmpList" + tmpList.toString());
		} 

		matches.addAll(tmpList);
		logger.info("Exiting Method Matches size .search" + matches.size());
		logger.info("Exiting Method JdgConnectionManagerImpl.search");
		return matches;
	}
	
//========================================
	/*
	 * @Override public Set<PositionUIResponse> search(HashMap<String, String>
	 * paramMap) throws Exception { //public Set<PositionUIResponse> search(String
	 * POSTN_ROWID) throws Exception {
	 * System.out.println("==========size====="+paramMap.size());
	 * Map<SearchParameters, String> searchParameters = new
	 * HashMap<SearchParameters, String>(); ObjectMapper mapper = new
	 * ObjectMapper(); //HashMap<String, String> map1=paramMap;
	 * 
	 * String StrJson=""; String StrNewJson=""; String key=""; String value="";
	 * 
	 * try {
	 * 
	 * for (Map.Entry map : paramMap.entrySet() ) {
	 * 
	 * key = map.getKey().toString(); value = map.getValue().toString();
	 * 
	 * // StrJson="{\""+key+"\":\"" + value + "\"}"; StrJson="\""+key+"\":\"" +
	 * value + "\""; logger.info("StrJson inside" + StrJson); // StrJson=
	 * mapper.writeValueAsString(map); StrNewJson = StrNewJson+"," + StrJson;
	 * 
	 * } System.out.println("=======StrNewJson======="+StrNewJson.substring(1));
	 * //StrJson="{\"POSTN_ROWID\":\"" +POSTN_ROWID+ "\"}"; String newJsonStr =
	 * "{"+StrNewJson.substring(1)+"}"; logger.info("StrJson " +
	 * StrJson+"-----------"+StrNewJson.substring(1)+"--------#############"+
	 * newJsonStr); //logger.info("searchParameters 1st" +
	 * searchParameters.toString()); // Convert JSON to Map
	 * 
	 * //searchParameters = mapper.readValue(StrJson,HashMap.class);
	 * searchParameters = mapper.readValue(newJsonStr,new
	 * TypeReference<Map<SearchParameters, String>>() {});
	 * logger.info("searchParameters " + searchParameters.toString()); } catch
	 * (JsonGenerationException e) { e.printStackTrace(); } catch
	 * (JsonMappingException e) { e.printStackTrace(); } catch (IOException e) {
	 * e.printStackTrace(); }
	 * logger.info("Entering Method  JdgConnectionManagerImpl.search"+
	 * searchParameters.get(SearchParameters.DEALER_CODE)+"======"+searchParameters.
	 * get(SearchParameters.CREDIT_MNTH)+"======="+
	 * searchParameters.get(SearchParameters.DEALER_CODE)+"======"+searchParameters.
	 * get(SearchParameters.CREDIT_MNTH)); QueryFactory qf =
	 * Search.getQueryFactory(this.cache);
	 * 
	 * logger.info("qf " + qf); Query query = null; QueryBuilder queryBuilder =
	 * null; FilterConditionContextQueryBuilder filterConditionContext = null;
	 * List<PositionUIResponse> tmpList = null;
	 * 
	 * Set<PositionUIResponse> matches = new HashSet<>(); SearchParameters
	 * searchParametersKey = null; //String searchParametersKey = null; String
	 * searchParameterValue = null;
	 * 
	 * for (Map.Entry<SearchParameters, String> searchParameterEntrySet :
	 * searchParameters.entrySet()) {
	 * 
	 * //for (Map.Entry searchParameterEntrySet : searchParameters.entrySet()) {
	 * searchParametersKey = searchParameterEntrySet.getKey();
	 * logger.info("searchParametersKey11" + searchParametersKey);
	 * searchParameterValue = searchParameterEntrySet.getValue();
	 * logger.info("searchParameterValue22" + searchParameterValue); if
	 * (searchParameterValue != null && !searchParameterValue.equals("")) {
	 * logger.info("searchParameterValue33" +
	 * SearchParameters.DEALER_CODE+"-------"+SearchParameters.CREDIT_MNTH+"-------"
	 * +SearchParameters.CREDIT_YR+"-------"+SearchParameters.INVC_TYPE); if
	 * ((searchParametersKey.equals(SearchParameters.DEALER_CODE)) ||
	 * (searchParametersKey.equals(SearchParameters.CREDIT_MNTH))||(
	 * searchParametersKey.equals(SearchParameters.CREDIT_YR))||
	 * (searchParametersKey.equals(SearchParameters.INVC_TYPE))) {
	 * //logger.info("searchParametersKey 11" + searchParametersKey.getValue());
	 * logger.info("searchParameterValue44" + searchParameterValue);
	 * filterConditionContext = qf.from(PositionUIResponse.class)
	 * .having(searchParametersKey.getValue()).equal(searchParameterValue);
	 * logger.info("filterConditionContext" + filterConditionContext);
	 * logger.info("filterConditionContext" + filterConditionContext.toString());
	 * 
	 * } } } query = filterConditionContext.build(); logger.info("query" + query);
	 * logger.info("query" + query.toString());
	 * 
	 * tmpList = query.list(); logger.info("tmpList" + tmpList);
	 * logger.info("tmpList" + tmpList.size()); String dealer_code =
	 * searchParameters.get(SearchParameters.DEALER_CODE); String credit_mnth =
	 * searchParameters.get(SearchParameters.CREDIT_MNTH); String credit_yr =
	 * searchParameters.get(SearchParameters.CREDIT_YR); String invc_type =
	 * searchParameters.get(SearchParameters.INVC_TYPE);
	 * logger.info("Exiting Method Matches size .search" +
	 * dealer_code+"==========="+credit_mnth+"==========="+credit_yr+"==========="+
	 * invc_type); //String invcEndDate =
	 * searchParameters.get(SearchParameters.INVOICE_END_DATE);
	 * 
	 * if ((dealer_code != null && !dealer_code.equals("")) &&(credit_mnth != null
	 * && !credit_mnth.equals("")) &&(credit_yr != null && !credit_yr.equals(""))
	 * &&(invc_type != null && !invc_type.equals(""))) {
	 * 
	 * 
	 * tmpList = tmpList.stream().filter((PositionUIResponse accountObject) -> {
	 * return (accountObject.getPar_row_id().equals(dealer_code) &&
	 * accountObject.getPartner_name().equals(credit_mnth) &&
	 * accountObject.getPartner_name().equals(credit_yr) &&
	 * accountObject.getPartner_name().equals(invc_type));
	 * }).collect(Collectors.toList()); logger.info("tmpList" + tmpList.toString());
	 * }
	 * 
	 * matches.addAll(tmpList); logger.info("Exiting Method Matches size .search" +
	 * matches.size());
	 * logger.info("Exiting Method JdgConnectionManagerImpl.search"); return
	 * matches; }
	 */
//===============================
	

}