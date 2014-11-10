package com.unrc.app.controllers;

import com.unrc.app.models.User;
import com.unrc.app.models.City;

import spark.Session;
import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class UserController {

	/**
	 * Get the view for all users.
	 * @return the users view if exists a session or the correspondent redirect
	 * if no exists a session.
	 */
	public ModelAndView getUsersView(Request req) {
		Session session = req.session(false);
        boolean existSession = false;
        if (session != null) existSession = true;
        Map<String,Object> attributes = new HashMap<String,Object>();
        if (existSession) {
            List<User> users = User.findAll();
            boolean notEmpty = !users.isEmpty();
            attributes.put("users",users);
            attributes.put("notEmpty",notEmpty);
            return new ModelAndView(attributes,"users.mustache");
        } else {
            String url = "/";
            attributes.put("url",url);
            return new ModelAndView(attributes,"redirect.mustache"); 
        }
	}

	/**
	 * Get the view for a user.
	 * @return the user view if exists a session or the corresponden redirect
	 * if no exists a session.
	 */
	public ModelAndView getUserView(Request req) {
		Map<String,Object> attributes = new HashMap<String,Object>();
        Session session = req.session(false);
        boolean isOwnerOrAdmin = false;
        if (session != null) {
            User u = User.findById(req.params("id"));
            String userEmail = u.email();
            if (session.attribute("user_email").equals(userEmail)) {
                isOwnerOrAdmin = true;
            } else {
                isOwnerOrAdmin = u.isAdmin();
            }
            attributes.put("user",u);
            attributes.put("isOwnerOrAdmin", isOwnerOrAdmin);
            return new ModelAndView(attributes,"user_id.mustache");
        } else {
            String url = "/";
            attributes.put("url",url);
            return new ModelAndView(attributes,"redirect.mustache"); 
        }
	}

    /**
     * Add user view
     * @return the view for add a new user if not exists session.
     */
    public ModelAndView getAddView(Request req){
        Session session = req.session(false);
        Map<String,Object> attributes = new HashMap<String,Object>();
        if (session==null) {
            List<City> cities = City.findAll();
            attributes.put("cities",cities);
            return new ModelAndView(attributes,"new_user.mustache");
        } else {
            attributes.put("url","/");
            return new ModelAndView(attributes,"redirect.mustache");
        }
    }

	/**
	 * Add a new user
	 * @param req is the Request that contains the user information.
	 * @param resp is the Response that will make the redirection.
	 */
	public void add(Request req,Response resp) {
		User u = new User();
        u.set("email", req.queryParams("email"));
        u.set("first_name",req.queryParams("firstName"));
        u.set("last_name", req.queryParams("lastName"));
        u.set("password", req.queryParams("password"));
        u.set("mobile",req.queryParams("movil"));
        u.set("telephone",req.queryParams("fijo"));
        u.set("address",req.queryParams("direccion"));
        u.set("isAdmin",0);
        u.saveIt();
        	
        City c = City.findById(req.queryParams("ciudad"));
        c.add(u);
        Session session = req.session(true);
        session.attribute("user_email", u.email());
        session.attribute("user_id", u.id());
        session.maxInactiveInterval(30*60);

        resp.redirect("/users/"+u.id());             
	}

    /**
     * Edit user view
     * @return the view for edit a user only if the session correspond to the profile owner.
     */
    public ModelAndView getEditView(Request req) {
        Session session = req.session(false);
        Map<String,Object> attributes = new HashMap<String,Object>();
        if (session != null) {
            User u = User.findById(req.params("id"));
            String userEmail = u.email();
            if (session.attribute("user_email").equals(userEmail)){ 
                List<City> c = City.findAll();
                attributes.put("user",u);
                attributes.put("cities",c);            
                return new ModelAndView(attributes,"edit_user.mustache");
            } else {
                String url = "/users/"+u.id();
                attributes.put("url",url);
                return new ModelAndView(attributes,"redirect.mustache"); 
            }
        } else {
            attributes.put("url","/login");
            return new ModelAndView(attributes,"redirect.mustache");
        }
    }

	/**
	 * Edit a user
	 * @param req is the Request that contains the new user information.
	 * @param resp is the Response that will make the redirection.
	 */
	public void edit(Request req,Response resp) {
		User u = User.findById(req.params("id"));
        u.set("email", req.queryParams("email"));
        u.set("first_name",req.queryParams("firstName"));
        u.set("last_name", req.queryParams("lastName"));
        u.set("password", req.queryParams("password"));
        u.set("mobile",req.queryParams("movil"));
        u.set("telephone",req.queryParams("fijo"));
        u.set("address",req.queryParams("direccion"));
        u.saveIt();
            
        City c = City.findById(req.queryParams("ciudad"));
        c.add(u);
        
        resp.redirect("/users/"+u.id());
	}

	/**
	 * Delete a user
	 * @param req is the Request that contains the user key which will be deleted.
	 * @param resp is the Response that will make the redirection. 
	 */
	public void delete(Request req,Response resp) {
		User u = User.findById(req.params("id"));
        u.deleteCascade();
        resp.redirect("/users");
	}

	/**
	* User login
	* @param req is the Request that contains the user email and password.
	* @param resp is the Response that will make the redirection.
	*/
	public void login(Request req,Response resp) {
		String email = req.queryParams("email");
        String password = req.queryParams("password");
        User u = User.findFirst("email = ?", email);
        if (u != null ? u.password().equals(password) : false) {
            Session session = req.session(true);
            session.attribute("user_email", email);
            session.attribute("user_id", u.id());
            session.maxInactiveInterval(30*60);               
        }
        resp.redirect("/users/"+u.id());
	}

    /**
     * User login view
     * @return the view for the user login if not exists a session.
     */
    public ModelAndView getLoginView(Request req) {
        Session session = req.session(false);
        if (session == null) {
            return new ModelAndView(null,"login.mustache");
        } else {
            Map<String,Object> attributes = new HashMap<String,Object>();
            attributes.put("url","/");
            return new ModelAndView(attributes,"redirect.mustache");
        }
    }
}