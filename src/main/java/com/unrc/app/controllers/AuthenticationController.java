package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.*;
import com.unrc.app.App;

import com.unrc.app.controllers.*;

import java.util.*;
import com.unrc.app.MustacheTemplateEngine;

import spark.Request;
import spark.Response;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import spark.ModelAndView;
import spark.TemplateEngine;
import static spark.Spark.*;
import org.elasticsearch.node.*;
import org.elasticsearch.client.*;

import spark.Session;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

public class AuthenticationController{

    public String getAuthentication(Request request, Response response) {
            String res = "";
            String pass = request.queryParams("passs");
            String email = request.queryParams("email");
            User us = User.findByEmail(email);
            if (us != null) {
                Boolean usuario=User.existUser(email);
                Boolean clave=User.findUserByPass(pass);
                if (usuario && clave) {
                    Session session = request.session(true);
                    session.attribute("user_email", email);
                    session.attribute("user_id", us.getId());
                    session.maxInactiveInterval(30*60);
                    String aux = "admin@carsapp.com";
                    if (email.equals(aux)){
                        response.redirect("/homeAdmin"); 
                        res += "existe el usuario.";
                        return res;    
                    }
                    response.redirect("/home"); 
                    res += "existe el usuario.";
                    return res;
                }
                else{
                    res += "El password indicado no es correcto.";
                    return res;
                }    
            }
            else {
                res += "El email indicado no existe .";
                return res;
            }
        }
}
