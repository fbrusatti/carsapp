package com.unrc.app;

import com.unrc.app.models.*;
import java.util.*;
import spark.ModelAndView;
import spark.Request;

public class LoginController{
    public ModelAndView getLogin(Request request){
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "login.mustache");
    }
}

