package com.unrc.app;

import org.javalite.activejdbc.Base;
import com.unrc.app.models.*;
import java.util.*;
import com.unrc.app.MustacheTemplateEngine;

import spark.ModelAndView;
import static spark.Spark.*;
import spark.Request;
import spark.Session;
import org.elasticsearch.node.*;
import org.elasticsearch.client.*;

public class App{
    public static final Node node = org.elasticsearch.node
                                        .NodeBuilder
                                        .nodeBuilder()
                                        .clusterName("carsapp")
                                        .local(true)
                                        .node();
    public static Client client(){
        return node.client();
    }

	public static void main( String[] args ){
        System.out.println("Starting...");
        
        before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        });

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "root");
        User admin = new User();
        admin.createUser("admin","admin","admin@carsapp.com", "admin");
        Base.close();

        get("/login",(request, response) -> {
            LoginController controller = new LoginController();
            return controller.getLogin(request);
        },
            new MustacheTemplateEngine()
        );

        get("/users",(request, response) -> {
            UsersController controller = new UsersController();
            return controller.getUsers(request);
        },
            new MustacheTemplateEngine()
        );

        get("/users/:id",(request,response) -> {
            UsersController controller = new UsersController();
            return controller.getUsersId(request);
        },
            new MustacheTemplateEngine()
        );

        //Lista todos los vehiculos
        get("/vehicles",(request,response)->{
            VehicleController vehicleController = new VehicleController();
            return vehicleController.VehiclesList(request);
        },
            new MustacheTemplateEngine()
        );

        //Lista un vehiculo particular
        get("/vehicles/:patent",(request,response)->{
            VehicleController vehicleController = new VehicleController();
            return vehicleController.VehiclesLicensePlate(request);
        },
            new MustacheTemplateEngine()
        );

        //Lista todos los autos
        get("/cars",(request,response)->{
            CarController carController=new CarController();
            return carController.CarList();
        },
            new MustacheTemplateEngine()
        );   

        //Lista un auto particular
        get("/cars/:patent",(request,response)->{
           CarController carController = new CarController();
           return carController.CarLicensePlate(request);
        },
            new MustacheTemplateEngine()
        );

        //Lista todas las motocicletas
        get("/motorcycles",(request,response)->{
            MotorcycleController motorcycles=new MotorcycleController();
            return motorcycles.MotorcycleList();
        },
            new MustacheTemplateEngine()
        );       

        //Lista una motocicleta en particular
        get("/motorcycles/:patent",(request,response)->{
            MotorcycleController motorcycles=new MotorcycleController();
            return motorcycles.MotorcycleLicensePlate(request);
        },
            new MustacheTemplateEngine()
        );

        //Lista todos los Trucks
        get("/trucks",(request,response)->{
            TruckController truck=new TruckController();
            return truck.TruckList();
        },
            new MustacheTemplateEngine()
        );       

        //Lista un truck particular
        get("/trucks/:patent",(request,response)->{
            TruckController truck=new TruckController();
            return truck.TruckLicensePlate(request);
        },
            new MustacheTemplateEngine()
        );

        //Lista todos los posts
        get("/posts",(request, response) -> {
            PostsController controller = new PostsController();
            return controller.PostsList();
        },
            new MustacheTemplateEngine()
        );      

        //Lista un post particular
        get("/posts/:id",(request,response)->{
            PostsController controller = new PostsController();
            return controller.PostId(request);           
        },
            new MustacheTemplateEngine()
        );    

        get("/questions",(request, response) -> {
            QuestionController questions=new QuestionController();
            return questions.QuestionList();
        },
            new MustacheTemplateEngine()
        );

        //Lista una question particular
        get("/questions/:id",(request,response)->{
          QuestionController questions=new QuestionController();
          return questions.QuestionId(request); 
        },
            new MustacheTemplateEngine()
        );
       
        //List all answers
        get("/answers", (request,response) ->{
            AnswerController answers=new AnswerController();
            return answers.AnswerList();
        },
            new MustacheTemplateEngine()
        );       

        //List a specific answer
        get("/answers/:id", (request,responser)->{
          AnswerController answers=new AnswerController();
          return answers.AnswerId(request);
        },
            new MustacheTemplateEngine()
        );

        get("/rates",(request, response) -> {
            RateController rates=new RateController();
            return rates.RateList();
        },
            new MustacheTemplateEngine()
        );
        
         //Lista un post particular
        get("/rates/:id",(request,response)->{
            RateController rate=new RateController();
            return rate.RateId(request);
        },
            new MustacheTemplateEngine()
        );      

        //List all Punctuations
        get("/punctuations",(request,response)->{
            PunctuationController punctuation=new PunctuationController();
            return punctuation.PunctuationList();
        },
            new MustacheTemplateEngine()
        );             

        //List a specific Punctuation
        get("/punctuations/:id",(request,response)->{
            PunctuationController punctuation=new PunctuationController();
            return punctuation.PunctuationId(request);
        },
            new MustacheTemplateEngine()
        );

        //List all addresses
        get("/addresses", (request, response)->{
            if (null == LibraryController.existsSession(request)) {
                response.redirect("/");
            }
            AdressController adress=new AdressController();
            return adress.AdressList();
        },
            new MustacheTemplateEngine()
        );  

        //List a specific address
        get("/addresses/:id",(request,response)->{
            AdressController adress=new AdressController();
            return adress.AdressId(request);
        },
            new MustacheTemplateEngine()
        );

        /**INSERCIONES**/
        get("/newUser",(request,response)->{
            UsersController controller = new UsersController();
            return controller.getNewUser(request, response);
        },
            new MustacheTemplateEngine()
        );

        post ("/search",(request, response) ->{
            SearchController controller = new SearchController();
            return controller.postSearch(request, response);
        },
            new MustacheTemplateEngine()
        );

        //Insert an User
        post ("/users",(request, response) ->{
            UsersController controller = new UsersController();
            controller.postUsers(request, response);
            return "success";
        });

        //Insert an address
        get("/newAddress",(request,response)->{
            if (null == LibraryController.existsSession(request)) {
                response.redirect("/");
            } 
            AdressController adress=new AdressController();
            return adress.AdressNew();
        });  

        post ("/addresses",(request, response) ->{
            AdressController adress=new AdressController();
            adress.PostAdress(request,response);
            return "success";
        });

        get("/newVehicle",(request,response)->{
            VehicleController controller = new VehicleController();
            return controller.GetNewVehicle(request,response);
        }); 

        post ("/vehicles",(request, response) ->{
            VehicleController controller = new VehicleController();
            controller.NewVehicle(request,response);
            return "success";
        });
 
        get("/newPost",(request,response)->{
            PostsController controller = new PostsController();
            return controller.GetNewPost(request,response);
        });

        post ("/posts",(request, response) ->{
            PostsController controller = new PostsController();
            controller.NewPost(request,response);
            return "success";
        });

        get("/newQuestion",(request,response)->{
            if (null == LibraryController.existsSession(request)) {
                response.redirect("/");
            }
            QuestionController question=new QuestionController();
            return question.QuestionNew();
        }); 

        post ("/questions",(request, response) ->{
            QuestionController question=new QuestionController();
            question.QuestionPost(request,response);
            return "success";   
        });

        get("/newAnswer",(request,response)->{
            if (null == LibraryController.existsSession(request)) {
                response.redirect("/");
            }
            AnswerController answer=new AnswerController();
            return answer.AnswerNew();
        }); 

        post ("/answers",(request, response) ->{
            AnswerController answer=new AnswerController();
            answer.AnswerPost(request,response);
            return "success";
        });

        /**INSERCIONES**/
        get("/",(request,response)->{
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "invitado.mustache");
        },
            new MustacheTemplateEngine()
        );

        get("/home",(request, response) -> {
            Session session = LibraryController.existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "hello.mustache");
        },
            new MustacheTemplateEngine()
        );

        get("/homeAdmin",(request, response) -> {
            Session session = LibraryController.existsSession(request);
            if (null == session) {
                response.redirect("/");
            }
            Map<String, Object> attributes = new HashMap<>();
            return new ModelAndView(attributes, "admin.mustache");
        },
            new MustacheTemplateEngine()
        );

        get ("/authentication",(request, response) ->{
            AuthenticationController controller = new AuthenticationController();
            return controller.getAuthentication(request, response);
        });

        get("/logout", (request, response) -> {
            LogoutController controller = new LogoutController();
            return controller.getLogout(request, response);
        });

        after((request, response) -> {
            Base.close();    
        });
    }

    public static void close(){
        node.close();
    }
}
