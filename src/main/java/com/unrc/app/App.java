package com.unrc.app;

import org.javalite.activejdbc.*;
import static spark.Spark.*;
import java.util.List;

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


        //List of users
        get("/users", (request,response) -> {
            List<User> userList = User.findAll();
            String list = new String();
            for (User u: userList) {
                list = list+u.getString("id")+" "+u.getString("first_name")+" "+u.getString("last_name")+"\n";
            }
            return list;
        });

        //List of vehicles
        get("/vehicles", (request,response) -> {
            List<Vehicle> vehicleList = Vehicle.findAll();
            String list = new String();
            for (Vehicle v: vehicleList) {
                User u = User.findById(v.getInteger("user_id"));
                list = list+v.getString("id")+" "+"Vehicle Name: "+v.getString("name")+"Model: "+v.getString("model")+"KM: "+v.getString("km")+"Belongs to:"+u.getString("first_name")+"\n";
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

        after((request, response) -> {
            Base.close();    
        });
    }
}
