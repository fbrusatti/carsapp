package com.unrc.app.controllers;

import com.unrc.app.models.User;
import com.unrc.app.models.City;

import spark.Session;
import spark.Request;
import spark.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class UserController {

	/**
	 * Get the view for all users.
	 * @return the users view if exists a session or the corresponden redirect
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
	 * Add a new user
	 * @param req is the Request that contains the user information.
	 * @return a string that is the path of redirection.
	 */
	public String add(Request req) {
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

        return "/users/"+u.id();               
	}

	/**
	 * Edit a user
	 * @param req is the Request that contains the new user information.
	 * @return a string that is the path of redirection.
	 */
	public String edit(Request req) {
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
        
        return "/users/"+u.id();
	}

	/**
	 * Delete a user
	 * @param req is the Request that contains the user key which will be deleted.
	 * @return a string that is the path of redirection.
	 */
	public String delete(Request req) {
		User u = User.findById(req.params("id"));
        u.deleteCascade();
        return "/users";
	}

	/**
	* User login
	* @param req is the Request that contains the user email and password.
	* @return a string that is the path of redirection.
	*/
	public String login(Request req) {
		String email = req.queryParams("email");
        String password = req.queryParams("password");
        User u = User.findFirst("email = ?", email);
        if (u != null ? u.password().equals(password) : false) {
            Session session = req.session(true);
            session.attribute("user_email", email);
            session.attribute("user_id", u.id());
            session.maxInactiveInterval(30*60);               
        }
        return "/users/"+u.id();
	}
}