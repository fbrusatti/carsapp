package com.unrc.app;
import com.unrc.app.models.User;
import com.unrc.app.models.Post;
import com.unrc.app.models.Punctuation;
import com.unrc.app.models.Vehicle;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class PunctuationTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("PunctuationTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("PunctuationTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
 
    //Creo un auto invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFieldsPunctuation(){
        Punctuation r = new Punctuation();
        the(r).shouldNotBe("valid");
        the(r.errors().get("point_u")).shouldBeEqual("value is missing");
        r.set("point_u", 1,"id_user", 1,"id_user_receiver",2);

        // Everything is good:
        the(r).shouldBe("valid");
        
    }

  
    //creo dos autos y verifico si son o no iguales
    @Test
    public void shouldValidatefindByPunctuation(){
        User m = User.createUser("Jhony", "GUzman", "gm@gmail.com");
        Vehicle v = Vehicle.createVehicle("qwe123", "asd", "ford", m);

        Post p = Post.createPost("titulo", "descripcion", m, v); 
        Punctuation c1 = Punctuation.createPunctuation(1, p, m);
        Punctuation c2 = Punctuation.createPunctuation(-1,p,m);
        Punctuation c3 = Punctuation.findByPunctuation(c1.getInteger("id"));
        the(c1.getInteger("id")).shouldNotBeEqual(c2.getInteger("id"));  
        the(c1.getInteger("id")).shouldBeEqual(c3.getInteger("id"));  
    }

    //verifico si un auto(creado anteriormente) existe y luego busco un auto inexistente
    @Test
    public void shouldValidateExistPunctuation(){
        User u1 = User.createUser("Jhony","GUzman","gm@gmail.com");
        User u2 = User.createUser("ariel","GUzman","jf@gmail.com");
        Vehicle v = Vehicle.createVehicle("qwe123", "asd", "ford", u1);
        Post p = Post.createPost("t","d", u1, v); 
        Punctuation c1 = Punctuation.createPunctuation(1, p, u2);
        the(Punctuation.existPunctuation(p, u1)).shouldBeTrue();
        the(Punctuation.existPunctuation(p,u2)).shouldBeFalse();
    } 

   //creo un nuevo auto y verifico la consistencia de ese auto
    @Test
    public void shouldValidateCreatePunctuation(){
        User u1 = User.createUser("Jhony","GUzman","gm@gmail.com");
        User u2 = User.createUser("ariel","GUzman","jf@gmail.com");
        Vehicle v = Vehicle.createVehicle("qwe123", "asd", "ford", u1);
        Post p = Post.createPost("t","d", u1, v); 
        Punctuation c1 = Punctuation.createPunctuation(1, p, u2);
        the(c1).shouldBe("valid");
        the(c1).shouldNotBeNull();
        the(c1).shouldContain(1);
        the(c1).shouldContain(u2.getInteger("id"));
        the(c1).shouldContain(u1.getInteger("id"));
        User u3 = User.createUser("Jhony","GUzman","gmd@gmail.com");
        User u4 = User.createUser("ariel","GUzman","jfd@gmail.com");
        Post p2 = Post.createPost("t","d", u3, v); 
        the(c1).shouldNotContain(-1);
        the(c1).shouldNotContain(u3.getInteger("id")); 
        the(c1).shouldNotContain(u4.getInteger("id")); 

        
      
    } 
/**/

}   
