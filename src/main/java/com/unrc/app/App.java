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

import org.elasticsearch.node.*;
import static org.elasticsearch.node.NodeBuilder.*;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.*;
import org.elasticsearch.common.settings.*;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;


public class App {
    
	public static void main( String[] args) {
            staticFileLocation("/public"); 
		
		/**
		 * Open and close de database.
		 */
		before((request, response) -> {
        	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development?zeroDateTimeBehavior=convertToNull", "root", "");
        });
		
		after((request, response) -> {
			Base.close();
		});
		
		/**
		 * Getting the principal page
		 */
		get("/", (request, response) -> {
			Map<String,Object> attributes = new HashMap<String,Object>();
			return new ModelAndView(attributes,"carsapp.mustache");
			},
			new MustacheTemplateEngine()
		);
		
		/**
         * Getting search
         */
        get("/search", (request, response) -> {
            return new ModelAndView(null,"search.mustache");
            },
            new MustacheTemplateEngine()
        );
        
        /**
         * Realizing a search
         */
        post("/search", (request, response) -> {
           
            Client client = new TransportClient()
        					.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));

            ClusterHealthResponse health = client.admin()
            								.cluster()
            								.prepareHealth()
            								.setWaitForGreenStatus()
            								.execute()
            								.actionGet();

            SearchResponse resp = new SearchResponse();
            SearchHit[] docs;
            Map<String,Object> attributes = new HashMap<String,Object>();

            //The search was executed through users.
            if (request.queryParams("type").charAt(0)=='1') {
                resp = client.prepareSearch("users")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(QueryBuilders.matchQuery("name",request.queryParams("name"))) 
                        .setFrom(0).setSize(60).setExplain(true)
                        .execute()
                        .actionGet();
            
            	List<Map<String,Object>> usersFounded = new LinkedList<Map<String,Object>>();
            	docs = resp.getHits().getHits();
            	for (SearchHit hit : docs) {
					Map<String,Object> result = hit.getSource(); 
					String userId = hit.getId();
					result.put("id",userId);
					usersFounded.add(result);
				}

				attributes.put("searchExecuted",true);
				if (!usersFounded.isEmpty()) { 
					attributes.put("results",true); 
					attributes.put("userSearchResult",usersFounded);
				}
            }

            //The search was executed through posts.
            if (request.queryParams("type").charAt(0)=='2') {
                resp = client.prepareSearch("posts")
                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                        .setQuery(QueryBuilders.matchQuery("title",request.queryParams("dates")))   
                        .setFrom(0).setSize(60).setExplain(true)
                        .execute()
                        .actionGet();

            	List<Map<String,Object>> postsFounded = new LinkedList<Map<String,Object>>();
            	docs = resp.getHits().getHits();
            	for (SearchHit hit : docs) {
					Map<String,Object> result = hit.getSource(); 
					String postId = hit.getId();
					result.put("id",postId);
					postsFounded.add(result);
				}
				attributes.put("searchExecuted",true);
				if (!postsFounded.isEmpty()) { 
					attributes.put("results",true); 
					attributes.put("postSearchResult",postsFounded);
				}
			}

            client.close();

            return new ModelAndView(attributes,"search.mustache");
            },
            new MustacheTemplateEngine()
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
         * Deleting a user
         */
        post("users/:id/delete", (request, response) -> {
            User u = User.findById(request.params("id"));
            u.deleteCascade();
            Map<String,Object> attributes = new HashMap<String,Object>();
            String url = "/users";
            attributes.put("url",url);
            return new ModelAndView(attributes,"redirect.mustache"); 
            },
            new MustacheTemplateEngine()
        );

        /**
         * Getting all the posts of a user
         */
        get("/users/:id/posts", (request, response) -> {
            User u = User.findById(request.params("id"));
        	List<Post> posts = Post.where("user_id = ?", request.params("id"));
			boolean notEmpty = !posts.isEmpty();
            Map<String,Object> attributes = new HashMap<String,Object>();
			attributes.put("id",u.id());
            attributes.put("userName",u.name());
			attributes.put("userPosts",posts);
			attributes.put("notEmpty",notEmpty);
            return new ModelAndView(attributes,"user_posts.mustache");
        	},
        	new MustacheTemplateEngine()
        );
        
        /**
         * Getting a post of a user
         */
        get("users/:id/posts/:postId", (request, response) -> {
            Post p = Post.findById(request.params("postId"));
            User u = User.findById(request.params("id"));
            Vehicle v = Vehicle.findById(p.get("vehicle_id"));
            List<Question> q = Question.where("post_id = ?",request.params("postId"));
            Map<String,Object> attributes = new HashMap<String,Object>();
            attributes.put("userName",u.name());
            attributes.put("post",p);
            attributes.put("vehicle",v);
            attributes.put("questions",q);
            attributes.put("postId",p.id());
            switch (v.type()) {
                case "Auto":
                    Car c = Car.findFirst("vehicle_id = ?", v.id());
                    attributes.put("car",c);
                break;
                case "Motocicleta":
                    Motorcycle m = Motorcycle.findFirst("vehicle_id = ?", v.id());
                    attributes.put("motorcycle",m);
                break;
                case "Camión":
                   Truck t = Truck.findFirst("vehicle_id = ?", v.id());
                    attributes.put("truck",t);
                break;
            }     
            return new ModelAndView(attributes,"post_id.mustache");
            },
            new MustacheTemplateEngine()
	    );

