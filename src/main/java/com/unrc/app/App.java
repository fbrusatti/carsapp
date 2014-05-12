package com.unrc.app;

import org.javalite.activejdbc.Base;

import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Post;
import com.unrc.app.models.Question;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello cruel World!" );

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");

        User user = new User();
        user.set("first_name", "Marilyn");
        user.set("last_name", "Monroe");
        user.set("email","monroe@example.com");
        // user.set("dob", "1935-12-06");
        user.saveIt();

        User.createIt("first_name", "Marcelo", "last_name", "Uva","email","uva@dc.exa.unrc.edu.ar");

        //vehicle stuff
        Vehicle vehicle = Vehicle.create("name", "Honda Accord","model","1999","km","32000");
        vehicle.saveIt();

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

        Base.close();
    }
}
