package com.unrc.app;

import org.javalite.activejdbc.*;
import static spark.Spark.*;
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

public class UserController {

    ModelAndView newUserForm() {
        Map<String, Object> attributes = new HashMap<>();
        return new ModelAndView(attributes, "usersNew.mustache");
    }
    
    String addUser(request Request, response Response) {
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
}