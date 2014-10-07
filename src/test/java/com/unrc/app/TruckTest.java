package com.unrc.app;

import com.unrc.app.models.Truck;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TruckTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "root");
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
        the(truck.errors().get("brake_system")).shouldBeEqual("value is missing");
        the(truck.errors().get("direction")).shouldBeEqual("value is missing");
        the(truck.errors().get("capacity")).shouldBeEqual("value is missing");
        
        
        truck.set("brake_system","Aire","direction","Hidraulica","capacity","2");

        the(truck).shouldBe("valid");
    } 
}
