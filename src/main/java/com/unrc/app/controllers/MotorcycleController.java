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

public class MotorcycleController{

    public ModelAndView MotorcycleList(){
            Map<String, Object> attributes = new HashMap<>();
            List<Motorcycle> motorcycles = Motorcycle.findAll();
            attributes.put("motorcycles_count", motorcycles.size());
            attributes.put("motorcycles", motorcycles);   
            return new ModelAndView(attributes, "motorcycles.mustache");
    }
    
    public ModelAndView MotorcycleLicensePlate(Request req){
            Map<String, Object> attributes = new HashMap<>();
            Motorcycle motorcycleSelected = Motorcycle.findByMotorcycle(req.params("patent"));
            attributes.put("motorcycles_id", motorcycleSelected);
            return new ModelAndView(attributes, "motorcycleId.mustache");
    }
}