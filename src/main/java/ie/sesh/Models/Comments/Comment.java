package ie.sesh.Models.Comments;

import java.sql.Timestamp;

public class Comment {

  private int id;
  private int status_id;
  private int user_id;

  private String message;
  private String username;
  private String name;
  private int likes;
  private Timestamp date;
  private String profilePic;

  public Comment() {}

  public Comment(int status_id, int user_id, String message, Timestamp date, String profilePic) {
    this.status_id = status_id;
    this.user_id = user_id;
    this.message = message;
    this.date = date;
    this.profilePic = profilePic;
  }

  public Comment(int status_id, int user_id, String message, Timestamp date) {
    this.status_id = status_id;
    this.user_id = user_id;
    this.message = message;
    this.date = date;
  }

  public Comment(
      int id,
      int status_id,
      int user_id,
      String message,
      String username,
      String name,
      int likes,
      Timestamp date) {
    this.id = id;
    this.status_id = status_id;
    this.user_id = user_id;
    this.message = message;
    this.username = username;
    this.name = name;
    this.likes = likes;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getStatus_id() {
    return status_id;
  }

  public void setStatus_id(int status_id) {
    this.status_id = status_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public Timestamp getDate() {
    return date;
  }

  public void setDate(Timestamp date) {
    this.date = date;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }
}
