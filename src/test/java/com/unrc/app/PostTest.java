package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.Post;
import com.unrc.app.models.Vehicle;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class PostTest{
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
        Vehicle vehicle = new Vehicle();
        Post post = new Post();
        the(user).shouldNotBe("valid");
        the(post).shouldNotBe("valid");
        the(vehicle).shouldNotBe("valid");
        the(post.errors().get("id_user")).shouldBeEqual("value is missing");
        the(post.errors().get("id_address")).shouldBeEqual("value is missing");
        the(post.errors().get("patent")).shouldBeEqual("value is missing");
	the(post.errors().get("description")).shouldBeEqual("value is missing");

        post.set("id_user","1","id_address","1","patent","kff911","description","si es un buen coche");
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","contrasena","asd123");
        vehicle.set("id_vehicle","1","patent","kff911","mark","ferrari","model","enzo","id_user","1","color","rojo","tipo","auto","cc","0","isCoupe","si","capacity","2");
        

        // Everything is good:
        the(user).shouldBe("valid");
        the(post).shouldBe("valid");
        the(vehicle).shouldBe("valid");
    }
}
