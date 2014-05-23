package com.unrc.app;

import com.unrc.app.models.Post;
import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

public class PostTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        System.out.println("PostTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("PostTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }
     
    //Creo un Post invalido y lo corroboro 
    @Test
    public void shouldValidateMandatoryFields(){
        Post post = new Post();
        User user = new User();
        Vehicle vehicle = new Vehicle();
        the(post).shouldNotBe("valid");
        the(post.errors().get("description")).shouldBeEqual("value is missing");
        the(post.errors().get("title")).shouldBeEqual("value is missing");
        post.set("title", "Nuevo post", "description", "Nueva descripcion de un vehiculo", "id_users", user , "id_vehicle", vehicle);

        // Everything is good:
        the(post).shouldBe("valid");
        
    }

    //creo dos usuarios y verifico si son o no iguales
    @Test
    public void shouldValidatefindById(){
        User user = User.createUser("adrian", "tissera", "adriantissera@gmail.com");
        Vehicle vehicle = Vehicle.createVehicle("qwe123", "asd", "ford", user);
        Post post1 = Post.createPost("nuevopost1", "nuevadescripcion", user, vehicle);
        Post post2 = Post.createPost("nuevopost2", "nuevadescripcion", user, vehicle);

        Post post3 = Post.findById(post1.getId());
        the(post1.getString("description")).shouldBeEqual(post3.getString("description"));  
        the(post2.getString("title")).shouldNotBeEqual(post3.getString("title"));   
    }


    //verifico si un usuario(creado anteriormente) existe y luego busco un usuario inexistente
    @Test
    public void shouldValidateExistPost(){
        User user = User.createUser("adrian", "tissera", "adriantissera@gmail.com");
        Vehicle vehicle = Vehicle.createVehicle("qwe123", "asd", "ford", user);
        Post post = Post.createPost("nuevopost", "nuevadescripcion", user, vehicle);
        the(Post.existPost(post.getInteger("id"))).shouldBeTrue();
        the(Post.existPost(post.getInteger("id") + 1)).shouldBeFalse();
    } 

    //creo un nuevo usuario y verifico la consistencia de ese usuario
    @Test
    public void shouldValidateCreatePost(){
        User user = User.createUser("adrian", "tissera", "adriantissera@gmail.com");
        Vehicle vehicle = Vehicle.createVehicle("qwe123", "asd", "ford", user);
        Post post = Post.createPost("nuevo post","nueva descripcion", user, vehicle);
        the(post).shouldBe("valid");
        the(post).shouldNotBeNull();
        the(post).shouldContain("nuevo post");
        the(post).shouldContain("nueva descripcion");
        the(post).shouldNotContain("falso post");
    } 

    //creo un usuario y luego intento eliminar un usuario existente,luego intento eliminar un usuario inexistente
    @Test
    public void shouldValidateDelete(){
        User user = User.createUser("adrian", "tissera", "adriantissera@gmail.com");

        Vehicle vehicle = Vehicle.createVehicle("qwe123", "asd", "ford", user);

        Post post = Post.createPost("postNuevo", "descripcionNueva", user, vehicle);
        the(Post.deletePost(post.getInteger("id"))).shouldBeTrue();
        the(Post.deletePost(post.getInteger("id") + 1)).shouldBeFalse();
    }   
}