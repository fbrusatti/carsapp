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

public class QuestionController{

    public ModelAndView QuestionList(){
            Map<String, Object> attributes = new HashMap<>();
            List<Question> questions = Question.findAll();
            attributes.put("questions_count", questions.size());
            attributes.put("questions", questions);   
            return new ModelAndView(attributes, "questions.mustache");
    }
    
    public ModelAndView QuestionId(Request req){
            Map<String, Object> attributes = new HashMap<>();
            Question questionsSelect = Question.findById(req.params("id"));
            attributes.put("questions_id",questionsSelect);
            return new ModelAndView(attributes,"questions_id.mustache");       
    }

    public String QuestionPost(Request req,Response res){
            String mail = req.queryParams("userEmail");
            String post = req.queryParams("postTitle");
            String descripcion = req.queryParams("descripcion");     
            Question pregunta = Question.createQuestion(descripcion,User.findByEmail(mail),Post.findByTitle(post));
            res.redirect("/questions");
            return "success";
    }

    public String QuestionNew(){
            String form = "<form action= \"/questions \" method= \"post\">";
            form+="<center><h1>Realizar pregunta</h1></center>";
            form+="Quien pregunta? : ";
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
            form+="Post : ";
            //combobox para seleccionar el post al cual se le quiere realizar la pregunta
            form+="<select name=\"postTitle\">";
            List<Post> postList = Post.findAll();
            for (Post p: postList) {
                String titulo = p.getString("title");
                form+="<option value =\""+titulo+"\">";            
                form+=" "+p.getString("title");
            };
            form+="</select><br><br>";
            //fin combobox   
            form+="Pregunta : <br>";
            form+="<textarea name=\"descripcion\" placeholder=\"Escribe aqui tu pregunta\" rows=\"20\" cols=\"60\">";        
            form+="</textarea><br>";    
            form+="<input type= \"submit\" value = \"Submit\">";
            form+="</form>";
            return form;    }
}
