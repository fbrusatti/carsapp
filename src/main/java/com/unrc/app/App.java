package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.*;
import static spark.Spark.*;
import spark.ModelAndView;
import java.util.*;
import org.elasticsearch.*;

/**
 * Hello world!
 *
 */
public class App 
{
    
   
    
    
    
    public static void main( String[] args )
    {
        System.out.println( "Hello cruel World!" );

        
        
      /*
      get("/hello",(request, response) -> {
         return "Hello World!";
      });
     
       */
        
        
        before((request, response) -> {                                 //OJO CON LA CONTRASEÑA
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        }); 
        
        get("/hello",(request, response) -> {
          try {
              String result = org.apache.http.client.fluent.Request.Get("http://localhost:9200/?pretty").execute().returnContent().asString();
              System.out.println(result);

              return result;
          } catch(Exception e) {
              System.out.println("grrrr, exception...");

              return "Execption catch";
          }
        });
        
         get("/users",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<User> users = User.findAll();
            
            String a ="<html> <head> <title> Usuarios </title> </head> <body> <form action=\"/usuario\" method=\"post\">";
            a= a + "<BODY> <h1> Usuario </h1> ";
            a= a + "<table border=1>";
            for (User usuario: users){
                
                a = a + "<tr><td>" + usuario.getFirstName() + "</td><td>" + usuario.getLastName()+"</td></tr>";
            }
            a = a + "</table>";
        return a;    
            
         } );   
            
       
            
            
            
