package com.unrc.app.controllers;

import org.javalite.activejdbc.*;
import static spark.Spark.*;
import spark.Response;
import spark.Request;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import com.unrc.app.MustacheTemplateEngine;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import com.unrc.app.models.User;
import com.unrc.app.models.Post;
import com.unrc.app.models.Question;
import com.unrc.app.models.Answer;

public class AnswerController {

	public String addAnswer( Request request, Response response) {

		    Question question = Question.findById(request.queryParams("questionId"));
            User user = User.findById(request.queryParams("userId"));
            Post post = Post.findById(question.getString("post_id"));
            String description = request.queryParams("description");
            //The user answering should be the owner of the post
            if (user.id() == post.getInteger("user_id")) {
                user.addAnswer(description,post,question);
                response.redirect("/posts/"+post.id());
                return "success";
            } else {
                response.redirect("/whoops",403);
                return "error";
            }
	}

    public ModelAndView answerForm(Request request){

        Map<String, Object> attributes = new HashMap<>();
            Question question = Question.findById(request.queryParams("questionId"));
            User user = User.findFirst("email = ?",request.queryParams("login"));
            attributes.put("question",question);
            attributes.put("user",user);

            return new ModelAndView(attributes, "postAnswer.mustache");
    }


}