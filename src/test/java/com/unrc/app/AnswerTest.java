package com.unrc.app;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.*;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.unrc.app.models.Answer;

public class AnswerTest {

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
        Answer answer = new Answer();

        the(answer).shouldNotBe("valid");
        the(answer.errors().get("description")).shouldBeEqual("value is missing");
       
        answer.set("description", "Tiene un motor 1.6");

        // Everything is good:
        the(answer).shouldBe("valid");
    }

}
