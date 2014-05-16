package com.unrc.app;

import com.unrc.app.models.User;

public class VehicleTest{
	
	@Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("VehicleTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("VehicleTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Vehicle vehicle = new Vehicle();
        User user = new User();

        the(Vehicle).shouldNotBe("valid");
        the(Vehicle.errors().get("id_user")).shouldBeEqual("value is missing");
        the(user.errors().get("last_name")).shouldBeEqual("value is missing");

        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com");

        // Everything is good:
        the(user).shouldBe("valid");
    }
}