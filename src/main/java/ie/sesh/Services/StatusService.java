package ie.sesh.Services;


import ie.sesh.Models.Status;
import ie.sesh.Models.StatusDAO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private static final Logger log = Logger.getLogger(StatusService.class);

    @Autowired
    StatusDAO statusDAO;

    @Autowired
    AuthenticationService authenticationService;

    public StatusService() {
    }

    public Status getStatus(int id){
        return statusDAO.getStatus(id);
    }

    public List<Status> getLiveFeed(int id){
        return statusDAO.getLiveFeed(id);
    }

    public List<Status> getAllUserStatus(int id){
        return statusDAO.getAllUserStatus(id);
    }

    public List<Status> getAllUserProfileStatus(String username){
        return statusDAO.getAllUserProfileStatus(username);
    }

    public void updateStatus(Status status){
        statusDAO.updateStatus(status);
    }

    public boolean createStatus(Status status, String token){
        if(authenticationService.checkUserToken(token, status.getUser_id())) {
            return statusDAO.createStatus(status);
        }
        return false;
    }

    public void deleteStatus(int id){
        statusDAO.deleteStatus(id);
    }

    public boolean checkLikedStatus(int id, int status_id){
        return statusDAO.checkLikedStatus(id, status_id);
    }

    public void likeStatus(int id, int status_id){
        statusDAO.likeStatus(id, status_id);
    }

    public void unlikeStatus(int id, int status_id){
        statusDAO.unlikeStatus(id, status_id);
    }
}
