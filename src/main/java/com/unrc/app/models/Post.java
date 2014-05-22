package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Post extends Model {
  static {

      validatePresenceOf("title", "description");
    }

    //retorna el modelo Post a partir del titulo en la bd
	public static Post findById(int id){
		return (findFirst("id = ?", id));
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
	public static Post createPost(String title, String description){
    	Post post = create("title", title, "description", description);
        post.saveIt();
        return findById(post.getId());
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
}
