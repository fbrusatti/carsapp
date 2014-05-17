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
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("VehicleTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("VehicleTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
   
    //Creo un User invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFieldsVehicle(){
        Vehicle v = new Vehicle();
        the(v).shouldNotBe("valid");
        the(v.errors().get("patent")).shouldBeEqual("value is missing");
        the(v.errors().get("model")).shouldBeEqual("value is missing");
        the(v.errors().get("mark")).shouldBeEqual("value is missing");
        v.set("patent", "gkg237","model","corsa","mark","chevrolet");

        // Everything is good:
        the(v).shouldBe("valid");
        
    }
/*
    //creo dos usuarios y verifico si son o no iguales
    @Test
    public void shouldValidatefindByEmail(){
        User a = User.createUser("Jhony","GUzman","gm@gmail.com");
        User a3 = User.createUser("claudio","GUzman","gma@gmail.com");
        User a2 = User.findByEmail("gm@gmail.com");
        the(a2.getString("email")).shouldBeEqual(a.getString("email"));  
        the(a3.getString("email")).shouldNotBeEqual(a.getString("email"));   
    }


    //verifico si un usuario(creado anteriormente) existe y luego busco un usuario inexistente
    @Test
    public void shouldValidateExistUser(){
        User a = User.createUser("cludio","toresani","gmaa@gmail.com");
        the(User.existUser(a.getString("email"))).shouldBeTrue();
        the(User.existUser("gustavito")).shouldBeFalse();
    } 

    //creo un nuevo usuario y verifico la consistencia de ese usuario
    @Test
    public void shouldValidateCreateUser(){
        User a = User.createUser("abc","def","ghi@gmail.com");
        the(a).shouldBe("valid");
        the(a).shouldNotBeNull();
        the(a).shouldContain("abc");
        the(a).shouldContain("def");
        the(a).shouldContain("ghi@gmail.com");
      
    } 

/*
    //creo un usuario y luego intento eliminar un usuario existente,luego intento eliminar un usuario inexistente
      @Test
    public void shouldValidateDelete(){
      User c = User.createUser("nombre","apellido","mail");
      the(User.deleteUser("mail")).shouldBeTrue();
      the(User.deleteUser("algo")).shouldBeFalse();
      
    } 
*/    
}    
