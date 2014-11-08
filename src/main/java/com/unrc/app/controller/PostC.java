package com.unrc.app.controller;

import com.unrc.app.Html;
import com.unrc.app.models.*;
import static java.lang.Integer.parseInt;
import java.util.List;

public class PostC {
    
    Html html = new Html();
    
    public String getPostId(spark.Request req,spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            Post post = Post.findFirst("id_post = ?", req.params(":id"));
            String own = "";
            User name = User.findFirst("id_user = ?", post.getString("id_user"));
            own = own + name.getString("first_name") + "}"  + post.getString("patent") + "}" + post.getString("description");
            return html.getPostBy(own);
        }
        else
            return html.getFailLogin();
    }
    
    
    public String getOwnPosts(spark.Request req,spark.Response resp,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){ 
            resp.type("text/html");
            List<Post> post = Post.find("id_user = ?", currentUser.get("id_user"));
            if (!post.isEmpty()) {
                String vh = "";
                for (int i = 0; i < post.size(); i++) {
                    vh = vh + post.get(i).getString("id_post") + "}" + post.get(i).getString("description") + "}" + currentUser.getString("first_name") + "}" + post.get(i).getString("patent") + ",";
                }
                if(usuarioActivo.equals("User")){
                    return html.getPostUser(vh);
                }
                else{
                    return html.getPostGuest(vh);
                }
            } 
            else 
                return  html.getMessagePag("No hay post registrados","/webpag");
        }
        else
            return html.getFailLogin();
    }
    
    
    public String getInsertPost (spark.Request req,spark.Response resp,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("User")){
            resp.type("text/html");
            List<Vehicle> v = Vehicle.find("id_user = ?", currentUser.get("id_user"));
            String vh = "";
            if (!v.isEmpty()) {
                for (int i = 0; i < v.size(); i++) {
                    vh = vh + v.get(i).getString("patent") + ",";
                }
            }
            else
                return html.getMessagePag("Necesita ingresar vehiculos para crear un post","/webpag");  
            
            List<UsersAddress> a = UsersAddress.find("id_user = ?", currentUser.get("id_user"));
            String ad = "";
            if (!a.isEmpty()) {    
                for (int i = 0; i < a.size(); i++) {
                    Address b = Address.findFirst("id_address = ?", a.get(i).getString("id_address"));
                    ad = ad + b.getString("id_address")+ "}" +b.getString("province") + " | " + b.getString("city") + " | " + b.getString("postal_code") + " | " + b.getString("street") + " | " +b.getString("num")+ ",";         
                }
            }
            else 
                return  html.getMessagePag("Necesita ingresar una direccion para crear un post","/webpag");
            return html.IngresarPost(currentUser,vh,ad);
        }
        else
            return html.getFailLogin();
    }
    
    public String postInsertPost (spark.Request req,spark.Response resp,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("User")){
            resp.type("text/html");
            Post.createPost(currentUser, parseInt(req.queryParams("ciudad")),  req.queryParams("patente"), req.queryParams("descripcion"));
            List<Vehicle> v = Vehicle.find("id_user = ?", currentUser.get("id_user"));
            String vh = "";            
            if (!v.isEmpty()) {
                for (int i = 0; i < v.size(); i++) {
                    vh = vh + v.get(i).getString("patent") + ",";
                }
            }
            List<UsersAddress> a = UsersAddress.find("id_user = ?", currentUser.get("id_user"));
            String ad = "";
            if (!a.isEmpty()) {  
                for (int i = 0; i < a.size(); i++) {
                    Address b = Address.findFirst("id_address = ?", a.get(i).getString("id_address"));
                    ad = ad + b.getString("id_address")+ "}" +b.getString("province") + " | " + b.getString("city") + " | " + b.getString("postal_code") + " | " + b.getString("street") + " | " +b.getString("num")+ ",";                
                }
            }
            return html.IngresarPost(currentUser,vh,ad);
        }
        else
            return html.getFailLogin();
    }
    
    public String postGetPostSearch(spark.Request req, spark.Response resp,User currentUser,Post currentPost,String usuarioActivo){
        if(usuarioActivo.equals("Admin")|| usuarioActivo.equals("User")){
            resp.type("text/html");
            if (req.queryParams("coment") == null || req.queryParams("coment").equals("")) {
                return  html.getMessagePag(" No escribio ningun comentario ","/post");
            }
            Question.createQuestion(currentUser,currentPost.getInteger("id_post"),req.queryParams("coment"));
            return  html.getMessagePag("Se envio la pregunta","/post");
        }
        else
            return html.getFailLogin();
    }
    
    public String postAnswerPost (spark.Request req,spark.Response resp,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User")){
            resp.type("text/html");
            Question q = Question.findFirst("id_question = ?", req.queryParams("question"));
            if (q.equals(null)||req.queryParams("responder") == null || req.queryParams("responder").equals("")) {
                return  html.getMessagePag("No se guardo correctamente la respuesta","/post");
            }
            Answer.createAnswer(currentUser, q.getInteger("id_question"), req.queryParams("responder"));
            return  html.getMessagePag("Se guardo la respuesta","/post");
        }
        else
            return html.getFailLogin();
    }
    
    public String getPost(spark.Request req, spark.Response resp,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User") || usuarioActivo.equals("Guest")){
            resp.type("text/html");
            List<Post> post = Post.findAll();
            String vh = "";
            for (int i = 0; i < post.size(); i++) {
                User name = User.findFirst("id_user = ?", post.get(i).getString("id_user"));
                if (name != null) {
                    vh = vh + post.get(i).getString("id_post") + "}" + post.get(i).getString("description") + "}" + name.getString("first_name") + "}" + post.get(i).getString("patent") + ",";
                }
            }
            if(usuarioActivo.equals("User") || usuarioActivo.equals("Guest")){
                if(usuarioActivo.equals("User")) return html.getPostUser(vh);
                else 
                    return html.getPostGuest(vh);
            }
            else{
                return html.getAllPost(vh);
            }
        }
        else
            return html.getFailLogin();
    }
    
    public String postPost(spark.Request req,spark.Response resp,Post currentPost,User currentUser,String usuarioActivo){
        if(usuarioActivo.equals("Admin") || usuarioActivo.equals("User") || usuarioActivo.equals("Guest")){
            if( ( (usuarioActivo.equals("Admin")  && !req.queryParams("postEliminar").isEmpty()) )){
                Post tmp = Post.findFirst("id_post = ?", req.queryParams("postEliminar"));
                Post.deletePost(tmp.getInteger("id_post"));
                return html.getMessagePag("Post Eliminado con exito","/post");
            }   
            else{
                resp.type("text/html");
                Post post = Post.findFirst("id_post = ?", req.queryParams("id_post"));
                currentPost = post;
                String own = "";
                String desc =  post.getString("description");
                User name = User.findFirst("id_user = ?", post.getString("id_user"));
                own = own + name.getString("first_name")+", " + name.getString("last_name")+"}";
                Address b = Address.findFirst("id_address = ?", post.getString("id_address"));
                own = own + b.getString("province") + "}" + b.getString("city") + "}" + b.getString("postal_code") + "}" + b.getString("street") + "}" +b.getString("num");
                Vehicle tmp = Vehicle.findFirst("patent = ?", post.getString("patent"));
                String vl = tmp.getString("patent") + "," + tmp.getString("mark") + "," + tmp.getString("model") + "," + tmp.getString("color") + "," + tmp.getString("tipo") + "," + tmp.getString("isCoupe") + "," + Integer.toString(tmp.getInteger("cc")) + "," + Integer.toString(tmp.getInteger("capacity"));
                List<Question> ques = Question.find("id_post = ?", req.queryParams("id_post"));
                String q = "";
                for (int i = 0; i < ques.size(); i++) {
                    q+= ques.get(i).get("id_question")+"}"+User.findFirst("id_user = ?", ques.get(i).getInteger("id_user")).getString("first_name") +"}" + ques.get(i).getString("description")+"}";
                    Answer ans = Answer.findFirst("id_question = ?", ques.get(i).get("id_question"));
                    if( ans!=null){
                        q+= ans.getString("description")+",";
                    }
                    else 
                        q+="-"+",";
                }
                if (usuarioActivo.equals("Admin") || usuarioActivo.equals("Guest")){
                    return html.getPostBySearchAdmin(desc, own, vl,q);
                }
                else{
                    if(post.getInteger("id_user")!=currentUser.getInteger("id_user"))    
                        return html.getPostBySearch(desc,own, vl,q);
                    else
                        return html.getOwnPostBySearch(desc,own, vl,q);
                }
            }
        }
        else
            return html.getFailLogin();
    }
}