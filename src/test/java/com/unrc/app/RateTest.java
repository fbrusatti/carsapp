package com.unrc.app;

import com.unrc.app.models.Rate;
import com.unrc.app.models.User;
import com.unrc.app.models.Post;
import com.unrc.app.models.Vehicle;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class RateTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("RateTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("RateTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
     
    //Creo un Rate invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFields(){
        User user = User.createUser("adrian", "tissera", "adriantissera@gmail.com");
        Vehicle vehicle = Vehicle.createVehicle("qwe123", "asd", "ford", user);
        Post post = Post.createPost("nuevopost1", "nuevadescripcion", user, vehicle);
        Rate rate = new Rate();

        the(rate).shouldNotBe("valid");
        the(rate.errors().get("stars")).shouldBeEqual("value is missing");
        rate.set("stars", 5, "id_post", post.getInteger("id"), "id_user", user.getInteger("id"));

        // Everything is good:
        the(rate).shouldBe("valid");
        
    }

    //creo tres rates y verifico si son o no iguales
    @Test
    public void shouldValidatefindById(){
        User user1 = User.createUser("adrian", "tissera", "adriantissera@gmail.com");
        User user2 = User.createUser("alejandro", "tissera", "alejandrotissera@gmail.com");
        Vehicle vehicle = Vehicle.createVehicle("qwe123", "asd", "ford", user1);
        Post post = Post.createPost("nuevopost1", "nuevadescripcion", user1, vehicle);
        Rate rate1 = Rate.createRate(1, post, user1);
        Rate rate2 = Rate.createRate(4, post, user2);
        Rate rate3 = Rate.findById(rate1.getId());
        the(rate1.getInteger("stars")).shouldBeEqual(rate3.getInteger("stars"));  
        the(rate2.getInteger("stars")).shouldNotBeEqual(rate3.getInteger("stars"));
    }


    //verifico si un rate(creado anteriormente) existe y luego busco un rate inexistente
    @Test
    public void shouldValidateExistRate(){
        User user = User.createUser("adrian", "tissera", "adriantissera@gmail.com");
        Vehicle vehicle = Vehicle.createVehicle("qwe123", "asd", "ford", user);
        Post post = Post.createPost("nuevopost1", "nuevadescripcion", user, vehicle);
        Rate rate = Rate.createRate(5, post, user);
        the(Rate.existRate(rate.getInteger("id"))).shouldBeTrue();
        the(Rate.existRate(rate.getInteger("id") + 1)).shouldBeFalse();;
    } 

    //creo un nuevo rate y verifico la consistencia de ese rate
    @Test
    public void shouldValidateCreateRate(){
        User user = User.createUser("adrian", "tissera", "adriantissera@gmail.com");
        Vehicle vehicle = Vehicle.createVehicle("qwe123", "asd", "ford", user);
        Post post = Post.createPost("nuevopost1", "nuevadescripcion", user, vehicle);
        Rate rate = Rate.createRate(3, post, user);
        the(rate).shouldBe("valid");
        the(rate).shouldNotBeNull();
        the(rate).shouldContain(rate.getInteger("id"));
        the(rate).shouldNotContain(rate.getInteger("id")-1);
    } 
}