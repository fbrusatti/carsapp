package com.unrc.app;
import com.unrc.app.models.Question;
import com.unrc.app.models.*;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class QuestionTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("QuestionTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("QuestionTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
 
    //Creo una pregunta invalida y la corroboro 
    @Test
    public void shouldValidateMandatoryFieldsQuestion(){
        Question p = new Question();
        the(p).shouldNotBe("valid");
        the(p.errors().get("description")).shouldBeEqual("value is missing");
        p.set("description", "a cuanto vende el auto?","id_users", 1, "id_posts", 1);

        // Everything is good:
        the(p).shouldBe("valid");
        
    }
  
    //creo dos preguntas y verifico si son o no iguales
    @Test
    public void shouldValidatefindByQuestion(){
        User user = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle vehicle = Vehicle.createVehicle("ghg345","corsa","chevrolet",user);
        Post post = Post.createPost("nuevopost1", "nuevadescripcion", user, vehicle);
        Question c1= Question.createQuestion("a cuanto vende el auto?" , user, post);
        Question c2= Question.createQuestion("cuanto sale la moto?" , user, post);
        Question c3 = Question.findByQuestion(c1.getInteger("id"));
        the(c1.getInteger("id")).shouldNotBeEqual(c2.getInteger("id"));  
        the(c1.getInteger("id")).shouldBeEqual(c3.getInteger("id")); 
    }


    //verifico si una pergunta(creado anteriormente) existe y luego busco una pregunta inexistente
    @Test
    public void shouldValidateExistQuestion(){
        User user = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle vehicle = Vehicle.createVehicle("ghg345","corsa","chevrolet",user);
        Post post = Post.createPost("nuevopost1", "nuevadescripcion", user, vehicle);
        Question p= Question.createQuestion("pregunta1" , user, post);
        the(Question.existQuestion(p.getInteger("id"))).shouldBeTrue();
        the(Question.existQuestion(654)).shouldBeFalse();
    } 

   //creo una nueva pregunta y verifico la consistencia de esa pregunta
    @Test
    public void shouldValidateCreateQuestion(){
        User user = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle vehicle = Vehicle.createVehicle("ghg345","corsa","chevrolet",user);
        Post post = Post.createPost("nuevopost1", "nuevadescripcion", user, vehicle);
        Question p = Question.createQuestion("pregunta1" , user, post);
        the(p).shouldBe("valid");
        the(p).shouldNotBeNull();
        the(p).shouldContain("pregunta1");
        the(p).shouldContain(user.getInteger("id"));
        the(p).shouldNotContain("pregunta2");
    } 


    //creo una pregunta y luego intento eliminarla ,luego intento eliminar una pregunta inexistente
      @Test
    public void shouldValidateDeleteQuestion(){
        User user = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle vehicle = Vehicle.createVehicle("ghg345","corsa","chevrolet",user);
        Post post = Post.createPost("nuevopost1", "nuevadescripcion", user, vehicle);
        Question t = Question.createQuestion("pregunta1", user, post);
        the(Question.deleteQuestion(t.getInteger("id"))).shouldBeTrue();
        the(Question.deleteQuestion(69)).shouldBeFalse();
      
    } 
/*    */

}   
