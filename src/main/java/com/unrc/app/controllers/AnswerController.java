package com.unrc.app.controllers;

import com.unrc.app.models.User;
import com.unrc.app.models.Question;
import com.unrc.app.models.Post;
import com.unrc.app.models.Answer;

import spark.Session;
import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class AnswerController {

	/**
	 * Get the answers of a question.
	 * @return the view for the answers of a question of a post.
	 */
	public ModelAndView getAnswersOfQuestionView(Request req) {
		Session session = req.session(false);
        boolean isOwnerOrAdmin = false;
        boolean isGuest;
        if (session != null) {
            User u = User.findById(req.params("userId"));
            String userEmail = u.email();
            if (session.attribute("user_email").equals(userEmail)) {
                isOwnerOrAdmin = true;
            } else {
                isOwnerOrAdmin = u.isAdmin();
            }
            isGuest=false;
        } else {
            isGuest=true;
        }
            
        List<Answer> answers = Answer.where("question_id = ?",req.params("idQuestion"));
        Question q = Question.findById(req.params("idQuestion"));
        Post p = Post.findById(req.params("postId"));
        Map<String,Object> attributes = new HashMap<String,Object>();

        attributes.put("isGuest",isGuest);
        attributes.put("isOwnerOrAdmin", isOwnerOrAdmin);
        attributes.put("question",q);
        attributes.put("answers",answers);
		attributes.put("post",req.params("postId"));
        attributes.put("user",req.params("userId"));
        attributes.put("userName", p.ownerName());
        return new ModelAndView(attributes,"question_answers.mustache");
	}

	/**
	 * Add a new answer
	 * @param req is the Request that contains the answer information and the correspondent question, 
	 * user, and post id.
	 * @param resp is the Response that will make the redirection.
	 */
	public void add(Request req,Response resp) {
		Answer a = new Answer();
        a.set("description",req.queryParams("descrip"));
        a.set("user_id", req.params("id"));
        a.set("question_id",req.params("questionId"));
        a.saveIt();

        resp.redirect("/users/"+req.params("id")+"/posts/"+req.params("postId")+"/question/"+req.params("questionId"));  
	}

	
	
}