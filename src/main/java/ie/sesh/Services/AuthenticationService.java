package ie.sesh.Services;

import ie.sesh.Models.AuthDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthDAO authDAO;
    public boolean checkUserToken(String token, int user_id){
        return authDAO.checkUserToken(token, user_id);
    }
}
