package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.Address;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Car;
import com.unrc.app.models.Post;
import com.unrc.app.models.Question;
import com.unrc.app.models.Answer;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class UserTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
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

        the(user).shouldNotBe("valid");
        the(user.errors().get("first_name")).shouldBeEqual("value is missing");
        the(user.errors().get("last_name")).shouldBeEqual("value is missing");
        the(user.errors().get("email")).shouldBeEqual("value is missing");

        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","is_admin",false);

        // Everything is good:
        the(user).shouldBe("valid");
    }
    @Test
    public void createUserTest(){
        User user = new User();
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","is_admin",true);
        user.createUser("John","Hanckok","hanckok@mail.com","alvear","244");
        User user2 = User.findFirst("email = ?","hanckok@mail.com");
        assertThat(user2.getBoolean("is_admin"),is(false));
    }
    @Test
    public void addAddressTest(){
        User user = new User();
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","is_admin",true);
        user.saveIt();
        user.addAddress("calle","101");
        Address a = Address.findFirst("user_id = ?", user.getString("id"));
        assertThat(a.getString("street"),is("calle"));
        assertThat(a.getString("address_number"),is("101"));

    }
    @Test
    public void addVehicleTest(){
        User user = new User();
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","is_admin",true);
        user.saveIt();
        user.addCar("car","90","10000","sedan");
        Vehicle a = Vehicle.findFirst("user_id = ?", user.getString("id"));
        assertThat(a.getString("name"),is("car"));
        assertThat(a.getString("model"),is("90"));
        assertThat(a.getString("km"),is("10000"));
        Car c = Car.findFirst("vehicle_id = ?", a.getString("id"));
        assertThat(c.getString("type"),is("sedan"));
    }
    @Test
    public void addPostTest(){
        User user = new User();
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","is_admin",true);
        user.saveIt();
        user.addTruck("mercedes","90","9000","heavy");
        Vehicle a = Vehicle.findFirst("user_id = ?", user.getString("id"));
        user.addPost("120000","Vendo camion",a);
        Post p = Post.findFirst("user_id = ?", user.getString("id"));
        assertThat(p.getString("price"),is("120000"));
        assertThat(p.getString("description"),is("Vendo camion"));
    }
    @Test
    public void addQuestionTest(){
        User user = new User();
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","is_admin",true);
        user.saveIt();
        user.addTruck("mercedes","90","9000","heavy");
        Vehicle a = Vehicle.findFirst("user_id = ?", user.getString("id"));
        user.addPost("120000","Vendo camion",a);
        Post p = Post.findFirst("user_id = ?", user.getString("id"));
        user.createUser("John","Hanckok","hanckok@mail.com","alvear","244");
        User user2 = User.findFirst("email = ?","hanckok@mail.com");
        user2.addQuestion ("pregunto por camion!",p.getString("id"));
        Question q = Question.findFirst("user_id = ?", user2.getString("id"));
        assertThat(q.getString("description"),is("pregunto por camion!"));
    }
    @Test
    public void addAnswerTest(){
        User user = new User();
        user.set("first_name", "John", "last_name", "Doe", "email", "example@email.com","is_admin",true);
        user.saveIt();
        user.addTruck("mercedes","90","9000","heavy");
        Vehicle a = Vehicle.findFirst("user_id = ?", user.getString("id"));
        user.addPost("120000","Vendo camion",a);
        Post p = Post.findFirst("user_id = ?", user.getString("id"));
        user.createUser("John","Hanckok","hanckok@mail.com","alvear","244");
        User user2 = User.findFirst("email = ?","hanckok@mail.com");
        user2.addQuestion ("pregunto por camion!",p.getString("id"));
        Question q = Question.findFirst("user_id = ?", user2.getString("id"));
        user.addAnswer("respondo por camion",p,q);
        Answer an = Answer.findFirst("user_id = ?", user.getString("id"));
        assertThat(an.getString("description"),is("respondo por camion"));
    }
}
