package com.unrc.app;

import com.unrc.app.models.Address;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class AddressTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("AddressTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("AddressTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Address address = new Address();

        the(address).shouldNotBe("valid");
        the(address.errors().get("street")).shouldBeEqual("value is missing");
        the(address.errors().get("address_number")).shouldBeEqual("value is missing");

        address.set("street","streetName","address_number","42");
        // Everything is good:
        the(address).shouldBe("valid");
    }
}
