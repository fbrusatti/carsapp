package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Truck;
import com.unrc.app.models.User;
import com.unrc.app.models.Post;
import com.unrc.app.models.Answer;
import com.unrc.app.models.Question;
import com.unrc.app.models.Car;
import com.unrc.app.models.Address;
//import static spark.Spark.*;


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
               
    }
}
