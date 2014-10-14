package com.unrc.app;

import com.unrc.app.models.Question;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class QuestionTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "root");
        System.out.println("QuestionTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("QuestionTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Question question = new Question();

        the(question).shouldNotBe("valid");
        the(question.errors().get("text")).shouldBeEqual("value is missing");
        
        
        question.set("text","cuanto cuesta el envio? Saludos");

        the(question).shouldBe("valid");
    } 
}
