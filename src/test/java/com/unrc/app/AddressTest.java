package com.unrc.app;

import com.unrc.app.models.Address;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AddressTest {
    
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "root");
        System.out.println("CityTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("CityTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Address city = new Address();

        the(city).shouldNotBe("valid");
        the(city.errors().get("country")).shouldBeEqual("value is missing");
        the(city.errors().get("state")).shouldBeEqual("value is missing");
        the(city.errors().get("name")).shouldBeEqual("value is missing");
        
        
        city.set("country","Argentina","state","Cordoba","name","La Carlota");

        the(city).shouldBe("valid");
    } 
}
