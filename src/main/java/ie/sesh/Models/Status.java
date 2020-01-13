package ie.sesh.Models;

import java.sql.Timestamp;

public class Status {

  private int id;
  private int user_id;
  private String username;
  private String first_name;
  private String last_name;
  private String profile_pic;

  private String message;
  private int location;
  private int likes;
  private boolean liked;

  private Timestamp date;

  // uses User ID
  private String going;
  private String maybe;
  private String not_going;

  public Status() {}

  public Status(
      int id,
      int user_id,
      String username,
      String first_name,
      String last_name,
      String message,
      int location,
      int likes,
      boolean liked,
      Timestamp date,
      String going,
      String maybe,
      String not_going) {
    this.id = id;
    this.user_id = user_id;
    this.username = username;
    this.first_name = first_name;
    this.last_name = last_name;
    this.message = message;
    this.location = location;
    this.likes = likes;
    this.liked = liked;
    this.date = date;
    this.going = going;
    this.maybe = maybe;
    this.not_going = not_going;
  }

  public Status(int user_id, String message, int location, Timestamp date) {
    this.user_id = user_id;
    this.message = message;
    this.location = location;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getLocation() {
    return location;
  }

  public void setLocation(int location) {
    this.location = location;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public boolean isLiked() {
    return liked;
  }

  public void setLiked(boolean liked) {
    this.liked = liked;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }

  public String getGoing() {
    return going;
  }

  public void setGoing(String going) {
    this.going = going;
  }

  public String getMaybe() {
    return maybe;
  }

  public void setMaybe(String maybe) {
    this.maybe = maybe;
  }

  public String getNot_going() {
    return not_going;
  }

  public void setNot_going(String not_going) {
    this.not_going = not_going;
  }

  public String getProfile_pic() {
    return profile_pic;
  }

  public void setProfile_pic(String profile_pic) {
    this.profile_pic = profile_pic;
  }
}
