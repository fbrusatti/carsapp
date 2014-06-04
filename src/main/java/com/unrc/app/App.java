package com.unrc.app;

import org.javalite.activejdbc.Base;

import com.unrc.app.models.*;

import java.util.List;

import static spark.Spark.*;
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
        Address address1 = Address.createAddress("Lincoln", 874, User.findByEmail("mfwebdesign@gmail.com"));
        Address address2 = Address.createAddress("Sobremonte", 547, user2);
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


        get("/index.html", (request, response) -> {
            return "Hello world";
        });
      

        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        }); 

        //Lista todo los users
        get("/users",(request,response)->{
            List<User> userList = User.findAll();
            String list = new String();
            for (User u: userList) {
                list = list+u.getString("id")+" "+u.getString("first_name")+" "+u.getString("last_name")+" "+u.getString("email")+"<br>";
            }
            return list;
        });

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
        //List all vehicles
        get("/vehicles",(request,response)->{
            List<Vehicle> vehiclesList = Vehicle.findAll();
            String list = new String();
            for(Vehicle v: vehiclesList){
                list = list+v.getString("patent")+" "+v.getString("model")+" "+v.getString("brand")+"<br>";
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
                list = list+" || "+" Calle: "+a.getString("street")+" "+" Numero "+a.getInteger("num")+" "+" Departamento "+a.getString("apartment")+" "+"Ciudad "+a.getString("city");
            }
            return list;
        });

        /**INSERCIONES**/
     
        get("/newUser",(request,response)->{
            String form = "<form action= \"/users \" method= \"post\">";
            form+="First name : ";
            form+="<input type = \"text\" name=\"first_name\">";
            form+="Last Name: ";
            form+="<input type = \"text\" name=\"last_name\">";
            form+="Email: ";
            form+="<input type = \"text\" name=\"email\">";            
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
            form+="Calle : ";
            form+="<input type = \"text\" name=\"street\">";
            form+="Numero: ";
            form+="<input type = \"number\" name=\"num\">";
            form+="Departamento: ";
            form+="<input type = \"text\" name=\"apartment\">";  
            form+="Ciudad: ";
            form+="<input type \"text\" name=\"city\">";          
            form+="<input type= \"submit\" value = \"Submit\">";
            form+="</form>";
            return form;
        });      
        /*
        //Insert an Address
        post ("/addresses",(request, response) ->{ 
            String calle = request.queryParams("street");
            String numero = request.queryParams("num");
            String departamento = request.queryParams("apartment");
            String ciudad = request.queryParams("city");
            //User newUser = request.queryParams(":id");
            Address nuevoAddress=new Address();
            //nuevoAddress.createAddress(calle, numero, ciudad, departamento, newUser);
            response.redirect("/addresses");
            return "success"; 
        }); 
        */
        post ("/addresses",(request, response) ->{
            String email = request.queryParams("email");
            String calle = request.queryParams("street");
            String numero = request.queryParams("num");
            Address nuevoAddress = new Address();
            User u = User.findFirst("email = ?",email);
            int num = Integer.parseInt(numero); 
            nuevoAddress.createAddress(calle, num, u);
            response.redirect("/addresses");
            return "success";
        });

        after((request, response) -> {
            Base.close();    
        });
    }
}
