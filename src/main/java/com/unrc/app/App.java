package com.unrc.app;

import org.javalite.activejdbc.*;
import static spark.Spark.*;
import java.util.List;

import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Post;
import com.unrc.app.models.Car;
import com.unrc.app.models.Question;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //System.out.println( "Hello cruel world!" );
        // get("/hello",(request, response) -> {
        //     return "Hello World!";
        // });

        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        });

        //Users url
        get("/user/:id", (request, response) -> {
            User u = User.findById(Integer.parseInt(request.params(":id")));
            return "User: " + u.toString();
        });

        //Vehicles url
        get("/vehicle/:id", (request, response) -> {
            Vehicle v = Vehicle.findById(Integer.parseInt(request.params(":id")));
            return "Vehicle: " + v.toString();
        });

        //Posts url
        get("/post/:id", (request, response) -> {
            Post p = Post.findById(Integer.parseInt(request.params(":id")));
            return "Post: " + p.toString();
        });

        after((request, response) -> {
            Base.close();    
        });
    }
}
