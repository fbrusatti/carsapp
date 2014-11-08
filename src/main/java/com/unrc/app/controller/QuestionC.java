package com.unrc.app.controller;

import com.unrc.app.Html;
import com.unrc.app.models.*;
import java.util.List;

public class QuestionC {
    
    Html html = new Html();
    
    public String getQuestions(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            List<User> u = User.findAll();
            String users = "";
            for (int i = 0; i < u.size(); i++) {
                users = users + u.get(i).getString("first_name") + " " + u.get(i).getString("last_name") + ",";
            }
            return html.getAllUsers(users);  
        }
        else
            return html.getFailLogin();
    }
    
    
    
}
