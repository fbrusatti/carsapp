package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.*;
import com.unrc.app.App;

import com.unrc.app.controllers.*;

import java.util.*;
import com.unrc.app.MustacheTemplateEngine;

import spark.Request;
import spark.Response;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import spark.ModelAndView;
import spark.TemplateEngine;
import static spark.Spark.*;
import org.elasticsearch.node.*;
import org.elasticsearch.client.*;

import spark.Session;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

public class SearchController{

    public ModelAndView postSearch(Request request, Response response) {
            String query = new String();
            if(request.queryParams("query") == null){
                response.redirect("/");
            }else{
                query = request.queryParams("query");
            }
            SearchResponse res = App.client().prepareSearch("posts")
                            .setQuery(QueryBuilders.matchQuery("title", query))
                            .execute()
                            .actionGet();

            List<Map<String,Object>> queryResult = new LinkedList<Map<String,Object>>();
            SearchHit[] hits = res.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String,Object> result = hit.getSource(); 
                queryResult.add(result);
            }

            Map<String,Object> attributes = new HashMap<String,Object>();
            
            attributes.put("results",false);
            if (!queryResult.isEmpty()) { 
                attributes.put("results",true); 
                attributes.put("print",queryResult);
            }
            return new ModelAndView(attributes,"search.mustache");
    }
}
