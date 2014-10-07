package com.unrc.app;

import com.unrc.app.models.*;

import com.unrc.app.models.Vehicle;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseInt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.node.*;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.percolate.TransportShardMultiPercolateAction.Response;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.javalite.activejdbc.Base;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.mustache.MustacheTemplateEngine;
import static org.elasticsearch.node.NodeBuilder.*;

/**
 * Hello world!
 *
 */
public class App {

    
    public static User currentUser = new User();
    public static Post currentPost = new Post();
    public static boolean connectionAdmin = false;
    public static boolean connectionUser = false;
    public static boolean connectionGuest = false;

    public static void main(String[] args) {
        System.out.println("hi!");
        Html html = new Html();
        
        Spark.before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "yoush4llnotp4ss");
        });
        Spark.after((request, response) -> {
            Base.close();
        });
        
//        System.out.println("---------elastic-------------------------");
//       GetResponse response =ElasticSearch.client().prepareGet("vehicles", "vehicle", "2")
//                  .execute()
//                  .actionGet();
//            System.out.println("---------------------------consulta---------------------------------------------");
//            
//            System.out.println("source.valuess---"+response.getSource());
//            System.out.println("source as string  --- "+response.getSourceAsString());
//            ElasticSearch.client().close();
        
      
//        response =ElasticSearch.client().prepareGet("vehicles", "vehicle", "2")
//                  .execute()
//                  .actionGet();
//            System.out.println("---------------------------consulta---------------------------------------------");
//            
//            System.out.println("source.valuess---"+response.getSource());
//            System.out.println("source as string  --- "+response.getSourceAsString());
//            ElasticSearch.client().close();
//        System.out.println("-----------cierroo-------------------------------------------");
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~users~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        get("/users", (request, resp) -> {
            
                Map<String, Object> attributes = new HashMap<>();
                List<User> users = User.findAll();
                attributes.put("users_count", users.size());
                attributes.put("users", users);
                return new ModelAndView(attributes, "users.mustache");
           
        },
                new MustacheTemplateEngine()
        );

        get("/user", (req, resp) -> {
            if(connectionAdmin == true){
                resp.type("text/html");
                List<User> u = User.findAll();
                String users = "";
                for (int i = 0; i < u.size(); i++) {
                    users = users + u.get(i).getString("id_user")+ " | " + " " + u.get(i).getString("first_name") + " " + u.get(i).getString("last_name")+ " | "+ " " + u.get(i).getString("email") + ",";
                
                }
                
                return html.getAllUsers(users);
             }
            else
                return html.getFailLogin();
        });
        get("/user/:id", (req, resp) -> {
            if(connectionAdmin == true){
                resp.type("text/html");
                System.out.print(req.params(":id"));
                User user = User.findFirst("id_user = ?", req.params(":id"));
                String vl = user.get("first_name") + "," + user.get("last_name") + "," + user.get("email");

                return html.getUserBy(vl);
                         }
            else
                return html.getFailLogin();
        });

        get("/insertuser", (req, resp) -> {
             if(connectionAdmin == true){
                resp.type("text/html");
                return html.IngresarUsuario();
            }
            else
                return html.getFailLogin();
        });
        post("/insertuser", (req, resp) -> {
            if(connectionAdmin == true){
                resp.type("text/html");
                User.createUser(req.queryParams("first_name"),req.queryParams("last_name"),req.queryParams("email"),req.queryParams("contrasena"));
                return html.admin();
            }
            else
                return html.getFailLogin();
        });
        
        get("/registeruser", (req, resp) -> {
                resp.type("text/html");
                return html.RegistrarUsuario();

        });
        post("/registeruser", (req, resp) -> {

                resp.type("text/html");
                User.createUser(req.queryParams("first_name"),req.queryParams("last_name"),req.queryParams("email"),req.queryParams("contrasena"));
                return html.irlogin();
        });
        
        post("/getusers", (req, resp) -> {
            if(connectionAdmin == true){
                resp.type("text/html");
                User tmp = User.findFirst("id_user = ?", req.queryParams("id_user"));
                User.deleteUser(tmp.getInteger("id_user"));
                return html.admin();
            }
            else
                return html.getFailLogin();
        });
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Post~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       
        get("/post/:id", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                Post post = Post.findFirst("id_post = ?", req.params(":id"));
                String own = "";
                User name = User.findFirst("id_user = ?", post.getString("id_user"));
                own = own + name.getString("first_name") + "}"  + post.getString("patent") + "}" + post.getString("description");
                return html.getPostBy(own);
                        }
            else
                return html.getFailLogin();
        });
        get("/question/answers", (req, resp) -> {
            
            
            return null;
        });
        get("/ownpost", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                List<Post> post = Post.find("id_user = ?", currentUser.get("id_user"));
                if (!post.isEmpty()) {
                    String vh = "";
                    for (int i = 0; i < post.size(); i++) {
                        vh = vh + post.get(i).getString("id_post") + "}" + post.get(i).getString("description") + "}" + currentUser.getString("first_name") + "}" + post.get(i).getString("patent") + ",";
                    }
                    if(connectionUser==true){
                    return html.getPostUser(vh);
                    }
                    else{
                        return html.getPostGuest(vh);
                    }
                } else 
                    return  html.getMessagePag("No hay post registrados","/webpag");
                }
             else
                return html.getFailLogin();
        });
        get("/insertpost", (req, resp) -> {
            if(connectionUser==true){
                resp.type("text/html");
                List<Vehicle> v = Vehicle.find("id_user = ?", currentUser.get("id_user"));
                String vh = "";

                if (!v.isEmpty()) {
                    for (int i = 0; i < v.size(); i++) {
                        vh = vh + v.get(i).getString("patent") + ",";
                    }
                }
                else return html.getMessagePag("Necesita ingresar vehiculos para crear un post","/webpag");

                
                List<UsersAddress> a = UsersAddress.find("id_user = ?", currentUser.get("id_user"));
                String ad = "";
                if (!a.isEmpty()) {

                    
                    for (int i = 0; i < a.size(); i++) {
                        Address b = Address.findFirst("id_address = ?", a.get(i).getString("id_address"));
                        ad = ad + b.getString("id_address")+ "}" +b.getString("province") + " | " + b.getString("city") + " | " + b.getString("postal_code") + " | " + b.getString("street") + " | " +b.getString("num")+ ",";
                        
                    }
                }
                else return  html.getMessagePag("Necesita ingresar una direccion para crear un post","/webpag");

                return html.IngresarPost(currentUser,vh,ad);

             }
             else
                return html.getFailLogin();
        });
        post("/insertpost", (req, resp) -> {
            if(connectionUser==true){
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
        });
        post("/getpostbysearch", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                if (req.queryParams("coment") == null || req.queryParams("coment").equals("")) {
                    return  html.getMessagePag(" No escribio ningun comentario ","/post");
                }

                Question.createQuestion(currentUser,currentPost.getInteger("id_post"),req.queryParams("coment"));
                return  html.getMessagePag("Se envio la pregunta","/post");
             }
             else
                return html.getFailLogin();

        });
        post("/answerpost", (req, resp) -> {
        if(connectionAdmin == true || connectionUser==true){

            resp.type("text/html");
            Question q = Question.findFirst("id_question = ?", req.queryParams("question"));
            if (q.equals(null)||req.queryParams("responder") == null || req.queryParams("responder").equals("")) {
                return  html.getMessagePag("No se guardo correctamente la respuesta","/post");
            }
            System.out.println(req.queryParams("coment"));
            Answer.createAnswer(currentUser, q.getInteger("id_question"), req.queryParams("responder"));
            return  html.getMessagePag("Se guardo la respuesta","/post");
        }
             else
                return html.getFailLogin();

        });
        get("/post", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true || connectionGuest==true){
                resp.type("text/html");
                List<Post> post = Post.findAll();
                String vh = "";
                for (int i = 0; i < post.size(); i++) {
                    User name = User.findFirst("id_user = ?", post.get(i).getString("id_user"));
                    if (name != null) {
                        vh = vh + post.get(i).getString("id_post") + "}" + post.get(i).getString("description") + "}" + name.getString("first_name") + "}" + post.get(i).getString("patent") + ",";
                    }
                }
                if(connectionUser==true || connectionGuest==true){
                    if(connectionUser==true) return html.getPostUser(vh);
                    else return html.getPostGuest(vh);
                }
                else{
                    return html.getAllPost(vh);
                }
            }
             else
                return html.getFailLogin();
        });
        
        post("/post", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true || connectionGuest==true){

               if( ( (connectionAdmin==true  && !req.queryParams("postEliminar").isEmpty()) )){
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
                        }else q+="-"+",";

                    }
                    if (connectionAdmin == true || connectionGuest ==true){
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
        });
        


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Questions~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        get("/questions", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                List<User> u = User.findAll();
                String users = "";
                for (int i = 0; i < u.size(); i++) {
                    users = users + u.get(i).getString("first_name") + " " + u.get(i).getString("last_name") + ",";
                }

                return html.getAllUsers(users);
             }
             else
                return html.getFailLogin();
        });
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~vehicles~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        get("/vehicles", (req, resp) -> {
            if(connectionAdmin == true){
                resp.type("text/html");
                List<Vehicle> v = Vehicle.findAll();
                String vh = "";
                for (int i = 0; i < v.size(); i++) {
                    vh = vh + v.get(i).getString("Patent") + "}" + v.get(i).getString("Mark") + "}" + v.get(i).getString("Model") + "}" + v.get(i).getString("Color") + "}" + v.get(i).getString("Tipo") + "}" + v.get(i).getString("id_user") + "}" + v.get(i).getString("isCoupe") + "}" + v.get(i).getString("cc") + "}" + v.get(i).getString("capacity") + ",";
                }
                

                return html.getAutomoviles(vh);
                         }
             else
                return html.getFailLogin();
        });
        get("/vehicle/:patente", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                Vehicle v = Vehicle.findFirst("patent = ?", req.params(":patente"));
                String vh = "";

                vh = vh + v.getString("Patent") + "}" + v.getString("Mark") + "}" + v.getString("Model") + "}" + v.getString("Color") + "}" + v.getString("Tipo") + "}" + v.getString("id_user") + "}"  + v.getString("isCoupe") + "}" + v.getString("cc") + "}" + v.getString("capacity");

                return html.getVehicleBy(vh);
             }
             else
                return html.getFailLogin();
        });

        get("/insertvehicle", (req, resp) -> {
            if(connectionUser==true){
                resp.type("text/html");
                return html.IngresarAutomovil();
             }
             else
                return html.getFailLogin();
        });
        
        post("/insertvehicle", (req, resp) -> {
            if(connectionUser==true){
                resp.type("text/html");
                if (req.queryParams("tipo").equals("0")){
                    Vehicle.createVehicle(req.queryParams("patente"), req.queryParams("marca"), req.queryParams("modelo"), currentUser.getInteger("id_user"), req.queryParams("color"), "Auto", 0, req.queryParams("isCoupe"), 0); 
                }
                else 
                    if (req.queryParams("tipo").equals("2")) {
                        Vehicle.createVehicle(req.queryParams("patente"), req.queryParams("marca"), req.queryParams("modelo"), currentUser.getInteger("id_user"), req.queryParams("color"), "Camion", 0,"-", parseInt(req.queryParams("Capacity")));
                    } 
                    else  
                        if (req.queryParams("tipo").equals("1")) {
                            Vehicle.createVehicle(req.queryParams("patente"), req.queryParams("marca"), req.queryParams("modelo"), currentUser.getInteger("id_user"), req.queryParams("color"), "Moto", parseInt(req.queryParams("CC")),"-", 0);
                    }

                return html.IngresarAutomovil();
                         }
             else
                return html.getFailLogin();
        });
        get("/ownvehicles", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                List<Vehicle> v = Vehicle.find("id_user = ?", currentUser.get("id_user"));
                if (!v.isEmpty()) {

                    String vh = "";
                    for (int i = 0; i < v.size(); i++) {
                        vh = vh + v.get(i).getString("patent") + "}" + v.get(i).getString("mark") + "}" + v.get(i).getString("model") + "}" + v.get(i).getString("color") + "}" + v.get(i).getString("tipo") + "}" + v.get(i).getString("id_user") + "}" + v.get(i).getString("isCoupe") + "}" + v.get(i).getString("cc") + "}" + v.get(i).getString("capacity") + ",";
                    }
                    

                    return html.getAutomoviles(vh);
                }
                return  html.getMessagePag("No posee vehiculos registrados","/webpag");
                                     }
             else
                return html.getFailLogin();
        });

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Address~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        get("/address", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                List<Address> v = Address.findAll();
                String vh = "";
                for (int i = 0; i < v.size(); i++) {
                    vh = vh + v.get(i).getString("province") + "}" + v.get(i).getString("city") + "}" + v.get(i).getString("postal_code") + "}" + v.get(i).getString("street") + "}" +v.get(i).getString("num")+ ",";
                }

                return html.getCities(vh);
            }
             else
                return html.getFailLogin();
        });
        get("/insertaddress", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                return html.IngresarCiudad();
            }
             else
                return html.getFailLogin();
        });
        post("/insertaddress", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                Address.createAddress(req.queryParams("direccion"),parseInt(req.queryParams("num")),req.queryParams("ciudad"),req.queryParams("provincia"),parseInt(req.queryParams("codigo_postal")));
                Address a = Address.findByAddress(req.queryParams("direccion"),parseInt(req.queryParams("num")),req.queryParams("ciudad"),req.queryParams("provincia"),parseInt(req.queryParams("codigo_postal")));
                int id_address= parseInt(a.getString("id_address"));
                UsersAddress.createUsersAddress(currentUser.getInteger("id_user"), id_address);
                return html.IngresarCiudad();
            }
            else
                return html.getFailLogin();
        });
        
        get("/ownaddress", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                List<UsersAddress> a = UsersAddress.find("id_user = ?", currentUser.get("id_user"));
                if (!a.isEmpty()) {

                    String vh = "";
                    
                    for (int i = 0; i < a.size(); i++) {
                        Address b = Address.findFirst("id_address = ?", a.get(i).getString("id_address"));
                        vh = vh + b.getString("province") + "}" + b.getString("city") + "}" + b.getString("postal_code") + "}" + b.getString("street") + "}" +b.getString("num")+ ",";
                        
                    }

                    return html.getOwnAddress(vh);
                }
                return  html.getMessagePag("No posee ciudades registradas","/webpag");
                                     }
             else
                return html.getFailLogin();
        });
        

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Answers~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        get("/answers", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                List<Answer> v = Answer.findAll();
                String vh = "";
                for (int i = 0; i < v.size(); i++) {
                    vh = vh + v.get(i).getString("id_post") + " " + v.get(i).getString("id_user") + " " + v.get(i).getString("description") + ",";
                }
                return html.getAnswers(vh);
            }
            else
                return html.getFailLogin();
        });
        get("/answers/:id", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){

                resp.type("text/html");
                Answer v = Answer.findFirst("id_answer", ":id");
                String vh = "";
                vh = vh + v.getString("id_post") + " " + v.getString("id_user") + " " + v.getString("description");
                return html.getAnswersByid(vh);
                        }
            else
                return html.getFailLogin();
        });

        post("/insertanswer", (req, resp) -> {
            if(connectionAdmin == true || connectionUser==true){
                resp.type("text/html");
                Answer.createAnswer(currentUser, parseInt(req.queryParams("id_pregunta")), req.queryParams("descripcion"));
                return html.IngresarRespuesta();
            }
            else
                return html.getFailLogin();
        });
        
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Contacto Admin~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        
        get("/admincontactuser", (req, resp) -> {
            if(connectionUser==true || connectionGuest==true){
                resp.type("text/html");
                return html.contactAdminUser();
                     }
            else
                return html.getFailLogin();
        });
        
        post("/admincontactuser", (req, resp) -> {
            if(connectionUser==true || connectionGuest==true){
                resp.type("text/html");
                Message.createMessage(currentUser.getInteger("id_user"), req.queryParams("mensaje"));
                return html.contactAdminUser();
            }
            else
                return html.getFailLogin();
        });
        
        get("/admincontactguest", (req, resp) -> {
            if(connectionUser==true || connectionGuest==true){
                resp.type("text/html");
                return html.contactAdminGuest();
                     }
            else
                return html.getFailLogin();
        });
        
        post("/admincontactguest", (req, resp) -> {
            if(connectionUser==true || connectionGuest==true){
                resp.type("text/html");
                MessageGuest.createMessageGuest(req.queryParams("mensaje"));
                return html.contactAdminGuest();
            }
            else
                return html.getFailLogin();
        });
        
        
        get("/inbox",(req,resp) -> {
            if(connectionAdmin == true){
               resp.type("text/html");
                List<Message> m = Message.findAll();
                List<MessageGuest> mg = MessageGuest.findAll();
                String message = "";
                String messageGuest = "";
                for (int i = 0; i < m.size(); i++) {
                    User name = User.findFirst("id_user = ?", m.get(i).getString("id_user"));
                    message = message + m.get(i).getString("id_message")+ "}" +  name.getString("first_name") + ",";
                }
                for (int i = 0; i < mg.size(); i++) {
                    messageGuest = messageGuest + mg.get(i).getString("id_messageGuest")+ ",";
                }
                return html.AdminInbox(message,messageGuest);
            }
            return html.getFailLogin();  
        });
        
        
        post("/inbox",(req,resp) -> {
            if(connectionAdmin == true){
               resp.type("text/html");
               
               if(!req.queryParams("idmu").isEmpty()){
                   Message messageu = Message.findFirst("id_message = ?", req.queryParams("idmu"));
                    String mu =  messageu.getString("description");   
                    return html.getMessage(mu);
               }
               if(!req.queryParams("idmi").isEmpty()){
                   MessageGuest messagei = MessageGuest.findFirst("id_messageGuest = ?", req.queryParams("idmi"));
                   String mi =  messagei.getString("description");
                   return html.getMessage(mi);
               }
               if(!req.queryParams("eliminarmu").isEmpty()){
                   Message messageu = Message.findFirst("id_message = ?", req.queryParams("eliminarmu"));
                   messageu.deleteMessage(messageu.getInteger("id_message"));
                    return html.getMessagePag("Mensaje Eliminado Con Exito","/inbox");
               }
               if(!req.queryParams("eliminarmi").isEmpty()){
                   MessageGuest messagei = MessageGuest.findFirst("id_messageGuest = ?", req.queryParams("eliminarmi"));
                   messagei.deleteMessageGuest(messagei.getInteger("id_messageGuest"));
                   return html.getMessagePag("Mensaje Eliminado Con Exito","/inbox");
               }

               return html.getMessagePag("Error","/inbox");
               
               
            }
            else
                return html.getFailLogin();


        });
        
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Login~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        
        get("/loginuser", (req, resp) -> {
            resp.type("text/html");
            connectionAdmin =false;
            connectionUser =false;
            connectionGuest =true;
            currentUser = null;
            currentPost = null;
            
            return html.loginUsuario();
        });
        post("/loginuser", (req, resp) -> {

            
            resp.type("text/html");
            //validar usuario exitente o  admin
            User tmp = User.findFirst("email = ?", req.queryParams("email"));

            if (req.queryParams("email").equals("admin") && req.queryParams("contrasena").equals("1234")) {
                    connectionAdmin =true;
                    connectionUser =false;
                    connectionGuest =false;
                    return html.adminControlPane();
                }
            else{
                if (tmp != null && tmp.get("contrasena").equals(req.queryParams("contrasena"))) {
                        connectionAdmin =false;
                        connectionUser =true;
                        connectionGuest =false;
                        currentUser = tmp;
                        return html.userControlPane();
                }
            }
            return  html.getMessagePag("El usuario o la contrasena son incorrectos","/loginuser");
        });
        
        get("/webpag", (req, resp) -> {
            if(connectionUser==true){
                resp.type("text/html");
                return html.webpage();
            }
            else
                return html.getFailLogin();
        });
        
        get("/admin", (req, resp) -> {
            if(connectionAdmin == true){
                resp.type("text/html");
                return html.admin();
            }
            else
                return html.getFailLogin();
        });
        
        get("/guestcp", (req, resp) -> {
                resp.type("text/html");
                return html.guest();
        });
    }
    

}
