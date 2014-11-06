package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.*;
import java.util.*;
import com.unrc.app.MustacheTemplateEngine;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import spark.ModelAndView;
import spark.TemplateEngine;
import static spark.Spark.*;
import org.elasticsearch.node.*;
import org.elasticsearch.client.*;
import spark.Request;
import spark.Session;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

public class RateController{

    public ModelAndView RateList(){
            Map<String, Object> attributes = new HashMap<>();
            List<Rate> rates = Rate.findAll();
            attributes.put("rates_count", rates.size());
            attributes.put("rates", rates);   
            return new ModelAndView(attributes, "rates.mustache");
    }
    
    public ModelAndView RateId(Request req){
            Map<String, Object> attributes = new HashMap<>();
            Rate rateSelect = Rate.findById(req.params("id"));
            attributes.put("rates_id",rateSelect);
            return new ModelAndView(attributes,"rates_id.mustache");       
    }
}