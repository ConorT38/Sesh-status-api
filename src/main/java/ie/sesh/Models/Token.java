package ie.sesh.Models;

public class Token {

  public String userToken;
  public int userId;

  public Token(String userToken, int userId) {
    this.userToken = userToken;
    this.userId = userId;
  }

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }
}
