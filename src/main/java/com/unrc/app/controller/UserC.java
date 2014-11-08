package com.unrc.app.controller;

import com.unrc.app.Html;
import com.unrc.app.models.*;
import java.util.List;


public class UserC   {
 
    
    Html html = new Html();
    
          
    public String UserAll (spark.Response resp, String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
            resp.type("text/html");
            List<User> u = User.findAll();
            String users = "";
            for (int i = 0; i < u.size(); i++) {
                 users = users + u.get(i).getString("id_user")+ " | " + " " + u.get(i).getString("first_name") + " " + u.get(i).getString("last_name")+ " | "+ " " + u.get(i).getString("email") + ",";
             }    
            return html.getAllUsers(users);
        }
            else
        return html.getFailLogin();
    }
    
    public String getIdUser(spark.Request req, spark.Response resp, String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
            resp.type("text/html");
            System.out.print(req.params(":id"));
            User user = User.findFirst("id_user = ?", req.params(":id"));
            String vl = user.get("first_name") + "," + user.get("last_name") + "," + user.get("email");
            return html.getUserBy(vl);
        }
        else
            return html.getFailLogin();
    }
    
    public String getInsertUser(spark.Request req, spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
            resp.type("text/html");
            return html.IngresarUsuario();
        }
        else
            return html.getFailLogin();
    }
    
    public String postInsertUser(spark.Request req, spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
            resp.type("text/html");
            User.createUser(req.queryParams("first_name"),req.queryParams("last_name"),req.queryParams("email"),req.queryParams("contrasena"));
            return html.admin();
        }
        else
            return html.getFailLogin();            
    }
    
    public String getRegisterUser(spark.Request req, spark.Response resp){
        resp.type("text/html");
        return html.RegistrarUsuario();
    }
    
    public String postRegisterUser(spark.Request req, spark.Response resp){
        resp.type("text/html");
        User.createUser(req.queryParams("first_name"),req.queryParams("last_name"),req.queryParams("email"),req.queryParams("contrasena"));
        return html.admin();
    }
    
    public String postEliminateUser(spark.Request req, spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
            resp.type("text/html");
            User tmp = User.findFirst("id_user = ?", req.queryParams("id_user"));
            User.deleteUser(tmp.getInteger("id_user"));
        return html.admin();
        }
        else
            return html.getFailLogin();
    }

}
  
