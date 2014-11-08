package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.Address;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class CityTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        System.out.println("UserTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("UserTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        User user = new User();
        Address adress = new Address();
        the(user).shouldNotBe("valid");
        the(adress).shouldNotBe("valid");
        the(adress.errors().get("street")).shouldBeEqual("value is missing");
        the(adress.errors().get("city")).shouldBeEqual("value is missing");
        the(adress.errors().get("province")).shouldBeEqual("value is missing");
        the(adress.errors().get("postal_code")).shouldBeEqual("value is missing");
	the(adress.errors().get("num")).shouldBeEqual("value is missing");

        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","contrasena","asd123");
        adress.set("street","mitre","city","rio cuarto","province","cordoba","postal_code","5800","num","220");
       
        // Everything is good:
        the(user).shouldBe("valid");
        the(adress).shouldBe("valid");
    }
}
