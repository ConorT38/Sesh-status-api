package ie.sesh.Utils;

import ie.sesh.Models.Token;

public class AuthUtils {

  public static Token buildToken(String token) {
    String[] auth_arr = CommonUtils.splitAuthTokenValues(token);
    if (auth_arr[0] != null && auth_arr[1] != null) {
      int user_id = Integer.parseInt(auth_arr[0]);
      String user_token = auth_arr[1];
      return new Token(user_token, user_id);
    }
    return null;
  }
}
