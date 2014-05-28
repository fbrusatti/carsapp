package com.unrc.app;

import org.javalite.activejdbc.Base;

import com.unrc.app.models.*;


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
        /*
        User user = new User();
        String nombre="gustavo";
        String apellido="martinez";
        String mail="mfwebdesign@gmail.com";
        User.createUser(nombre, apellido, mail);
        */
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
        Vehicle vehicle3 = Vehicle.createVehicle("asd456","nija","kawasaki", User.findByEmail("mfwebdesign@gmail.com"));
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


        get("/hello", (request, response) -> {
            return "Hello world";
        });

        /*get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });*/
    }
}
