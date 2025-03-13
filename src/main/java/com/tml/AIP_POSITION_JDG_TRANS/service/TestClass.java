package com.tml.AIP_POSITION_JDG_TRANS.service;

import java.util.HashMap;

public class TestClass {

	/*
	 * public Set<CInvcDtl> search(HashMap<String, String> paramMap) {
	 * Map<SearchParameters, String> searchParameters = new
	 * HashMap<SearchParameters, String>(); ObjectMapper mapper = new
	 * ObjectMapper(); String StrJson = ""; String StrNewJson = ""; String key = "";
	 * String value = ""; HashMap<String, String> paramMap = new HashMap<>();
	 * if(request.getDealer_code() != null) { paramMap.put("DEALER_CODE",
	 * request.getDealer_code()); } if (request.getCredit_mnth() != null){
	 * paramMap.put("CREDIT_MNTH", request.getCredit_mnth()); } if
	 * (request.getCredit_yr() != null) { paramMap.put("CREDIT_YR",
	 * request.getCredit_yr()); } if (request.getInvc_type() != null) {
	 * paramMap.put("INVC_TYPE", request.getInvc_type()); }
	 * logger.info("Request param =" + paramMap); try { for (Map.Entry<String,
	 * String> map : paramMap.entrySet()) { key = map.getKey().toString(); value =
	 * map.getValue().toString(); StrJson = "\"" + key + "\":\"" + value + "\"";
	 * logger.info("StrJson inside" + StrJson); StrNewJson = StrNewJson + "," +
	 * StrJson; } String newJsonStr = "{" + StrNewJson.substring(1) + "}";
	 * logger.info("StrJson " + StrJson + "-----------" + StrNewJson.substring(1) +
	 * "--------#############" + newJsonStr); searchParameters =
	 * mapper.readValue(newJsonStr, new TypeReference<Map<SearchParameters,
	 * String>>() {}); logger.info("searchParameters " +
	 * searchParameters.toString()); } catch (JsonGenerationException e) {
	 * e.printStackTrace(); } catch (JsonMappingException e) { e.printStackTrace();
	 * } catch (IOException e) { e.printStackTrace(); }
	 * logger.info("Entering Method GstInvcJdgServiceImpl.search"+
	 * searchParameters.get(SearchParameters.DEALER_CODE) + "-" +
	 * searchParameters.get(SearchParameters.CREDIT_MNTH) + "-" +
	 * searchParameters.get(SearchParameters.CREDIT_YR) + "-" +
	 * searchParameters.get(SearchParameters.INVC_TYPE)); QueryFactory qf =
	 * Search.getQueryFactory(this.cache); logger.info("qf " + qf); Query query =
	 * null; FilterConditionContextQueryBuilder filterConditionContext = null;
	 * List<CInvcDtl> tmpList = new ArrayList<>(); Set<CInvcDtl> matches = new
	 * HashSet<>(); if (searchParameters.containsKey(SearchParameters.DEALER_CODE))
	 * { logger.info("inside dealer code block" + request.getDealer_code());
	 * logger.info("dealer key= " + SearchParameters.DEALER_CODE.getValue() +
	 * "dealer value=" + searchParameters.get(SearchParameters.DEALER_CODE));
	 * filterConditionContext =
	 * qf.from(CInvcDtl.class).having(SearchParameters.DEALER_CODE.getValue()).equal
	 * (searchParameters.get(SearchParameters.DEALER_CODE));
	 * logger.info("dealer key= " + SearchParameters.DEALER_CODE.getValue()+
	 * "dealer value=" + searchParameters.get(SearchParameters.DEALER_CODE)); }
	 * query = filterConditionContext.build(); logger.info("query" + query);
	 * logger.info("query" + query.toString()); tmpList = query.list();
	 * logger.info("tmpList" + tmpList); logger.info("tmpList" + tmpList.size());
	 * String dealerCode= searchParameters.get(SearchParameters.DEALER_CODE); String
	 * creditMonth= searchParameters.get(SearchParameters.CREDIT_MNTH); String
	 * creditYear= searchParameters.get(SearchParameters.CREDIT_YR); String
	 * invcType= searchParameters.get(SearchParameters.INVC_TYPE);
	 * logger.info("Exiting Method Matches size .search" + dealerCode); if
	 * ((dealerCode!= null && !dealerCode.equals("")) && (creditMonth!= null &&
	 * !creditMonth.equals("")) && (creditYear!= null && !creditYear.equals("")) &&
	 * (invcType!= null && !invcType.equals(""))) { tmpList =
	 * tmpList.stream().filter((CInvcDtl accountObject) -> { return
	 * ((accountObject.getDealer_code().equals(dealerCode) ||
	 * accountObject.getCredit_mnth().equals(creditMonth)) &&
	 * accountObject.getCredit_yr().equals(creditYear) &&
	 * accountObject.getInvc_type().equals(invcType));
	 * }).collect(Collectors.toList()); logger.info("tmpList" + tmpList.toString());
	 * } matches.addAll(tmpList); logger.info("Exiting Method Matches size .search"
	 * + matches.size());
	 * logger.info("Exiting Method GstInvcJdgServiceImpl.search"); return matches; }
	 * }
	 * 
	 */

}