        get ("/usuario/registarse/", (req, res) -> {
                                    
            String a ="<html> <head> <title> Registrarse </title> </head> <body> <form action=\"/usuario\" method=\"post\">";
            a= a + "<BODY> <h1> Registrarse </h1> <FORM >";
            
            //usuario
            //lectura nombre
            a=a+"<h3> Nombre</h3><INPUT type=text SIZE=25 NAME=first_name>";
            //lectura apellido
            a=a+"<h3> Apellido </h3><INPUT type=text SIZE=25 NAME=last_name>";
            //lectura email
            a=a+"<h3> Email </h3><INPUT type=text SIZE=25 NAME=email>";
            //lectura contraseña
            a=a+"<h3> Contraseña </h3><INPUT type=password SIZE=25 NAME=pass>";
            
            //ciudad
            //lectura pais
            a=a+"<h3> Pais </h3><INPUT type=text SIZE=25 NAME=country>";
            //lectura provincia
            a=a+"<h3> Provincia </h3><INPUT type=text SIZE=25 NAME=state>";
            //lectura ciudad
            a=a+"<h3> Ciudad </h3><INPUT type=text SIZE=25 NAME=name>";					

            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
 
        
 
        post("/usuario", (request, response) -> {
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            user.set("first_name", request.queryParams("first_name"));
            user.set("last_name", request.queryParams("last_name"));
            user.set("email",request.queryParams("email"));
            user.set("pass",request.queryParams("pass"));

            
            Address city = new Address();
            //Carga variable city con datos tomados por pantalla
            city.set("country", request.queryParams("country"));
            city.set("state",request.queryParams("state"));
            city.set("name",request.queryParams("name"));
            city.saveIt();

            user.saveIt();
            city.add(user);
            return "carga exitosa";
         });   
     
        
        
        get ("/vehiculoAuto/cargarAuto/", (req, res) -> {         
            String a ="<html> <head> <title> cargarAuto </title> </head> <body> <form action=\"/vehiculoAuto\" method=\"post\">";
            a= a + "<BODY> <h1> Cargar Auto </h1> <FORM >";
            
            //usuario
            //lectura email usuario
            a=a+"<h3> Email Usuario</h3><INPUT type=text SIZE=25 NAME=email>";
            //lectura apellido
            a=a+"<h3> Contraseña Usuario </h3><INPUT type=password SIZE=25 NAME=pass>";
      
            //vehiculo
            //lectura modelo vehiculo
            a=a+"<h3> Modelo Vehiculo </h3><INPUT type=text SIZE=25 NAME=model>";
            //lectura contraseña
            a=a+"<h3> Color Vehiculo </h3><INPUT type=text SIZE=25 NAME=color>";
            //lectura km vehiculo
            a=a+"<h3> Km Vehiculo </h3><INPUT type=text SIZE=25 NAME=km>";
            //lectura marcas vehiculo
            a=a+"<h3> Marca Vehiculo </h3><INPUT type=text SIZE=25 NAME=mark>";
            //lectura año vehiculo
            a=a+"<h3> Año Vehiculo </h3><INPUT type=text SIZE=25 NAME=year>";          
            
            //auto
            //lectura puertas auto
            a=a+"<h3> Puertas Auto </h3><INPUT type=text SIZE=25 NAME=doors>";					
            //lectura version auto
            a=a+"<h3> Version Auto </h3><INPUT type=text SIZE=25 NAME=version>";					
            //lectura transmision Auto
            a=a+"<h3> Transmision (Manual,Automatica)</h3><INPUT type=text SIZE=25 NAME=transmission>";
            //lectura direccion auto
            a=a+"<h3> Direccion Auto (Hidraulica,Asistida,Mecanica) </h3><INPUT type=text SIZE=25 NAME=direction>";       
            
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
 
        
 
        post("/vehiculoAuto", (request, response) -> {
            String retornar;
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            user.set("email",request.queryParams("email"));
            
            //controlar existencia de usuario en base de datos
            User user2 = new User();
            user2 = User.findFirst("email=?",user.get("email"));
            if (user2 == null){
                retornar="No se encontro usuario";
            }else{
                Vehicle vehicle = new Vehicle();
                //carga variable vehicle con datos tomados por pantalla
                vehicle.set("model",request.queryParams("model"));
                vehicle.set("color",request.queryParams("color"));
                vehicle.set("km",request.queryParams("km"));
                vehicle.set("mark",request.queryParams("mark"));
                vehicle.set("year",request.queryParams("year"));
                vehicle.saveIt();
                user2.add(vehicle); 


                Car car = new Car();
                //carga variable car con datos tomados por pantalla
                car.set("doors",request.queryParams("doors"));
                car.set("version",request.queryParams("version"));
                car.set("transmission",request.queryParams("transmission"));
                car.set("direction",request.queryParams("direction"));
                
                car.saveIt();
                vehicle.add(car);
                retornar ="carga exitosa";
           }
            return retornar;
         });   
        
        
        
        
        get ("/vehiculoMoto/cargarMoto/", (req, res) -> {         
            String a ="<html> <head> <title> cargarAuto </title> </head> <body> <form action=\"/vehiculoMoto\" method=\"post\">";
            a= a + "<BODY> <h1> Cargar Auto </h1> <FORM >";
            
            //usuario
            //lectura email usuario
            a=a+"<h3> Email Usuario</h3><INPUT type=text SIZE=25 NAME=email>";
            //lectura apellido
            a=a+"<h3> Contraseña Usuario </h3><INPUT type=password SIZE=25 NAME=pass>";
      
            //vehiculo
            //lectura modelo vehiculo
            a=a+"<h3> Modelo Vehiculo </h3><INPUT type=text SIZE=25 NAME=model>";
            //lectura contraseña
            a=a+"<h3> Color Vehiculo </h3><INPUT type=text SIZE=25 NAME=color>";
            //lectura km vehiculo
            a=a+"<h3> Km Vehiculo </h3><INPUT type=text SIZE=25 NAME=km>";
            //lectura marcas vehiculo
            a=a+"<h3> Marca Vehiculo </h3><INPUT type=text SIZE=25 NAME=mark>";
            //lectura año vehiculo
            a=a+"<h3> Año Vehiculo </h3><INPUT type=text SIZE=25 NAME=year>";  				
         
            //moto
            //lectura tipo
            a=a+"<h3> Tipo Moto (Street,Chopper,Standard,Sport,Touring,Scooters)</h3><INPUT type=text SIZE=25 NAME=type>";					
            //lectura tipo motor
            a=a+"<h3> Tipo Motor Moto </h3><INPUT type=text SIZE=25 NAME=type_motor>";					
            //lectura boot_system
            a=a+"<h3> Sistemas De Arranque Moto (Pedal,Electrico,Pedal y Electrico)</h3><INPUT type=text SIZE=25 NAME=boot_system>";
            //lectura cilindrada
            a=a+"<h3> Cilindrada Moto <h3><INPUT type=text SIZE=25 NAME=displacement>";       
                    
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
        
        post("/vehiculoMoto", (request, response) -> {
            String retornar;
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            user.set("email",request.queryParams("email"));
            
            //controlar existencia de usuario en base de datos
            User user2 = new User();
            user2 = User.findFirst("email=?",user.get("email"));
            if (user2 == null){
                retornar="No se encontro usuario";
            }else{
                Vehicle vehicle = new Vehicle();
                //carga variable vehicle con datos tomados por pantalla
                vehicle.set("model",request.queryParams("model"));
                vehicle.set("color",request.queryParams("color"));
                vehicle.set("km",request.queryParams("km"));
                vehicle.set("mark",request.queryParams("mark"));
                vehicle.set("year",request.queryParams("year"));
                vehicle.saveIt();
                user2.add(vehicle);    
                    
                Motorcycle moto = new Motorcycle();
                //carga variable moto con datos tomados por pantalla
                moto.set("type",request.queryParams("type"));
                moto.set("type_motor",request.queryParams("type_motor"));
                moto.set("boot_system",request.queryParams("boot_system"));
                moto.set("displacement",request.queryParams("displacement"));
                               
                moto.saveIt();
                vehicle.add(moto);
                
                retornar ="carga exitosa";
            }
            return retornar;
         });   
        
         
        get ("/vehiculoCamion/cargarCamion/", (req, res) -> {         
            String a ="<html> <head> <title> cargarCamion </title> </head> <body> <form action=\"/vehiculoCamion\" method=\"post\">";
            a= a + "<BODY> <h1> Cargar Camion </h1> <FORM >";
            
            //usuario
            //lectura email usuario
            a=a+"<h3> Email Usuario</h3><INPUT type=text SIZE=25 NAME=email>";
            //lectura apellido
            a=a+"<h3> Contraseña Usuario </h3><INPUT type=password SIZE=25 NAME=pass>";
      
            //vehiculo
            //lectura modelo vehiculo
            a=a+"<h3> Modelo Vehiculo </h3><INPUT type=text SIZE=25 NAME=model>";
            //lectura contraseña
            a=a+"<h3> Color Vehiculo </h3><INPUT type=text SIZE=25 NAME=color>";
            //lectura km vehiculo
            a=a+"<h3> Km Vehiculo </h3><INPUT type=text SIZE=25 NAME=km>";
            //lectura marcas vehiculo
            a=a+"<h3> Marca Vehiculo </h3><INPUT type=text SIZE=25 NAME=mark>";
            //lectura año vehiculo
            a=a+"<h3> Año Vehiculo </h3><INPUT type=text SIZE=25 NAME=year>";  				
         
            //camion
            //lectura sistema de frenos
            a=a+"<h3> Sistema de frenos Camion </h3><INPUT type=text SIZE=25 NAME=brake_system>";					
            //lectura direction 
            a=a+"<h3> direccion Camion (Hidraulica,Asistida,Mecanica) </h3><INPUT type=text SIZE=25 NAME=direction>";					
            //lectura capacidad
            a=a+"<h3> Capacidad Camion </h3><INPUT type=text SIZE=25 NAME=capacity>"; 
                               
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
        
        
        post("/vehiculoCamion", (request, response) -> {
            String retornar;
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            user.set("email",request.queryParams("email"));
            
            //controlar existencia de usuario en base de datos
            User user2 = new User();
            user2 = User.findFirst("email=?",user.get("email"));
            if (user2 == null){
                retornar="No se encontro usuario";
            }else{
                Vehicle vehicle = new Vehicle();
                //carga variable vehicle con datos tomados por pantalla
                vehicle.set("model",request.queryParams("model"));
                vehicle.set("color",request.queryParams("color"));
                vehicle.set("km",request.queryParams("km"));
                vehicle.set("mark",request.queryParams("mark"));
                vehicle.set("year",request.queryParams("year"));
                vehicle.saveIt();
                user2.add(vehicle);    
                    
                Truck camion = new Truck();
                //carga variable camion con datos tomados por pantalla
                camion.set("brake_system",request.queryParams("brake_system"));
                camion.set("direction",request.queryParams("direction"));
                camion.set("capacity",request.queryParams("capacity"));                    
                camion.saveIt();
                vehicle.add(camion);
                
                retornar ="carga exitosa";
            }
            return retornar;
         });   
        
        get("/users",
      (request, response) -> {
          Map<String, Object> attributes = new HashMap<>();
          List<User> users = User.findAll();
          attributes.put("users_count", users.size());
          attributes.put("users", users);
          return new ModelAndView(attributes, "users.moustache");
      },
      new MustacheTemplateEngine()
  );

        
        //cierra la base de datos
        after((request, response) -> {
            Base.close();    
        });
        
    
        /*    
        Address city = new Address();
        city.set("country", "Argentina");
        city.set("state","Cordoba");
        city.set("name","La Carlota");
        city.saveIt();
        
        
        User user = new User();
        user.set("first_name", "Marilyn");
        user.set("last_name", "Mollea");
        user.set("email","7mmollea@gmail.com");
        user.set("pass","123hola");
        user.saveIt();
        city.add(user);
       
        User user2 = new User();
        user2.set("first_name", "Marcelo");
        user2.set("last_name", "Uva");  
        user2.set("email","marcelitouva@gmail.com");
        user2.set("pass","123chau");
        user2.saveIt();
       
        city.add(user2);
        
         
        Vehicle vehicle = new Vehicle();
        vehicle.set("model","Corsa");
        vehicle.set("color","negro");
        vehicle.set("km",1000);
        vehicle.set("mark","Chevrolet");
        vehicle.set("year",2006);
        vehicle.saveIt();
        user.add(vehicle);
        
        Car car = new Car();
        car.set("doors","3");
        car.set("version","1.5");
        car.set("transmission","Manual");
        car.set("direction","Hidraulica");
        car.saveIt();
        vehicle.add(car);
       
        
        Post post = new Post();
        post.set("title","Vendo Corsa 2006");
        post.set("description","bastante usado");
        post.set("price","30000");
        post.saveIt();
        user.add(post);
        vehicle.add(post);
        
        Question question = new Question();
        question.set("text","tiene stereo? Gracias");
        question.saveIt();
        post.add(question);
        user2.add(question);
         
        Answer answer = new Answer();
        answer.set("text","No");
        answer.saveIt();
        question.add(answer);

        Base.close();
        
        
        <a href="/users/new"> Nuevo usuario </a>
        */       
    }
}
