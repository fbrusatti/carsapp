package com.unrc.app.controllers;

import org.javalite.activejdbc.*;

import static spark.Spark.*;
import spark.Request;
import spark.Response;
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

import com.unrc.app.models.Post;
import com.unrc.app.models.Question;
import com.unrc.app.models.User;

public class QuestionController 
{
            public String addQuestion( Request request, Response response) {

                Post post = Post.findById(request.queryParams("postId"));
                String description = request.queryParams("description");
                String email = request.queryParams("user");

                User userAsking = User.findFirst("email = ?",email);

                //If the user asking is the owner of the post, redirect to 403
                if (post.getInteger("user_id") == userAsking.id()) {
                        response.redirect("/whoops",403);
                        return "error";
                } else {
                        userAsking.addQuestion(description,post);
                        response.redirect("/posts");
                        return "success";
                }
            }
}

