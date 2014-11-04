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

public class AdressController{

    public ModelAndView AdressList(){
            Map<String, Object> attributes = new HashMap<>();
            List<Address> addresses = Address.findAll();
            attributes.put("addresses_count", addresses.size());
            attributes.put("addresses", addresses);   
            return new ModelAndView(attributes, "addresses.mustache");
    }
    
    public ModelAndView AdressId(Request req){
            Map<String, Object> attributes = new HashMap<>();
            Address addressSelected = Address.findById(req.params("id"));
            attributes.put("addresses_id", addressSelected);
            return new ModelAndView(attributes, "addressId.mustache");
    }

    public String AdressNew(){
            String form = "<form action= \"/addresses \" method= \"post\">";
            form+="<center><h1>Crear Direccion</h1></center>";
            form+="Email usuario : ";
            //combobox para seleccionar el usuario al cual corresponde la direccion a agregar
            form+="<select name=\"userEmail\">";
            List<User> userList = User.findAll();
            for (User u: userList) {
                String mail = u.getString("email");
                form+="<option value =\""+mail+"\">";            
                form+=" "+u.getString("email");
            };
            form+="</select><br>";
            //fin combobox
            form+="Calle : ";
            form+="<input type = \"text\" name=\"street\"><br>";
            form+="Numero: ";
            form+="<input type = \"number\" name=\"num\"><br>";  
            form+="Ciudad: ";
            form+="<input type \"text\" name=\"city\">";          
            form+="<input type= \"submit\" value = \"Submit\">";
            form+="</form>";
            return form;    
    }

    public String PostAdress(Request req,Response res){
            String email = req.queryParams("userEmail");
            String calle = req.queryParams("street");
            String numero = req.queryParams("num");
            String ciudad = req.queryParams("city");
            Address nuevoAddress = new Address();
            User u = User.findFirst("email = ?",email);
            int num = Integer.parseInt(numero); 
            nuevoAddress.createAddress(calle, num, ciudad, u);
            res.redirect("/addresses");
            return "success";
    }


}
