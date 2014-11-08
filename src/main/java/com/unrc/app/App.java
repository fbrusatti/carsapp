package com.unrc.app;

import com.unrc.app.models.*;
import com.unrc.app.controller.*;
import java.util.Iterator;
import org.javalite.activejdbc.Base;
import static spark.Spark.*;

public class App {

    public static User currentUser = new User();
    public static Post currentPost = new Post();
        
    public String usuarioActivo(spark.Request req){
        Iterator<String> it =  req.session().attributes().iterator(); 
        while(it.hasNext()){
            String i = it.next();
                if((boolean)req.session().attribute(i) == true){
                    return(i);
                }      
            }
        return("fail");
    }

    public static void main(String[] args) {
        Html html = new Html();

        App app = new App();
         
        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        });
        after((request, response) -> {
            Base.close();
        });
       
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~users~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        get("/user", (req, resp) -> {
            UserC user = new UserC();
            return (user.UserAll(resp, app.usuarioActivo(req)));  
        });
        
        get("/user/:id", (req, resp) -> {
            UserC user = new UserC();
            return (user.getIdUser(req,resp,app.usuarioActivo(req)));
        });

        get("/insertuser", (req, resp) -> {                 
            UserC user = new UserC();
            return (user.getInsertUser(req,resp,app.usuarioActivo(req)));
        });
        post("/insertuser", (req, resp) -> {
            UserC user = new UserC();
            return (user.postInsertUser(req,resp,app.usuarioActivo(req)));
        });
        
        get("/registeruser", (req, resp) -> {
            UserC user = new UserC();
            return (user.getRegisterUser(req,resp));
        });
        post("/registeruser", (req, resp) -> {
            UserC user = new UserC();
            return (user.postRegisterUser(req,resp));
        });
        
        post("/getusers", (req, resp) -> {
            UserC user = new UserC();
            return (user.postEliminateUser(req,resp,app.usuarioActivo(req)));
        });
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Post~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
       
        get("/post/:id", (req, resp) -> {                
            PostC post = new PostC();
            return post.getPostId(req,resp,app.usuarioActivo(req));
        });
        
        get("/question/answers", (req, resp) -> {
            return null;
        });
        
        get("/ownpost", (req, resp) -> {
            PostC post = new PostC();
            return post.getOwnPosts(req,resp,currentUser,app.usuarioActivo(req));
        });
        
        get("/insertpost", (req, resp) -> {
            PostC post = new PostC();
            return post.getInsertPost(req,resp,currentUser,app.usuarioActivo(req));
        });
        post("/insertpost", (req, resp) -> {
            PostC post = new PostC();
            return post.postInsertPost(req,resp,currentUser,app.usuarioActivo(req));
        });
        
        post("/getpostbysearch", (req, resp) -> {
            PostC post = new PostC();
            return post.postGetPostSearch(req,resp,currentUser,currentPost,app.usuarioActivo(req));
        });
        
        post("/answerpost", (req, resp) -> {
            PostC post = new PostC();
            return  post.postAnswerPost(req,resp,currentUser,app.usuarioActivo(req));
        });
        
        get("/post", (req, resp) -> {
            PostC post = new PostC();
            return post.getPost(req,resp,app.usuarioActivo(req));
        });  
        post("/post", (req, resp) -> {
            PostC post = new PostC();
            return post.postPost(req,resp,currentPost,currentUser,app.usuarioActivo(req));
        });
        
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Questions~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        get("/questions", (req, resp) -> {
            QuestionC question = new QuestionC();
            return question.getQuestions(req,resp,app.usuarioActivo(req));
        });
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~vehicles~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
       
        get("/vehicles", (req, resp) -> {
            VehiclesC vehicles = new VehiclesC();
            return vehicles.getVehicles(req,resp,app.usuarioActivo(req));

        });
        get("/vehicle/:patente", (req, resp) -> {
            VehiclesC vehicles = new VehiclesC();
            return vehicles.getVehiclePatente(req,resp,app.usuarioActivo(req));

        });

        get("/insertvehicle", (req, resp) -> {
            VehiclesC vehicles = new VehiclesC();
            return vehicles.getInsertvehicle(req, resp,app.usuarioActivo(req));
        });
        post("/insertvehicle", (req, resp) -> {
            VehiclesC vehicles = new VehiclesC();
            return vehicles.postInsertVehicle(req, resp,currentUser,app.usuarioActivo(req));
        });
        
        get("/ownvehicles", (req, resp) -> {
            VehiclesC vehicles = new VehiclesC();
            return vehicles.getOwnVehicle(req,resp,currentUser,app.usuarioActivo(req)); 
        });

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Address~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        get("/address", (req, resp) -> {
            AddressC address = new AddressC();
            return address.getAddress(req,resp,app.usuarioActivo(req));
        });
        
        get("/insertaddress", (req, resp) -> {
            AddressC address= new AddressC();
            return address.getInsertAddress(req,resp,app.usuarioActivo(req));
        });
        post("/insertaddress", (req, resp) -> {
            AddressC address= new AddressC();
            return address.postInsertAddress(req,resp,currentUser,app.usuarioActivo(req));
        });
        
        get("/ownaddress", (req, resp) -> {
            AddressC address= new AddressC();
            return address.getOwnAddress(req,resp,currentUser,app.usuarioActivo(req)); 
        });
        
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Answers~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        get("/answers", (req, resp) -> {
            AnswerC answer = new AnswerC();
            return answer.getAnswers(req,resp,app.usuarioActivo(req));
        });
        
        get("/answers/:id", (req, resp) -> {
            AnswerC answer = new AnswerC();   
            return answer.getAnswersId(req,resp,app.usuarioActivo(req));
        });

        post("/insertanswer", (req, resp) -> {   
            AnswerC answer = new AnswerC();
            return answer.postInsertAnswer(req,resp,currentUser,app.usuarioActivo(req));
        });
        
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Contacto Admin~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        
        get("/admincontactuser", (req, resp) -> {
            AdminC admin = new AdminC();
            return admin.getAdminContactUser(req,resp,app.usuarioActivo(req));
        });  
        post("/admincontactuser", (req, resp) -> {
            AdminC admin = new AdminC();
            return admin.postAdminContactUser(req,resp,currentUser,app.usuarioActivo(req));
        });
        
        get("/admincontactguest", (req, resp) -> {
            AdminC admin = new AdminC();
            return admin.getAdminContactGuest(req,resp,app.usuarioActivo(req));
        }); 
        post("/admincontactguest", (req, resp) -> {
            AdminC admin = new AdminC();
            return admin.postAdminContactGuest(req,resp,app.usuarioActivo(req));      
        });
              
        get("/inbox",(req,resp) -> {        
            AdminC admin = new AdminC();
            return admin.getInbox(req,resp,app.usuarioActivo(req));        
        });
        post("/inbox",(req,resp) -> {       
            AdminC admin = new AdminC();
            return admin.postInbox(req,resp,app.usuarioActivo(req));
        });
        
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Login~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
        
        get("/cSession", (req, resp) -> {
            req.session().removeAttribute(app.usuarioActivo(req));
            currentUser = null;
            currentPost = null;   
            return html.getExitLogin();
        });
        
        get("/loginuser", (req, resp) -> {
            resp.type("text/html");
            return html.loginUsuario();
        });
        post("/loginuser", (req, resp) -> {
            resp.type("text/html");
            User tmp = User.findFirst("email = ?", req.queryParams("email"));
            if (req.queryParams("email").equals("admin") && req.queryParams("contrasena").equals("1234")) {
                req.session().attribute("Admin", true);;
                return html.adminControlPane();
            }
            else{
                if (tmp != null && tmp.get("contrasena").equals(req.queryParams("contrasena"))) {
                    currentUser = tmp;
                    req.session().attribute("User", true);
                    return html.userControlPane();
                }
            }
            return  html.getMessagePag("El usuario o la contrasena son incorrectos","/loginuser");
        });
        
        get("/webpag", (req, resp) -> {
            LoginCo login = new LoginCo();
            return login.getWebPag(req,resp,app.usuarioActivo(req));            
        });
        
        get("/admin", (req, resp) -> {
            LoginCo login = new LoginCo();
            return login.getAdmin(req,resp,app.usuarioActivo(req));
        });
        
        get("/guestcp", (req, resp) -> {
            req.session().attribute("Guest", true);
            LoginCo login = new LoginCo();
            return login.getGuestCP(req,resp);
        });
        
    }
}
