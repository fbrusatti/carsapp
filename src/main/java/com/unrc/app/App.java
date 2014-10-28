package com.unrc.app;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.unrc.app.models.*;
import com.unrc.app.controllers.*;
import org.javalite.activejdbc.Base;

import spark.Spark.*;
import spark.Session;
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
            Session session = request.session(false);
            boolean existSession = false;
            if (session != null) existSession = true;
			Map<String,Object> attributes = new HashMap<String,Object>();
            attributes.put("existSession", existSession);
			return new ModelAndView(attributes,"carsapp.mustache");
			},
			new MustacheTemplateEngine()
		);
                
        /**
         * Getting login
         */
        get("/login", (request, response) -> {
            UserController userController = new UserController();
            return userController.getLoginView(request);
            },
            new MustacheTemplateEngine()
        );
        
        
        // Post Login
        post("/login", (request, response) -> {
            UserController userController = new UserController();
            userController.login(request,response);
            return null; 
        });
        
        // Get logout
        get("/logout", (request, response) -> {
            Session session = request.session(false);
            if (session!=null) {
                session.invalidate();
            }
            response.redirect("/");
            return null;
        });
        
        /**
         * Getting search
         */
        get("/search", (request, response) -> {
            Session session = request.session(false);
            boolean existSession = false;
            if (session != null) existSession = true;
            Map<String,Object> attributes = new HashMap<String,Object>();
            attributes.put("existSession", existSession);
            return new ModelAndView(attributes,"search.mustache");
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
            if (request.queryParams("type").charAt(0)=='2') {
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
            if (request.queryParams("type").charAt(0)=='1') {
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

            Session session = request.session(false);
            boolean existSession = false;
            if (session != null) existSession = true;
            attributes.put("existSession", existSession);

            return new ModelAndView(attributes,"search.mustache");
            },
            new MustacheTemplateEngine()
        );
		
		/**
         * Getting users
         */
		get("/users", (request, response) -> {
            UserController userController = new UserController();
            return userController.getUsersView(request);
			},
			new MustacheTemplateEngine()
        );
        
        
        /**
         * Getting user by id
         */
        get("/users/:id", (request, response) -> {
            UserController userController = new UserController();
            return userController.getUserView(request);
			},
			new MustacheTemplateEngine()
        );
        
        /**
         * Deleting a user
         */
        post("users/:id/delete", (request, response) -> {
            UserController userController = new UserController();
            userController.delete(request,response);
            return null;
            }
        );

        /**
         * Getting all the posts of a user
         */
        get("/users/:id/posts", (request, response) -> {
            Session session = request.session(false);
            boolean existSession = false;
            if (session != null) existSession = true;
            Map<String,Object> attributes = new HashMap<String,Object>();
            if (existSession) {
                User u = User.findById(request.params("id"));
                List<Post> posts = Post.where("user_id = ?", request.params("id"));
                boolean notEmpty = !posts.isEmpty();
                attributes.put("id",u.id());
                attributes.put("userName",u.name());
                attributes.put("userPosts",posts);
                attributes.put("notEmpty",notEmpty);
                return new ModelAndView(attributes,"user_posts.mustache");
            } else {
                String url = "/";
                attributes.put("url",url);
                return new ModelAndView(attributes,"redirect.mustache"); 
            } 
        	},
        	new MustacheTemplateEngine()
        );
        
        /**
         * Getting a post of a user
         */
        get("users/:id/posts/:postId", (request, response) -> {
            Session session = request.session(false);
            boolean isOwnerOrAdmin = false;
            boolean isGuest;
            Map<String,Object> attributes = new HashMap<String,Object>();
            User u = User.findById(request.params("id"));
            if (session != null) {
                String userEmail = u.email();
                String session_id = session.attribute("user_id");
                attributes.put("session_id",session_id);
                if (session.attribute("user_email").equals(userEmail)) {
                    isOwnerOrAdmin = true;
                } else {
                    isOwnerOrAdmin = u.isAdmin();
                }
                isGuest=false;
            } else {
                isGuest=true;
            }

            Post p = Post.findById(request.params("postId"));
            Vehicle v = Vehicle.findById(p.get("vehicle_id"));
            List<Question> q = Question.where("post_id = ?",request.params("postId"));
            
            attributes.put("isGuest",isGuest);
            attributes.put("isOwnerOrAdmin", isOwnerOrAdmin);
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
        	Session session = request.session(false);
            Map<String,Object> attributes = new HashMap<String,Object>();
            if (session != null) {
            	Post p = Post.findById(request.params("postId"));
                User u = User.findById(request.params("id"));
                String userEmail = u.email();	
        	    if (session.attribute("user_email").equals(userEmail)) {	
            		attributes.put("post",p);
            		return new ModelAndView(attributes,"edit_post.mustache");
            	} else {
                    String url = "/users/"+u.id()+"/posts/"+p.id();
                    attributes.put("url",url);
                    return new ModelAndView(attributes,"redirect.mustache"); 
                }
        	} else {
                String url = "/login";
                attributes.put("url",url);
                return new ModelAndView(attributes,"redirect.mustache");
            }	 
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
            VehicleController vehicleController = new VehicleController();
            return vehicleController.getUserVehiclesView(request);
            },
        	new MustacheTemplateEngine()
        );
        
        /**
		 *Adding a new User 
		 */
		get("newUser", (request, response) -> {
            UserController userController = new UserController();
            return userController.getAddView(request);
			},
			new MustacheTemplateEngine()
        );
		
        /**
         * Posting a new user
         */
        post("/users", (request, response) -> {
        	UserController userController = new UserController();
            userController.add(request,response);
        	return null; 
            }	
		);
        
        /**
         * Editing a user
         */ 
        get("/users/:id/edit", (request,response) -> {
            UserController userController = new UserController();
            return userController.getEditView(request); 
            },
            new MustacheTemplateEngine()
        );
         
        /**
         * Posting a user edited
         */       
        post("/users/:id/edit", (request,response) -> {
            UserController userController = new UserController();
            userController.edit(request,response);
            return null;
            } 
        );

        /**
         * Adding a new Post 
         */
        get("/users/:id/newPost", (request,response) -> {
		    Session session = request.session(false);
            Map<String,Object> attributes = new HashMap<String,Object>();
            if (session != null) {
                User u = User.findById(request.params("id"));
                String userEmail = u.email();
                if (session.attribute("user_email").equals(userEmail)) {
                    attributes.put("id",request.params("id"));
                    List<Vehicle> vehicles = Vehicle.where("user_id = ?",request.params("id"));
                    attributes.put("vehicles",vehicles);
                    return new ModelAndView(attributes,"user_new_post.mustache");
                } else {
                    String url = "/users/"+u.id();
                    attributes.put("url",url);
                    return new ModelAndView(attributes,"redirect.mustache"); 
                }
            } else {
                String url = "/";
                attributes.put("url",url);
                return new ModelAndView(attributes,"redirect.mustache");
            }
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
            VehicleController vehicleController = new VehicleController();
            return vehicleController.getAddView(request);
            },
            new MustacheTemplateEngine()
        );


        /**
         * Posting a new Vehicle 
		 */
        post("users/:id/newVehicle", (request, response) -> {
            VehicleController vehicleController = new VehicleController();
            vehicleController.add(request,response);
            return null;
			}
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
            AnswerController answerController = new AnswerController();
            return answerController.getAnswersOfQuestionView(request);
            },
            new MustacheTemplateEngine()
        );
        
        /**
         * Posting a new question 
         */
        post("users/:id/posts/:postId/newQuestion", (request, response) -> {
            QuestionController questionController = new QuestionController();
            questionController.add(request,response);
            return null;
            }
		);
        
        /**
         * Posting a new answer 
         */
         post("users/:id/posts/:postId/question/:questionId/newAnswer", (request, response) -> {
            AnswerController answerController = new AnswerController();
            answerController.add(request,response);
            return null;
            }
		);
        
        
    }  
}
