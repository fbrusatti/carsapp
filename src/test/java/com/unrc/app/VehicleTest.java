package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class VehicleTest {

    @Before
    public void before() {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        System.out.println("UserTest setup");
        Base.openTransaction();
    }

    @After
    public void after() {
        System.out.println("UserTest tearDown");
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields() {
        Vehicle vehicle = new Vehicle();
        User user = new User();
	the(user).shouldNotBe("valid");
	the(vehicle).shouldNotBe("valid");
        the(vehicle.errors().get("patent")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("mark")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("model")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("id_user")).shouldBeEqual("value is missing");
	the(vehicle.errors().get("color")).shouldBeEqual("value is missing");
	the(vehicle.errors().get("tipo")).shouldBeEqual("value is missing");
	the(vehicle.errors().get("cc")).shouldBeEqual("value is missing");
	the(vehicle.errors().get("isCoupe")).shouldBeEqual("value is missing");
	the(vehicle.errors().get("capacity")).shouldBeEqual("value is missing");

        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","contrasena","asd123");
        vehicle.set("patent","kff911","mark","ferrari","model","enzo","id_user","1","color","rojo","tipo","auto","cc","0","isCoupe","si","capacity","0");

        // Everything is good:
        
        the(user).shouldBe("valid");
        the(vehicle).shouldBe("valid");
        
    }
}
