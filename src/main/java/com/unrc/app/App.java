package com.unrc.app;

import com.unrc.app.models.*;
import java.util.*;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.javalite.activejdbc.Base;
import spark.ModelAndView;
import static spark.Spark.*;
/**
 * Hello world!
 *
 */


        


public class App {
    public static final Node node = org.elasticsearch.node
                                        .NodeBuilder
                                        .nodeBuilder()
                                        .clusterName("carsapp")
                                        .local(true)
                                        .node();
    public static Client client(){
        return node.client();
    }
      
    public static void main( String[] args )
    {
        System.out.println( "Hello cruel World!" );
	externalStaticFileLocation("./Images");
   
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
		


		get ("/List", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			return new ModelAndView(attributes, "List.moustache");

			 },
            new MustacheTemplateEngine()
        );


	


//------------------------------------------Listar----------------------------  
   // Listar Usuario
    get("/ListUser", (request, response) -> {
          Map<String, Object> attributes = new HashMap<>();
          
        //pasa a list todos los usuarios
          List<User> users = User.findAll();
          //carga tamaño de usuarios
          attributes.put("users_count", users.size());
          //carga list en map
          attributes.put("users", users);
          return new ModelAndView(attributes, "ListUsers.moustache");
      },
      new MustacheTemplateEngine()
  );
    

    get("/ListUser/:id", (request, response) -> {
        User u = User.findById(request.params("id"));
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("user",u);
        return new ModelAndView(attributes,"UserId.moustache");
                },
        new MustacheTemplateEngine()
    );
     
        
        
    get("/ListUser/:id/posts",(request,response)-> {
        User u = User.findById(request.params("id"));
        List <Post> posts = Post.where("user_id = ?", request.params("id"));
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("UserPosts",posts);
        attributes.put("UserId",u.id());
        
        return new ModelAndView(attributes, "UserPosts.moustache");
        } ,
       new MustacheTemplateEngine()
    );
            
            
    get("ListUser/:id/posts/:pId", (request, response) -> {
        Post p = Post.findById(request.params("pId"));
        User u = User.findById(request.params("id"));
        Vehicle v = Vehicle.findById(p.getVehicle());
        List<Car> listC = Car.where("vehicle_id = ?", v.getId());
       
        List<Truck> listT = Truck.where("vehicle_id = ?", v.getId());
        List<Motorcycle> listM = Motorcycle.where("vehicle_id = ?", v.getId());   
        
              
        
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("userFirstName",u.getFirstName());
        attributes.put("userFirstName",u.getLastName());
        attributes.put("price",p.getPrice());
        attributes.put("title",p.getTitle());
        attributes.put("model",v.getModel());
        attributes.put("patent",v.getPatent());
       
        
        
        if (!(listM.isEmpty())){
            attributes.put("type_motor",listM.get(0).getTypeMotor()); 
        } 
        if (!(listC.isEmpty())) {
            attributes.put("version",listC.get(0).getVersion());
    
        } 
     
        if (!(listT.isEmpty())){
            attributes.put("brake_system",listT.get(0).getbrakeSystem());
        } 
            
           
        return new ModelAndView(attributes,"postId.mustache");
        },
        new MustacheTemplateEngine()
            );

