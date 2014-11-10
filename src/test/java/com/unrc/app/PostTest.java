package com.unrc.app;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.*;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.unrc.app.models.Post;

public class PostTest {

	@Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("PostTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("PostTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Post post = new Post();

        the(post).shouldNotBe("valid");
        the(post.errors().get("title")).shouldBeEqual("value is missing");
        the(post.errors().get("description")).shouldBeEqual("value is missing");
        
        post.set("title", "Vendo F", "description", "Excelente");

        // Everything is good:
        the(post).shouldBe("valid");
    }

}
