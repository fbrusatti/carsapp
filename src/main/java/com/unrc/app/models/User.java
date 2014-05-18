package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import java.util.List;
import java.util.LinkedList;

public class User extends Model {
  static {     
		validatePresenceOf("first_name", "last_name");
  }
  
  @Override
  public void add(Model m) {
	  if (m.getClass()==Car.class) {
		  Car c = (Car)m;
		  Vehicle v = Vehicle.findById(c.get("vehicle_id"));
		  this.add(v);
	  } else {
		  super.add(m);
	  }
  }
  
  /**
   *Returns a list of Posts that was posted by Users that live in the city "location". If the city 
   *don't exists return the empty list.
   * @param location is the name of the city where the posts were posted.
   * @return Returns a list of Posts that was posted by Users that live in the city "location".
   */
  public List<Post> searchPostByLocation(String location) {
	  City c = City.findFirst("name = ?", location);
	  List<Post> postFromLocate=new LinkedList<Post>();
	  if (c!=null) {
	  
		  //List<User> usersFromLocate = (User.where("city_id = ?",c.getId()));
		  List<User> usersFromLocate = (List<User>)(c.get("users"));
		  for (User u : usersFromLocate) {
			  List<Post> postOfUser = u.getAll(Post.class);
			  for (Post p : postOfUser) {
				  postFromLocate.add(p);
			  }
		  }
	  }
	  return postFromLocate;
  }
  
  /**
   * Returns a list of all Posts related with a Vehicle of type "vehicleType"
   * @param vehicleType is the type of the vehicle to search the posts.
   * @return a list of vehicleType 
   */
  public List<Post> searchPostByVehicleType(String vehicleType) {
	  List<Post> postOfVehicleType=new LinkedList<Post>();
	  if (vehicleType=="Car") {
		  
	  }
	  if (vehicleType=="Truck") {
		  
	  }
	  if (vehicleType=="Motorcycle") {
		  
	  }
	  return postOfVehicleType;
	  // TODO implements this. 
  }
}
