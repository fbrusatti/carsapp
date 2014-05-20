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
        the(address.errors().get("street")).shouldBeEqual("value is missing");
        address.set("street", "Estrada");
        address.set("num", 2030);

        // Everything is good:
        the(address).shouldBe("valid");
    }

    @Test
    public void shouldValidateCreateAddress(){
        Address address = Address.createAddress("Estrada", 2030);
        the(address).shouldBe("valid");
        the(address).shouldNotBeNull();
        the(address).shouldContain("Estrada");
        the(address).shouldContain(2030);
    }

    //Creo un Address, luego la busco y me fijo si son el mismo Address
    //También creo un Address distinto, y 
    @Test
    public void shouldValidateFindByAddress(){
        Address a = Address.createAddress("Estrada", 2030);
        Address a3 = Address.createAddress("Rioja", 1023);
        Address a2 = Address.findByAddress("Estrada", 2030);
        the(a2.getString("street")).shouldBeEqual(a.getString("street"));  
        the(a2.getInteger("num")).shouldBeEqual(a.getInteger("num"));  
        the(a3.getString("street")).shouldNotBeEqual(a.getString("street"));   
        the(a3.getInteger("num")).shouldNotBeEqual(a.getInteger("num"));   
    }

    //verifico si un usuario(creado anteriormente) existe y luego busco un usuario inexistente
    @Test
    public void shouldValidateExistsAddress(){
        Address a = Address.createAddress("Estrada", 2030);
        the(Address.existAddress(a.getString("street"),a.getInteger("num"))).shouldBeTrue();
        the(Address.existAddress("Parana", 1024)).shouldBeFalse();
    }

    @Test
    public void shouldValidateDeleteAddress(){
      Address a = Address.createAddress("Estrada", 2030);
      the(Address.deleteAddress("Estrada", 2030)).shouldBeTrue();
      the(Address.deleteAddress("Another Street", 4567)).shouldBeFalse();
      the(Address.deleteAddress("Another Street", 2030)).shouldBeFalse();
      the(Address.deleteAddress("Estrada", 4567)).shouldBeFalse();

      
    } 
}