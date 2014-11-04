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

public class LogoutController{

    public String getLogout(Request request, Response response){
            String res = "";
            Session session = LibraryController.existsSession(request);
            if (null == session) {
                res += "No hay ninguna sesion activa.";
            } else {
                session.invalidate();
                response.redirect("/"); 
            }
            return res;
    }
}
