package com.unrc.app;

import com.unrc.app.models.Address;
import com.unrc.app.models.User;

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
        address.set("street", "Lincoln");
        address.set("num", 874);

        // Everything is good:
        the(address).shouldBe("valid");
    }

    @Test
    public void shouldValidateCreateAddress(){
        User u = User.createUser("Didier","Drogba","tito_drogba@hotmail.com");
        Address address = Address.createAddress("Lincoln", 874,u);
        the(address).shouldBe("valid");
        the(address).shouldNotBeNull();
        the(address).shouldContain("Lincoln");
        the(address).shouldContain(874);
    }

    //Creo un Address, luego la busco y me fijo si son el mismo Address
    //También creo un Address distinto, y 
    @Test
    public void shouldValidateFindByAddress(){
        User u = User.createUser("Didier","Drogba","tito_drogba@hotmail.com");
        Address a = Address.createAddress("Lincoln", 874,u);
        Address a3 = Address.createAddress("Rioja", 1023,u);
        Address a2 = Address.findByAddress("Lincoln", 874);
        the(a2.getString("street")).shouldBeEqual(a.getString("street"));  
        the(a2.getInteger("num")).shouldBeEqual(a.getInteger("num"));  
        the(a3.getString("street")).shouldNotBeEqual(a.getString("street"));   
        the(a3.getInteger("num")).shouldNotBeEqual(a.getInteger("num"));   
    }

    //verifico si una direccion(creada anteriormente) existe y luego busco una direccion inexistente
    @Test
    public void shouldValidateExistsAddress(){
        User u = User.createUser("Didier","Drogba","tito_drogba@hotmail.com");
        Address a = Address.createAddress("Lincoln", 874,u);
        the(Address.existAddress(a.getString("street"),a.getInteger("num"))).shouldBeTrue();
        the(Address.existAddress("Parana", 1024)).shouldBeFalse();
    }

    //Creo un usuario y una direccion asociada a este usuario("Didier Drogba -> Lincoln 874").
    //Intento eliminar Lincoln 874, lo cual NO se puede (tiene un usuario asociado)
    //Asocio una nueva direccion al usuario (Didier Drogba -> San Martin 1654)
    //Elimino la relacion entre Didier Drogba -> San Martin 1654
    //Intento eliminar San Martin 1654, lo cual SI se puede (NO tiene ningun usuario asociado)
    @Test
    public void shouldValidateDeleteAddress(){        
      User u = User.createUser("Didier","Drogba","tito_drogba@hotmail.com");
      Address a = Address.createAddress("Lincoln", 874,u);
      Address a2 = Address.createAddress("San Martin", 1654,u);
      User.deleteUserAddress("San Martin",1654,"tito_drogba@hotmail.com"); //elimino la relacion San Martin 1654 -> Didier Drogba
      the(Address.deleteAddress("Lincoln", 874)).shouldBeFalse(); //no la puedo eliminar porque tiene un usuario asociado
      the(Address.deleteAddress("San Martin", 1654)).shouldBeTrue(); //se deberia poder eliminar(nada asociado a ella)
    }

}