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
import static org.elasticsearch.node.NodeBuilder.*;
import static org.elasticsearch.common.xcontent.XContentFactory.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello cruel World!" );

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        User user = User.createUser ("gustavo", "martinez", "mfwebdesign@gmail.com");
        User user2 = User.createUser("adrian", "tissera", "adriantissera@gmail.com");
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");      
        Address address1 = Address.createAddress("Lincoln", 874,"Venado Tuerto", User.findByEmail("mfwebdesign@gmail.com"));
        Address address2 = Address.createAddress("Sobremonte", 547,"Rio Cuarto", user2);
        Address address3 = Address.createAddress("Chiclana", 4686,"Rio Cuarto", user2);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Vehicle vehicle1 = Vehicle.createVehicle("ghg345","corsa","chevrolet", User.findByEmail("adriantissera@gmail.com"));
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Car car1 = Car.createCar(true, vehicle1);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Post post1 = Post.createPost("Vendo chevrolet corsa", "chevrolet corsa casi nuevo", user2, vehicle1);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Vehicle vehicle2 = Vehicle.createVehicle("qwe123","modeloChata","chevrolet", User.findByEmail("mfwebdesign@gmail.com"));
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Truck truck1 = Truck.createTruck(5, vehicle2);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Post post3 = Post.createPost("vendo chata", "chata chevrolet casi nueva sin parabrisa", user, vehicle2);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Vehicle vehicle3 = Vehicle.createVehicle("asd456","ninja","kawasaki", User.findByEmail("mfwebdesign@gmail.com"));
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Motorcycle motorcycle1 = Motorcycle.createMotorcycle(24, 600, vehicle3);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Post post2 = Post.createPost("vendo moto", "kawasaki ninja para repuestos", user, vehicle3);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Question question1 = Question.createQuestion("a cuanto me vendes la rueda de auxilio?", user, post1);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Answer answer1= Answer.createAnswer("a veinte pe!" , user2, question1);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Question question2 = Question.createQuestion("a cuanto la cadena mosstro?", user2, post2);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Rate rate1 = Rate.createRate(5, post1, user2);
        Base.close();

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Punctuation punctuation1 = Punctuation.createPunctuation(5, post2, user2);
        Base.close();


        get("/hello",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "hello.mustache");
        },
         new MustacheTemplateEngine()
        );

        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        }); 

        get("/users",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<User> users = User.findAll();
            attributes.put("users_count", users.size());
            attributes.put("users", users);   
            return new ModelAndView(attributes, "users.mustache");
        },
         new MustacheTemplateEngine()
        );

        //Lista un usuario particular
        get("/users/:id",(request,response)->{
            User userRequested = User.findById(request.params(":id"));
            if(userRequested!=null){
                String name = userRequested.getString("first_name")+" "+userRequested.getString("last_name");
                String email = userRequested.getString("email");
                return "Nombre: "+name+" Email: "+email;
            }
            else{
                return "Usuario no encontrado!";
            }            
        });


        //Lista todos los vehiculos
        get("/vehicles",(request,response)->{
            List<Vehicle> vehiclesList = Vehicle.findAll();
            String list = new String();
            for(Vehicle v: vehiclesList){
                list = list+"Patente: "+v.getString("patent")+" "+v.getString("model")+" "+v.getString("brand")+"<br>";
            }
            return list;
        });

        //Lista un vehiculo particular
        get("/vehicles/:patent",(request,response)->{
            Vehicle vehicleRequested = Vehicle.findByPatent(request.params(":patent"));
            if(vehicleRequested!=null){
                String patente = vehicleRequested.getString("patent");
                String marca = vehicleRequested.getString("brand");
                String modelo = vehicleRequested.getString("model");
                return "Chapa patente: "+patente+". "+"Marca: "+marca+". "+"Modelo: "+modelo;
            }
            else{
                return "Vehiculo no encontrado!";
            }            
        });

        //Lista todos los autos
        get("/cars",(request,response)->{
            List<Car> carsList = Car.findAll();
            String list = new String();
            for(Car i: carsList){
                list = list+"Patente: "+i.getString("id_vehicle")+" "+i.getBoolean("is_coupe")+"<br>";
            }
            return list;
        });    

        //Lista un auto particular
        get("/cars/:patent",(request,response)->{
            Car carRequested = Car.findByCar(request.params(":patent"));
            if(carRequested!=null){
                String patente = carRequested.getString("id_vehicle");
                Boolean coupe = carRequested.getBoolean("is_coupe");
                return "Chapa patente: " + patente + ". "+"Coupe? " + coupe;
            }
            else{
                return "Vehiculo no encontrado!";
            }            
        });

        //Lista todas las motocicletas
        get("/motorcycles",(request,response)->{
            List<Motorcycle> motorcyclesList = Motorcycle.findAll();
            String list = new String();
            for(Motorcycle i: motorcyclesList){
                list = list+"Patente: "+i.getString("id_vehicle")+". Rodado: "+i.getInteger("wheel_size")+". Cilindrada: "+i.getInteger("engine_size")+"<br>";
            }
            return list;
        });    

        //Lista una motocicleta en particular
        get("/motorcycles/:patent",(request,response)->{
            Motorcycle motorcycleRequested = Motorcycle.findByMotorcycle(request.params(":patent"));
            if(motorcycleRequested!=null){
                String patente = motorcycleRequested.getString("id_vehicle");
                Integer rodado = motorcycleRequested.getInteger("wheel_size");
                Integer cilindrada = motorcycleRequested.getInteger("engine_size");
                return "Chapa patente: " + patente + ". "+"Rodado: " + rodado + ". "+"Cilindrada " + cilindrada;
            }
            else{
                return "Vehiculo no encontrado!";
            }            
        });

        //Lista todos los Trucks
        get("/trucks",(request,response)->{
        List<Truck> trucksList = Truck.findAll();
        String list = new String();
        for(Truck i: trucksList){
            list = list+"Patente: "+i.getString("id_vehicle")+". n° cinturones: "+i.getInteger("count_belt")+"<br>";
        }
        return list;
        });

        //Lista un truck particular
        get("/trucks/:patent",(request,response)->{
            Truck truckRequested = Truck.findByTruck(request.params(":patent"));
            if(truckRequested!=null){
                String patente = truckRequested.getString("id_vehicle");
                Integer cinturones = truckRequested.getInteger("count_belt");
                return "Chapa patente: " + patente + ". "+"n° cinturones " + cinturones;
            }
            else{
                return "Vehiculo no encontrado!";
            }            
        });

        //List all posts
        get("/posts",(request,response)->{
            List<Post> postsList = Post.findAll();
            String list = new String();
            for(Post p: postsList){
                list = list+"Titulo: "+p.getString("title")+". "+"Descripcion: "+" "+p.getString("description")+"<br>";
            }
            return list;
        });        

        //Lista un post particular
        get("/posts/:id",(request,response)->{
            Post postRequested = Post.findById(request.params(":id"));
            if(postRequested!=null){
                String titulo = postRequested.getString("title");
                String descripcion = postRequested.getString("description");
                return "Titulo: "+titulo+". "+"Descripcion: "+descripcion;
            }
            else{
                return "Post no encontrado!";
            }            
        });    

        //List all questions
        get("/questions",(request,response)->{
            List<Question> questionsList = Question.findAll();
            String list = new String();
            for(Question q: questionsList){
                list = list+"Pregunta: "+q.getString("description")+". "+"<br>";
            }
            return list;
        });    

        //List a specific question
        get("/questions/:id",(request,response)->{
            Question questionRequested = Question.findById(request.params(":id"));
            if(questionRequested!=null){
                String pregunta = questionRequested.getString("description");
                return "Descripcion: "+pregunta+".";
            }
            else{
                return "Pregunta no encontrado!";
            }            
        });  
       
        //List all answers
        get("/answers", (request,response) ->{
            List<Answer> answerList = Answer.findAll();
            String list = new String();
            for(Answer a : answerList){
                list = list+"Respuesta: "+a.getString("description")+". "+"<br>";
            }
            return list;
        });  

        //List a specific answer
        get("/answers/:id", (request,responser)->{
            Answer answerRequested = Answer.findById(request.params(":id"));
            if(answerRequested!=null){
                String respuesta = answerRequested.getString("description");
                return "Descripcion: "+respuesta+".";
            }
            else{
                return "Respuesta no encontrada!";
            }
        });

        //List all rates
        get("/rates", (request,response) ->{
            List<Rate> rateList = Rate.findAll();
            String list = new String();
            for(Rate a : rateList){
                list = list+"N° de puntuacion: "+a.getInteger("id")+". "+"puntos: "+a.getInteger("stars")+". "+"N° post: "+a.getInteger("id_post")+"."+"N° usuario: "+a.getInteger("id_user")+"."+"<br>";
            }
            return list;
        });   

        //Lista una puntuacion particular
        get("/rates/:id",(request,response)->{
            Rate rateRequested = Rate.findById(request.params(":id"));
            if(rateRequested!=null){
                int points = rateRequested.getInteger("stars");
                return "Cantidad de puntos: "+points+".";
            }
            else{
                return "Puntuacion no encontrada!";
            }            
        });                 

        //List all Punctuations
        get("/punctuations",(request,response)->{
            List<Punctuation> punctuationsList = Punctuation.findAll();
            String list = new String();
            for(Punctuation p: punctuationsList){
                list = list+"Puntuación: "+p.getInteger("point_u")+" "+"Entregada por "+(User.findById(p.getInteger("id_user"))).getString("first_name")+" a "+(User.findById(p.getInteger("id_user_receiver"))).getString("first_name")+"<br>";
            }
            return list;
        });        

        //List a specific Punctuation
        get("/punctuations/:id",(request,response)->{
            Punctuation punctuationRequested = Punctuation.findById(request.params(":id"));
            if(punctuationRequested!=null){
                String puntuacion = punctuationRequested.getString("point_u");
                int usuarioPuntuado = punctuationRequested.getInteger("id_user_receiver");
                int usuarioPuntuador = punctuationRequested.getInteger("id_user");
                return "Puntaje: "+puntuacion+". "+"Entregado por "+(User.findById(punctuationRequested.getInteger("id_user"))).getString("first_name")+" a "+(User.findById(punctuationRequested.getInteger("id_user_receiver"))).getString("first_name");
            }
            else{
                return "Pregunta no encontrada!";
            }            
        }); 

        //List all addresses
        get("/addresses", (request, response)->{
            List<Address> addressesList = Address.findAll();
            String list = new String();
            for(Address a : addressesList){
                list = list+" <br> "+" Calle: "+a.getString("street")+" "+" Numero "+a.getInteger("num")+"Ciudad "+a.getString("city");
            }
            return list;
        });

        //List a specific address
        get("/addresses/:id",(request,response)->{
            Address addressRequested = Address.findById(request.params(":id")) ;
            if(addressRequested!=null){
                String direccion = addressRequested.getString("street");
                int numero = addressRequested.getInteger("num");
                return "Calle: "+direccion+". "+"Numero: "+numero;
            }
            else{
                return "Direccion no encontrada!";
            }            
        }); 

        /**INSERCIONES**/
        get("/newUser",(request,response)->{
            String form = "<form action= \"/users \" method= \"post\">";
            form+="First name : ";
            form+="<input type = \"text\" name=\"first_name\"> <br>";
            form+="Last Name: ";
            form+="<input type = \"text\" name=\"last_name\"> <br>";
            form+="Email: ";
            form+="<input type = \"text\" name=\"email\"> <br>";            
            form+="<input type= \"submit\" value = \"Submit\">";
            form+="</form>";
            return form;
        });      
     
     
        //Insert an User
        post ("/users",(request, response) ->{
            String name = request.queryParams("first_name");
            String lastname = request.queryParams("last_name");
            String email = request.queryParams("email");
            User nuevoUser= new User();nuevoUser.createUser(name,lastname,email);
            response.redirect("/users");
            return "success";
        });


        get("/newAddress",(request,response)->{
            String form = "<form action= \"/addresses \" method= \"post\">";
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
            String form = "<form action= \"/vehicles \" method= \"post\">";
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
            String form = "<form action= \"/posts \" method= \"post\">";
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
            String form = "<form action= \"/questions \" method= \"post\">";
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
            String form = "<form action= \"/answers \" method= \"post\">";
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
            String respuesta = request.queryParams("respuesta");     
            Answer resp = Answer.createAnswer(respuesta,User.findByEmail(mail),Question.findByDescription(question));
            response.redirect("/answers");
            return "success";
        });


        after((request, response) -> {
            Base.close();    
        });


    }
}