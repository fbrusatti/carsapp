package com.unrc.app;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.unrc.app.models.*;
import org.javalite.activejdbc.Base;

import spark.Spark.*;
import spark.ModelAndView;
import static spark.Spark.*;

import org.elasticsearch.client.*;
import org.elasticsearch.node.*;
import org.elasticsearch.action.index.IndexResponse;

import static org.elasticsearch.node.NodeBuilder.*;



public class App {
    
	public static void main( String[] args) {
		
		before((request, response) -> {
        	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development?zeroDateTimeBehavior=convertToNull", "root", "");
        });
		
		after((request, response) -> {
			Base.close();
		});
		
		/**
		 * Gettin the principal page
		 */
		get("/", (request, response) -> {
			Map<String,Object> attributes = new HashMap<String,Object>();
			return new ModelAndView(attributes,"carsapp.mustache");
			},
			new MustacheTemplateEngine()
		);
		
		get("/search", (request, response) -> {
			//Map<String,Object> attributes = new HashMap<String,Object>();
			return new ModelAndView(null,"search.mustache");
			},
			new MustacheTemplateEngine()
		);
		
		post("/search", (request, response) -> {
			return "";
			}
		);
		
		/**
         * Getting users
         */
		get("/users", (request, response) -> {
			Map<String,Object> attributes = new HashMap<String,Object>();
			List<User> users = User.findAll();
			boolean notEmpty = !users.isEmpty();
			attributes.put("users",users);
			attributes.put("notEmpty",notEmpty);
			return new ModelAndView(attributes,"users.mustache");
			},
			new MustacheTemplateEngine()
        );
        
        
        /**
         * Getting user by id
         */
        get("/users/:id", (request, response) -> {
        	User u = User.findById(request.params("id"));
        	Map<String,Object> attributes = new HashMap<String,Object>();
        	attributes.put("user",u);
        	return new ModelAndView(attributes,"user_id.mustache");
			},
			new MustacheTemplateEngine()
        );
        
        /**
         * Getting the posts of a user
         */
        get("/users/:id/posts", (request, response) -> {
        	User u = User.findById(request.params("id"));
        	List<Post> posts = Post.where("user_id = ?", request.params("id"));
			boolean notEmpty = !posts.isEmpty();
			Map<String,Object> attributes = new HashMap<String,Object>();
			attributes.put("userName",u.name());
			attributes.put("userPosts",posts);
			attributes.put("notEmpty",notEmpty);
			return new ModelAndView(attributes,"user_posts.mustache");
        	//else {
			//	return "El usuario no posee posts";
			//}
        	},
        	new MustacheTemplateEngine()
        );
        
        /**
         * Getting post by of a user
         */
        get("users/:id/posts/:postId", (request, response) -> {
			Post p = Post.findById(request.params("postId"));
			User u = User.findById(request.params("id"));
			Vehicle v = Vehicle.findById(p.get("vehicle_id"));
			Map<String,Object> attributes = new HashMap<String,Object>();
        	attributes.put("userName",u.name());
        	attributes.put("post",p);
        	attributes.put("vehicle",v);
        	return new ModelAndView(attributes,"post_id.mustache");
			},
			new MustacheTemplateEngine()
		);
		
        /**
         * Getting vehicles of a User
         */ 
         get("/users/:id/vehicles", (request, response) -> {
        	User u = User.findById(request.params("id"));
        	List<Vehicle> vehicles = Vehicle.where("user_id = ?", request.params("id"));
        	boolean notEmpty = !vehicles.isEmpty();
        	Map<String,Object> attributes = new HashMap<String,Object>();
        	attributes.put("userName",u.name());
        	attributes.put("userVehicles",vehicles);
        	attributes.put("notEmpty",notEmpty);
        	return new ModelAndView(attributes,"user_vehicles.mustache");
        	},
        	new MustacheTemplateEngine()
        );
        
        /**
		 *Adding a new User 
		 */
		get("newUser", (request, response) -> {
        	Map<String,Object> attributes = new HashMap<String,Object>();
        	List<City> cities = City.findAll();
        	attributes.put("cities",cities);
        	return new ModelAndView(attributes,"new_user.mustache");
			},
			new MustacheTemplateEngine()
        );
		
        /**
         * Posting a new user
         */
        post("/users", (request, response) -> {
        	User u = new User();
        	u.set("email", request.queryParams("email"));
        	u.set("first_name",request.queryParams("firstName"));
        	u.set("last_name", request.queryParams("lastName"));
        	u.set("mobile",request.queryParams("movil"));
        	u.set("telephone",request.queryParams("fijo"));
        	u.set("address",request.queryParams("direccion"));
        	u.saveIt();
        	
        	City c = City.findById(request.queryParams("ciudad"));
        	c.add(u);
        	
        	/*Node node = nodeBuilder().clusterName("elasticsearch").node();
			Client client = node.client();
			
        	
        	Map<String, Object> json = new HashMap<String, Object>();
			json.put("user",u.name());
			json.put("city",c.name());
			json.put("message","trying out Elasticsearch");
			
			IndexResponse indexResponse = client.prepareIndex("users", "user")
				.setSource(json)
				.execute()
				.actionGet();
			node.close();	*/
			
        	Map<String,Object> attributes = new HashMap<String,Object>();
            String url = "/users/"+u.id();
            attributes.put("url",url);
        	return new ModelAndView(attributes,"redirect.mustache"); 
			},
			new MustacheTemplateEngine()
		);
        
        /**
         *Adding a new Post 
        */
        get("/users/:id/newPost", (request,response) -> {
		Map<String,Object> attributes = new HashMap<String,Object>();
		attributes.put("id",request.params("id"));
        	List<Vehicle> vehicles = Vehicle.where("user_id = ?",request.params("id"));
        	attributes.put("vehicles",vehicles);
        	return new ModelAndView(attributes,"user_new_post.mustache"); 
			},
			new MustacheTemplateEngine()
		);
		
		/**
		 *Posting a new Post  
         */
         post("/users/:id/newPost", (request, response) -> {
        	Post p = new Post();
        	p.set("title", request.queryParams("title"));
        	p.set("description",request.queryParams("descrip"));
        	p.set("user_id", request.params("id"));
        	Vehicle v = Vehicle.findById(request.queryParams("vehicles"));
        	p.saveIt();
        	v.add(p);
            Map<String,Object> attributes = new HashMap<String,Object>();
            String url = "posts/"+p.id();
            attributes.put("url",url);
        	return new ModelAndView(attributes,"redirect.mustache"); 
			},
			new MustacheTemplateEngine()
		);
         
         
         /**
          *Adding a new Vehicle 
          */
         get("/users/:id/newVehicle", (request,response) -> {
        	Map<String,Object> attributes = new HashMap<String,Object>();
        	attributes.put("id",request.params("id"));
        	return new ModelAndView(attributes,"user_new_vehicle.mustache"); 
			},
			new MustacheTemplateEngine()
		);
         
         /**
         *Posting a new Vehicle 
		 */
        post("users/:id/newVehicle", (request, response) -> {
			Vehicle v = new Vehicle();
			v.set("brand", request.queryParams("brand"));
			v.set("model", request.queryParams("model"));
			v.set("year", request.queryParams("year"));
			v.set("color", request.queryParams("color"));
			v.set("user_id",request.params("id"));
			if (request.queryParams("type").charAt(0)=='1') {
				v.set("type","Auto");
				v.saveIt();
				Car c = new Car();
				c.set("capacity",request.queryParams("capacity"));
				c.saveIt();
				v.add(c);
			}
			if (request.queryParams("type").charAt(0)=='2') {
				v.set("type","Motocicleta");
				v.saveIt();
				Motorcycle m = new Motorcycle();
				m.set("cylinder_capacity",request.queryParams("cylinder_capacity"));
				m.saveIt();
				v.add(m);
			}
			if (request.queryParams("type").charAt(0)=='3') {
				v.set("type","Camión");
				v.saveIt();
				Truck t = new Truck();
				t.set("length",request.queryParams("length"));
				t.set("height",request.queryParams("height"));
				t.saveIt();
				v.add(t);
			}
            Map<String,Object> attributes = new HashMap<String,Object>();
            String url = "newPost";
            attributes.put("url",url);
        	return new ModelAndView(attributes,"redirect.mustache"); 
			},
			new MustacheTemplateEngine()
		);  
        
        /*
         * Getting posts
         
         */
        get("/posts", (request, response) -> {
        	List<Post> posts = Post.findAll();
        	boolean notEmpty = !posts.isEmpty();
        	Map<String,Object> attributes = new HashMap<String,Object>();
        	attributes.put("posts",posts);
        	attributes.put("notEmpty",notEmpty);
        	return new ModelAndView(attributes,"posts.mustache");
			},
			new MustacheTemplateEngine()
        );
        
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
