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

public class CarController{

    public ModelAndView CarList(){
            Map<String, Object> attributes = new HashMap<>();
            List<Car> cars = Car.findAll();
            attributes.put("cars_count", cars.size());
            attributes.put("cars", cars);   
            return new ModelAndView(attributes, "cars.mustache");
    }
    
    public ModelAndView CarLicensePlate(Request req){
    	   Map<String, Object> attributes = new HashMap<>();
            Motorcycle motorcycleSelected = Motorcycle.findByMotorcycle(req.params("patent"));
            attributes.put("motorcycles_id", motorcycleSelected);
            return new ModelAndView(attributes, "motorcycleId.mustache");
    }
}