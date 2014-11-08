package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.Post;
import com.unrc.app.models.Question;
import com.unrc.app.models.Vehicle;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class QuestionTest{
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
        User user2 = new User();
        Vehicle vehicle = new Vehicle();
        Post post = new Post();
        Question question = new Question();
        the(question).shouldNotBe("valid");
        the(user).shouldNotBe("valid");
        the(post).shouldNotBe("valid");
        the(vehicle).shouldNotBe("valid");
        the(post.errors().get("id_user")).shouldBeEqual("value is missing");
        the(post.errors().get("id_address")).shouldBeEqual("value is missing");
        the(post.errors().get("patent")).shouldBeEqual("value is missing");
	the(post.errors().get("description")).shouldBeEqual("value is missing");

        post.set("id_user","1","id_address","1","patent","kff911","description","es un gran coche");
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","contrasena","asd123");
        user2.set("first_name", "Juan", "last_name", "hulk", "email", "example2@email.com","contrasena","asd456");
        vehicle.set("id_vehicle","1","patent","kff911","mark","ferrari","model","enzo","id_user","1","color","rojo","tipo","auto","cc","0","isCoupe","si","capacity","2");
        question.set("id_user","2","id_post","1","description","cual es el precio");
        
        

        // Everything is good:
        the(user).shouldBe("valid");
        the(post).shouldBe("valid");
        the(vehicle).shouldBe("valid");
        the(question).shouldBe("valid");
    }
}
