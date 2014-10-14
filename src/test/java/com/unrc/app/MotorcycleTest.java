package com.unrc.app;

import com.unrc.app.models.Motorcycle;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class MotorcycleTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("MotorcycleTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("MotorcycleTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Motorcycle motorcycle = new Motorcycle();
        the(motorcycle).shouldNotBe("valid");

        the(motorcycle.errors().get("type")).shouldBeEqual("value is missing");

        motorcycle.set("type","sport");

        the(motorcycle).shouldBe("valid");
    } 
}