package com.unrc.app;

import org.javalite.activejdbc.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

import org.elasticsearch.node.Node;
import org.elasticsearch.client.Client;
import static org.elasticsearch.node.NodeBuilder.*;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.index.query.FilterBuilders.*;
import org.elasticsearch.index.query.QueryBuilders.*;
import org.elasticsearch.index.query.*;

import com.unrc.app.MustacheTemplateEngine;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Post;
import com.unrc.app.models.Car;
import com.unrc.app.models.Question;
import com.unrc.app.models.Answer;
import com.unrc.app.models.Address;

import com.unrc.app.controllers.UserController;


public class App {
    public static void main( String[] args ) {
        /*try {                           //code to open browser in hello url. problem, it loads without the content of spark
            URI uri = new URI("http://localhost:4567/hello");
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            if (desktop != null){
                desktop.browse(uri);
            }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (URISyntaxException use) {
                use.printStackTrace();
            }*/

        /* - CONTROLLER CREATIONS - */
        UserController userController = new UserController();

        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        });

        get("/",(request, response) -> {
            response.redirect("/hello");
            return "Welcome";
        }); 
        
        get("/whoops", (request, response) -> {
            return "error";
        });

        get("/hello",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "hello.mustache");
        },
            new MustacheTemplateEngine()
        );

        /*------------------------ USER ROUTES ----------------------------*/

        //Form to create a user
        get("/users/new" , (request, response) ->{
            return userController.addUserForm();
        },
            new MustacheTemplateEngine()
        );

        post ("/users",(request, response) ->{ 
            return userController.addUser(request,response);
        });

        //Form to add a vehicle
        get("/users/add/vehicles" , (request, response) ->{
            return userController.addVehicleForm();
        },
            new MustacheTemplateEngine()
        );

        post("/user/add/vehicles" , (request, response) ->{
            return userController.addVehicle(request);
        },
            new MustacheTemplateEngine()
        );

        // Form to add Addresses
        get("/users/add/addresses" , (request, response) ->{
            return userController.addAddressesForm();
        },
            new MustacheTemplateEngine()
        );

        // Form to write the answer to a question
        get("/questions/post/:id" , (request,response) -> {
            return userController.addAnswerForm(request, response);
        },
            new MustacheTemplateEngine()
        );


        //From to create a Post
        get("/users/add/post" , (request,response) -> {
            return userController.addPostForm();
        },
            new MustacheTemplateEngine()
        );

        post("/users/add/post" , (request,response) -> {
            return userController.selectVehiclesForm(request); //This form list the vehicles for the post creation
        },
            new MustacheTemplateEngine()
        );

        post("/posts" , (request,response) -> {
            return userController.addPost(request, response);
        });

        //List of users
        get("/users",(request, response) -> {
            return userController.listUsers();
        },
            new MustacheTemplateEngine()
        );

        
        //Show User by the id
        get("/users/:id", (request, response) -> {
            return userController.listUserById(request, response);
        },
            new MustacheTemplateEngine()
        );


        /*---------------------- POST ROUTES -----------------*/

        //Show Post by id
        get("/posts/:id", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Post post = Post.findById(Integer.parseInt(request.params(":id")));
            if (post == null) {
                response.redirect("/whoops",404);
                return new ModelAndView(attributes, "postId.mustache");
            } else {
                List<Question> questions = Question.where("post_id = ?", post.id());
                attributes.put("vehicle_name", post.vehicle().name());
                attributes.put("post", post);
                if (questions != null) {
                    attributes.put("questions", questions);
                }
                return new ModelAndView(attributes, "postId.mustache");
            }
        },
            new MustacheTemplateEngine()
        );

        //List of Posts
        get("/posts",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<Post> posts = Post.findAll();
            attributes.put("posts_count", posts.size());
            attributes.put("posts", posts);
            return new ModelAndView(attributes, "posts.mustache");
        },
            new MustacheTemplateEngine()
        );

        post("/posts/answer",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Question question = Question.findById(request.queryParams("questionId"));
            User user = User.findFirst("email = ?",request.queryParams("login"));
            attributes.put("question",question);
            attributes.put("user",user);

            return new ModelAndView(attributes, "postAnswer.mustache");
        }, 
            new MustacheTemplateEngine()
        );

        /*---------------------- VEHICLE ROUTES ----------------*/

        // /users/add/vehicle does a POST to this route
        post("/vehicles",(request, response) -> { 
            String name = request.queryParams("name");
            String model = request.queryParams("model");
            String km = request.queryParams("km"); //should take integers in the form
            String user = request.queryParams("user"); //later we should use the id of the user logged
            String type =request.queryParams("vehicleType");
            User u = User.findFirst("email = ?",user);
            if (type.equals("car")) {
                u.addCar(name,model,km,request.queryParams("carType"));
            }if (type.equals("truck")) {
                u.addTruck(name,model,km,request.queryParams("truckType"));
            }if (type.equals("moto")) {
                u.addMoto(name,model,km,request.queryParams("motoType"));
            }if (type.equals("other")) {
                u.addVehicle(name,model,km);
            }
            response.redirect("/vehicles");
            return "success"; 
        });

        
        //List of vehicles
        get("/vehicles",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<Vehicle> vehicles = Vehicle.findAll();
            attributes.put("vehicles_count", vehicles.size());
            attributes.put("vehicles", vehicles);
            return new ModelAndView(attributes, "vehicles.mustache");
        },
            new MustacheTemplateEngine()
        );
        
        //Show Vehicles 
        get("/vehicles/:id", (request, response) -> {
            Vehicle v = Vehicle.findById(Integer.parseInt(request.params(":id")));
            if (v == null) {
                response.redirect("/whoops", 404);
                return "not found";
            } else {
                String vehicleName = v.getString("name") +" "+ v.getString("model");
                String km = v.getString("km");
                User u1 = User.findById(v.getInteger("user_id"));
                String userName = u1.getString("first_name");
                return "Vehicle: " + vehicleName+"\n"+"Belongs to: "+userName;
            }
        });

        /*---------------------- QUESTION ROUTES ----------------*/

        
        /*//List of Questions
        get("/questions", (request,response) -> {
            List<Question> questionList = Question.findAll();
            String list = new String();
            for (Question q: questionList) {
                User u = User.findById(q.getInteger("user_id"));
                Post p = Post.findById(q.getInteger("post_id"));
                list = list+q.getString("id")+" "+"User Name: "+u.getString("first_name")+" "+"Post: "+p.getString("id")+" "+"Question: "+q.getString("description")+"\n";
            }
            return list;
        });*/

        // The form shown in the post details POSTs to this route
        post ("/questions",(request, response) -> { 
            Post post = Post.findById(request.queryParams("postId"));

            String description = request.queryParams("description");
            String email = request.queryParams("user");

            User userAsking = User.findFirst("email = ?",email);

            //If the user asking is the owner of the post, redirect to 403
            if (post.getInteger("user_id") == userAsking.id()) {
                response.redirect("/whoops",403);
                return "error";
            } else {
                userAsking.addQuestion(description,post);
                response.redirect("/posts");
                return "success";
            }
        });

        /*//Show Question by id
        get("/questions/:id", (request, response) -> {
            Question q = Question.findById(Integer.parseInt(request.params(":id")));
            return "Question: " + q.toString();
        });*/

        /*---------------------- ANSWERS ROUTES ----------------*/

        //List of Answers
        get("/answers", (request,response) -> {
            List<Answer> answerList = Answer.findAll();
            String list = new String();
            for (Answer a: answerList) {
                User u = User.findById(a.getInteger("user_id"));
                Post p = Post.findById(a.getInteger("post_id"));
                list = list+a.getString("id")+" "+"User Name: "+u.getString("first_name")+" "+"Post: "+p.getString("id")+" "+"Answer: "+a.getString("description")+"\n";
            }
            return list;
        });

        post("/answers", (request,response) -> {
            Question question = Question.findById(request.queryParams("questionId"));
            User user = User.findById(request.queryParams("userId"));
            Post post = Post.findById(question.getString("post_id"));
            String description = request.queryParams("description");
            //The user answering should be the owner of the post
            if (user.id() == post.getInteger("user_id")) {
                user.addAnswer(description,post,question);
                response.redirect("/posts/"+post.id());
                return "success";
            } else {
                response.redirect("/whoops",403);
                return "error";
            }
        });

        // //Show Answer by id
        // get("/answers/:id", (request, response) -> {
        //     Answer a = Answer.findById(Integer.parseInt(request.params(":id")));
        //     return "Answer: " + a.toString();
        // });

        
        /*---------------------- SEARCH ROUTES ----------------*/

        // Show a search bar
        get("/search/users",(request,response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("found?",false);
            return new ModelAndView(attributes,"searchUsers.mustache");
        }, 
            new MustacheTemplateEngine()
        );

        // Search for users
        post("/search/users", (request,response) -> {
            Map<String, Object> attributes = new HashMap<>();

            String query = request.queryParams("carsappsearch");

            //Starts the elasticsearch cluster
            Node node = nodeBuilder().local(true).clusterName("carsapp").node();
            Client client = node.client();

            //Waits until the cluster is ready
            ClusterHealthResponse health = client.admin()
                                            .cluster()
                                            .prepareHealth()
                                            .setWaitForYellowStatus()
                                            .execute()
                                            .actionGet();

            //Executes the search
            SearchResponse res = client.prepareSearch("users")
                                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                        .setQuery(QueryBuilders.matchQuery("name",query))
                                        .execute()
                                        .actionGet();

            //Gets the search results
            SearchHit[] docs = res.getHits().getHits();

            //Closes the cluster
            node.close();

            Map<String,Object> map = new HashMap<String,Object>();
            List<Map<String,Object>> userList = new LinkedList<Map<String,Object>>();
            for (SearchHit sh : docs) {
                map = sh.getSource();
                userList.add(map);

            } //Puts all the results in a list to be treated in the mustache

            long hits = res.getHits().getTotalHits();

            attributes.put("result",userList);
            attributes.put("result_count",hits);
            if (hits > 0) { attributes.put("found?",true); }

            return new ModelAndView(attributes,"searchUsers.mustache");
        },
            new MustacheTemplateEngine()
        );


        // Show a search bar for posts
        get("/search/posts",(request,response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("found?",false);
            return new ModelAndView(attributes,"searchPosts.mustache");
        }, 
            new MustacheTemplateEngine()
        );


        // Search for posts
        post("/search/posts", (request,response) -> {
            Map<String, Object> attributes = new HashMap<>();

            String description = request.queryParams("postSearch");
            String minPrice = request.queryParams("minPrice");
            String maxPrice = request.queryParams("maxPrice");

            //Starts the elasticsearch cluster
            Node node = nodeBuilder().local(true).clusterName("carsapp").node();
            Client client = node.client();

            //Waits until the cluster is ready
            ClusterHealthResponse health = client.admin()
                                            .cluster()
                                            .prepareHealth()
                                            .setWaitForYellowStatus()
                                            .execute()
                                            .actionGet();

            SearchResponse res = new SearchResponse();

            if (minPrice.equals("") && maxPrice.equals("")) {
                //Executes the search only with the description
                res = client.prepareSearch("posts")
                            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                            .setQuery(QueryBuilders.matchQuery("description",description))
                            .execute()
                            .actionGet();
            } else {
                if (minPrice.equals("") && !(maxPrice.equals(""))) {
                    //Executes the search with minimun price range and description
                    res = client.prepareSearch("posts")
                                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                .setQuery(QueryBuilders.boolQuery()
                                                       .must(QueryBuilders.matchQuery("description",description))
                                                       .must(QueryBuilders.rangeQuery("price")
                                                                          .lte(maxPrice)))
                                .execute()
                                .actionGet();
                } else {
                    if (!(minPrice.equals("")) && maxPrice.equals("")) {
                        //Executes the search with maximun price range and description
                        res = client.prepareSearch("posts")
                                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                    .setQuery(QueryBuilders.boolQuery()
                                                       .must(QueryBuilders.matchQuery("description",description))
                                                       .must(QueryBuilders.rangeQuery("price")
                                                                          .gte(minPrice)))
                                    .execute()
                                    .actionGet();
                    } else {
                        if (!(minPrice.equals("")) && !(maxPrice.equals(""))) {
                            //Executes the search with both price ranges and description
                            res = client.prepareSearch("posts")
                                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                        .setQuery(QueryBuilders.boolQuery()
                                                       .must(QueryBuilders.matchQuery("description",description))
                                                       .must(QueryBuilders.rangeQuery("price")
                                                                          .from(minPrice)
                                                                          .to(maxPrice)
                                                                          .includeLower(true)
                                                                          .includeUpper(true)))
                                        .execute()
                                        .actionGet();
                        }
                    }
                }
            }

            //Gets the search results
            SearchHit[] docs = res.getHits().getHits();

            //Closes the cluster
            node.close();

            Map<String,Object> map = new HashMap<String,Object>();
            List<Map<String,Object>> postList = new LinkedList<Map<String,Object>>();
            for (SearchHit sh : docs) {
                map = sh.getSource();
                postList.add(map);

            } //Puts all the results in a list to be treated in the mustache

            long hits = res.getHits().getTotalHits();

            if (hits > 0) { 
                attributes.put("found?",true);
                attributes.put("result",postList);
                attributes.put("result_count",hits);
            }

            return new ModelAndView(attributes,"searchPosts.mustache");
        },
            new MustacheTemplateEngine()
        );

        after((request, response) -> {
            Base.close();    
        });
    }
}
