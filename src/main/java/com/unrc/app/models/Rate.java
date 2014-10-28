package com.unrc.app.models;
import com.unrc.app.App;
import java.util.*;
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
        if (Rate.first("id_post = ? and id_user = ?",post.getInteger("id"), user.getInteger("id"))==null){
            rate.saveIt();
        }
        return findById(rate.getInteger("id"));
    }

    public int id(){
        return this.getInteger("id");
    }

    public int stars(){
        return this.getInteger("stars");
    }  

    public int id_post(){
        return this.getInteger("id_post");
    }     

    public int id_user(){
        return this.getInteger("id_user");
    }

    public void afterCreate() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id", this.id());
        json.put("id_post", this.id_post());
        json.put("id_user", this.id_user());
        json.put("stars", this.stars());
        App.client().prepareIndex("rates", "rate")
                  .setSource(json)
                  .execute()
                  .actionGet();
    }

}
