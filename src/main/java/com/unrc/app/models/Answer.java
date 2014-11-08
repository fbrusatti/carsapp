
package com.unrc.app.models;

import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.Map;
import org.javalite.activejdbc.Model;

public class Answer extends Model{
    static {
        validatePresenceOf("id_user","description","id_question");
    }
//    @Override
//    public void afterCreate(){
//
//      Map<String, Object> json = new HashMap<>();
//      json.put("id_user", this.get("id_user"));
//      json.put("description", this.get("description"));
//      json.put("id_question", this.get("id_question"));
//      ElasticSearch.client().prepareIndex("questions", "question",String.valueOf(this.getId()))
//                  .setSource(json)
//                  .execute()
//                  .actionGet();
//      
//    }
     public static Answer findByAnswer(int id_user, int id_question){
	return (findFirst("id_question = ? and id_user = ?", id_question, id_user));
    }

    public static Answer createAnswer(User user, int question, String descripcion){
        int user2 = user.getInteger("id_user");
        Answer answer =create("id_user",user2, "description",descripcion,"id_question",question);
        answer.saveIt();
        return findByAnswer(user2,question);
    }

    public static Boolean existAnswer(int id_question){
        return (Answer.first("id_question = ? ", id_question) != null);
    }
   
    public static void deleteAnswer(int id_question){
        if(existAnswer(id_question)){
            Answer.delete("id_question = ? ", id_question);
        }
    }   
    
    
}
