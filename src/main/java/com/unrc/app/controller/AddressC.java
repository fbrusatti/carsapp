package com.unrc.app.controller;

import com.unrc.app.Html;
import com.unrc.app.models.*;
import static java.lang.Integer.parseInt;
import java.util.List;

public class AddressC {
    
    Html html = new Html();
    
    public String getAddress(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            List<Address> v = Address.findAll();
            String vh = "";
            for (int i = 0; i < v.size(); i++) {
                vh = vh + v.get(i).getString("province") + "}" + v.get(i).getString("city") + "}" + v.get(i).getString("postal_code") + "}" + v.get(i).getString("street") + "}" +v.get(i).getString("num")+ ",";
            }
            return html.getCities(vh);
        }
        else
            return html.getFailLogin();
    }
    
    public String getInsertAddress(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            return html.IngresarCiudad();
        }
        else
            return html.getFailLogin();
    }
    
    public String postInsertAddress(spark.Request req,spark.Response resp,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            Address.createAddress(req.queryParams("direccion"),parseInt(req.queryParams("num")),req.queryParams("ciudad"),req.queryParams("provincia"),parseInt(req.queryParams("codigo_postal")));
            Address a = Address.findByAddress(req.queryParams("direccion"),parseInt(req.queryParams("num")),req.queryParams("ciudad"),req.queryParams("provincia"),parseInt(req.queryParams("codigo_postal")));
            int id_address= parseInt(a.getString("id_address"));
            UsersAddress.createUsersAddress(currentUser.getInteger("id_user"), id_address);
            return html.IngresarCiudad();
        }
        else
           return html.getFailLogin();
    }
    
    public String getOwnAddress(spark.Request req,spark.Response resp,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            List<UsersAddress> a = UsersAddress.find("id_user = ?", currentUser.get("id_user"));
            if (!a.isEmpty()) {
                String vh = "";
                for (int i = 0; i < a.size(); i++) {
                    Address b = Address.findFirst("id_address = ?", a.get(i).getString("id_address"));
                    vh = vh + b.getString("province") + "}" + b.getString("city") + "}" + b.getString("postal_code") + "}" + b.getString("street") + "}" +b.getString("num")+ ",";
                }
                return html.getOwnAddress(vh);
            }
            return  html.getMessagePag("No posee ciudades registradas","/webpag");
        }
        else
            return html.getFailLogin();
    }
}
