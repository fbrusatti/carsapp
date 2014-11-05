package com.unrc.app;

import com.unrc.app.models.Truck;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class TruckTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("TruckTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("TruckTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Truck truck = new Truck();
        the(truck).shouldNotBe("valid");

        the(truck.errors().get("type")).shouldBeEqual("value is missing");

        truck.set("type","medium");

        the(truck).shouldBe("valid");
    } 
}