package com.unrc.app.controllers;

import org.javalite.activejdbc.*;
import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import com.unrc.app.MustacheTemplateEngine;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import org.elasticsearch.node.Node;
import org.elasticsearch.client.Client;
import static org.elasticsearch.node.NodeBuilder.*;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.index.query.FilterBuilders.*;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

public class SearchController {

    public ModelAndView searchUsersForm() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("found?",false);
        return new ModelAndView(attributes,"searchUsers.mustache");
    }

    public ModelAndView searchPostsForm() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("found?",false);
        return new ModelAndView(attributes,"searchPosts.mustache");
    }

    public ModelAndView searchUsers(Request request, Response response) {
        Map<String, Object> attributes = new HashMap<>();

        String query = request.queryParams("carsappsearch");

        //Starts the elasticsearch cluster
        Node node = nodeBuilder().local(true).clusterName("carsapp").node();
        Client client = node.client();

        //Waits until the cluster is ready
        ClusterHealthResponse health = client.admin()
                                        .cluster()
                                        .prepareHealth()
                                        .setWaitForYellowStatus()
                                        .execute()
                                        .actionGet();

        //Executes the search
        SearchResponse res = client.prepareSearch("users")
                                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                    .setQuery(QueryBuilders.matchQuery("name",query))
                                    .execute()
                                    .actionGet();

        //Gets the search results
        SearchHit[] docs = res.getHits().getHits();

        //Closes the cluster
        node.close();

        Map<String,Object> map = new HashMap<String,Object>();
        List<Map<String,Object>> userList = new LinkedList<Map<String,Object>>();
        for (SearchHit sh : docs) {
            map = sh.getSource();
            userList.add(map);

        } //Puts all the results in a list to be treated in the mustache

        long hits = res.getHits().getTotalHits();

        attributes.put("result",userList);
        attributes.put("result_count",hits);
        if (hits > 0) { attributes.put("found?",true); }

        return new ModelAndView(attributes,"searchUsers.mustache");
    }

    public ModelAndView searchPosts(Request request, Response response) {
        Map<String, Object> attributes = new HashMap<>();

        String description = request.queryParams("postSearch");
        String minPrice = request.queryParams("minPrice");
        String maxPrice = request.queryParams("maxPrice");

        //Starts the elasticsearch cluster
        Node node = nodeBuilder().local(true).clusterName("carsapp").node();
        Client client = node.client();

        //Waits until the cluster is ready
        ClusterHealthResponse health = client.admin()
                                        .cluster()
                                        .prepareHealth()
                                        .setWaitForYellowStatus()
                                        .execute()
                                        .actionGet();

        SearchResponse res = new SearchResponse();

        if (minPrice.equals("") && maxPrice.equals("")) {
            //Executes the search only with the description
            res = client.prepareSearch("posts")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(QueryBuilders.matchQuery("description",description))
                        .execute()
                        .actionGet();
        } else {
            if (minPrice.equals("") && !(maxPrice.equals(""))) {
                //Executes the search with minimun price range and description
                res = client.prepareSearch("posts")
                            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                            .setQuery(QueryBuilders.boolQuery()
                                                   .must(QueryBuilders.matchQuery("description",description))
                                                   .must(QueryBuilders.rangeQuery("price")
                                                                      .lte(maxPrice)))
                            .execute()
                            .actionGet();
            } else {
                if (!(minPrice.equals("")) && maxPrice.equals("")) {
                    //Executes the search with maximun price range and description
                    res = client.prepareSearch("posts")
                                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                .setQuery(QueryBuilders.boolQuery()
                                                   .must(QueryBuilders.matchQuery("description",description))
                                                   .must(QueryBuilders.rangeQuery("price")
                                                                      .gte(minPrice)))
                                .execute()
                                .actionGet();
                } else {
                    if (!(minPrice.equals("")) && !(maxPrice.equals(""))) {
                        //Executes the search with both price ranges and description
                        res = client.prepareSearch("posts")
                                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                    .setQuery(QueryBuilders.boolQuery()
                                                   .must(QueryBuilders.matchQuery("description",description))
                                                   .must(QueryBuilders.rangeQuery("price")
                                                                      .from(minPrice)
                                                                      .to(maxPrice)
                                                                      .includeLower(true)
                                                                      .includeUpper(true)))
                                    .execute()
                                    .actionGet();
                    }
                }
            }
        }

        //Gets the search results
        SearchHit[] docs = res.getHits().getHits();

        //Closes the cluster
        node.close();

        Map<String,Object> map = new HashMap<String,Object>();
        List<Map<String,Object>> postList = new LinkedList<Map<String,Object>>();
        for (SearchHit sh : docs) {
            map = sh.getSource();
            postList.add(map);

        } //Puts all the results in a list to be treated in the mustache

        long hits = res.getHits().getTotalHits();

        if (hits > 0) { 
            attributes.put("found?",true);
            attributes.put("result",postList);
            attributes.put("result_count",hits);
        }

        return new ModelAndView(attributes,"searchPosts.mustache");
    }

}