package com.unrc.app;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.*;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.unrc.app.models.City;

public class CityTest {

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
        City city = new City();

        the(city).shouldNotBe("valid");
        the(city.errors().get("name")).shouldBeEqual("value is missing");
       
        city.set("name", "Río Cuarto");

        // Everything is good:
        the(city).shouldBe("valid");
    }

}
