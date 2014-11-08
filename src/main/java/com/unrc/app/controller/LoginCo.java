package com.unrc.app.controller;

import com.unrc.app.Html;

public class LoginCo {
    
    Html html = new Html();
   
    public String getWebPag(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("User")){
            resp.type("text/html");
            return html.webpage();
        }
        else
            return html.getFailLogin();
    }
    
    public String getAdmin(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
            resp.type("text/html");
            return html.admin();
        }
        else
            return html.getFailLogin();
    }
    
    public String getGuestCP(spark.Request req,spark.Response resp){
        resp.type("text/html");
        return html.guest();
    }
}
