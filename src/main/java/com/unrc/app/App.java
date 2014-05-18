package com.unrc.app;

import org.javalite.activejdbc.Base;

import spark.Spark;

import com.unrc.app.models.*;

import static spark.Spark.*; 	
/**
 * Hello world!
 *
 */
public class App {
    
	public static void main( String[] args) {
		

		
		
		System.out.println( "Hello cruel World!" );

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");

        
        //User.createIt("first_name", "Marcelo", "last_name", "Uva");
        User user = new User();
        user.set("first_name", "Marcelo");
        user.set("last_name", "Uva");
        user.set("mobile","3584256359");
        user.saveIt();
        
        Car car = new Car();
        car.setVehicleAttributes("Renault","Megane","2011","Negro");
        car.set("capacity","4");
        car.saveIt();
        user.add(car);
        
        Post post = new Post();
        post.set("title", "Vendo Renault Megane");
        post.set("description", "Excelente estado");
        post.saveIt();
        
        String m = post.getString("title");
        get("/hello", (request, response) -> {
	         return m;
        });
        
        Spark.stop();
        
        user.add(post);
        car.add(post);
        
        
        User user2 = new User();
        user2.set("first_name", "Pedro");
        user2.set("last_name", "Gonzales");
        user2.set("mobile","3584256357");
        user2.saveIt();
        
        City city = new City();
        city.set("name","Río Cuarto");
        city.saveIt();
        city.add(user);
        city.add(user2);
        
        Question question = new Question();
        question.set("description","¿A qué precio lo vendés?");
        question.saveIt();
        user2.add(question);
        post.add(question);
        
        Answer answer = new Answer();
        answer.set("description","Contactame a mi movil");
        answer.saveIt();
        
        user.add(answer);
        question.add(answer);
        User user3 = User.findFirst("id = 1");
        System.out.println(user3.searchPostByLocation("Río Cuarto").get(0).toString());
        Base.close();
    }  
}
