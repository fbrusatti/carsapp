package com.unrc.app;

import org.javalite.activejdbc.Base;

import com.unrc.app.models.Post;
import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;

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

        //User.createIt("first_name", "Marcelo", "last_name", "Uva");
        User user = new User();
        user.set("first_name", "Marcelo");
        user.set("last_name", "Uva");
        user.saveIt();
        Vehicle vehicle = new Vehicle();
        vehicle.set("brand", "Ford");
        vehicle.set("model", "Focus");
        vehicle.set("year", "2010");
       
        vehicle.saveIt();
        user.add(vehicle);
        
        Post post = new Post();
        post.set("title", "Vendo Ford Focus");
        post.set("description", "Excelente estado");
        post.saveIt();
        
        user.add(post);
        vehicle.add(post);
        
        Base.close();
    }  
}
