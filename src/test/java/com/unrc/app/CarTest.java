package com.unrc.app;
import com.unrc.app.models.Car;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.User;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class CarTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("CarTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("CarTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
 
    //Creo un User invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFieldsCar(){
        Car v = new Car();
        the(v).shouldNotBe("valid");
        the(v.errors().get("is_coupe")).shouldBeEqual("value is missing");
        v.set("id_vehicle", "gkg237","is_coupe", true);

        // Everything is good:
        the(v).shouldBe("valid");
        
    }
  
    //creo dos usuarios y verifico si son o no iguales
    @Test
    public void shouldValidatefindByCar(){
        User m = User.createUser("Jhony","GUzman","gm@gmail.com");
        Vehicle a1 = Vehicle.createVehicle("ghg345","corsa","chevrolet",m);
        Vehicle a2 = Vehicle.createVehicle("ghg344","escort","ford",m);
        Vehicle a3 = Vehicle.findByPatent("ghg345"); 
        Car c1= Car.createCar(true , a1);
        Car c2= Car.createCar(false , a2);
        Car c3 = Car.findByCar("ghg345");
        the(c1.getString("id_vehicle")).shouldNotBeEqual(c2.getString("id_vehicle"));  
        the(c1.getString("id_vehicle")).shouldBeEqual(c3.getString("id_vehicle"));   
    }


    //verifico si un usuario(creado anteriormente) existe y luego busco un usuario inexistente
    @Test
    public void shouldValidateExistCar(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com");
        Vehicle a = Vehicle.createVehicle("gkg357","polo","volkswagen",u);
        Car t1= Car.createCar(true , a);
        the(Car.existCar(t1.getString("id_vehicle"))).shouldBeTrue();
        the(Car.existCar("abd123")).shouldBeFalse();
    } 

   //creo un nuevo usuario y verifico la consistencia de ese usuario
    @Test
    public void shouldValidateCreateCar(){
        User u = User.createUser("Jhony","GUzman","gm@gmail.com");
        Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
        Car t = Car.createCar(true , a);
        the(a).shouldBe("valid");
        the(a).shouldNotBeNull();
        the(a).shouldContain("abc123");
        the(a).shouldContain("ka");
        the(a).shouldContain("ford");
        the(a).shouldContain(u.getInteger("id"));
        the(t).shouldContain(true);
        the(t).shouldContain("abc123");

        the(t).shouldNotContain("dfg667");

      
    } 


    //creo un usuario y luego intento eliminar un usuario existente,luego intento eliminar un usuario inexistente
      @Test
    public void shouldValidateDeleteCar(){
     User u = User.createUser("Jhony","GUzman","gm@gmail.com");
     Vehicle a = Vehicle.createVehicle("abc123","ka","ford",u);
     Car t = Car.createCar(true , a);
     the(Car.deleteCar("abc123")).shouldBeTrue();
     the(Car.deleteCar("aaa111")).shouldBeFalse();
      
    } 

}   