
package com.unrc.app.models;
import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.Map;
import org.javalite.activejdbc.Model;


public class Question extends Model{
    static {
        validatePresenceOf("id_user","id_post","description");
    }
//    @Override
//    public void afterCreate(){
//
//      Map<String, Object> json = new HashMap<>();
//      json.put("id_user", this.get("id_user"));
//      json.put("id_post", this.get("id_post"));
//      json.put("description", this.get("description"));
//      ElasticSearch.client().prepareIndex("questions", "question",String.valueOf(this.getId()))
//                  .setSource(json)
//                  .execute()
//                  .actionGet();
//      
//    }
    
    public static Question findByQuestion(int id_user, int id_post){
	return (findFirst("id_post = ? and id_user = ?", id_post, id_user));
    }

    public static Question createQuestion(User user, int id_post, String descripcion){
        int user2 = user.getInteger("id_user");
        Question question =create("id_user",user2, "description",descripcion,"id_post",id_post);
        question.saveIt();
        return findByQuestion(user2,id_post);
    }

    public static Boolean existQuestion(int id_user,int id_post){
        return (Answer.first("id_user = ? and id_post = ? ", id_user,id_post) != null);
    }
   
    public static void deleteQuestion(int id_question,int id_user,int id_post){
        if(existQuestion(id_user,id_post)){
            Answer.delete("id_question = ?", id_question );
            Question.delete("id_user = ? and id_post = ? ",id_user, id_post);
        }
    }   
    
    
}
