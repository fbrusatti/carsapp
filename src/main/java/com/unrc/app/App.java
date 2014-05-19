package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.User;
import com.unrc.app.models.Post;
import com.unrc.app.models.Answer;
import com.unrc.app.models.Question;
import com.unrc.app.models.Car;
import com.unrc.app.models.City;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello cruel World!" );

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        
        City city = new City();
        city.set("name","La Carlota");
        
        
        User user = new User();
        user.set("first_name", "Marilyn");
        user.set("last_name", "Mollea");
        user.set("email","mmollea@gmail.com");
        user.add(city);
        // user.set("dob", "1935-12-06");
        user.saveIt();

        User user2 = User.createIt("first_name", "Marcelo", "last_name", "Uva","email","marcelitouva@gmail.com");
        
        Car car = new Car(); 
        Vehicle vehicle = new Vehicle();
        vehicle.set("model","Corsa");
        vehicle.set("color","negro");
        vehicle.set("km","1000");
        vehicle.set("mark","Chevrolet");
        vehicle.set("year","2006");
        car.set("doors","3","transmission","Manual","direction","Hidraulica");
        user.add(vehicle);
        vehicle.add(car);
        user.saveIt();
        vehicle.saveIt();
        car.saveIt();
        
        Post post = new Post();
        post.set("title","Vendo Corsa 2006");
        post.set("description","bastante usado");
        post.set("price","30000");
        user.add(post);
        post.saveIt();
        user.saveIt();
        
        Question question = new Question();
        question.set("text","tiene stereo? Gracias");
        post.add(question);
        post.add(user2);
        question.saveIt();
        post.saveIt();
        
        Answer answer = new Answer();
        answer.set("text","No");
        question.add(answer);
        answer.saveIt();
        question.saveIt();

        Base.close();
    }
}
