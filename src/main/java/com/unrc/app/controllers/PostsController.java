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
import com.unrc.app.controllers.*;
import spark.Request;
import spark.Response;
import spark.Session;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

public class PostsController{

	public ModelAndView PostsList(){
		Map<String, Object> attributes = new HashMap<>();
        List<Post> posts = Post.findAll();
        attributes.put("posts_count", posts.size());
        attributes.put("posts", posts);   
        return new ModelAndView(attributes, "posts.mustache");
	}

	public ModelAndView PostId(Request req){
		Map<String, Object> attributes = new HashMap<>();
        Post postsSelect = Post.findById(req.params("id"));
        attributes.put("posts_id",postsSelect);
        return new ModelAndView(attributes,"posts_id.mustache"); 
	}

	public void NewPost(Request req, Response resp){
		String mail = req.queryParams("userEmail");
        String licensePlate = req.queryParams("patent");
        String titulo = req.queryParams("titulo");
        String descripcion = req.queryParams("descripcion");     
        Post post = Post.createPost(titulo,descripcion,User.findByEmail(mail),Vehicle.findByPatent(licensePlate));
        resp.redirect("/posts");
	}

	public String GetNewPost(Request req, Response resp){
		Session session = LibraryController.existsSession(req);
        if (null == session) {
            resp.redirect("/");
        }
        String form = "<form action= \"/posts \" method= \"post\">";
        form+="<center><h1>Crear nueva publicación</h1></center>";
        form+="Email usuario : ";
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
        form+="Vehiculo a publicar : ";
        //combobox para seleccionar el vehiculo que se quiere publicar
        form+="<select name=\"patent\">";
        List<Vehicle> vehicleList = Vehicle.findAll();
        for (Vehicle u: vehicleList) {
            String patente = u.getString("patent");
            form+="<option value =\""+patente+"\">";            
            form+=" "+u.getString("patent");
        };                    
        form+="</select><br><br>";
        //fin combobox
        form+="Titulo : ";
        form+="<input type= \"text\" name=\"titulo\" placeholder=\"Titulo\"><br><br>";
        form+="Descripción : <br>";
        form+="<textarea name=\"descripcion\" placeholder=\"Contenido del post\" rows=\"20\" cols=\"60\">";
        form+="</textarea><br>";
        form+="<input type= \"submit\" value = \"Submit\">";
        form+="</form>";
        return form;
	}
}