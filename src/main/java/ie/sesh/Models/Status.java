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
  private int numComments;

  private boolean isRepost;
  private boolean userDidRepost;
  private int numReposts;
  private String reposterName;
  private String reposterUsername;
  private Timestamp repostDate;

  private boolean hasImage;
  private String imageLocation;

  private Timestamp date;

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
      int numComments,
      boolean userDidRepost,
      boolean isRepost,
      int numReposts,
      String reposterName,
      String reposterUsername,
      Timestamp repostDate) {
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
    this.numComments = numComments;
    this.userDidRepost = userDidRepost;
    this.isRepost = isRepost;
    this.numReposts = numReposts;
    this.reposterName = reposterName;
    this.reposterUsername = reposterUsername;
    this.repostDate = repostDate;
  }

  public Status(
      String message, int location, Timestamp date, boolean hasImage, String imageLocation) {
    this.message = message;
    this.location = location;
    this.date = date;
    this.hasImage = hasImage;
    this.imageLocation = imageLocation;
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

  public String getProfile_pic() {
    return profile_pic;
  }

  public void setProfile_pic(String profile_pic) {
    this.profile_pic = profile_pic;
  }

  public int getNumComments() {
    return numComments;
  }

  public void setNumComments(int numComments) {
    this.numComments = numComments;
  }

  public boolean isHasImage() {
    return hasImage;
  }

  public void setHasImage(boolean hasImage) {
    this.hasImage = hasImage;
  }

  public String getImageLocation() {
    return imageLocation;
  }

  public void setImageLocation(String imageLocation) {
    this.imageLocation = imageLocation;
  }

  public boolean isRepost() {
    return isRepost;
  }

  public void setIsRepost(boolean repost) {
    isRepost = repost;
  }

  public int getNumReposts() {
    return numReposts;
  }

  public void setNumReposts(int numReposts) {
    this.numReposts = numReposts;
  }

  public String getReposterName() {
    return reposterName;
  }

  public void setReposterName(String reposterName) {
    this.reposterName = reposterName;
  }

  public String getReposterUsername() {
    return reposterUsername;
  }

  public void setReposterUsername(String reposterUsername) {
    this.reposterUsername = reposterUsername;
  }

  public Timestamp getRepostDate() {
    return repostDate;
  }

  public void setRepostDate(Timestamp repostDate) {
    this.repostDate = repostDate;
  }

  public boolean isUserDidRepost() {
    return userDidRepost;
  }

  public void setUserDidRepost(boolean userDidRepost) {
    this.userDidRepost = userDidRepost;
  }
}
