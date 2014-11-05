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

public class PostController {

	public ModelAndView showById (Request request, Response response) {

 		Map<String, Object> attributes = new HashMap<>();
        Post post = Post.findById(Integer.parseInt(request.params(":id")));
        if (post == null) {
            response.redirect("/whoops",404);
            return new ModelAndView(attributes, "postId.mustache");
        } else {
            //Questions
            List<Question> questions = Question.where("post_id = ?", post.id());
            attributes.put("vehicle_name", post.vehicle().name());
            attributes.put("post", post);
            if (questions != null) {
                attributes.put("questions", questions);
            }
            //Rating
            if (post.rate() != 0) { //rate() = 0 means the post is not rated yet
                attributes.put("rating",post.rate());
            }

            return new ModelAndView(attributes, "postId.mustache");
        }
    }

    public ModelAndView show(){

    	Map<String, Object> attributes = new HashMap<>();
        List<Post> posts = Post.findAll();
        attributes.put("posts_count", posts.size());
        attributes.put("posts", posts);
        return new ModelAndView(attributes, "posts.mustache");
    }

    public String addRate(Request request, Response response){

    	String postId = request.queryParams("postId");
            Post post = Post.findById(postId);
            User user = User.findFirst("email = ?",request.queryParams("login"));
            Integer rating = Integer.valueOf(request.queryParams("rating"));

            if (user == null || post.userId() == user.id()) { // User trying to rate is null or the post owner,
                response.redirect("/posts/"+postId);          // reload page without rating.
                return "fail";
            }
            else {
                post.addRate(rating);
                response.redirect("/posts/"+postId);
                return "success";
            }
    }
}