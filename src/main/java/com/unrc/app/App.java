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
        
        get("/hello",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "hello.moustache");
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
            return new ModelAndView(attributes, "vehiclesAdd.moustache");
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

        post ("/users",(request, response) ->{ 
            User admin = User.findFirst("email = ?",request.queryParams("admin")); //search if the user creating the user is an admin
            String message = new String();
            if (!(admin.getBoolean("is_admin"))) {
                message = "fail";
            } else {
                String name = request.queryParams("name");
                String lastname = request.queryParams("lastname");
                String email = request.queryParams("email");
                admin.createUser(name,lastname,email); 
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
            User u = User.findById(Integer.parseInt(request.params(":id")));
            String name = u.getString("first_name") +" "+ u.getString("last_name");
            String email = u.getString("email");
            return "User: "+name+"\n"+"Email: "+email+"\n";
        });

        /*----------------------ADDRESS STUFF-----------------*/

        //Add User Address
        post ("/addresses",(request, response) ->{ 
            String user = request.queryParams("user");
            String street = request.queryParams("street");
            String number = request.queryParams("number");
            Address a = Address.create("street", street, "address_number", number);
            User u = User.findFirst("email = ?",user);
            u.add(a);
            u.saveIt();
            response.redirect("/addresses");
            return "success";
        });

        //List of Addresses
        get("/addresses", (request,response) -> {
            List<Address> addressList = Address.findAll();
            String list = new String();
            for (Address a: addressList) {
                User u = User.findById(a.getInteger("user_id"));
                list = list+"User Name: "+u.getString("first_name")+" "+"Street: "+a.getString("street")+" "+"Address Number: "+a.getString("address_number")+"\n";
            }
            return list;
        });


        //Show Address
        get("/addresses/:id", (request, response) -> {
            Address a = Address.findById(Integer.parseInt(request.params(":id")));
            User u = User.findById(a.getInteger("user_id"));
            String address = a.getString("street")+" "+a.getString("address_number");
            return "Address: "+ u.getString("first_name")+" "+address;
        });

        /*----------------------POST STUFF-----------------*/

        //Show Posts
        get("/posts/:id", (request, response) -> {
            Post p = Post.findById(Integer.parseInt(request.params(":id")));
            return "Post: " + p.toString();
        });

        //List of Posts
        get("/posts",(request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            List<Post> posts = Post.findAll();
            attributes.put("posts_count", posts.size());
            attributes.put("posts", posts);
            return new ModelAndView(attributes, "posts.moustache");
        },
            new MustacheTemplateEngine()
        );

        /*----------------------VEHICLE STUFF----------------*/

        post ("/vehicles",(request, response) ->{ 
            String name = request.queryParams("name");
            String model = request.queryParams("model");
            String km = request.queryParams("km"); //should take integers in the form
            String user = request.queryParams("user"); //later we should use the id of the user logged
            Vehicle v = Vehicle.create("name", name, "model", model,"km",km);
            User u = User.findFirst("email = ?",user);
            u.add(v);
            u.saveIt();//writes to te DB
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
        get("/search/users",(request,response) -> {
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes,"search.moustache");
        }, 
            new MustacheTemplateEngine()
        );

        //Show a search
        post("/search/users", (request,response) -> {
            Map<String, Object> attributes = new HashMap<>();
            String query = request.queryParams("carsappsearch");

            Node node = nodeBuilder().local(true).clusterName("carsapp").node();
            Client client = node.client();

            ClusterHealthResponse health = client.admin()
                                            .cluster()
                                            .prepareHealth()
                                            .setWaitForGreenStatus()
                                            .execute()
                                            .actionGet();

            SearchResponse res = client.prepareSearch("users")
                                        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                                        .setQuery(QueryBuilders.termQuery("name",query))
                                        .execute()
                                        .actionGet();

            SearchHit[] docs = res.getHits().getHits();


            node.close();

            Map<String,Object> map;
            List<String> userList = new LinkedList<String>();
            for (SearchHit sh : docs) {
                map = sh.getSource();
                userList.add((String) map.get("name"));

            }

            attributes.put("result",userList);

            return new ModelAndView(attributes,"search.moustache");
        },
            new MustacheTemplateEngine()
        );

        after((request, response) -> {
            Base.close();    
        });
    }
}
