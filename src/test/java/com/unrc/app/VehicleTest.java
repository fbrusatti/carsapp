package com.unrc.app;

import com.unrc.app.models.Vehicle;
import com.unrc.app.models.User;
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
   
    //Creo un vehiculo invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFieldsVehicle(){
        Vehicle v = new Vehicle();
        the(v).shouldNotBe("valid");
        the(v.errors().get("patent")).shouldBeEqual("value is missing");
        the(v.errors().get("model")).shouldBeEqual("value is missing");
        the(v.errors().get("brand")).shouldBeEqual("value is missing");
        v.set("patent", "gkg237","model","corsa","brand","chevrolet");

        // Everything is good:
        the(v).shouldBe("valid");
        
    }

    //creo dos vehiculo y verifico si son o no iguales
    @Test
    public void shouldValidatefindByPatent(){
        User m = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        User n = User.createUser("Jhony","GUzman","ga@gmail.com", "asd1234");
        Vehicle a1 = Vehicle.createVehicle("ghg345","corsa","chevrolet",m);
        Vehicle a2 = Vehicle.createVehicle("ghg344","escort","ford",n);
        Vehicle a3 = Vehicle.findByPatent("ghg345");
        the(a3.getString("patent")).shouldBeEqual(a1.getString("patent"));  
        the(a2.getString("patent")).shouldNotBeEqual(a1.getString("patent"));   
    }


    //verifico si un vehiculo(creado anteriormente) existe y luego busco un vehiculo inexistente
    @Test
    public void shouldValidateExistVehicle(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle a = Vehicle.createVehicle("gkg357","polo","volkswagen",u);
        the(Vehicle.existVehicle(a.getString("patent"))).shouldBeTrue();
        the(Vehicle.existVehicle("abd123")).shouldBeFalse();
    } 

   //creo un nuevo vehiculo y verifico la consistencia de ese vehiculo
    @Test
    public void shouldValidateCreateVehicle(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
        the(a).shouldBe("valid");
        the(a).shouldNotBeNull();
        the(a).shouldContain("abc123");
        the(a).shouldContain("ka");
        the(a).shouldContain("ford");
        the(a).shouldContain(u.getInteger("id"));
        the(a).shouldNotContain("dfg667");

      
    } 


    //creo un vehiculo y luego intento eliminar un vehiculo existente,luego intento eliminar un vehiculo inexistente
      @Test
    public void shouldValidateDeleteVehicle(){
     User u = User.createUser("Jhony","GUzman","gm@gmail.com","asd123");
     Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
     the(Vehicle.deleteVehicle("abc123")).shouldBeTrue();
     the(Vehicle.deleteVehicle("aaa111")).shouldBeFalse();
      
    } 

}    
