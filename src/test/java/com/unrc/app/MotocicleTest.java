package com.unrc.app;

import com.unrc.app.models.Motocicle;

import com.unrc.app.models.Vehicle;
import com.unrc.app.models.User;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class MotocicleTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("MotocicleTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("MotocicleTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
   
    //Creo una moto invalida y lo corroboro 
    @Test
    public void shouldValidateMandatoryFieldsMotocicle(){
        Motocicle v = new Motocicle();
        the(v).shouldNotBe("valid");
        the(v.errors().get("roll")).shouldBeEqual("value is missing");
        the(v.errors().get("cylinder")).shouldBeEqual("value is missing");
        
        v.set("id_vehicle", "gkg237","roll", 216,"cylinder", 125);

        // Everything is good:
        the(v).shouldBe("valid");
        
    }

   //creo dos motos y verifico si son o no iguales
    @Test
    public void shouldValidatefindByMotocicle(){
        User m = User.createUser("Jhony","GUzman","gm@gmail.com");
        Vehicle a1 = Vehicle.createVehicle("ghg345","corsa","chevrolet",m);
        Vehicle a2 = Vehicle.createVehicle("ghg344","escort","ford",m);
        Vehicle a3 = Vehicle.findByPatent("ghg345"); 
        Motocicle c1= Motocicle.createMotocicle(25 ,125 , a1);
        Motocicle c2= Motocicle.createMotocicle(25 ,125 , a2);
        Motocicle c3 = Motocicle.findByMotocicle("ghg345");
        the(c1.getString("id_vehicle")).shouldNotBeEqual(c2.getString("id_vehicle"));  
        the(c1.getString("id_vehicle")).shouldBeEqual(c3.getString("id_vehicle"));   
    }


    //verifico si una moto(creado anteriormente) existe y luego busco una moto inexistente
    @Test
    public void shouldValidateExistMotocicle(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com");
        Vehicle a = Vehicle.createVehicle("gkg357","polo","volkswagen",u);
        Motocicle t1= Motocicle.createMotocicle(125, 125 , a);
        the(Motocicle.existMotocicle(t1.getString("id_vehicle"))).shouldBeTrue();
        the(Motocicle.existMotocicle("abd123")).shouldBeFalse();
    } 

   //creo una nueva moto y verifico la consistencia de esa moto
    @Test
    public void shouldValidateCreateMotocicle(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com");
        Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
        Motocicle t = Motocicle.createMotocicle(24, 125 , a);
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


    //creo una moto y luego intento eliminar una moto existente,luego intento eliminar una moto inexistente
      @Test
    public void shouldValidateDeleteMotocicle(){
     User u = User.createUser("Jhony","GUzman","gm@gmail.com");
     Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
     Motocicle t = Motocicle.createMotocicle(24, 125 , a);
     the(Motocicle.deleteMotocicle("abc123")).shouldBeTrue();
     the(Motocicle.deleteMotocicle("aaa111")).shouldBeFalse();
      
    } 
 

}    
