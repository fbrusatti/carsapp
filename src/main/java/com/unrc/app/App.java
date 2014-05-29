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

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        Punctuation punctuation1 = Punctuation.createPunctuation(5, post2, user2);
        Base.close();


        get("/hello", (request, response) -> {
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

        get("/questions",(request,response)->{
            List<Question> questionsList = Question.findAll();
            String list = new String();
            for(Question q: questionsList){
                list = list+"Pregunta: "+q.getString("description")+". "+"<br>";
            }
            return list;
        });    

        //Lista una pregunta particular
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


        after((request, response) -> {
            Base.close();    
        });
    }
}
