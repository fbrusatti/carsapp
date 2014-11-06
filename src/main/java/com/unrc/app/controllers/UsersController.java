package com.unrc.app;

import com.unrc.app.models.*;
import java.util.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Session;

public class UsersController{

    public ModelAndView getUsers(Request request){
            Map<String, Object> attributes = new HashMap<>();
            List<User> users = User.findAll();
            attributes.put("users_count", users.size());
            attributes.put("users", users);   
            return new ModelAndView(attributes, "users.mustache");
    }   

    public ModelAndView getUsersId(Request request){
            Map<String, Object> attributes = new HashMap<>();
            User userSelected = User.findById(request.params("id"));
            attributes.put("user_id", userSelected);
            return new ModelAndView(attributes, "userId.mustache");
    }

    public void postUsers(Request request, Response response){
            String name = request.queryParams("first_name");
            String lastname = request.queryParams("last_name");
            String email = request.queryParams("email");
            String pass = request.queryParams("passs");
            User nuevoUser= new User();
            nuevoUser.createUser(name,lastname,email,pass);
            response.redirect("/users");
    }

    public ModelAndView getNewUser(Request request, Response response){
            Session session = LibraryController.existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "newUser.mustache");
    }
}
