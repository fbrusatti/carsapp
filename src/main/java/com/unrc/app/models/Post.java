package com.unrc.app.models;
import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Model;

public class Post extends Model {
  static {
      validatePresenceOf("id_user", "id_address","patent","description");
  }
//  @Override
//    public void afterCreate(){
//
//      Map<String, Object> json = new HashMap<>();
//      json.put("id_user", this.get("id_user"));
//      json.put("id_address", this.get("id_address"));
//      json.put("patent", this.get("patent"));
//      json.put("description", this.get("description"));
//      ElasticSearch.client().prepareIndex("posts", "post",String.valueOf(this.getId()))
//                  .setSource(json)
//                  .execute()
//                  .actionGet();
//      
//    }
  
    public static Post findByPost(int id_user, String patente){
	return (findFirst("id_user = ? and patent = ?", id_user, patente));
    }

    public static Post createPost(User user, int id_address,String patente, String descripcion){
        int user2 = user.getInteger("id_user");
        Post post =create("id_user",user2,"id_address",id_address,  "description",descripcion,"patent",patente);
        post.saveIt();
        return findByPost(user2,patente);
    }

    public static Boolean existPost(int id_user,String patente){
        return (Post.first("id_user = ? and patent = ? ", id_user,patente) != null);
    }
   
    public static void deletePost(int id_post){
            List<Question>  q = Question.find("id_post =?",id_post);
            for (int i = 0; i < q.size(); i++) {
                   Answer.deleteAnswer(q.get(i).getInteger("id_question"));
                    
            }
            
            Question.delete("id_post = ?", id_post );

            Post.delete("id_post = ? ",id_post);
    } 
}

