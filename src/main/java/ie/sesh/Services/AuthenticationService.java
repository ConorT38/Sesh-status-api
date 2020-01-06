package ie.sesh.Services;

import ie.sesh.Models.AuthDAO;
import ie.sesh.Models.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  @Autowired AuthDAO authDAO;

  public boolean checkUserToken(Token token) {
    if (token == null) {
      return false;
    }
    return authDAO.checkUserToken(token.getUserToken(), token.getUserId());
  }
}
