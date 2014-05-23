package com.unrc.app.models;
import org.javalite.activejdbc.Model;



public class Rate extends Model {
  static {
      validatePresenceOf("stars");
    }

    //retorna el modelo Rate a partir del id
    public static Rate findById(int id){
        return (findFirst("id = ?", id));
    }
    
    //devuelve si existe un rate llamado id
    public static Boolean existRate(int id){
        Boolean status = true;
        if( Rate.first("id = ? ", id) == null){
            return false;
        }
        return status;
    }

    //crea un rate a partir de un usuario y un post
    public static Rate createRate(int stars, Post post, User user){
        Rate rate = create("stars", stars, "id_post", post.getInteger("id"), "id_user", user.getInteger("id"));
        if( Rate.first("id_post = ? and id_user = ?",post.getInteger("id"), user.getInteger("id"))==null){
            rate.saveIt();
        }
        return findById(rate.getInteger("id"));
    }

}