     get("/ListUser/:id/vehicles",(request,response)-> {
        User u = User.findById(request.params("id"));
        List <Vehicle> vehicles = Vehicle.where("user_id = ?", request.params("id"));
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("UserVehicles",vehicles);
        attributes.put("UserId",u.id());
        
        return new ModelAndView(attributes, "UserVehicles.moustache");
        } ,
       new MustacheTemplateEngine()
    );  
    
     
    get("ListUser/:id/vehicles/:vId", (request, response) -> {
        Vehicle v = Vehicle.findById(request.params("vId"));
        User u = User.findById(request.params("id"));
        List<Car> listC = Car.where("vehicle_id = ?", v.getId());
       
        List<Truck> listT = Truck.where("vehicle_id = ?", v.getId());
        List<Motorcycle> listM = Motorcycle.where("vehicle_id = ?", v.getId());   
        
              
        
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("userFirstName",u.getFirstName());
        attributes.put("userLastName",u.getLastName());
        attributes.put("model",v.getModel());
        attributes.put("patent",v.getPatent());
        
        if (!(listM.isEmpty())){
            attributes.put("type_motor",listM.get(0).getTypeMotor()); 
        } 
        if (!(listC.isEmpty())) {
            attributes.put("version",listC.get(0).getVersion());
    
        } 
     
        if (!(listT.isEmpty())){
            attributes.put("brake_system",listT.get(0).getbrakeSystem());
        } 
            
           
        return new ModelAndView(attributes,"VehicleId.moustache");
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
        

                
   get("/ListPost/:id", (request, response) -> {
        Post p = Post.findById(request.params("id"));
          
        Vehicle v = Vehicle.findById(p.getVehicle());
        User u = User.findById(v.getOwner());
        
        List<Car> listC = Car.where("vehicle_id = ?", v.getId());
       
        List<Truck> listT = Truck.where("vehicle_id = ?", v.getId());
        List<Motorcycle> listM = Motorcycle.where("vehicle_id = ?", v.getId());   
        
              
        
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("userFirstName",u.getFirstName());
        attributes.put("userFirstName",u.getLastName());
        attributes.put("price",p.getPrice());
        attributes.put("title",p.getTitle());
        attributes.put("model",v.getModel());
        attributes.put("patent",v.getPatent());
        
       
        
        if (!(listM.isEmpty())){
            attributes.put("type_motor",listM.get(0).getTypeMotor()); 
        } 
        if (!(listC.isEmpty())) {
            attributes.put("version",listC.get(0).getVersion());
    
        } 
     
        if (!(listT.isEmpty())){
            attributes.put("brake_system",listT.get(0).getbrakeSystem());
        } 
            
           
        return new ModelAndView(attributes,"postId.mustache");
        },
        new MustacheTemplateEngine()
            
   );
 
  get("ListUser/:id/posts/:idP/questions", (request, response) -> {
        Post p = Post.findById(request.params("id"));
        List <Question> questions = Question.where("post_id = ?", request.params("id"));
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("QuestionPosts",questions);
        
        
        return new ModelAndView(attributes, "QuestionPosts.moustache");
        } ,
       new MustacheTemplateEngine()
   );

  
   get("/ListUser/:id/posts/:idP/questions/:idQ/answerInd", (request, response) -> {
       
        Question q = Question.findById(request.params("idQ")); 
        Answer a = new Answer();
        a = Answer.findFirst("question_id=?",q.getId());
        
        
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("getText",a.getText());
        
        
        return new ModelAndView(attributes, "AnswerQuestions.moustache");
        } ,
       new MustacheTemplateEngine()
   );
 
  
  
   
   get("/ListUser/:id/delete", (request, response) -> {
            User u = User.findById(request.params("id"));
            u.deleteCascade();
            return "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
    });
  
   
   
    
   
   
   
   
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
        
        
    get("/ListVehicles/:id", (request, response) -> {
        Vehicle v = Vehicle.findById(request.params("id"));
        
        User u = User.findById(v.getOwner());
        
        List<Car> listC = Car.where("vehicle_id = ?", v.getId());
       
        List<Truck> listT = Truck.where("vehicle_id = ?", v.getId());
        List<Motorcycle> listM = Motorcycle.where("vehicle_id = ?", v.getId());   
        
              
        
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("userFirstName",u.getFirstName());
        attributes.put("userFirstName",u.getLastName());
        attributes.put("model",v.getModel());
        attributes.put("patent",v.getPatent());
       
        if (!(listM.isEmpty())){
            attributes.put("type_motor",listM.get(0).getTypeMotor()); 
        } 
        if (!(listC.isEmpty())) {
            attributes.put("version",listC.get(0).getVersion());
    
        } 
     
        if (!(listT.isEmpty())){
            attributes.put("brake_system",listT.get(0).getbrakeSystem());
        } 
            
           
        return new ModelAndView(attributes,"vehicleId.mustache");
        },
        new MustacheTemplateEngine()
            );
 
        
   get("/ListVehicles/:id/delete", (request, response) -> {
            Vehicle v = Vehicle.findById(request.params("id"));
            v.deleteCascade();
            return "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
    });
        
        
           
        
    
    
  //--------------------------------------Insert---------------------------------- 
     
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
            String retornar;
			retornar=" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+"><body bgcolor="+"#BDBDBD"+">";
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
			retornar = retornar + "Usuario Cargado Correctamente";
			retornar = retornar + "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
            return retornar;
            
         });   
                                                                                                           
         
        get ("/ListUser/:id/newCar", (request, response) -> {         
            String a ="<html> <head> <title> cargarAuto </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/ListUser/"+request.params("id")+"/car\" method=\"post\">";
            a= a + "<h1> Cargar Auto </h1> <FORM >";

			a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
			
            // crea un enlace para volver a la pag anterior.
            a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/InsertVehicle"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";
            
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
 
        
        
        post("/ListUser/:id/car", (request, response) -> {
            String retornar;
            retornar=" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+"><body bgcolor="+"#BDBDBD"+">";
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            User user2 = new User();
            user.set("id",request.params("id"));
            
            user2 = User.findFirst("id=?",user.get("id"));
            //controlar existencia de usuario en base de datos

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
            retornar =retornar +"Carga Exitosa";
           
            retornar = retornar + "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
            return retornar;
         });   
        
        
        
        
        get ("/ListUser/:id/newMotorcycle", (request, response) -> {         
            String a ="<html> <head> <title> cargarAuto </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/ListUser/"+request.params("id")+"/motorcycle\" method=\"post\">";
            a= a + "<BODY> <h1> Cargar Moto </h1> <FORM >";
            
            a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
			
            // crea un enlace para volver a la pag anterior.
            a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/InsertVehicle"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";
      
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
        
        post("/ListUser/:id/motorcycle", (request, response) -> {
            String retornar;
            retornar=" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+"><body bgcolor="+"#BDBDBD"+">";
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            User user2 = new User();
            user.set("id",request.params("id"));
            
            user2 = User.findFirst("id=?",user.get("id"));
            
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

            retornar =retornar +"Carga Exitosa";
        retornar = retornar + "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
        return retornar;
     });   
        
         
        get ("/ListUser/:id/newTruck", (request, response) -> {         
            String a ="<html> <head> <title> cargarCamion </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/ListUser/"+request.params("id")+"/truck\" method=\"post\">";
            a= a + "<h1> Cargar Camion </h1> <FORM >";

            a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";

            // crea un enlace para volver a la pag anterior.
            a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/InsertVehicle"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";

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
        
        
        post("/ListUser/:id/truck" , (request, response) -> {
            String retornar;
            retornar=" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+"><body bgcolor="+"#BDBDBD"+">";
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            User user2 = new User();
            user.set("id",request.params("id"));
            
            user2 = User.findFirst("id=?",user.get("id"));
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


            retornar =retornar +"Carga Exitosa";
            retornar = retornar + "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
            return retornar;
         });   
        


        get ("/ListUser/:id/vehicles/:idV/newPost", (request, response) -> {         
            String a ="<html> <head> <title> Create Post </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/ListUser/"+request.params("id")+"/vehicles/"+request.params("idV")+"/post\" method=\"post\">";
            a= a + "<h1> Crear Post </h1> <FORM >";
            a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";

            // crea un enlace para volver a la pag anterior.
            a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";
          
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
        
        
        post("/ListUser/:id/vehicles/:idV/post", (request, response) -> {
            String retornar;
            retornar=" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+"><body bgcolor="+"#BDBDBD"+">";
            User user = new User();
            //Cargar variable user con datos tomados por pantalla
            User user2 = new User();
            user.set("id",request.params("id"));
            
            user2 = User.findFirst("id=?",user.get("id"));
            
            Vehicle vehicle = new Vehicle();            //Cargar variable vehiculo con datos tomados por pantalla
            vehicle.set("id",request.params("idV"));
            
            //controlar existencia de usuario y de vehiculo en base de datos
            Vehicle vehicle2 = new Vehicle();
            vehicle2 = Vehicle.findFirst("id=?",vehicle.get("id"));
            

            Post post = new Post();
            post.set("title",request.queryParams("title"));
            post.set("description",request.queryParams("description"));
            post.set("price",request.queryParams("price"));
            post.saveIt();
            user2.add(post);
            vehicle2.add(post);
            retornar =retornar +"Carga Exitosa";
            retornar = retornar + "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
            return retornar;
         });
        
        
         get("/ListUser/:id/posts/:idQ/question", (request, response) -> {        
            String a ="<html> <head> <title> cargarPregunta </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/ListPost/"+request.params("id")+"/question\" method=\"post\">";
            a= a + "<h1> Cargar Pregunta </h1> <FORM >";       

            //usaurio
            //lectura email111111
            a=a+"<h3> email Usuario </h3><INPUT type=text SIZE=25 NAME=email>";
            
            //Post
            //lectura texto
            a=a+"<h3> Texto Pregunta</h3><INPUT type=text SIZE=25 NAME=textQ>";
            
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            
            
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
        
         
        
         
          
                       
         
         
        post("/ListPost/:id/question", (request, response) -> {
            String retornar;
            retornar=" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+"><body bgcolor="+"#BDBDBD"+">";
            User user = new User();
            Post post = new Post();
            
            //Cargar variable user con datos tomados por pantalla
            user.set("email",request.queryParams("email"));
            post.set ("id",request.params("id"));
            
            //controlar existencia de usuario y de post en base de datos
            User user2 = new User();
            Post post2 = new Post();
            
            user2 = User.findFirst("email=?",user.get("email"));
            
            
            post2 = post.findFirst("id=?",post.get("id"));
            
            if (user2 == null){
                retornar=retornar +"No se encontro usuario ";
            }else{
                Question question = new Question();
                question.set("textQ",request.queryParams("textQ"));
                question.saveIt();
                user2.add(question);
                post2.add(question);
                 
                
                retornar =retornar +"Carga Exitosa";
              }
            
             
		retornar = retornar + "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
            
                return retornar; 
         });
                 
                                                                                    
        get ("/ListUser/:id/posts/:idP/questions/:idQ/newAnswer", (request, response) -> {         
            String a ="<html> <head> <title> cargarRespuesta </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\" /ListUser/"+request.params("id")+"/posts/"+request.params("idP")+"/questions/"+request.params("idQ")+"/answer\" method=\"post\">";
            a= a + "<h1> Cargar Respuesta </h1> <FORM >";       

            //Usaurio
            //Lectura Email
            a=a+"<h3> Email Usuario </h3><INPUT type=text SIZE=25 NAME=email>";
            
            //Respuesta
            //lectura texto Respuesta
            a=a+"<h3> Texto Respuesta </h3><INPUT type=text SIZE=25 NAME=textA>";
            
       
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a=a+"<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
        
        post("/ListUser/:id/posts/:idP/questions/:idQ/answer", (request, response) -> {
            String retornar;
            retornar=" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+"><body bgcolor="+"#BDBDBD"+">";
            User user = new User();
            Question question = new Question();
            
            //Cargar variable user con datos tomados por pantalla
            user.set("email",request.queryParams("email"));
            question.set ("id",request.params("idQ"));
            
            //controlar existencia de usuario y de post en base de datos
            User user2 = new User();
            Question question2 = new Question();
            
            user2 = User.findFirst("email=?",user.get("email"));
            
            
            question2 = question.findFirst("id=?",question.get("id"));
            
            if (user2 == null){
                retornar=retornar +"No se encontro usuario ";
            }else{
                Answer answer = new Answer();
                answer.set("textA",request.queryParams("textA"));
                question2.saveIt();
                answer.saveIt();
                question2.add(answer);
                retornar =retornar +"Carga Exitosa";
              }
            
             
		retornar = retornar + "<a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a>";
            
                return retornar; 
         });
        
    /*----------------------------------------------BUSQUEDAS----------------------------------------------------*/    
        get ("/Search/User", (request, response) -> {         
            String a ="<html> <head> <title> Busqueda </title> </head> <body bgcolor="+"#BDBDBD"+"> <form action=\"/Search\" method=\"post\">";
            a= a + "<h1> Buscar Usuario </h1> <FORM >";

            a= a +" <body link="+"#4000FF"+"><body alink="+"#4000FF"+"><body vlink="+"#4000FF"+">";
            // crea un enlace para volver a la pag anterior.
            a= a + "<table align="+"right"+"><td> <a href="+"http://localhost:4567/app"+"><h3 style="+"color:#0000FF"+"> Volver </h3></a></td></table>";
            
            //Usuario
            //lectura 
            a=a+"<h3> Email  </h3><INPUT type=text SIZE=25 NAME=firstName>";
                      
            a= a + "<h3><td align=right valign=top></td><td align=center>";
            //creacion de botones
            a = a + "<input type=reset value=Borrar_informacion><input type=submit value= Enviar></FORM></BODY></HTML>";
            return a; 	
        });
        
        post("/Search",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Client client = node.client();
  
            SearchResponse response_elastic = client.prepareSearch("users")
                                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                        .setQuery(QueryBuilders.matchQuery("name",request.queryParams("firstName")))
                                        .execute()
                                        .actionGet();

            SearchHit[] docs = response_elastic.getHits().getHits();

            node.close();

          
            Map<String,Object> map = new HashMap<String,Object>();
            List<Map<String,Object>> lista = new LinkedList<Map<String,Object>>();
            for (SearchHit sh : docs) {
                map = sh.getSource();
                lista.add(map);

            } 
            long cant = response_elastic.getHits().getTotalHits();

            attributes.put("list",lista);
            attributes.put("cantidad",cant);
            
            return new ModelAndView(attributes,"SearchUser.moustache");
        },
            new MustacheTemplateEngine()
       
     );

        
     get("/cerrar", (request, response) -> {
            node.close();
            return "servidor cerrado!";
        });    
        
        
        //cierra la base de datos
        after((request, response) -> {
            Base.close();    
        });
        
        
    }
    
    
    
   
}
