package com.unrc.app.controller;

import com.unrc.app.Html;
import com.unrc.app.models.*;
import static java.lang.Integer.parseInt;
import java.util.List;

public class VehiclesC {
    
    Html html = new Html();
    
    public String getVehicles(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin")){
        resp.type("text/html");
                List<Vehicle> v = Vehicle.findAll();
                String vh = "";
                for (int i = 0; i < v.size(); i++) {
                    vh = vh + v.get(i).getString("Patent") + "}" + v.get(i).getString("Mark") + "}" + v.get(i).getString("Model") + "}" + v.get(i).getString("Color") + "}" + v.get(i).getString("Tipo") + "}" + v.get(i).getString("id_user") + "}" + v.get(i).getString("isCoupe") + "}" + v.get(i).getString("cc") + "}" + v.get(i).getString("capacity") + ",";
                }
                return html.getAutomoviles(vh);
                
        }
        else
        return html.getFailLogin();
    }
    
    public String getVehiclePatente(spark.Request req,spark.Response resp, String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            Vehicle v = Vehicle.findFirst("patent = ?", req.params(":patente"));
            String vh = "";
            vh = vh + v.getString("Patent") + "}" + v.getString("Mark") + "}" + v.getString("Model") + "}" + v.getString("Color") + "}" + v.getString("Tipo") + "}" + v.getString("id_user") + "}"  + v.getString("isCoupe") + "}" + v.getString("cc") + "}" + v.getString("capacity");
            return html.getVehicleBy(vh);
        }
        else
            return html.getFailLogin();
    }
    
    public String getInsertvehicle(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("User")){
            resp.type("text/html");
            return html.IngresarAutomovil();
        }
        else
            return html.getFailLogin();
    }
    
    public String postInsertVehicle(spark.Request req,spark.Response resp, User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("User")){
            resp.type("text/html");
            if (req.queryParams("tipo").equals("0")){
                Vehicle.createVehicle(req.queryParams("patente"), req.queryParams("marca"), req.queryParams("modelo"), currentUser.getInteger("id_user"), req.queryParams("color"), "Auto", 0, req.queryParams("isCoupe"), 0); 
            }
            else {
                if (req.queryParams("tipo").equals("2")) {
                    Vehicle.createVehicle(req.queryParams("patente"), req.queryParams("marca"), req.queryParams("modelo"), currentUser.getInteger("id_user"), req.queryParams("color"), "Camion", 0,"-", parseInt(req.queryParams("Capacity")));
                } 
                else  
                    if (req.queryParams("tipo").equals("1")) {
                        Vehicle.createVehicle(req.queryParams("patente"), req.queryParams("marca"), req.queryParams("modelo"), currentUser.getInteger("id_user"), req.queryParams("color"), "Moto", parseInt(req.queryParams("CC")),"-", 0);
                    }
            }
            return html.IngresarAutomovil();
        }
        else
            return html.getFailLogin();
    }
    
    public String getOwnVehicle(spark.Request req,spark.Response resp, User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            List<Vehicle> v = Vehicle.find("id_user = ?", currentUser.get("id_user"));
            if (!v.isEmpty()) {
                String vh = "";
                for (int i = 0; i < v.size(); i++) {
                    vh = vh + v.get(i).getString("patent") + "}" + v.get(i).getString("mark") + "}" + v.get(i).getString("model") + "}" + v.get(i).getString("color") + "}" + v.get(i).getString("tipo") + "}" + v.get(i).getString("id_user") + "}" + v.get(i).getString("isCoupe") + "}" + v.get(i).getString("cc") + "}" + v.get(i).getString("capacity") + ",";
                }
                return html.getAutomoviles(vh);
            }
            return  html.getMessagePag("No posee vehiculos registrados","/webpag");
        }
        else
            return html.getFailLogin();
            
    }
}
