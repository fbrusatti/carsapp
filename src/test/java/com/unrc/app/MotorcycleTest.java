package com.unrc.app;

import com.unrc.app.models.Motorcycle;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MotorcycleTest {
     @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "root");
        System.out.println("MotorcycleTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("MotorcycleTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Motorcycle moto = new Motorcycle();

        the(moto).shouldNotBe("valid");
        the(moto.errors().get("type")).shouldBeEqual("value is missing");
        the(moto.errors().get("type_motor")).shouldBeEqual("value is missing");
        the(moto.errors().get("boot_system")).shouldBeEqual("value is missing");
        the(moto.errors().get("displacement")).shouldBeEqual("value is missing");
        
        moto.set("type","Street","type_motor","4","boot_sistem","Pedal y Electrico","displacement","125");

        the(moto).shouldBe("valid");
    } 
}
