package com.unrc.app;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.*;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.unrc.app.models.Question;

public class QuestionTest {

	@Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
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
        the(question.errors().get("description")).shouldBeEqual("value is missing");
       
        question.set("description", "¿Qué motor tiene este vehículo?");

        // Everything is good:
        the(question).shouldBe("valid");
    }

}
