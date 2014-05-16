package com.unrc.app;

import com.unrc.app.models.User;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class UserTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("UserTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("UserTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
     
    //Creo un User invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFields(){
        User user = new User();

        the(user).shouldNotBe("valid");
        the(user.errors().get("first_name")).shouldBeEqual("value is missing");
        the(user.errors().get("last_name")).shouldBeEqual("value is missing");
        the(user.errors().get("email")).shouldBeEqual("value is missing");
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com");

        // Everything is good:
        the(user).shouldBe("valid");
        
    }

    @Test
    public void shouldValidatefindByEmail(){
        User a = User.createUser("Jhony","GUzman","gm@gmail.com");
        User a3 = User.createUser("claudio","GUzman","gma@gmail.com");
        User a2 = User.findByEmail("gm@gmail.com");
        the(a2.getString("email")).shouldBeEqual(a.getString("email"));  
        the(a3.getString("email")).shouldNotBeEqual(a.getString("email"));   
    }

    @Test
    public void shouldValidateExistUser(){
        User a = User.createUser("cludio","toresani","gmaa@gmail.com");
        the(User.existUser(a.getString("email"))).shouldBeTrue();
        the(User.existUser("gustavito")).shouldBeFalse();
    } 

    @Test
    public void shouldValidateCreateUser(){
        User a = User.createUser("abc","def","ghi@gmail.com");
        the(a).shouldBe("valid");
        the(a).shouldNotBeNull();
        the(a).shouldContain("abc");
        the(a).shouldContain("def");
        the(a).shouldContain("ghi@gmail.com");
      
    }  
    
}    
      
    
    