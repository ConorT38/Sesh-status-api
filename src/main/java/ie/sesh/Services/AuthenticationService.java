package ie.sesh.Services;

import ie.sesh.Models.AuthDAO;
import ie.sesh.Utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired AuthDAO authDAO;

  public boolean checkUserToken(String token) {
    String[] auth_arr = CommonUtils.splitAuthTokenValues(token);
    int user_id = Integer.parseInt(auth_arr[0]);
    String user_token = auth_arr[1];

    if (user_token.isEmpty() || user_token == null || user_id < 0) {
      return false;
    }
    return authDAO.checkUserToken(user_token, user_id);
  }
}
