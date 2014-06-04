package com.unrc.app;

import org.javalite.activejdbc.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.TemplateEngine;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
            response.type("text/html");
            return WebStuff.link("Menu","http://localhost:4567/users/new","Alta usuario");
        });

        get("/users/new" , (request, response) ->{
            response.type("text/html");
            String[] a={"name","lastname","email"};
            String form = WebStuff.form("Create User",a,"/users","post");
            return form;
        });

        get("/users/add/vehicles" , (request, response) ->{
            response.type("text/html");
            String[] data = {"Name","Model (YYYY)","KM (in numbers)","User name"};
            String form = WebStuff.form("Add Vehicle",data,"/vehicles","post");
            return form;
        });

        post ("/vehicles",(request, response) ->{ 
            String name = request.queryParams("Name");
            String model = request.queryParams("Model (YYYY)");
            String km = request.queryParams("KM (in numbers)"); //should take integers in the form
            String user = request.queryParams("User name"); //later we should use the id of the user logged
            Vehicle v = Vehicle.create("name", name, "model", model,"km",km);
            User u = User.findFirst("first_name = ?",user);
            u.add(v);
            u.saveIt();//writes to te DB
            response.redirect("/vehicles");
            return "success"; 
        });

        post ("/users",(request, response) ->{ 
            String name = request.queryParams("name");
            String lastname = request.queryParams("lastname");
            String email = request.queryParams("email");
            User.createIt("first_name", name, "last_name", lastname,"email",email,"is_admin","0"); //acá iria nuestro metodo de creacion
            response.redirect("/users");
            return "success"; 
        });


        // Form Add Address
        get("/users/add/addresses" , (request, response) ->{
            response.type("text/html");
            String[] data = {"User name","Street","Address number"};
            String form = WebStuff.form("Add Address",data,"/addresses","post");
            return form;
        });

        //Add User Address
        post ("/addresses",(request, response) ->{ 
            String user = request.queryParams("User name");
            String street = request.queryParams("Street");
            String number = request.queryParams("Address number");
            Address a = Address.create("street", street, "address_number", number);
            User u = User.findFirst("first_name = ?",user);
            u.add(a);
            u.saveIt();
            response.redirect("/addresses");
            return "success";
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

        //List of vehicles
        get("/vehicles", (request,response) -> {
            List<Vehicle> vehicleList = Vehicle.findAll();
            String list = new String();
            for (Vehicle v: vehicleList) {
                User u = User.findById(v.getInteger("user_id"));
                list = list+v.getString("id")+" "+"Vehicle Name: "+v.getString("name")+" "+"Model: "+v.getString("model")+" "+"KM: "+v.getString("km")+"Belongs to:"+u.getString("first_name")+"\n";
            }
            return list;
        });
        
        //List of Posts
        get("/posts", (request,response) -> {
            List<Post> postList = Post.findAll();
            String list = new String();
            for (Post p: postList) {
                User u = User.findById(p.getInteger("user_id"));
                Vehicle v = Vehicle.findById(p.getInteger("vehicle_id"));
                list = list+p.getString("id")+" "+"Vehicle Name: "+v.getString("name")+" "+"Model: "+v.getString("model")+" "+"KM: "+v.getString("km")+" "+"Price: "+p.getString("price")+" "+"Description: "+p.getString("description")+" "+"Belongs to:"+u.getString("first_name")+"\n";
            }
            return list;
        });

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

        //Show Users 
        get("/users/:id", (request, response) -> {
            User u = User.findById(Integer.parseInt(request.params(":id")));
            String name = u.getString("first_name") +" "+ u.getString("last_name");
            String email = u.getString("email");
            return "User: "+name+"\n"+"Email: "+email+"\n";
        });

        //Show Vehicles 
        get("/vehicles/:id", (request, response) -> {
            Vehicle v = Vehicle.findById(Integer.parseInt(request.params(":id")));
            String vehicleName = v.getString("name") +" "+ v.getString("model");
            String km = v.getString("km");
            User u1 = User.findById(v.getInteger("user_id"));
            String userName = u1.getString("first_name");
            return "Vehicle: " + vehicleName+"\n"+"Belongs to: "+userName;
        });

        //Show Posts
        get("/posts/:id", (request, response) -> {
            Post p = Post.findById(Integer.parseInt(request.params(":id")));
            return "Post: " + p.toString();
        });

        //Show Questions 
        get("/questions/:id", (request, response) -> {
            Question q = Question.findById(Integer.parseInt(request.params(":id")));
            return "Question: " + q.toString();
        });

        //Show Answers
        get("/answers/:id", (request, response) -> {
            Answer a = Answer.findById(Integer.parseInt(request.params(":id")));
            return "Answer: " + a.toString();
        });

        //Show Address
        get("/addresses/:id", (request, response) -> {
            Address a = Address.findById(Integer.parseInt(request.params(":id")));
            User u = User.findById(a.getInteger("user_id"));
            String address = a.getString("street")+" "+a.getString("address_number");
            return "Address: "+ u.getString("first_name")+" "+address;
        });

        after((request, response) -> {
            Base.close();    
        });
    }
}
