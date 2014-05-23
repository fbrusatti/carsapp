package com.unrc.app;

import com.unrc.app.models.Vehicle;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;


public class VehicleTest{
	
	@Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "root");
        System.out.println("VehicleTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("VehicleTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Vehicle vehicle = new Vehicle();

        the(vehicle).shouldNotBe("valid");
        the(vehicle.errors().get("model")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("color")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("km")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("mark")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("year")).shouldBeEqual("value is missing");

        vehicle.set("model", "Gol", "color","black", "km","0", "mark", "Volkswagen","year","2014");

        // Everything is good:
        the(vehicle).shouldBe("valid");
    }
}