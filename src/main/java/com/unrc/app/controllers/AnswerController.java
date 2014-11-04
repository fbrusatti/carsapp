package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.*;
import java.util.*;
import com.unrc.app.MustacheTemplateEngine;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import spark.ModelAndView;
import spark.TemplateEngine;
import static spark.Spark.*;
import org.elasticsearch.node.*;
import org.elasticsearch.client.*;
import spark.Request;
import spark.Response;
import spark.Session;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

public class AnswerController{

    public ModelAndView AnswerList(){
            Map<String, Object> attributes = new HashMap<>();
            List<Answer> answers = Answer.findAll();
            attributes.put("answers_count", answers.size());
            attributes.put("answers", answers);   
            return new ModelAndView(attributes, "answers.mustache");
    }
    
    public ModelAndView AnswerId(Request req){
            Map<String, Object> attributes = new HashMap<>();
            Answer answerSelected = Answer.findById(req.params("id"));
            attributes.put("answers_id", answerSelected);
            return new ModelAndView(attributes, "answerId.mustache");
    }

    public String AnswerPost(Request req,Response res){
        String mail = req.queryParams("userEmail");
        String question = req.queryParams("questionTitle");
        String respuesta = req.queryParams("descripcion");     
        Answer resp = Answer.createAnswer(respuesta,User.findByEmail(mail),Question.findByDescription(question));
        res.redirect("/answers");
        return "success";
    }

    public String AnswerNew(){
            String form = "<form action= \"/answers \" method= \"post\">";
            form+="<center><h1>Responder pregunta</h1></center>";
            form+="Quien responde? : ";
            //combobox para seleccionar el usuario al cual corresponde el post a publicar
            form+="<select name=\"userEmail\">";
            List<User> userList = User.findAll();
            for (User u: userList) {
                String mail = u.getString("email");
                form+="<option value =\""+mail+"\">";            
                form+=" "+u.getString("email");
            };
            form+="</select><br><br>";
            //fin combobox   
            form+="Pregunta : ";
            //combobox para seleccionar la pregunta a la cual se quiere responder
            form+="<select name=\"questionTitle\">";
            List<Question> questionList = Question.findAll();
            for (Question q: questionList) {
                String descripcion = q.getString("description");
                form+="<option value =\""+descripcion+"\">";            
                form+=" "+q.getString("description");
            };
            form+="</select><br><br>";
            //fin combobox   
            form+="Respuesta : <br>";
            form+="<textarea name=\"descripcion\" placeholder=\"Escribe aqui tu respuesta\" rows=\"20\" cols=\"60\">";    
            form+="</textarea><br>";            
            form+="<input type= \"submit\" value = \"Submit\">";
            form+="</form>";
            return form;

    }




}
