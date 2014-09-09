package com.unrc.app.models;

import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.javalite.activejdbc.Model;

public class User extends Model {
    static {
      validatePresenceOf("first_name", "last_name", "email", "contrasena");
    }
     @Override
    public void afterCreate(){

      Map<String, Object> json = new HashMap<>();
      json.put("name", this.get("first_name"));
      json.put("email", this.get("email"));
      json.put("id", this.getId());
      ElasticSearch.client().prepareIndex("users", "user",String.valueOf(this.getId()))
                  .setSource(json)
                  .execute()
                  .actionGet();
      getUserElasticsearch();
      
    }
        public void  getUserElasticsearch(){
              System.out.println("---------------------------antesconsulta---------------------------------------------");  
            
            GetResponse response = ElasticSearch.client().prepareGet("users", "user", String.valueOf(this.getId()))
        .execute()
        .actionGet();
            System.out.println("---------------------------consulta---------------------------------------------");
            
            System.out.println("source---"+response.getSource());
        }
    
    
    public static User findByEmail(String email){
	return (findFirst("email = ?", email));
    }

    public static Boolean existUser(int id_user){
        return (User.first("id_user = ? ", id_user) != null);
    }
   
    public static User createUser(String nombre, String apellido, String email, String contrasena){
        User user =create("first_name", nombre, "last_name", apellido, "email", email, "contrasena", contrasena);
        user.saveIt();
        return findByEmail(email);
    }

    public static void deleteUser(int id_user){
    	if(existUser(id_user)){
        	User.delete("id_user = ?", id_user);
                Vehicle.delete("id_user=?", id_user);
                Post.delete("id_user=?", id_user);
                Answer.delete("id_user=?", id_user);
                Question.delete("id_user=?", id_user);
        }
        
    }
}
