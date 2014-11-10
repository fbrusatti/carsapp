package com.unrc.app.controllers;

import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Post;
import com.unrc.app.models.Question;

import spark.Session;
import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class QuestionController {
	
	/**
	 * Add a new question.
	 * @param req is the Request that contains the question information.
	 * @param resp is the Response that will make the redirection.
	 */
	public void add(Request req,Response resp) {
		Session session = req.session(false);
        if (session != null) {
            String session_id = session.attribute("user_id");
            Question q = new Question();
            q.set("description",req.queryParams("descrip"));
            q.set("post_id",req.params("postId"));
            q.set("user_id", session_id);
            q.saveIt();
            resp.redirect("/users/"+req.params("id")+"/posts/"+req.params("postId"));
        } else {
            resp.redirect("/login");
       	} 
	}
}