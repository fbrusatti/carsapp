package com.unrc.app.controller;

import com.unrc.app.Html;
import com.unrc.app.models.*;
import java.util.List;

public class AdminC {
    
    Html html = new Html();
    
    public String getAdminContactUser(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("User") || usuarioActivo.equals("Guest")){
            resp.type("text/html");
            return html.contactAdminUser();
        }
        else
            return html.getFailLogin();
    }
    
    public String postAdminContactUser(spark.Request req,spark.Response resp,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("User") || usuarioActivo.equals("Guest")){
            resp.type("text/html");
            Message.createMessage(currentUser.getInteger("id_user"), req.queryParams("mensaje"));
            return html.contactAdminUser();
        }
        else
            return html.getFailLogin();
    }
        
    
    public String getAdminContactGuest(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Guest")){
            resp.type("text/html");
            return html.contactAdminGuest();
        }
        else
            return html.getFailLogin();
    }
        
    
    public String postAdminContactGuest(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("User") || usuarioActivo.equals("Guest")){
            resp.type("text/html");
            MessageGuest.createMessageGuest(req.queryParams("mensaje"));
            return html.contactAdminGuest();
        }
        else
            return html.getFailLogin();
    }  
    
    public String getInbox(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
            resp.type("text/html");
            List<Message> m = Message.findAll();
            List<MessageGuest> mg = MessageGuest.findAll();
            String message = "";
            String messageGuest = "";
            for (int i = 0; i < m.size(); i++) {
                User name = User.findFirst("id_user = ?", m.get(i).getString("id_user"));
                message = message + m.get(i).getString("id_message")+ "}" +  name.getString("first_name") + ",";
            }
            for (int i = 0; i < mg.size(); i++) {
                messageGuest = messageGuest + mg.get(i).getString("id_messageGuest")+ ",";
            }
            return html.AdminInbox(message,messageGuest);
        }
        else
            return html.getFailLogin(); 
    }
    
    public String postInbox(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
           resp.type("text/html");       
           if(!req.queryParams("idmu").isEmpty()){
               Message messageu = Message.findFirst("id_message = ?", req.queryParams("idmu"));
                String mu =  messageu.getString("description");   
                return html.getMessage(mu);
           }
           if(!req.queryParams("idmi").isEmpty()){
               MessageGuest messagei = MessageGuest.findFirst("id_messageGuest = ?", req.queryParams("idmi"));
               String mi =  messagei.getString("description");
               return html.getMessage(mi);
           }
           if(!req.queryParams("eliminarmu").isEmpty()){
               Message messageu = Message.findFirst("id_message = ?", req.queryParams("eliminarmu"));
               messageu.deleteMessage(messageu.getInteger("id_message"));
                return html.getMessagePag("Mensaje Eliminado Con Exito","/inbox");
           }
           if(!req.queryParams("eliminarmi").isEmpty()){
               MessageGuest messagei = MessageGuest.findFirst("id_messageGuest = ?", req.queryParams("eliminarmi"));
               messagei.deleteMessageGuest(messagei.getInteger("id_messageGuest"));
               return html.getMessagePag("Mensaje Eliminado Con Exito","/inbox");
           }
           return html.getMessagePag("Error","/inbox");
        }
        else
            return html.getFailLogin();
    }
    
}


