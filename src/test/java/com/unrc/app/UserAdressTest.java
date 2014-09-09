package com.unrc.app;

import com.unrc.app.models. *;


import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class UserAdressTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        System.out.println("UserTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("UserTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

 @Test
    public void shouldValidateMandatoryFields(){
        User user = new User();
        Post post = new Post();
        UsersAddress address = new  UsersAddress();
        the(user).shouldNotBe("valid");
        the(post).shouldNotBe("valid");
        the(address).shouldNotBe("valid");
        the(address.errors().get("id_user")).shouldBeEqual("value is missing");
        the(address.errors().get("id_address")).shouldBeEqual("value is missing");

        post.set("id_user","1","id_address","1","patent","kff911","description","es un gran coche");
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","contrasena","asd123");
        address.set("id_user","1","id_address","1");        
        

        // Everything is good:
        the(user).shouldBe("valid");
        the(post).shouldBe("valid");
        the(address).shouldBe("valid");
       
    }



}