        /**
         * Editing a post
         */ 
        get("/users/:id/posts/:postId/edit", (request,response) -> {
            Map<String,Object> attributes = new HashMap<String,Object>();
            Post p = Post.findById(request.params("postId"));
            attributes.put("post",p);
            return new ModelAndView(attributes,"edit_post.mustache"); 
            },
            new MustacheTemplateEngine()
        );
         
        /**
         * Posting a post edited
         */       
         post("/users/:id/posts/:postId/edit", (request,response) -> {
            Post p = Post.findById(request.params("postId"));
            p.set("title", request.queryParams("title"));
            p.set("description",request.queryParams("descrip"));
            p.saveIt();
            Map<String,Object> attributes = new HashMap<String,Object>();
            String url = "/users/"+request.params("id")+"/posts/"+p.id();
            attributes.put("url",url);
            
            return new ModelAndView(attributes,"redirect.mustache"); 
            },
            new MustacheTemplateEngine()
        );
		
        /**
         * Deleting a post of a user
         */
        post("users/:id/posts/:postId/delete", (request, response) -> {
            Post p = Post.findById(request.params("postId"));
            p.deleteCascade();
            Map<String,Object> attributes = new HashMap<String,Object>();
            String url = "/users/"+request.params("id")+"/posts";
            attributes.put("url",url);
            return new ModelAndView(attributes,"redirect.mustache"); 
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
                u.set("password", request.queryParams("password"));
        	u.set("mobile",request.queryParams("movil"));
        	u.set("telephone",request.queryParams("fijo"));
        	u.set("address",request.queryParams("direccion"));
                u.set("isAdmin",0);
        	u.saveIt();
        	
        	City c = City.findById(request.queryParams("ciudad"));
        	c.add(u);
			
        	Map<String,Object> attributes = new HashMap<String,Object>();
            String url = "/users/"+u.id();
            attributes.put("url",url);
        	return new ModelAndView(attributes,"redirect.mustache"); 
			},
			new MustacheTemplateEngine()
		);
        
        /**
         * Editing a user
         */ 
         get("/users/:id/edit", (request,response) -> {
            Map<String,Object> attributes = new HashMap<String,Object>();
            User u = User.findById(request.params("id"));
            List<City> c = City.findAll();
            attributes.put("user",u);
            attributes.put("cities",c);
            
            return new ModelAndView(attributes,"edit_user.mustache"); 
            },
            new MustacheTemplateEngine()
        );
         
        /**
         * Posting a user edited
         */       
        post("/users/:id/edit", (request,response) -> {
            User u = User.findById(request.params("id"));
            u.set("email", request.queryParams("email"));
            u.set("first_name",request.queryParams("firstName"));
            u.set("last_name", request.queryParams("lastName"));
            u.set("password", request.queryParams("password"));
            u.set("mobile",request.queryParams("movil"));
            u.set("telephone",request.queryParams("fijo"));
            u.set("address",request.queryParams("direccion"));
            u.saveIt();
            
            City c = City.findById(request.queryParams("ciudad"));
            c.add(u);
            
            Map<String,Object> attributes = new HashMap<>();
            String url = "/users/"+u.id();
            attributes.put("url",url);
            return new ModelAndView(attributes,"redirect.mustache"); 
            },
            new MustacheTemplateEngine()
        );

        /**
         * Adding a new Post 
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
		 * Posting a new Post  
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
 
        /*
         * Adding a new Vehicle           
         */
        get("/users/:id/newVehicle", (request,response) -> {
           Map<String,Object> attributes = new HashMap<String,Object>();
           attributes.put("id",request.params("id"));
           return new ModelAndView(attributes,"user_new_vehicle.mustache"); 
           },
           new MustacheTemplateEngine()
        );


        /**
         * Posting a new Vehicle 
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
         * Getting all the posts
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
         * Getting the answers of a question
         */
        get("users/:userId/posts/:postId/question/:idQuestion", (request, response) -> {
            List<Answer> answers = Answer.where("question_id = ?",request.params("idQuestion"));
            Question q = Question.findById(request.params("idQuestion"));
            Post p = Post.findById(request.params("postId"));
            Map<String,Object> attributes = new HashMap<String,Object>();
            attributes.put("question",q);
            attributes.put("answers",answers);
			attributes.put("post",request.params("postId"));
            attributes.put("user",request.params("userId"));
            attributes.put("userName", p.ownerName());
            return new ModelAndView(attributes,"question_answers.mustache");
            },
            new MustacheTemplateEngine()
        );
        
        
        
        /**
         * Posting a new question 
         */
        post("users/:id/posts/:postId/newQuestion", (request, response) -> {
            Question q = new Question();
            q.set("description",request.queryParams("descrip"));
            q.set("user_id", request.queryParams("userId"));
            q.set("post_id",request.params("postId"));
            q.saveIt();        	
            Map<String,Object> attributes = new HashMap<>();
            String url = "/users/"+request.params("id")+"/posts/"+request.params("postId");
            attributes.put("url",url);
        	return new ModelAndView(attributes,"redirect.mustache"); 
			},
			new MustacheTemplateEngine()
		);
        
        /**
         * Posting a new answer 
         */
         post("users/:id/posts/:postId/question/:questionId/newAnswer", (request, response) -> {
            Answer a = new Answer();
            a.set("description",request.queryParams("descrip"));
            a.set("user_id", request.params("id"));
            a.set("question_id",request.params("questionId"));
            a.saveIt();        	
            Map<String,Object> attributes = new HashMap<>();
            String url = "/users/"+request.params("id")+"/posts/"+request.params("postId")+"/question/"+request.params("questionId");
            attributes.put("url",url);
        	return new ModelAndView(attributes,"redirect.mustache"); 
			},
			new MustacheTemplateEngine()
		);
        
        
    }  
}
