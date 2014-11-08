package com.unrc.app.models;
import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.Map;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

public class UsersAddress extends Model {
    static {
        validatePresenceOf("id_user","id_address");
    }
//    @Override
//    public void afterCreate(){
//
//      Map<String, Object> json = new HashMap<>();
//      json.put("id_user", this.get("id_user"));
//      json.put("id_address", this.get("id_address"));
//      ElasticSearch.client().prepareIndex("usersAddress", "useraddress",String.valueOf(this.getId()))
//                  .setSource(json)
//                  .execute()
//                  .actionGet();
//      
//    }
    
    public static UsersAddress findByUsersAddress(int id_user, int id_address){
	return (findFirst("id_user = ? and id_address= ?",  id_user,id_address));
    }

    public static UsersAddress createUsersAddress(int id_user, int id_address){
        UsersAddress answer =create("id_user",id_user, "id_address",id_address);
        answer.saveIt();
        return findByUsersAddress(id_user,id_address);
    }

    public static Boolean existUsersAddress(int id_user,int id_address){
        return (UsersAddress.first("id_user = ? and id_address = ? ", id_user,id_address) != null);
    }
   
    public static void deleteUsersAddress(int id_user,int id_address){
        if(existUsersAddress(id_user,id_address)){
            UsersAddress.delete("id_user = ? and id_question = ? ",id_user, id_address);
        }
    }   
    
    

}
