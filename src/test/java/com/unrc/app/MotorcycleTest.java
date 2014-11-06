package com.unrc.app;

import com.unrc.app.models.Motorcycle;

import com.unrc.app.models.Vehicle;
import com.unrc.app.models.User;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class MotorcycleTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("MotorcycleTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("MotorcycleTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
   
    //Creo un User invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFieldsMotorcycle(){
        Motorcycle v = new Motorcycle();
        the(v).shouldNotBe("valid");
        the(v.errors().get("wheel_size")).shouldBeEqual("value is missing");
        the(v.errors().get("engine_size")).shouldBeEqual("value is missing");
        
        v.set("id_vehicle", "gkg237","wheel_size", 216,"engine_size", 125);

        // Everything is good:
        the(v).shouldBe("valid");
        
    }

   //creo dos usuarios y verifico si son o no iguales
    @Test
    public void shouldValidatefindByMotorcycle(){
        User m = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle a1 = Vehicle.createVehicle("ghg345","corsa","chevrolet",m);
        Vehicle a2 = Vehicle.createVehicle("ghg344","escort","ford",m);
        Vehicle a3 = Vehicle.findByPatent("ghg345"); 
        Motorcycle c1= Motorcycle.createMotorcycle(25 ,125 , a1);
        Motorcycle c2= Motorcycle.createMotorcycle(25 ,125 , a2);
        Motorcycle c3 = Motorcycle.findByMotorcycle("ghg345");
        the(c1.getString("id_vehicle")).shouldNotBeEqual(c2.getString("id_vehicle"));  
        the(c1.getString("id_vehicle")).shouldBeEqual(c3.getString("id_vehicle"));   
    }


    //verifico si un usuario(creado anteriormente) existe y luego busco un usuario inexistente
    @Test
    public void shouldValidateExistMotorcycle(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle a = Vehicle.createVehicle("gkg357","polo","volkswagen",u);
        Motorcycle t1= Motorcycle.createMotorcycle(125, 125 , a);
        the(Motorcycle.existMotorcycle(t1.getString("id_vehicle"))).shouldBeTrue();
        the(Motorcycle.existMotorcycle("abd123")).shouldBeFalse();
    } 

   //creo un nuevo usuario y verifico la consistencia de ese usuario
    @Test
    public void shouldValidateCreateMotorcycle(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
        Motorcycle t = Motorcycle.createMotorcycle(24, 125 , a);
        the(a).shouldBe("valid");
        the(a).shouldNotBeNull();
        the(a).shouldContain("abc123");
        the(a).shouldContain("ka");
        the(a).shouldContain("ford");
        the(a).shouldContain(u.getInteger("id"));
        the(t).shouldContain(125);
        the(t).shouldContain(24);
        the(t).shouldContain("abc123");

        the(t).shouldNotContain("dfg667");

      
    } 


    //creo un usuario y luego intento eliminar un usuario existente,luego intento eliminar un usuario inexistente
      @Test
    public void shouldValidateDeleteMotorcycle(){
     User u = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
     Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
     Motorcycle t = Motorcycle.createMotorcycle(24, 125 , a);
     the(Motorcycle.deleteMotorcycle("abc123")).shouldBeTrue();
     the(Motorcycle.deleteMotorcycle("aaa111")).shouldBeFalse();
      
    } 
 

}    

