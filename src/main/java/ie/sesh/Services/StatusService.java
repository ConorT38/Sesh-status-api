package ie.sesh.Services;


import ie.sesh.Models.Status;
import ie.sesh.Models.StatusDAO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private static final Logger log = Logger.getLogger(StatusService.class);

    @Autowired
    StatusDAO statusDAO;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    SecurityConfigService securityConfigService;

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

    public ResponseEntity createStatus(Status status, String token){
        HttpHeaders headers = securityConfigService.getHttpHeaders();
        try {
            if(!authenticationService.checkUserToken(token, status.getUser_id())){
                log.error("User Token is not valid");
                return  ResponseEntity.status(HttpStatus.FORBIDDEN).headers(headers).body("User Token is not valid");
            }

            if(statusDAO.createStatus(status)){
                log.info("Created Status");
                return ResponseEntity.ok().headers(headers).body("Status Created");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(headers).body("Could not create status");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Status failed to Create");
    }

    public ResponseEntity deleteStatus(int status_id, int user_id, String token){
        HttpHeaders headers = securityConfigService.getHttpHeaders();
        try {
            if(!authenticationService.checkUserToken(token, user_id)){
                log.error("User Token is not valid");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(headers).body("User Token is not valid");
            }
            if(statusDAO.deleteStatus(status_id,user_id,token)){
                log.info("Status deleted with id: "+status_id);
                return ResponseEntity.ok().headers(headers).body("Status Deleted");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).headers(headers).body("Deletion denied");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body("Failed to delete Status");

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
