package com.unrc.app;

import com.unrc.app.models.Car;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CarTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "root");
        System.out.println("CarTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("CarTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Car car = new Car();

        the(car).shouldNotBe("valid");
        the(car.errors().get("doors")).shouldBeEqual("value is missing");
        the(car.errors().get("version")).shouldBeEqual("value is missing");
        the(car.errors().get("transmission")).shouldBeEqual("value is missing");
        the(car.errors().get("direction")).shouldBeEqual("value is missing");
        
        car.set("doors","3","version","15","transmission","Manual","direction","Hidraulica");

        the(car).shouldBe("valid");
    }
    
}
