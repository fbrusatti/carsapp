package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import java.util.Map;
import java.util.HashMap;
import org.elasticsearch.node.Node;
import org.elasticsearch.client.Client;
import org.elasticsearch.action.index.IndexResponse;
import static org.elasticsearch.node.NodeBuilder.*;

public class Post extends Model {
  static {
	validatePresenceOf("price");
  }

  public Integer id() {
    return this.getInteger("id");
  }

  public Integer price() {
    return this.getInteger("price");
  }

  public String description() {
    return this.getString("description");
  }

  public Integer rate() {
    return this.getInteger("rate");
  }

  public Integer ratings() {
    return this.getInteger("total_rating");
  }

  public Integer sumRate() {
    return this.getInteger("sum_rate");
  }

  public void addRate(Integer r) { //update rate, total_rating, sum_rate to a new rate
    this.set("total_rating", this.ratings() + 1);
    this.set("sum_rate", this.sumRate() + r);
    this.saveIt();
    this.set("rate",Integer.toString(this.sumRate()/this.ratings()));
    this.saveIt();
  }

  public User user() {
    User u = User.findById(this.getInteger("user_id"));
    return u;
  }

  public Integer userId() {
    return this.getInteger("user_id");
  }


  public Vehicle vehicle() {
    Vehicle v = Vehicle.findById(this.getInteger("vehicle_id"));
    return v;
  }

  public Integer vehicleId() {
    return this.getInteger("vehicle_id");
  }


  public void afterCreate() {
    //Starts the elastic search cluster
    Node node = nodeBuilder().local(true).clusterName("carsapp").node();
    Client client = node.client();

    //Index the just created post
    Map<String, Object> json = new HashMap<String, Object>();
    json.put("id",this.id());
    json.put("userId",this.userId());
    json.put("vehicleId",this.vehicleId());
    json.put("price",this.price());
    json.put("description",this.description());

    IndexResponse response = client.prepareIndex("posts","post")
                                    .setSource(json)
                                    .execute()
                                    .actionGet();


    node.close();
  }
}