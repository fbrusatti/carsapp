package com.unrc.app.models;
import com.unrc.app.App;
import java.util.*;
import org.javalite.activejdbc.Model;

public class Post extends Model {
  static {

      validatePresenceOf("title", "description");
    }

    //retorna el modelo Post a partir del id en la bd
	public static Post findById(int id){
		return (findFirst("id = ?", id));
	}

    //retorna el modelo Post a partir del titulo en la bd
    public static Post findByTitle(String title){
        return (findFirst("title = ?", title));
    }

    //chequea la existencia de un post dado por id
    public static Boolean existPost(int id){
        Boolean status = true;
        if( Post.first("id = ? ", id) == null){
            return false;
        }
        return status;
    }

    //crea un nuevo post con el titulo title y cuerpo description
	public static Post createPost(String title, String description, User user, Vehicle vehicle){
    	Post post = create("title", title, "description", description, "id_users", user.getInteger("id"), "id_vehicle", vehicle.getString("patent"));
        post.saveIt();
        return findById(post.getInteger("id"));
    }

    //elimina un post dado el nombre
    public static Boolean deletePost(int id){
    	if(existPost(id)){
        	Post post = Post.findById(id);
        	post.delete();
        	return true;
        }else{
            return false;
        }
    }

    public String title(){
        return this.getString("title");
    }

    public String description(){
        return this.getString("description");
    }  

    public int id(){
        return this.getInteger("id");
    }

    public void afterCreate() {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("id", this.id());
        json.put("title", this.title());
        json.put("description", this.description());
        App.client().prepareIndex("posts", "post")
                  .setSource(json)
                  .execute()
                  .actionGet();
    }
}
