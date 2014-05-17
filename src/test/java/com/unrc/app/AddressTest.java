package com.unrc.app;

import com.unrc.app.models.User;
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
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
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
        the(address.errors().get("direction")).shouldBeEqual("value is missing");
        address.set("direction", "Estrada 2030");

        // Everything is good:
        the(address).shouldBe("valid");
    }

    @Test
    public void shouldValidateCreateAddress(){
        Address address = Address.createAddress("Estrada 2030");
        the(address).shouldBe("valid");
        the(address).shouldNotBeNull();
        the(address).shouldContain("Estrada 2030");
    }

    /*Creo un Address, luego la busco y me fijo si son el mismo Address*/
    /*También creo un Address distinto, y */
    @Test
    public void shouldValidateFindByAddress(){
        Address a = Address.createAddress("Estrada 2030");
        Address a3 = Address.createAddress("Rioja 1023");
        Address a2 = Address.findByAddress("Estrada 2030");
        the(a2.getString("direction")).shouldBeEqual(a.getString("direction"));  
        the(a3.getString("direction")).shouldNotBeEqual(a.getString("direction"));   
    }

    //verifico si un usuario(creado anteriormente) existe y luego busco un usuario inexistente
    @Test
    public void shouldValidateExistsAddress(){
        Address a = Address.createAddress("Estrada 2030");
        the(Address.existAddress(a.getString("direction"))).shouldBeTrue();
        the(Address.existAddress("Parana 1024")).shouldBeFalse();
    }

}