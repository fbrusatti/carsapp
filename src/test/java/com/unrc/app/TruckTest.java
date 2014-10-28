package com.unrc.app;
import com.unrc.app.models.Truck;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.User;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class TruckTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("TruckTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("TruckTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
 
    //Creo una camioneta invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFieldsTruck(){
        Truck v = new Truck();
        the(v).shouldNotBe("valid");
        the(v.errors().get("count_belt")).shouldBeEqual("value is missing");
        v.set("id_vehicle", "gkg237","count_belt", 4);

        // Everything is good:
        the(v).shouldBe("valid");
        
    }
  
    //creo dos camionetas y verifico si son o no iguales
    @Test
    public void shouldValidatefindByTruck(){
        User m = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle a1 = Vehicle.createVehicle("ghg345","corsa","chevrolet",m);
        Vehicle a2 = Vehicle.createVehicle("ghg344","escort","ford",m);
        Vehicle a3 = Vehicle.findByPatent("ghg345"); 
        Truck t1= Truck.createTruck(4 , a1);
        Truck t2= Truck.createTruck(2 , a2);
        Truck t3 = Truck.findByTruck("ghg345");
        the(t1.getString("id_vehicle")).shouldNotBeEqual(t2.getString("id_vehicle"));  
        the(t1.getString("id_vehicle")).shouldBeEqual(t3.getString("id_vehicle"));   
    }


    //verifico si una camioneta(creado anteriormente) existe y luego busco una camioneta inexistente
    @Test
    public void shouldValidateExistTruck(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle a = Vehicle.createVehicle("gkg357","polo","volkswagen",u);
        Truck t1= Truck.createTruck(4 , a);
        the(Truck.existTruck(t1.getString("id_vehicle"))).shouldBeTrue();
        the(Truck.existTruck("abd123")).shouldBeFalse();
    } 

   //creo una nueva camioneta y verifico la consistencia de ese camioneta
    @Test
    public void shouldValidateCreateTruck(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
        Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
        Truck t = Truck.createTruck(4 , a);
        the(a).shouldBe("valid");
        the(a).shouldNotBeNull();
        the(a).shouldContain("abc123");
        the(a).shouldContain("ka");
        the(a).shouldContain("ford");
        the(a).shouldContain(u.getInteger("id"));
        the(t).shouldContain(4);
        the(t).shouldContain("abc123");

        the(t).shouldNotContain("dfg667");

      
    } 


    //creo una camioneta y luego intento eliminar una camioneta existente,luego intento eliminar una camioneta inexistente
      @Test
    public void shouldValidateDeleteTruck(){
     User u = User.createUser("Jhony","GUzman","gm@gmail.com", "asd123");
     Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
     Truck t = Truck.createTruck(4 , a);
     the(Truck.deleteTruck("abc123")).shouldBeTrue();
     the(Truck.deleteTruck("aaa111")).shouldBeFalse();
      
    } 

}    
