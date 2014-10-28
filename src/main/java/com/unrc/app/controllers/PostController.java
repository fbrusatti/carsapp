package com.unrc.app.controllers;

import com.unrc.app.models.*;

import spark.Session;
import spark.Request;
import spark.ModelAndView;
import spark.Response;

import java.util.List;
import java.util.HashMap;
import java.util.Map;



public class PostController {
    /**
     * Get the view for all Posts.
     * @return the posts view
     */
    public ModelAndView getPosts(Request req){
        List<Post> posts = Post.findAll();
        boolean notEmpty = !posts.isEmpty();
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("posts",posts);
        attributes.put("notEmpty",notEmpty);
        return new ModelAndView(attributes,"posts.mustache");
    }
    
    /**
     * Get the view for all Post of a User
     */
    public ModelAndView getPostsUser(Request req){
        Session session = req.session(false);
        boolean existSession = false;
        if (session != null) existSession = true;
        Map<String,Object> attributes = new HashMap<String,Object>();
        if (existSession) {
            User u = User.findById(req.params("id"));
            List<Post> posts = Post.where("user_id = ?", req.params("id"));
            boolean notEmpty = !posts.isEmpty();
            attributes.put("id",u.id());
            attributes.put("userName",u.name());
            attributes.put("userPosts",posts);
            attributes.put("notEmpty",notEmpty);
            return new ModelAndView(attributes,"user_posts.mustache");
        } else {
            String url = "/";
            attributes.put("url",url);
            return new ModelAndView(attributes,"redirect.mustache"); 
        }
    }
    
    /**
     *Get the view for a Post
     */
    public ModelAndView getPost(Request request){
	Session session = request.session(false);
	boolean isOwnerOrAdmin = false;
	boolean isGuest;
	Map<String,Object> attributes = new HashMap<String,Object>();
	User u = User.findById(request.params("id"));
	if (session != null) {
	    String userEmail = u.email();
	    String session_id = session.attribute("user_id");
	    attributes.put("session_id",session_id);
	    if (session.attribute("user_email").equals(userEmail)) {
		isOwnerOrAdmin = true;
	    } else {
		isOwnerOrAdmin = u.isAdmin();
	    }
	    isGuest=false;
	} else {
	    isGuest=true;
	}	    
	Post p = Post.findById(request.params("postId"));
	Vehicle v = Vehicle.findById(p.get("vehicle_id"));
	List<Question> q = Question.where("post_id = ?",request.params("postId"));            
	attributes.put("isGuest",isGuest);
	attributes.put("isOwnerOrAdmin", isOwnerOrAdmin);
	attributes.put("userName",u.name());
	attributes.put("post",p);
	attributes.put("vehicle",v);
	attributes.put("questions",q);
	attributes.put("postId",p.id());
	switch (v.type()) {
	case "Auto":
	    Car c = Car.findFirst("vehicle_id = ?", v.id());
	    attributes.put("car",c);
	    break;
	case "Motocicleta":
	    Motorcycle m = Motorcycle.findFirst("vehicle_id = ?", v.id());
	    attributes.put("motorcycle",m);
	    break;
	case "Camión":
	    Truck t = Truck.findFirst("vehicle_id = ?", v.id());
	    attributes.put("truck",t);
	    break;
	}     
	return new ModelAndView(attributes,"post_id.mustache");
    }

    /**
     * Get the view for add new post
     *
     */
    public ModelAndView getNewPost(Request request){
        Session session = request.session(false);
	Map<String,Object> attributes = new HashMap<String,Object>();
	if (session != null) {
	    User u = User.findById(request.params("id"));
	    String userEmail = u.email();
	    if (session.attribute("user_email").equals(userEmail)) {
		attributes.put("id",request.params("id"));
		List<Vehicle> vehicles = Vehicle.where("user_id = ?",request.params("id"));
		attributes.put("vehicles",vehicles);
		return new ModelAndView(attributes,"user_new_post.mustache");
	    } else {
		String url = "/users/"+u.id();
		attributes.put("url",url);
		return new ModelAndView(attributes,"redirect.mustache"); 
	    }
	} else {
	    String url = "/";
	    attributes.put("url",url);
	    return new ModelAndView(attributes,"redirect.mustache");
	}
    }
    
    /**
     * Get the view for edit a post
     * @param request
     * @return 
     */
    public ModelAndView getEditPost(Request request){
        Session session = request.session(false);
	Map<String,Object> attributes = new HashMap<String,Object>();
	if (session != null) {
	    Post p = Post.findById(request.params("postId"));
	    User u = User.findById(request.params("id"));
	    String userEmail = u.email();	
	    if (session.attribute("user_email").equals(userEmail)) {	
		attributes.put("post",p);
		return new ModelAndView(attributes,"edit_post.mustache");
	    } else {
		String url = "/users/"+u.id()+"/posts/"+p.id();
		attributes.put("url",url);
		return new ModelAndView(attributes,"redirect.mustache"); 
	    }
	} else {
	    String url = "/login";
	    attributes.put("url",url);
	    return new ModelAndView(attributes,"redirect.mustache");
	}	 
    }
    
    /**
     * Add a new post
     * @param request
     * @param response 
     */
    public void newPost(Request request, Response response){
	Post p = new Post();
	p.set("title", request.queryParams("title"));
	p.set("description",request.queryParams("descrip"));
	p.set("user_id", request.params("id"));
	Vehicle v = Vehicle.findById(request.queryParams("vehicles"));
	p.saveIt();
	v.add(p);
	response.redirect("posts/"+p.id());
    }
    
    /**
     * Edit a Post
     * @param request
     * @param response 
     */
    public void editPost(Request request, Response response){
        Post p = Post.findById(request.params("postId"));
        p.set("title", request.queryParams("title"));
        p.set("description",request.queryParams("descrip"));
        p.saveIt();
        response.redirect("/users/"+request.params("id")+"/posts/"+p.id());
    }
    
    /**
     * Delete a Post
     * @param request
     * @param response 
     */
    public void deletePost(Request request, Response response){
        Post p = Post.findById(request.params("postId"));
        p.deleteCascade();
        response.redirect("/users/"+request.params("id")+"/posts");
    }
}
