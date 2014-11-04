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

public class TruckController{

    public ModelAndView TruckList(){
            Map<String, Object> attributes = new HashMap<>();
            List<Truck> trucks = Truck.findAll();
            attributes.put("trucks_count", trucks.size());
            attributes.put("trucks", trucks);   
            return new ModelAndView(attributes, "trucks.mustache");
    }
    
    public ModelAndView TruckLicensePlate(Request req){
    	    Map<String, Object> attributes = new HashMap<>();
            Truck truckSelected = Truck.findByTruck(req.params("patent"));
            attributes.put("trucks_id", truckSelected);
            return new ModelAndView(attributes, "truckId.mustache");
    }
}