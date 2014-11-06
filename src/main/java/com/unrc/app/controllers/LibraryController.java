package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.*;

import com.unrc.app.controllers.*;

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
import spark.Session;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

public class LibraryController{
    public static Session existsSession(Request request) {
        Session session = request.session(false);
        if (null != session) {
            Set<String> attrb = request.session(false).attributes();
            if (attrb.contains("user_email") && attrb.contains("user_id"))
                return session;
            else return null;
        }
        else return null;
    }

}
