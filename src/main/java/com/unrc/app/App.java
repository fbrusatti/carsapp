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
import spark.Session;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

public class App 
{
    public static final Node node = org.elasticsearch.node
                                        .NodeBuilder
                                        .nodeBuilder()
                                        .clusterName("carsapp")
                                        .local(true)
                                        .node();
    public static Client client(){
    return node.client();
    }

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

	public static void main( String[] args )
    {
        System.out.println( "Hello cruel World!" );
        

        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        });


        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        User admin = new User();
        admin.createUser("admin","admin","admin@carsapp.com", "admin");
        Base.close();

        get("/login",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "login.mustache");
        },
         new MustacheTemplateEngine()
        );

        get("/users",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<User> users = User.findAll();
            attributes.put("users_count", users.size());
            attributes.put("users", users);   
            return new ModelAndView(attributes, "users.mustache");
        },
         new MustacheTemplateEngine()
       );

        get("/users/:id",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            User userSelected = User.findById(request.params("id"));
            attributes.put("user_id", userSelected);
            return new ModelAndView(attributes, "userId.mustache");
        },
         new MustacheTemplateEngine()
        );  

        //Lista todos los vehiculos
        get("/vehicles",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            List<Vehicle> vehicles = Vehicle.findAll();
            attributes.put("vehicles_count", vehicles.size());
            attributes.put("vehicles", vehicles);   
            return new ModelAndView(attributes, "vehicles.mustache");
        },
         new MustacheTemplateEngine()
        );

        //Lista un vehiculo particular
        get("/vehicles/:patent",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Vehicle vehicleSelected = Vehicle.findByPatent(request.params("patent"));
            attributes.put("vehicle_id", vehicleSelected);
            return new ModelAndView(attributes, "vehicleId.mustache");
        },
         new MustacheTemplateEngine()
        );

        //Lista todos los autos
        get("/cars",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            List<Car> cars = Car.findAll();
            attributes.put("cars_count", cars.size());
            attributes.put("cars", cars);   
            return new ModelAndView(attributes, "cars.mustache");
        },
         new MustacheTemplateEngine()
        );   

        //Lista un auto particular
        get("/cars/:patent",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Car carSelected = Car.findByCar(request.params("patent"));
            attributes.put("car_id", carSelected);
            return new ModelAndView(attributes, "carId.mustache");
        },
         new MustacheTemplateEngine()
        );


        //Lista todas las motocicletas
        get("/motorcycles",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            List<Motorcycle> motorcycles = Motorcycle.findAll();
            attributes.put("motorcycles_count", motorcycles.size());
            attributes.put("motorcycles", motorcycles);   
            return new ModelAndView(attributes, "motorcycles.mustache");
        },
         new MustacheTemplateEngine()
        );       

        //Lista una motocicleta en particular
        get("/motorcycles/:patent",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Motorcycle motorcycleSelected = Motorcycle.findByMotorcycle(request.params("patent"));
            attributes.put("motorcycles_id", motorcycleSelected);
            return new ModelAndView(attributes, "motorcycleId.mustache");
        },
         new MustacheTemplateEngine()
        );

        //Lista todos los Trucks
        get("/trucks",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            List<Truck> trucks = Truck.findAll();
            attributes.put("trucks_count", trucks.size());
            attributes.put("trucks", trucks);   
            return new ModelAndView(attributes, "trucks.mustache");
        },
         new MustacheTemplateEngine()
        );       

        //Lista un truck particular
        get("/trucks/:patent",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Truck truckSelected = Truck.findByTruck(request.params("patent"));
            attributes.put("trucks_id", truckSelected);
            return new ModelAndView(attributes, "truckId.mustache");
        },
         new MustacheTemplateEngine()
        );

        get("/posts",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<Post> posts = Post.findAll();
            attributes.put("posts_count", posts.size());
            attributes.put("posts", posts);   
            return new ModelAndView(attributes, "posts.mustache");
        },
         new MustacheTemplateEngine()
        );      

        //Lista un post particular
        get("/posts/:id",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Post postsSelect = Post.findById(request.params("id"));
            attributes.put("posts_id",postsSelect);
            return new ModelAndView(attributes,"posts_id.mustache");            
        },
            new MustacheTemplateEngine()
        );    

        get("/questions",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<Question> questions = Question.findAll();
            attributes.put("questions_count", questions.size());
            attributes.put("questions", questions);   
            return new ModelAndView(attributes, "questions.mustache");
        },
         new MustacheTemplateEngine()
        );  


        //Lista una question particular
        get("/questions/:id",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Question questionsSelect = Question.findById(request.params("id"));
            attributes.put("questions_id",questionsSelect);
            return new ModelAndView(attributes,"questions_id.mustache");            
        },
            new MustacheTemplateEngine()
        );   
       
        //List all answers
        get("/answers", (request,response) ->{
            Map<String, Object> attributes = new HashMap<>();
            List<Answer> answers = Answer.findAll();
            attributes.put("answers_count", answers.size());
            attributes.put("answers", answers);   
            return new ModelAndView(attributes, "answers.mustache");
        },
         new MustacheTemplateEngine()
        );       

        //List a specific answer
        get("/answers/:id", (request,responser)->{
            Map<String, Object> attributes = new HashMap<>();
            Answer answerSelected = Answer.findById(request.params("id"));
            attributes.put("answers_id", answerSelected);
            return new ModelAndView(attributes, "answerId.mustache");
        },
         new MustacheTemplateEngine()
        );

	    get("/rates",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<Rate> rates = Rate.findAll();
            attributes.put("rates_count", rates.size());
            attributes.put("rates", rates);   
            return new ModelAndView(attributes, "rates.mustache");
        },
         new MustacheTemplateEngine()
        );
        
         //Lista un post particular
        get("/rates/:id",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Rate rateSelect = Rate.findById(request.params("id"));
            attributes.put("rates_id",rateSelect);
            return new ModelAndView(attributes,"rates_id.mustache");            
        },
            new MustacheTemplateEngine()
        );     

        //List all Punctuations
        get("/punctuations",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            List<Punctuation> punctuations = Punctuation.findAll();
            attributes.put("punctuations_count", punctuations.size());
            attributes.put("punctuations", punctuations);   
            return new ModelAndView(attributes, "punctuations.mustache");
        },
         new MustacheTemplateEngine()
        );             

        //List a specific Punctuation
        get("/punctuations/:id",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Punctuation punctuationSelected = Punctuation.findById(request.params("id"));
            attributes.put("punctuations_id", punctuationSelected);
            return new ModelAndView(attributes, "punctuationsId.mustache");
        },
         new MustacheTemplateEngine()
        );

        //List all addresses
        get("/addresses", (request, response)->{
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            Map<String, Object> attributes = new HashMap<>();
            List<Address> addresses = Address.findAll();
            attributes.put("addresses_count", addresses.size());
            attributes.put("addresses", addresses);   
            return new ModelAndView(attributes, "addresses.mustache");
        },
         new MustacheTemplateEngine()
        );  

        //List a specific address
        get("/addresses/:id",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            Address addressSelected = Address.findById(request.params("id"));
            attributes.put("addresses_id", addressSelected);
            return new ModelAndView(attributes, "addressId.mustache");
        },
         new MustacheTemplateEngine()
        );

        /**INSERCIONES**/
        get("/newUser",(request,response)->{
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "newUser.mustache");
        },
            new MustacheTemplateEngine()
        );

        post ("/search",(request, response) ->{
            String query = new String();
            if(request.queryParams("query") == null){
                response.redirect("/");
            }else{
                query = request.queryParams("query");
            }
            SearchResponse res = client().prepareSearch("posts")
                            .setQuery(QueryBuilders.matchQuery("title", query))
                            .execute()
                            .actionGet();

            List<Map<String,Object>> queryResult = new LinkedList<Map<String,Object>>();
            SearchHit[] hits = res.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String,Object> result = hit.getSource(); 
                queryResult.add(result);
            }

            Map<String,Object> attributes = new HashMap<String,Object>();
            
            attributes.put("results",false);
            if (!queryResult.isEmpty()) { 
                attributes.put("results",true); 
                attributes.put("print",queryResult);
            }
            return new ModelAndView(attributes,"search.mustache");
        },
            new MustacheTemplateEngine()
        );

        
        //Insert an User
        post ("/users",(request, response) ->{
            String name = request.queryParams("first_name");
            String lastname = request.queryParams("last_name");
            String email = request.queryParams("email");
            String pass = request.queryParams("passs");
            User nuevoUser= new User();
            nuevoUser.createUser(name,lastname,email,pass);
            response.redirect("/users");
            return "success";
        });

        //Insert an address
        get("/newAddress",(request,response)->{
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
            }            
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
        });  

        
        post ("/addresses",(request, response) ->{
            String email = request.queryParams("userEmail");
            String calle = request.queryParams("street");
            String numero = request.queryParams("num");
            String ciudad = request.queryParams("city");
            Address nuevoAddress = new Address();
            User u = User.findFirst("email = ?",email);
            int num = Integer.parseInt(numero); 
            nuevoAddress.createAddress(calle, num, ciudad, u);
            response.redirect("/addresses");
            return "success";
        });

        get("/newVehicle",(request,response)->{
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
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
        }); 

        post ("/vehicles",(request, response) ->{
            String email = request.queryParams("userEmail");
            String patente = request.queryParams("patent");
            String modelo = request.queryParams("model");
            String marca = request.queryParams("brand");
            String tipo = request.queryParams("type");
            Vehicle nuevoVehiculo = new Vehicle();
            User u = User.findFirst("email = ?",email);
            nuevoVehiculo.createVehicle(patente, modelo, marca, u);
            if (request.queryParams("type").charAt(0)=='1') {
                Car c = new Car();
                Boolean esCoupe = Boolean.parseBoolean(request.queryParams("iscoupe"));
                String p = nuevoVehiculo.getString("patent");
                c.set("id_vehicle",patente);
                c.set("is_coupe",esCoupe);
                c.saveIt();
            }
            if (request.queryParams("type").charAt(0)=='2') {
                Motorcycle m = new Motorcycle();
                Integer cilindrada = Integer.parseInt(request.queryParams("engine_size"));
                Integer rodado = Integer.parseInt(request.queryParams("wheel_size"));
                m.set("id_vehicle",patente);
                m.set("engine_size",cilindrada);
                m.set("wheel_size",rodado);
                m.saveIt();
            }
            if (request.queryParams("type").charAt(0)=='3') {
                Truck t = new Truck();
                Integer cinturones = Integer.parseInt(request.queryParams("count_belt"));
                String p = nuevoVehiculo.getString("patent");
                t.set("id_vehicle",patente);
                t.set("count_belt",cinturones);
                t.saveIt();
            }            
            response.redirect("/vehicles");
            return "success";
        });
        
        get("/newPost",(request,response)->{
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            String form = "<form action= \"/posts \" method= \"post\">";
            form+="<center><h1>Crear nueva publicación</h1></center>";
            form+="Email usuario : ";
            //combobox para seleccionar el usuario al cual corresponde el post a publicar
            form+="<select name=\"userEmail\">";
            List<User> userList = User.findAll();
            for (User u: userList) {
                String mail = u.getString("email");
                form+="<option value =\""+mail+"\">";            
                form+=" "+u.getString("email");
            };
            form+="</select><br><br>";
            //fin combobox   
            form+="Vehiculo a publicar : ";
            //combobox para seleccionar el vehiculo que se quiere publicar
            form+="<select name=\"patent\">";
            List<Vehicle> vehicleList = Vehicle.findAll();
            for (Vehicle u: vehicleList) {
                String patente = u.getString("patent");
                form+="<option value =\""+patente+"\">";            
                form+=" "+u.getString("patent");
            };                    
            form+="</select><br><br>";
            //fin combobox
            form+="Titulo : ";
            form+="<input type= \"text\" name=\"titulo\" placeholder=\"Titulo\"><br><br>";
            form+="Descripción : <br>";
            form+="<textarea name=\"descripcion\" placeholder=\"Contenido del post\" rows=\"20\" cols=\"60\">";
            form+="</textarea><br>";
            form+="<input type= \"submit\" value = \"Submit\">";
            form+="</form>";
            return form;
        }); 

        post ("/posts",(request, response) ->{
            String mail = request.queryParams("userEmail");
            String licensePlate = request.queryParams("patent");
            String titulo = request.queryParams("titulo");
            String descripcion = request.queryParams("descripcion");     
            Post post = Post.createPost(titulo,descripcion,User.findByEmail(mail),Vehicle.findByPatent(licensePlate));
            response.redirect("/posts");
            return "success";
        });

        get("/newQuestion",(request,response)->{
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            String form = "<form action= \"/questions \" method= \"post\">";
            form+="<center><h1>Realizar pregunta</h1></center>";
            form+="Quien pregunta? : ";
            //combobox para seleccionar el usuario al cual corresponde el post a publicar
            form+="<select name=\"userEmail\">";
            List<User> userList = User.findAll();
            for (User u: userList) {
                String mail = u.getString("email");
                form+="<option value =\""+mail+"\">";            
                form+=" "+u.getString("email");
            };
            form+="</select><br><br>";
            //fin combobox   
            form+="Post : ";
            //combobox para seleccionar el post al cual se le quiere realizar la pregunta
            form+="<select name=\"postTitle\">";
            List<Post> postList = Post.findAll();
            for (Post p: postList) {
                String titulo = p.getString("title");
                form+="<option value =\""+titulo+"\">";            
                form+=" "+p.getString("title");
            };
            form+="</select><br><br>";
            //fin combobox   
            form+="Pregunta : <br>";
            form+="<textarea name=\"descripcion\" placeholder=\"Escribe aqui tu pregunta\" rows=\"20\" cols=\"60\">";        
            form+="</textarea><br>";    
            form+="<input type= \"submit\" value = \"Submit\">";
            form+="</form>";
            return form;
        }); 

        post ("/questions",(request, response) ->{
            String mail = request.queryParams("userEmail");
            String post = request.queryParams("postTitle");
            String descripcion = request.queryParams("descripcion");     
            Question pregunta = Question.createQuestion(descripcion,User.findByEmail(mail),Post.findByTitle(post));
            response.redirect("/questions");
            return "success";
        });


        get("/newAnswer",(request,response)->{
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            String form = "<form action= \"/answers \" method= \"post\">";
            form+="<center><h1>Responder pregunta</h1></center>";
            form+="Quien responde? : ";
            //combobox para seleccionar el usuario al cual corresponde el post a publicar
            form+="<select name=\"userEmail\">";
            List<User> userList = User.findAll();
            for (User u: userList) {
                String mail = u.getString("email");
                form+="<option value =\""+mail+"\">";            
                form+=" "+u.getString("email");
            };
            form+="</select><br><br>";
            //fin combobox   
            form+="Pregunta : ";
            //combobox para seleccionar la pregunta a la cual se quiere responder
            form+="<select name=\"questionTitle\">";
            List<Question> questionList = Question.findAll();
            for (Question q: questionList) {
                String descripcion = q.getString("description");
                form+="<option value =\""+descripcion+"\">";            
                form+=" "+q.getString("description");
            };
            form+="</select><br><br>";
            //fin combobox   
            form+="Respuesta : <br>";
            form+="<textarea name=\"descripcion\" placeholder=\"Escribe aqui tu respuesta\" rows=\"20\" cols=\"60\">";    
            form+="</textarea><br>";            
            form+="<input type= \"submit\" value = \"Submit\">";
            form+="</form>";
            return form;
        }); 

        post ("/answers",(request, response) ->{
            String mail = request.queryParams("userEmail");
            String question = request.queryParams("questionTitle");
            String respuesta = request.queryParams("descripcion");     
            Answer resp = Answer.createAnswer(respuesta,User.findByEmail(mail),Question.findByDescription(question));
            response.redirect("/answers");
            return "success";
        });

        /**INSERCIONES**/
        get("/",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "invitado.mustache");
        },
            new MustacheTemplateEngine()
        );

        get("/home",(request, response) -> {
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            Map<String, Object> attributes = new HashMap<>();
            //List<User> users = User.findAll();
            //attributes.put("users_count", users.size());
            //attributes.put("users", users);   
            return new ModelAndView(attributes, "hello.mustache");
        },
         new MustacheTemplateEngine()
        );

        get("/homeAdmin",(request, response) -> {
            Session session = existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "admin.mustache");
        },
         new MustacheTemplateEngine()
        );

        get ("/authentication",(request, response) ->{
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

        });
            
     

        get("/logout", (request, response) -> {
            String res = "";
            Session session = existsSession(request);
            if (null == session) {
                res += "No hay ninguna sesion activa.";
            } else {
                session.invalidate();
                response.redirect("/"); 
            }
            return res;
        });

        after((request, response) -> {
            Base.close();    
        });
    }

    public static void close(){
        node.close();
    }
}
