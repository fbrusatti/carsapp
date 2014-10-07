package com.unrc.app.models;

import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.Map;
import org.javalite.activejdbc.Model;


public class MessageGuest extends Model {
    static {
      validatePresenceOf("description");
    }
//     @Override
//    public void afterCreate(){
//
//      Map<String, Object> json = new HashMap<>();
//      json.put("description", this.get("description"));
//               System.out.println(this.getId()+"  ----este es el id---");
//      ElasticSearch.client().prepareIndex("messageguests", "messageguest",String.valueOf(this.getId()))
//                  .setSource(json)
//                  .execute()
//                  .actionGet();
//      
//    }
    
  public static MessageGuest findByMessageGuest(int id_messageGuest){
    return (MessageGuest.findFirst("id_messageGuest =?", id_messageGuest ));
  }

  public static void createMessageGuest(String descripcion){
        MessageGuest messageGuest = create("description", descripcion);
                messageGuest.saveIt();
  }

  public static Boolean messageGuestExistente(int id_messageGuest){
      return (MessageGuest.first("id_messageGuest =?", id_messageGuest)!=null);
  }

  public static void deleteMessageGuest(int id_messageGuest){
    MessageGuest messageGuest = MessageGuest.findByMessageGuest(id_messageGuest);
    if(messageGuestExistente(id_messageGuest))
        messageGuest.delete("id_messageGuest = ?", id_messageGuest);
  }
    
}
