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

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");

        User user = new User();
        user.set("first_name", "Marilyn");
        user.set("last_name", "Monroe");
        user.set("email","monroe@example.com");
        user.set("is_admin","1");  //True 
        // user.set("dob", "1935-12-06");
        user.saveIt();
        // get("/users",(request, response) -> {
        //     return user;
        // });

        user.createUser("Marcelo","Uva","uva@dc.exa.unrc.edu.ar");

        //vehicle stuff
        Vehicle vehicle = Vehicle.create("name", "Honda Accord","model","1999","km","32000");
        vehicle.saveIt();

        //car stuff
        Car car = Car.create ("type","sedan"); //there are errors of validation if created empty. That means that the field type is not "other" from default
        vehicle.add(car);

        //post stuff
        Post post = Post.create("price","15000","description","hello world");
        vehicle.add(post);
        user.add(vehicle); //the vehicle belong to this user
        user.add(post); //and is posted on this post (redundance)

        //question time!
        Question question = new Question();
        question.set("description", "hola?");
        //should check the user is not asking in self post

        user.add(question); //this user is asking
        post.add(question); //on this post


        //Spark section----------

        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        });
        after((request, response) -> {
            Base.close();    
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

        Base.close();
    }
}
