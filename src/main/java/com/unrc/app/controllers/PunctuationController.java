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

public class PunctuationController{

    public ModelAndView PunctuationList(){
            Map<String, Object> attributes = new HashMap<>();
            List<Punctuation> punctuations = Punctuation.findAll();
            attributes.put("punctuations_count", punctuations.size());
            attributes.put("punctuations", punctuations);   
            return new ModelAndView(attributes, "punctuations.mustache");
    }
    
    public ModelAndView PunctuationId(Request req){
            Map<String, Object> attributes = new HashMap<>();
            Punctuation punctuationSelected = Punctuation.findById(req.params("id"));
            attributes.put("punctuations_id", punctuationSelected);
            return new ModelAndView(attributes, "punctuationsId.mustache");      
    }
}