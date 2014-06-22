package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Question extends Model{
    static {
        validatePresenceOf("description");
    }

    public Integer id() {
    	return this.getInteger("id");
  	}

  	public String description() {
    	return this.getString("description");
  	}

 	public User user() {
    	User u = User.findById(getInteger("user_id"));
    	return u;
  	}
    public Post post() {
    	Post p = Post.findById(getInteger("post_id"));
    	return p;
  	}

    public String answer() {
    Answer answer = Answer.findFirst("user_id = ?", this.id());
    return answer.description();
  }
}