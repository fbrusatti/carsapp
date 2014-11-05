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

import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Post;
import com.unrc.app.models.Car;
import com.unrc.app.models.Question;
import com.unrc.app.models.Answer;
import com.unrc.app.models.Address;

public class UserController {

    /* -------------------------------- Forms -------------------------------------------- */

    public ModelAndView addUserForm() {
        Map<String, Object> attributes = new HashMap<>();
        return new ModelAndView(attributes, "usersNew.mustache");
    }
    
    public ModelAndView addVehicleForm() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("selectType",true); //This is to select the type of vehicle without seeing the other part of loading the vehicle
        return new ModelAndView(attributes, "vehiclesAdd.mustache");
    }

    public ModelAndView addAddressesForm() {
        Map<String, Object> attributes = new HashMap<>();
        return new ModelAndView(attributes, "addressAdd.mustache");
    }

    public ModelAndView addAnswerForm(Request request, Response response) {
        Map<String, Object> attributes = new HashMap<>();
        Post post = Post.findById(Integer.parseInt(request.params(":id")));
        List<Question> activeQuestions = Question.where("post_id = ? AND active = true",post.id());

        if (post == null || activeQuestions == null) {
            response.redirect("/whoops", 404);
            return new ModelAndView(attributes, "answerPost.mustache"); //only for compiling purposes
        } else {
            attributes.put("questions_count",activeQuestions.size());
            attributes.put("userId",post.userId());
            attributes.put("postId",post.id());
            attributes.put("questions",activeQuestions);
        }
        return new ModelAndView(attributes, "answerPost.mustache");
    }

    public ModelAndView addPostForm() {
        Map<String, Object> attributes = new HashMap<>();
        return new ModelAndView(attributes, "postAdd.mustache");
    }

    public ModelAndView selectVehiclesForm(Request request) {            
        Map<String, Object> attributes = new HashMap<>();
        String email = request.queryParams("login");
        User u = User.findFirst("email = ?",email);
        List<Vehicle> vehicles = Vehicle.where("user_id = ?", u.id());

        attributes.put("vehicles",vehicles);
        attributes.put("userId",u.id());

        return new ModelAndView(attributes, "postAdd.mustache");
    }

    /* -------------------------------- Lists -------------------------------------------- */

    public ModelAndView listUsers() {
        Map<String, Object> attributes = new HashMap<>();
        List<User> users = User.findAll();
        attributes.put("users_count", users.size());
        attributes.put("users", users);
        return new ModelAndView(attributes, "users.mustache");
    }

    public ModelAndView listUserById(Request request, Response response) {
        Map<String, Object> attributes = new HashMap<>();
        User user = User.findById(Integer.parseInt(request.params(":id")));
        if (user == null) {
            response.redirect("/whoops", 404);
            return new ModelAndView(attributes, "userId.mustache"); //only for compiling purposes
        }
        else {
            String address = user.address();
            List<Post> posts = Post.where("user_id = ?",user.id());
            attributes.put("user", user);
            attributes.put("address_user", address);
            attributes.put("posts",posts);
            return new ModelAndView(attributes, "userId.mustache");
        }
    }
    
    /* -------------------------------- Adds -------------------------------------------- */

    public String addUser(Request request, Response response) {
        User admin = User.findFirst("email = ?",request.queryParams("admin")); //search if the user creating the user is an admin
        String message = new String();
        if (!(admin.getBoolean("is_admin"))) {
            response.redirect("/whoops",403);
            message = "fail";
            return message;
        } else {
            String name = request.queryParams("name");
            String lastname = request.queryParams("lastname");
            String email = request.queryParams("email");
            String street = request.queryParams("street");
            String address_number = request.queryParams("address_number");
            admin.createUser(name,lastname,email,street,address_number); 
            message = "success"; 
            response.redirect("/hello");
            return message;
        }
    }
    
    public ModelAndView addVehicle(Request request) {
        Map<String, Object> attributes = new HashMap<>();
        String type = request.queryParams("vehicleType");
        if (type.equals("car")) {
            attributes.put("car",true);
        }if (type.equals("truck")) {
            attributes.put("truck",true);
        }if (type.equals("moto")) {
            attributes.put("moto",true);
        }if (type.equals("other")) {
            attributes.put("other",true);
        };
        attributes.put("load",true); //This is to select the type of vehicle without seeing the other part of loading the vehicle
        
        return new ModelAndView(attributes,"vehiclesAdd.mustache");
    }


    public String addPost(Request request, Response response) {
        User user = User.findById(request.queryParams("userId"));
        Vehicle vehicle = Vehicle.findById(request.queryParams("vehicle"));
        user.addPost(request.queryParams("price"),request.queryParams("description"),vehicle);
        response.redirect("/posts");
        return "success";
    }

}