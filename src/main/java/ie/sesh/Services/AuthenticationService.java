package ie.sesh.Services;

import ie.sesh.Models.AuthDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthDAO authDAO;
    public boolean checkUserToken(String user_token, int user_id){
        if(user_token.isEmpty() || user_token == null || user_id < 0){
            return false;
        }
        return authDAO.checkUserToken(user_token, user_id);
    }
}
