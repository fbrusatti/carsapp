package com.unrc.app.controller;

import com.unrc.app.Html;
import com.unrc.app.models.*;
import static java.lang.Integer.parseInt;
import java.util.List;

public class AnswerC {
    
    Html html = new Html();
    
    public String getAnswers(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            List<Answer> v = Answer.findAll();
            String vh = "";
            for (int i = 0; i < v.size(); i++) {
                vh = vh + v.get(i).getString("id_post") + " " + v.get(i).getString("id_user") + " " + v.get(i).getString("description") + ",";
            }
            return html.getAnswers(vh);
        }
        else
            return html.getFailLogin();
    }
      
    public String getAnswersId(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            Answer v = Answer.findFirst("id_answer", ":id");
            String vh = "";
            vh = vh + v.getString("id_post") + " " + v.getString("id_user") + " " + v.getString("description");
            return html.getAnswersByid(vh);
        }
        else
            return html.getFailLogin();
    }
    
    public String postInsertAnswer(spark.Request req,spark.Response resp,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            Answer.createAnswer(currentUser, parseInt(req.queryParams("id_pregunta")), req.queryParams("descripcion"));
            return html.IngresarRespuesta();
        }
        else
            return html.getFailLogin();
    }
}
