package com.unrc.app;

import com.unrc.app.models.Answer;
import com.unrc.app.models.Question;
import com.unrc.app.models.User;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class AnswerTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("answerTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("AnswerTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Answer answer = new Answer();
        the(answer).shouldNotBe("valid");
        the(answer.errors().get("description")).shouldBeEqual("value is missing");
        answer.set("description","esto es una respuesta valida","id_users",1,"id_question",1);
        the(answer).shouldBe("valid");
    } 

    //creo dos respuestas y verifico si son o no iguales
    @Test
    public void shouldValidatefindByAnswer(){
        User m = User.createUser("Jhony","GUzman","gm@gmail.com");
        Question p= Question.createQuestion("a cuanto vende el auto?" , m);
        Answer a1= Answer.createAnswer("respuesta1" , m, p);
        Answer a2= Answer.createAnswer("respuesta2" , m, p);
        Answer a3 = Answer.findByAnswer(a1.getInteger("id"));
        the(a1.getInteger("id")).shouldNotBeEqual(a2.getInteger("id"));  
        the(a1.getInteger("id")).shouldBeEqual(a3.getInteger("id")); 
    }

    //verifico si una respuesta(creada anteriormente) existe y luego busco una respuesta inexistente
    @Test
    public void shouldValidateExistAnswer(){
        User m = User.createUser("Jhony","GUzman","gm@gmail.com");
        Question p= Question.createQuestion("a cuanto vende el auto?" , m);
        Answer a1= Answer.createAnswer("respuesta1" , m, p);
        the(Answer.existAnswer(a1.getInteger("id"))).shouldBeTrue();
        the(Answer.existAnswer(120)).shouldBeFalse();
    } 

    //creo una nueva respuesta y verifico la consistencia de esa respuesta
    @Test
    public void shouldValidateCreateAnswer(){
        User m = User.createUser("Jhony","GUzman","gm@gmail.com");
        Question p= Question.createQuestion("a cuanto vende el auto?" , m);
        Answer a1= Answer.createAnswer("respuesta1" , m, p);
        the(a1).shouldBe("valid");
        the(a1).shouldNotBeNull();
        the(a1).shouldContain("respuesta1");
        the(a1).shouldContain(m.getInteger("id"));
        the(a1).shouldContain(p.getInteger("id"));
        the(a1).shouldNotContain("respuesta2");
       
    } 

    //creo una respuesta y luego intento eliminarla ,luego intento eliminar una respuesta inexistente
      @Test
    public void shouldValidateDeleteAnswer(){
     User m = User.createUser("Jhony","GUzman","gm@gmail.com");
     Question p= Question.createQuestion("a cuanto vende el auto?" , m);
     Answer a1= Answer.createAnswer("respuesta1" , m, p);
     the(Answer.deleteAnswer(a1.getInteger("id"))).shouldBeTrue();
     the(Answer.deleteAnswer(120)).shouldBeFalse();
      
    } 

}