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

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {                           //code to open browser in hello url. problem, it loads without the content of spark
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
            }


        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        });

        get("/",(request, response) -> {
            response.redirect("/hello");
            return "Welcome";
        }); 
        
        get("/hello",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "hello.mustache");
        },
            new MustacheTemplateEngine()
        );

        /*------------------------USER STUFF----------------------------*/
        get("/users/new" , (request, response) ->{
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "usersNew.moustache");
        },
            new MustacheTemplateEngine()
        );

        get("/users/add/vehicles" , (request, response) ->{
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("selectType",true); //This is to select the type of vehicle without seeing the other part of loading the vehicle
            return new ModelAndView(attributes, "vehiclesAdd.moustache");
        },
            new MustacheTemplateEngine()
        );

        post("/user/add/vehicles" , (request, response) ->{
            Map<String, Object> attributes = new HashMap<>();
            String type = request.queryParams("vehicleType");
            System.out.println(type);
            if (type.equals("car")) {
                attributes.put("car",true);
            }if (type.equals("truck")) {
                attributes.put("truck",true);
            }if (type.equals("moto")) {
                attributes.put("moto",true);
            }if (type.equals("other")) {
                attributes.put("other",true);
            };
            attributes.put("load",true); //This is to select the type of vehicle without seeing the other part of loading the vehicle
            
            return new ModelAndView(attributes,"vehiclesAdd.moustache");
        },
            new MustacheTemplateEngine()
        );

        // Form Add Address
        get("/users/add/addresses" , (request, response) ->{
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "addressAdd.moustache");
        },
            new MustacheTemplateEngine()
        );

        // Form Add Question
        get("/users/add/question" , (request,response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "questionAdd.mustache");
        },
            new MustacheTemplateEngine()
        );

        //From to create a Post
        get("/users/add/post" , (request,response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "postAdd.mustache");
        },
            new MustacheTemplateEngine()
        );

        post("/users/add/post" , (request,response) -> {
            Map<String, Object> attributes = new HashMap<>();

            String email = request.queryParams("login");
            User u = User.findFirst("email = ?",email);
            List<Vehicle> vehicles = Vehicle.where("user_id = ?", u.id());

            attributes.put("vehicles",vehicles);
            attributes.put("userId",u.id());

            return new ModelAndView(attributes, "postAdd.mustache");
        },
            new MustacheTemplateEngine()
        );

        post("/posts" , (request,response) -> {
            User user = User.findById(request.queryParams("userId"));
            Vehicle vehicle = Vehicle.findById(request.queryParams("vehicle"));
            user.addPost(request.queryParams("price"),request.queryParams("description"),vehicle);
            response.redirect("/posts");
            return "success";
        });



        /** **** **/
        post ("/users",(request, response) ->{ 
            User admin = User.findFirst("email = ?",request.queryParams("admin")); //search if the user creating the user is an admin
            String message = new String();
            if (!(admin.getBoolean("is_admin"))) {
                message = "fail";
            } else {
                String name = request.queryParams("name");
                String lastname = request.queryParams("lastname");
                String email = request.queryParams("email");
                String street = request.queryParams("street");
                String address_number = request.queryParams("address_number");
                admin.createUser(name,lastname,email,street,address_number); 
                message = "success"; 
            }
            response.redirect("/hello");
            return message;
        });

        //List of users
        get("/users",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<User> users = User.findAll();
            attributes.put("users_count", users.size());
            attributes.put("users", users);
            return new ModelAndView(attributes, "users.moustache");
        },
            new MustacheTemplateEngine()
        );

        
        //Show Users 
        get("/users/:id", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            User user = User.findById(Integer.parseInt(request.params(":id")));
            if (user == null) {
                response.redirect("/whoops", 404);
                return new ModelAndView(attributes, "userId.mustache"); //only for compiling purposes
            }
            else {
                String address = user.address();
                attributes.put("user", user);
                attributes.put("address_user", address);
                return new ModelAndView(attributes, "userId.mustache");
            }
        },
            new MustacheTemplateEngine()
        );

        get("/whoops", (request, response) -> {
            return "not found";
        });

        /*----------------------POST STUFF-----------------*/

        //Show Posts
        get("/posts/:id", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            Post post = Post.findById(Integer.parseInt(request.params(":id")));
            List<Question> questions = Question.where("post_id = ?", post.id());
            attributes.put("vehicle_name", post.vehicle().name());
            attributes.put("post", post);
            attributes.put("questions", questions);
            return new ModelAndView(attributes, "postId.mustache");
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

        /*----------------------VEHICLE STUFF----------------*/

        post ("/vehicles",(request, response) ->{ 
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
            }if (type.equals("car")) {
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
            return new ModelAndView(attributes, "vehicles.moustache");
        },
            new MustacheTemplateEngine()
        );
        
        //Show Vehicles 
        get("/vehicles/:id", (request, response) -> {
            Vehicle v = Vehicle.findById(Integer.parseInt(request.params(":id")));
            String vehicleName = v.getString("name") +" "+ v.getString("model");
            String km = v.getString("km");
            User u1 = User.findById(v.getInteger("user_id"));
            String userName = u1.getString("first_name");
            return "Vehicle: " + vehicleName+"\n"+"Belongs to: "+userName;
        });

        /*----------------------QUESTION STUFF----------------*/

        
        //List of Questions
        get("/questions", (request,response) -> {
            List<Question> questionList = Question.findAll();
            String list = new String();
            for (Question q: questionList) {
                User u = User.findById(q.getInteger("user_id"));
                Post p = Post.findById(q.getInteger("post_id"));
                list = list+q.getString("id")+" "+"User Name: "+u.getString("first_name")+" "+"Post: "+p.getString("id")+" "+"Question: "+q.getString("description")+"\n";
            }
            return list;
        });

        post ("/questions",(request, response) ->{ 
            String description = request.queryParams("description");
            String post = request.queryParams("postId");
            String user = request.queryParams("user"); //later we should use the id of the user logged
            User u = User.findFirst("email = ?",user);
            u.addQuestion(description,post);
            response.redirect("/posts");
            return "success"; 
        });

        //Show Questions 
        get("/questions/:id", (request, response) -> {
            Question q = Question.findById(Integer.parseInt(request.params(":id")));
            return "Question: " + q.toString();
        });

        /*----------------------ANSWER STUFF----------------*/

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


        //Show Answers
        get("/answers/:id", (request, response) -> {
            Answer a = Answer.findById(Integer.parseInt(request.params(":id")));
            return "Answer: " + a.toString();
        });
        
        /*----------------------SEARCH STUFF----------------*/
        //Show a search bar
        get("/search/users",(request,response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("found?",false);
            return new ModelAndView(attributes,"searchUsers.mustache");
        }, 
            new MustacheTemplateEngine()
        );

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


        //Show a search bar for posts
        get("/search/posts",(request,response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("found?",false);
            return new ModelAndView(attributes,"searchPosts.mustache");
        }, 
            new MustacheTemplateEngine()
        );

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
                //Executes the search without the price range
                res = client.prepareSearch("posts")
                            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                            .setQuery(QueryBuilders.matchQuery("description",description))
                            .execute()
                            .actionGet();
            } else {
                if (minPrice.equals("") && !(maxPrice.equals(""))) {
                    //Executes the search only with minimun price range
                    res = client.prepareSearch("posts")
                                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                .setQuery(QueryBuilders.rangeQuery("price")
                                                        .lte(maxPrice))
                                .execute()
                                .actionGet();
                } else {
                    if (!(minPrice.equals("")) && maxPrice.equals("")) {
                        //Executes the search only with maximun price range
                        res = client.prepareSearch("posts")
                                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                    .setQuery(QueryBuilders.rangeQuery("price")
                                                            .gte(minPrice))
                                    .execute()
                                    .actionGet();
                    } else {
                        if (!(minPrice.equals("")) && !(maxPrice.equals(""))) {
                            //Executes the search with both price ranges
                            res = client.prepareSearch("posts")
                                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                        .setQuery(QueryBuilders.rangeQuery("price")
                                                                .from(minPrice)
                                                                .to(maxPrice)
                                                                .includeLower(true)
                                                                .includeUpper(true))
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
