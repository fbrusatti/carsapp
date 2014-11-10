package com.unrc.app;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.unrc.app.models.*;
import com.unrc.app.controllers.*;
import org.javalite.activejdbc.Base;

import spark.Session;
import spark.ModelAndView;
import static spark.Spark.*;


public class App {
    
    private static UserController userController = new UserController();
    private static SearchController searchController = new SearchController();
    private static PostController postController = new PostController();
    private static VehicleController vehicleController = new VehicleController();
    private static AnswerController answerController = new AnswerController();
    private static QuestionController questionController = new QuestionController();

	public static void main( String[] args) {

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
         * Getting search
         */
        get("/search", (request, response) -> {
            return searchController.getSearchView(request);
            },
            new MustacheTemplateEngine()
        );
        
        /**
         * Realizing a search
         */
        post("/search", (request, response) -> {
            return searchController.postSearchView(request);
            },
            new MustacheTemplateEngine()
        );

        /**
         * Getting login
         */
        get("/login", (request, response) -> {
            return userController.getLoginView(request);
            },
            new MustacheTemplateEngine()
        );
        
        
        /**
         * Post login
         */
        post("/login", (request, response) -> {
            userController.login(request,response);
            return null; 
        });
        
        /**
         * Getting logout
         */
        get("/logout", (request, response) -> {
            Session session = request.session(false);
            if (session!=null) {
                session.invalidate();
            }
            response.redirect("/");
            return null;
        });
        
		
		/**
         * Getting users
         */
		get("/users", (request, response) -> {
            return userController.getUsersView(request);
			},
			new MustacheTemplateEngine()
        );
        
        
        /**
         * Getting user by id
         */
        get("/users/:id", (request, response) -> {
            return userController.getUserView(request);
			},
			new MustacheTemplateEngine()
        );

        /**
         *Adding a new User 
         */
        get("newUser", (request, response) -> {
            return userController.getAddView(request);
            },
            new MustacheTemplateEngine()
        );
        
        /**
         * Posting a new user
         */
        post("/users", (request, response) -> {
            userController.add(request,response);
            return null; 
            }   
        );
        
        /**
         * Editing a user
         */ 
        get("/users/:id/edit", (request,response) -> {
            return userController.getEditView(request); 
            },
            new MustacheTemplateEngine()
        );
         
        /**
         * Posting a user edited
         */       
        post("/users/:id/edit", (request,response) -> {
            userController.edit(request,response);
            return null;
        });

        /**
         * Deleting a user
         */
        post("users/:id/delete", (request, response) -> {
            userController.delete(request,response);
            return null;
        });

        /*
         * Getting all the posts
         */
        get("/posts", (request, response) -> {
                return postController.getPosts(request);
        },
        new MustacheTemplateEngine()
        );

        /**
         * Getting all the posts of a user
         */
        get("/users/:id/posts", (request, response) -> {
            return postController.getPostsUser(request);
            },
            new MustacheTemplateEngine()
        );
        
        /**
         * Getting a post of a user
         */
        get("users/:id/posts/:postId", (request, response) -> {
            return postController.getPost(request);
            },
            new MustacheTemplateEngine()
	    );

        /**
         * Adding a new Post 
         */
        get("/users/:id/newPost", (request,response) -> {
            return postController.getNewPost(request);
            },
            new MustacheTemplateEngine()
        );
        
        /**
         * Posting a new Post  
         */
        post("/users/:id/newPost", (request, response) -> {
            postController.newPost(request, response);
            return null;
        });

        /**
         * Editing a post
         */ 
        get("/users/:id/posts/:postId/edit", (request,response) -> {
            return postController.getEditPost(request);
            },
            new MustacheTemplateEngine()
        );
         
        /**
         * Posting a post edited
         */       
         post("/users/:id/posts/:postId/edit", (request,response) -> {
            postController.editPost(request, response);
            return null;
        });
		
        /**
         * Deleting a post of a user
         */
        post("users/:id/posts/:postId/delete", (request, response) -> {
            postController.deletePost(request, response);
            return null;
        });

        /**
         * Getting vehicles of a User
         */ 
        get("/users/:id/vehicles", (request, response) -> {
            return vehicleController.getUserVehiclesView(request);
            },
        	new MustacheTemplateEngine()
        );

        /*
         * Adding a new Vehicle           
         */
        get("/users/:id/newVehicle", (request,response) -> {
            return vehicleController.getAddView(request);
            },
            new MustacheTemplateEngine()
        );

        /**
         * Posting a new Vehicle 
		 */
        post("users/:id/newVehicle", (request, response) -> {
            vehicleController.add(request,response);
            return null;
		});  
        
        /**
         * Getting the answers of a question
         */
        get("users/:userId/posts/:postId/question/:idQuestion", (request, response) -> {
            return answerController.getAnswersOfQuestionView(request);
            },
            new MustacheTemplateEngine()
        );
        
        /**
         * Posting a new question 
         */
        post("users/:id/posts/:postId/newQuestion", (request, response) -> {
            questionController.add(request,response);
            return null;
            }
		);
        
        /**
         * Posting a new answer 
         */
         post("users/:id/posts/:postId/question/:questionId/newAnswer", (request, response) -> {
            answerController.add(request,response);
            return null;
            }
		);
        
    }  
}
