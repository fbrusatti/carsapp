package com.unrc.app.controllers;

import spark.Session;
import spark.Request;
import spark.ModelAndView;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.index.query.*;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;

public class SearchController {
    
    /**
     * Get de view for the search
     * @param request
     * @return 
     */
    public ModelAndView getSearchView(Request request){
        Session session = request.session(false);
        boolean existSession = false;
        if (session != null) existSession = true;
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("existSession", existSession);
        return new ModelAndView(attributes,"search.mustache");
    }
    
    /**
     * View for the search results
     * @param request
     * @return 
     */
    public ModelAndView postSearchView(Request request){
        Client client = new TransportClient()
	    .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
	ClusterHealthResponse health = client.admin()
	    .cluster()
	    .prepareHealth()
	    .setWaitForGreenStatus()
	    .execute()
	    .actionGet();
	SearchResponse resp = new SearchResponse();
	SearchHit[] docs;
	Map<String,Object> attributes = new HashMap<String,Object>();
	//The search was executed through users.
	if (request.queryParams("type").charAt(0)=='2') {
	    resp = client.prepareSearch("users")
		.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		.setQuery(QueryBuilders.matchQuery("name",request.queryParams("name"))) 
		.setFrom(0).setSize(60).setExplain(true)
		.execute()
		.actionGet();
	    List<Map<String,Object>> usersFounded = new LinkedList<Map<String,Object>>();
	    docs = resp.getHits().getHits();
	    for (SearchHit hit : docs) {
		Map<String,Object> result = hit.getSource(); 
		String userId = hit.getId();
		result.put("id",userId);
		usersFounded.add(result);
	    }
	    attributes.put("searchExecuted",true);
	    if (!usersFounded.isEmpty()) { 
		attributes.put("results",true); 
		attributes.put("userSearchResult",usersFounded);
	    }
	}
	//The search was executed through posts.
	if (request.queryParams("type").charAt(0)=='1') {
	    resp = client.prepareSearch("posts")
		.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
		.setQuery(QueryBuilders.matchQuery("title",request.queryParams("dates")))   
		.setFrom(0).setSize(60).setExplain(true)
		.execute()
		.actionGet();
	    List<Map<String,Object>> postsFounded = new LinkedList<Map<String,Object>>();
	    docs = resp.getHits().getHits();
	    for (SearchHit hit : docs) {
		Map<String,Object> result = hit.getSource(); 
		String postId = hit.getId();
		result.put("id",postId);
		postsFounded.add(result);
	    }
	    attributes.put("searchExecuted",true);
	    if (!postsFounded.isEmpty()) { 
		attributes.put("results",true); 
		attributes.put("postSearchResult",postsFounded);
	    }
	}
	client.close();
	Session session = request.session(false);
	boolean existSession = false;
	if (session != null) existSession = true;
	attributes.put("existSession", existSession);
	return new ModelAndView(attributes,"search.mustache");
    }    
}
