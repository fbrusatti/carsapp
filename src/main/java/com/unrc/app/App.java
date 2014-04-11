package com.unrc.app;

import org.javalite.activejdbc.Base;

import com.unrc.app.models.User;

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
        // user.set("dob", "1935-12-06");
        user.saveIt();

        User.createIt("first_name", "Marcelo", "last_name", "Uva");

        Base.close();
    }
}
