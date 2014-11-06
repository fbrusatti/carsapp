package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.*;
import java.util.*;
import com.unrc.app.MustacheTemplateEngine;
import com.unrc.app.controllers.*;

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

public class VehicleController{
    
    public ModelAndView VehiclesList(Request req){
            Map<String, Object> attributes = new HashMap<>();
            List<Vehicle> vehicles = Vehicle.findAll();
            attributes.put("vehicles_count", vehicles.size());
            attributes.put("vehicles", vehicles);   
            return new ModelAndView(attributes, "vehicles.mustache");
    }

    public ModelAndView VehiclesLicensePlate(Request req){
    	    Map<String, Object> attributes = new HashMap<>();
            Vehicle vehicleSelected = Vehicle.findByPatent(req.params("patent"));
            attributes.put("vehicle_id", vehicleSelected);
            return new ModelAndView(attributes, "vehicleId.mustache");
    }

    public void NewVehicle(Request req, Response resp){
        String email = req.queryParams("userEmail");
        String patente = req.queryParams("patent");
        String modelo = req.queryParams("model");
        String marca = req.queryParams("brand");
        String tipo = req.queryParams("type");
        Vehicle nuevoVehiculo = new Vehicle();
        User u = User.findFirst("email = ?",email);
        nuevoVehiculo.createVehicle(patente, modelo, marca, u);
        if (req.queryParams("type").charAt(0)=='1') {
            Car c = new Car();
            Boolean esCoupe = Boolean.parseBoolean(req.queryParams("iscoupe"));
            String p = nuevoVehiculo.getString("patent");
            c.set("id_vehicle",patente);
            c.set("is_coupe",esCoupe);
            c.saveIt();
        }
        if (req.queryParams("type").charAt(0)=='2') {
            Motorcycle m = new Motorcycle();
            Integer cilindrada = Integer.parseInt(req.queryParams("engine_size"));
            Integer rodado = Integer.parseInt(req.queryParams("wheel_size"));
            m.set("id_vehicle",patente);
            m.set("engine_size",cilindrada);
            m.set("wheel_size",rodado);
            m.saveIt();
        }
        if (req.queryParams("type").charAt(0)=='3') {
            Truck t = new Truck();
            Integer cinturones = Integer.parseInt(req.queryParams("count_belt"));
            String p = nuevoVehiculo.getString("patent");
            t.set("id_vehicle",patente);
            t.set("count_belt",cinturones);
            t.saveIt();
        }            
        resp.redirect("/vehicles");
    }

    public String GetNewVehicle(Request req, Response resp){
        Session session = LibraryController.existsSession(req);
        if (null == session) {
            resp.redirect("/");
        }
        String form = "<form action= \"/vehicles \" method= \"post\">";
        form+="<center><h1>Crear Vehículo</h1></center>";
        form+="Email usuario : ";
        //combobox para seleccionar el usuario al cual corresponde el vehiculo a agregar
        form+="<select name=\"userEmail\">";
        List<User> userList = User.findAll();
        for (User u: userList) {
            String mail = u.getString("email");
            form+="<option value =\""+mail+"\">";            
            form+=" "+u.getString("email");
        };
        form+="</select><br>";
        //fin combobox
        form+="Patente : ";
        form+="<input type = \"text\" name=\"patent\"><br>";
        form+="Modelo: ";
        form+="<input type = \"number\" name=\"model\"><br>";  
        form+="Marca: ";
        form+="<input type \"text\" name=\"brand\"><br>";  
        form+="Tipo Vehiculo : ";  
        form+="<select name=\"type\">";
        form+="<option value=\"1\">";
        form+="Automóvil";
        form+="<option value=\"2\">";
        form+="Motocicleta";
        form+="<option value=\"3\">";
        form+="Camioneta";            
        form+="</select><br>";
        form+="<br>";
        form+="Rellene solo los campos correspondientes a su tipo de vehiculo: <br>";            
        form+="<br> Automóviles: <br>";            
        form+="Es coupe? : ";  
        form+="<select name=\"iscoupe\">";
        form+="<option value=\"true\">";
        form+="Si";
        form+="<option value=\"false\">";
        form+="No";
        form+="</select><br>";
        form+="<br> Motocicletas: <br>";            
        form+="Cilindrada : ";
        form+="<input type = \"text\" name=\"engine_size\"><br>";
        form+="Rodado : ";
        form+="<input type = \"text\" name=\"wheel_size\"><br>";
        form+="<br> Camionetas: <br>";            
        form+="Cantidad Cinturones : ";
        form+="<input type = \"text\" name=\"count_belt\"><br>";
        form+="<input type= \"submit\" value = \"Submit\">";
        form+="</form>";
        return form;        
    }
}