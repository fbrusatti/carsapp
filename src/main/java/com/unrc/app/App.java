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
public class App {
    
   
    
    
    
    public static void main( String[] args )
    {
        System.out.println( "Hello cruel World!" );
   
	before((request, response) -> {                                 //OJO CON LA CONTRASEÑA
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        });     
   
      
		get ("/app", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			return new ModelAndView(attributes, "Main.moustache");

			 },
            new MustacheTemplateEngine()
        );
		
		get ("/InsertVehicle", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			return new ModelAndView(attributes, "InsertVehicle.moustache");

			 },
            new MustacheTemplateEngine()
        );
		
		get ("/Delete", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			return new ModelAndView(attributes, "Delete.moustache");

			 },
            new MustacheTemplateEngine()
        );

		get ("/List", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			return new ModelAndView(attributes, "List.moustache");

			 },
            new MustacheTemplateEngine()
        );

		get ("/Search", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			return new ModelAndView(attributes, "Search.moustache");

			 },
            new MustacheTemplateEngine()
        );
	



//------------------------------------------Listaar----------------------------  
   // Listar Usuario
		get("/ListUsers", (request, response) -> {
          Map<String, Object> attributes = new HashMap<>();
          List<User> users = User.findAll();
          attributes.put("users_count", users.size());
          attributes.put("users", users);
          return new ModelAndView(attributes, "ListUsers.moustache");
      },
      new MustacheTemplateEngine()
  );

	//listar Vehiculos
		get("/ListVehicles", (request, response) -> {
          Map<String, Object> attributes = new HashMap<>();
          List<Vehicle> vehicles = Vehicle.findAll();
          attributes.put("vehicles_count", vehicles.size());
          attributes.put("vehicles", vehicles);
          return new ModelAndView(attributes, "ListVehicles.moustache");
      },
      new MustacheTemplateEngine()
  );
  
	// Listar Post
		get("/ListPost", (request, response) -> {
          Map<String, Object> attributes = new HashMap<>();
          List<Post> post = Post.findAll();
          attributes.put("post_count", post.size());
          attributes.put("post", post);
          return new ModelAndView(attributes, "ListPost.moustache");
      },
      new MustacheTemplateEngine()
  );
 
    
  //--------------------------------------Inseert---------------------------------- 
     
         get ("/app/RegisterUser", (req, res) -> {                           
            String a ="<html> <head> <title> Registrarse </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/User\" method=\"post\">"; 
            a=a+      " <h1> Registrarse </h1> <FORM >";
            
			a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
			
			// crea un enlace para volver a la pag anterior.
			a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a> </td></table>";
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
 
        post("/User", (request, response) -> {
            
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
     
        
        
        get ("/app/InsertVehicle/InsertCar", (req, res) -> {         
            String a ="<html> <head> <title> cargarAuto </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/CarVehicle\" method=\"post\">";
            a= a + "<h1> Cargar Auto </h1> <FORM >";

			a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
			
			// crea un enlace para volver a la pag anterior.
			a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/InsertVehicle"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";
            
            //usuario
            //lectura email usuario
            a=a+"<h3> Email Usuario</h3><INPUT type=text SIZE=25 NAME=email>";
            //lectura apellido
            a=a+"<h3> Contraseña Usuario </h3><INPUT type=password SIZE=25 NAME=pass>";
      
            //vehiculo
            //lectura modelo vehiculo
            a=a+"<h3> Modelo Vehiculo </h3><INPUT type=text SIZE=25 NAME=model>";
            //lectura modelo vehiculo
            a=a+"<h3> Patente Vehiculo </h3><INPUT type=text SIZE=25 NAME=patent>";           
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
 
        
 
        post("/CarVehicle", (request, response) -> {
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
                vehicle.set("patent",request.queryParams("patent"));
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
        
        
        
        
        get ("/app/InsertVehicle/InsertMotorcycle", (req, res) -> {         
            String a ="<html> <head> <title> cargarMoto </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/InsertMotorcycle\" method=\"post\">";
            a= a + "<BODY> <h1> Cargar Moto </h1> <FORM >";
            
			a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
			
			// crea un enlace para volver a la pag anterior.
			a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/InsertVehicle"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";

            //usuario
            //lectura email usuario
            a=a+"<h3> Email Usuario</h3><INPUT type=text SIZE=25 NAME=email>";
            //lectura apellido
            a=a+"<h3> Contraseña Usuario </h3><INPUT type=password SIZE=25 NAME=pass>";
      
            //vehiculo
            //lectura modelo vehiculo
            a=a+"<h3> Modelo Vehiculo </h3><INPUT type=text SIZE=25 NAME=model>";
            //lectura modelo vehiculo
            a=a+"<h3> Patente Vehiculo </h3><INPUT type=text SIZE=25 NAME=patent>";
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
        
        post("/InsertMotorcyle", (request, response) -> {
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
                vehicle.set("patent",request.queryParams("patent"));
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
        
         
        get ("/app/InsertVehicle/InsertTruck", (req, res) -> {         
            String a ="<html> <head> <title> cargarCamion </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/InsertTruck\" method=\"post\">";
            a= a + "<h1> Cargar Camion </h1> <FORM >";
            
			a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
			
			// crea un enlace para volver a la pag anterior.
			a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/InsertVehicle"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";
            //usuario
            //lectura email usuario
            a=a+"<h3> Email Usuario</h3><INPUT type=text SIZE=25 NAME=email>";
            //lectura apellido
            a=a+"<h3> Contraseña Usuario </h3><INPUT type=password SIZE=25 NAME=pass>";
      
            //vehiculo
            //lectura modelo vehiculo
            a=a+"<h3> Modelo Vehiculo </h3><INPUT type=text SIZE=25 NAME=model>";
            //lectura modelo vehiculo
            a=a+"<h3> Patente Vehiculo </h3><INPUT type=text SIZE=25 NAME=patent>";
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
        
        
        post("/InsertTruck", (request, response) -> {
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
                vehicle.set("patent",request.queryParams("patent"));
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
        


        get ("/CreatePost", (req, res) -> {         
            String a ="<html> <head> <title> Create Post </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/Post\" method=\"post\">";
            a= a + "<h1> Crear Post </h1> <FORM >";
            a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
			
			// crea un enlace para volver a la pag anterior.
			a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";
            //usaurio
            //lectura email
            a=a+"<h3> email Usuario </h3><INPUT type=text SIZE=25 NAME=email>";
            //vehiculo
            //lectura patente vehiculo
            a=a+"<h3> Patente Vehiculo </h3><INPUT type=text SIZE=25 NAME=patent>";
            
            //Post
            //lectura titulo
            a=a+"<h3> Titulo Post</h3><INPUT type=text SIZE=25 NAME=title>";
            //lectura Descricion
            a=a+"<h3> Descripcion Post </h3><INPUT type=text < SIZE=25 NAME=description>";
            //lectura precio
            a=a+"<h3> Precio Post </h3><INPUT type=text SIZE=25 NAME=price>";					
            
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
        
        
        post("/Post", (request, response) -> {
            String retornar;
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            user.set("email",request.queryParams("email"));
            
            //Cargar variable vehiculo con datos tomados por pantalla
            Vehicle vehiculo = new Vehicle();
            vehiculo.set("patent",request.queryParams("patent"));
            
            //controlar existencia de usuario y de vehiculo en base de datos
            User user2 = new User();
            user2 = User.findFirst("email=?",user.get("email"));
            Vehicle vehiculo2 = new Vehicle();
            vehiculo2 = vehiculo.findFirst("patent=?",vehiculo.get("patent"));
            
            if ((user2 == null) || (vehiculo2 == null)){
                retornar="No se encontro usuario o vehiculo";
            }else{
                Post post = new Post();
                post.set("title",request.queryParams("title"));
                post.set("description",request.queryParams("title"));
                post.set("price",request.queryParams("price"));
                post.saveIt();
                user2.add(post);
                vehiculo2.add(post);
                retornar ="carga exitosa";
            }
            return retornar;
         });
        
        
   
        get ("/Pregunta/cargarPregunta/", (req, res) -> {         
            String a ="<html> <head> <title> cargarPregunta </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/Pregunta\" method=\"post\">";
            a= a + "<h1> Cargar Pregunta </h1> <FORM >";       

            //usaurio
            //lectura email
            a=a+"<h3> email Usuario </h3><INPUT type=text SIZE=25 NAME=email>";
            //Post
            //lectura titulo post
            a=a+"<h3> Titulo Post </h3><INPUT type=text SIZE=25 NAME=title>";
            
            //Post
            //lectura texto
            a=a+"<h3> Texto Pregunta</h3><INPUT type=text SIZE=25 NAME=textQ>";
            
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
        
        
        post("/Pregunta", (request, response) -> {
            String retornar;
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            user.set("email",request.queryParams("email"));
                       
            //Cargar variable post con datos tomados por pantalla
            Post post = new Post();
            post.set("title",request.queryParams("title"));
            
            //controlar existencia de usuario y de post en base de datos
            User user2 = new User();
            user2 = User.findFirst("email=?",user.get("email"));
            Post post2 = new Post();
            post2 = post.findFirst("title=?",post.get("title"));
            
            if ((user2 == null) || (post2 == null)){
                retornar="No se encontro usuario o post";
            }else{
                Question question = new Question();
                question.set("textQ",request.queryParams("textQ"));
                question.saveIt();
                post2.add(question);
                user2.add(question);
                retornar ="carga exitosa";
            }
            return retornar;
         });
                   
        
        
        get ("/Respuesta/cargarRespuesta/", (req, res) -> {         
            String a ="<html> <head> <title> cargarRespuesta </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/Respuesta\" method=\"post\">";
            a= a + "<h1> Cargar Respuesta </h1> <FORM >";       

            //Usaurio
            //Lectura Email
            a=a+"<h3> Email Usuario </h3><INPUT type=text SIZE=25 NAME=email>";
            
            //Pregunta
            //Lectura texto
            a=a+"<h3> Texto Pregunta </h3><INPUT type=text SIZE=25 NAME=textQ>";
            
            //Respuesta
            //lectura texto Respuesta
            a=a+"<h3> Texto Respuesta </h3><INPUT type=text SIZE=25 NAME=textA>";
            
       
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
        
        // ------------------------------------------------------------------------------------------
        // ----------------------------ELIMINAAAR--------------------------------------------------------------
        
 		get ("app/Delete/User",(request,response) -> {
        String a ="";
        a ="<html> <head> <title> Usuarios </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/dUser\" method=\"post\">";
		a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
		a =a + "<h1> Eliminar Usuario </h1>";        

		//lectura email
        a=a+"<h3> Email </h3><INPUT type=text SIZE=25 NAME=email>";
		//creacion de botones
        a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM>";
        a= a + "<a href="+"http://localhost:4567/Delete"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></BODY></HTML>";   
        return a;
    }); 
     
   		post("/dUser",(request,response)->{
        User user = new User();
        String retornar ="";
        //Cargar variable user con datos tomados por pantalla
        user.set("email",request.queryParams("email"));

        //controlar existencia de usuario en base de datos
        User user2 = new User();
        user2 = User.findFirst("email=?",user.get("email"));
        if (user2 == null){
           retornar="No se encontro usuario";
        }else{
             user2.deleteCascade();
             retornar = "Usuario Eliminado correctamete";
        }
        return retornar;
    });   
    
    get ("/app/Delete/Vehicle",(request,response) -> {
        String a ="";
        a ="<html> <head> <title> Post </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/dVehicle\" method=\"post\">";
        a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
        
		a = a+ "<h1> Eliminar Vehiculo </h1>";
		//Usuario
        //lectura email
        a=a+"<h3> Email Usuario </h3><INPUT type=text SIZE=25 NAME=email>";
        
        //Vehiculo
        //lectura patente
        a=a+"<h3> Patente Vehiculo </h3><INPUT type=text SIZE=25 NAME=patent>";
        
        //creacion de botones
        a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM>";
		a= a + "<a href="+"http://localhost:4567/Delete"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></BODY></HTML>";  
        return a;
    }); 
     
    post("/dVehicle",(request,response)->{
        User user = new User();
        String retornar ="";
        //Cargar variable user con datos tomados por pantalla
        user.set("email",request.queryParams("email"));

        Vehicle vehiculo = new Vehicle();
        vehiculo.set("patent",request.queryParams("patent"));

        
        //controlar existencia de usuario en base de datos
        User user2 = new User();
        user2 = User.findFirst("email=?",user.get("email"));
        Vehicle vehiculo2 = new Vehicle();
        vehiculo2 = Vehicle.findFirst("patent=?",vehiculo.get("patent"));
       
        
        if ((user2 == null) || (vehiculo2 == null) ){
           retornar="No se encontro usuario o vehiculo";
        }else{
             vehiculo2.deleteCascade();
             retornar = "Vehiculo Eliminado correctamete";
        }
        return retornar;
    });   
    
    
    
    get ("/app/Delete/Post",(request,response) -> {
        String a ="";
        a ="<html> <head> <title> Eliminar Post </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/dPost\" method=\"post\">";
		a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";        
		
		a = a + "<h1> Eliminar Post </h1>";
		//Usuario
        //lectura email
        a = a + "<h3> Email </h3><INPUT type=text SIZE=25 NAME=email>";
        //Post
        //titulo del post
        a = a + "<h3> Titulo Post </h3><INPUT type=text SIZE=25 NAME=title>"; 
        //creacion de botones
        a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM>";
		a= a + "<a href="+"http://localhost:4567/Delete"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></BODY></HTML>";  

        return a;
    });
    
    post("/dPost",(request,response)->{
        User user = new User();
        Post post = new Post();
        String retornar ="";
        //Cargar variable user con datos tomados por pantalla
        user.set("email",request.queryParams("email"));
        post.set("title",request.queryParams("title"));
        //controlar existencia de usuario en base de datos
        User user2 = new User();
        Post post2 = new Post();
        user2 = User.findFirst("email=?",user.get("email"));
        post2 = Post.findFirst("title=?",post.get("title"));
        if ((user2 == null) || (post2 == null)) {
           retornar="No se encontro usuario o post ha eliminar";
        }else{
             post2.delete();
             retornar = "Post Eliminado correctamete";
        }
        return retornar;
    });          
        
        
        
        
        //cierra la base de datos
        after((request, response) -> {
            Base.close();    
        });
        
    
       
    }
}
