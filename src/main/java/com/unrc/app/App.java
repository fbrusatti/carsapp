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

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");

        User user = new User();
        String nombre="gustavo";
        String apellido="martinez";
        String mail="mfwebdesign@gmail.com";
        User.createUser(nombre, apellido, mail);


        Base.close();
    }
}
