package com.unrc.app;

import java.util.LinkedList;
import java.util.List;

import org.javalite.activejdbc.Base;

import spark.Spark.*;

import static spark.Spark.*;

import com.unrc.app.models.*;
	
/**
 * Hello world!
 *
 */
public class App {
    
	public static void main( String[] args) {
		

		
		
		System.out.println( "Hello cruel World!" );

        //Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");

        
        //User.createIt("first_name", "Marcelo", "last_name", "Uva");
        /*User user = new User();
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
        */
        //String m = post.getString("title");
       // List<User> users = User.findAll();
        
		
		before((request, response) -> {
        	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        });
		
		after((request, response) -> {
			Base.close();
		});
		
		/**
         * Getting users
         */
		get("/users", (request, response) -> {
        	List<User> users = User.findAll();
        	return users.toString();
        });
        
        /**
         * Getting user by id
         */
        get("/users/:id", (request, response) -> {
        	User u = User.findById(request.params("id"));
        	if (u!=null) {
        		return u;
        	} else {
        		return "El usuario ingresado no es válido.";
        	}
        });
        
        /**
         * Getting the posts of a user
         */
        get("/users/:id/posts", (request, response) -> {
        	//User u = User.findById(request.params("id"));
        	List<Post> posts = Post.where("user_id = ?", request.params("id"));
        	if (posts!=null) {
        		if (posts.isEmpty()) {
        			return "El usuario no posee posts.";
        		} else {
        			return posts.toString();
        		}
        	} else {
        		return "El usuario ingresado no es válido.";
        	}
        	
        });
        
        /**
         * Getting posts
         */
        get("/posts", (request, response) -> {
        	List<Post> posts = Post.findAll();
        	return posts.toString();
        });
        
        /**
         * Getting posts by vehicle type.
         */
        get("/posts/:vehicleType", (request, response)-> {
        	List<Vehicle> vehicles = Vehicle.where("type = ?", request.params("vehicleType"));
      	  	if (vehicles!=null) {
      	  		if (vehicles.isEmpty()) {
      	  			return "No hay posts correspondientes a este tipo de vehículo.";
      	  		} else {
      	  			List<Post> postOfVehicleType=new LinkedList<Post>();
      	  			for (Vehicle v : vehicles) {
      	  				Post p = Post.findFirst("vehicle_id = ?", v.getId());
      	  				postOfVehicleType.add(p);
      	  			}
      	  			return postOfVehicleType;
      	  		}
      	  	} else {
      	  		return "El tipo de vehículo no es correcto";
      	  	}
      	 }); 
        
        /**
         * Getting cities
         */
        get("/cities", (request, response) -> {
        	List<City> cities = City.findAll();
        	return cities;
        });
        
        /**
         * Getting posts by location
         */
        get("/cities/:id/posts", (request, response) -> {
        	City c = City.findById(request.params("id"));
      	  	List<Post> postFromLocate=new LinkedList<Post>();
      	  	if (c!=null) {
      	  		List<User> usersFromLocate = (List<User>)(c.get("users"));
      	  		for (User u : usersFromLocate) {
      			  List<Post> postOfUser = u.getAll(Post.class);
      			  for (Post p : postOfUser) {
      				  postFromLocate.add(p);
      			  }
      	  		}
      	  		return postFromLocate;
      	  	} else {
      	  		return "La ubicación ingresada no es válida";
      	  	}
      	  
        });
        
        /**
         * Getting vehicles
         */
        get("/vehicles", (request, response) -> {
        	List<Vehicle> vehicles = Vehicle.findAll();
        	return vehicles.toString();
        });
        
        //Spark.stop();
        /*
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
        System.out.println(user3.searchPostByLocation("Río Cuarto").get(0).toString());*/
        //Base.close();
    }  
}
