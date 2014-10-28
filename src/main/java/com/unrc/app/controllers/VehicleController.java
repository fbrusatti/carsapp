package com.unrc.app.controllers;

import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Car;
import com.unrc.app.models.Motorcycle;
import com.unrc.app.models.Truck;

import spark.Session;
import spark.Request;
import spark.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class VehicleController  {
	
	/**
	 * Get user vehicles.
	 * @return the view for the user vehicles.
	 */
	public ModelAndView getUserVehiclesView(Request req) {
		Session session = req.session(false);
        Map<String,Object> attributes = new HashMap<String,Object>();
        if (session != null) {
        	User u = User.findById(req.params("id"));
            String userEmail = u.email();
            if (session.attribute("user_email").equals(userEmail)) {
                List<Vehicle> vehicles = Vehicle.where("user_id = ?", req.params("id"));
        	    boolean notEmpty = !vehicles.isEmpty();
        	   	attributes.put("userName",u.name());
       			attributes.put("userVehicles",vehicles);
       			attributes.put("notEmpty",notEmpty);
            	return new ModelAndView(attributes,"user_vehicles.mustache");
            } else {
                attributes.put("url","/users/"+u.id());
                return new ModelAndView(attributes,"redirect.mustache"); 
            }
       	} else {
            attributes.put("url","/");
            return new ModelAndView(attributes,"redirect.mustache");
        }
	}

	/**
	 * Add vehicle view
	 * @return the view for add a new vehicle only if the session correspond to the correct user.
	 */
	public ModelAndView getAddView(Request req) {
		Session session = req.session(false);
        Map<String,Object> attributes = new HashMap<String,Object>();
        if (session != null) {
            User u = User.findById(req.params("id"));
            String userEmail = u.email();
            if (session.attribute("user_email").equals(userEmail)) {
                attributes.put("id",req.params("id"));
                return new ModelAndView(attributes,"user_new_vehicle.mustache"); 
            } else {
                attributes.put("url","/users/"+u.id());
                return new ModelAndView(attributes,"redirect.mustache"); 
            }
        } else {
            attributes.put("url","/");
            return new ModelAndView(attributes,"redirect.mustache");
        }
	}

	/**
	 * Add a new vehicle
	 * @param req is the Request that contains the vehicle information and the vehicle owner id.
	 * @return a string that is the path of redirection.
	 */
	public String add(Request req) {
		Vehicle v = new Vehicle();
		v.set("brand", req.queryParams("brand"));
		v.set("model", req.queryParams("model"));
		v.set("year", req.queryParams("year"));
		v.set("color", req.queryParams("color"));
		v.set("user_id",req.params("id"));
		if (req.queryParams("type").charAt(0)=='1') {
			v.set("type","Auto");
			v.saveIt();
			Car c = new Car();
			c.set("capacity",req.queryParams("capacity"));
			c.saveIt();
			v.add(c);
		}
		if (req.queryParams("type").charAt(0)=='2') {
			v.set("type","Motocicleta");
			v.saveIt();
			Motorcycle m = new Motorcycle();
			m.set("cylinder_capacity",req.queryParams("cylinder_capacity"));
			m.saveIt();
			v.add(m);
		}
		if (req.queryParams("type").charAt(0)=='3') {
			v.set("type","Camión");
			v.saveIt();
			Truck t = new Truck();
			t.set("length",req.queryParams("length"));
			t.set("height",req.queryParams("height"));
			t.saveIt();
			v.add(t);
		}
        return "/users/"+req.params("id")+"/newPost";
	}
}