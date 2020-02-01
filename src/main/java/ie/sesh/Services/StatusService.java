package ie.sesh.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ie.sesh.Models.Status;
import ie.sesh.Models.StatusDAO;
import ie.sesh.Models.Token;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

  private static final Logger log = Logger.getLogger(StatusService.class);

  @Autowired StatusDAO statusDAO;

  @Autowired AuthenticationService authenticationService;

  @Autowired SecurityConfigService securityConfigService;

  public StatusService() {}

  public Status getStatus(int id) {
    return statusDAO.getStatus(id);
  }

  public ResponseEntity getLiveFeed(int id, Token token) {
    List<Status> liveFeed;

    try {
      if (!authenticationService.checkUserToken(token)) {
        log.info("User Token is not valid");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .headers(securityConfigService.getHttpHeaders())
            .body("User Token is not valid");
      }
      liveFeed = statusDAO.getLiveFeed(id, token.getUserToken());

      if (liveFeed != null) {
        log.info("Getting live feed for " + id);
        return ResponseEntity.ok().headers(securityConfigService.getHttpHeaders()).body(liveFeed);
      }

      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(securityConfigService.getHttpHeaders())
          .body("Could not get Live Feed");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to get Live feed");
  }

  public ResponseEntity getProfileLiveFeed(String username, Token token) {
    List<Status> liveFeed;

    try {
      if (!authenticationService.checkUserToken(token)) {
        log.info("User Token is not valid");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .headers(securityConfigService.getHttpHeaders())
            .body("User Token is not valid");
      }
      liveFeed = statusDAO.getProfileLiveFeed(username);

      if (liveFeed != null) {
        log.info("Getting live feed for " + username);
        return ResponseEntity.ok().headers(securityConfigService.getHttpHeaders()).body(liveFeed);
      }

      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(securityConfigService.getHttpHeaders())
          .body("Could not get Live Feed");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to get Live feed");
  }

  public ResponseEntity updateStatus(String status_data) {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
    Status status = gson.fromJson(status_data, Status.class);
    try {
      if (!statusDAO.updateStatus(status)) {
        return new ResponseEntity<>("Failed to update status", HttpStatus.INTERNAL_SERVER_ERROR);
      }
      log.info("Updated status " + status.getId());
      return new ResponseEntity<>("Status Updated", HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return new ResponseEntity<>("Failed to update status", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public ResponseEntity createStatus(Status status, Token token) {
    HttpHeaders headers = securityConfigService.getHttpHeaders();

    try {
      if (!authenticationService.checkUserToken(token)) {
        log.info("User Token is not valid");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .headers(headers)
            .body("User Token is not valid");
      }

      if (statusDAO.createStatus(status, token)) {
        log.info("Created Status");
        return ResponseEntity.ok().headers(headers).body("Status Created");
      }
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(headers)
          .body("Could not create status");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(headers)
        .body("Status failed to Create");
  }

  public ResponseEntity deleteStatus(int status_id, Token token) {

    try {
      if (!authenticationService.checkUserToken(token)) {
        log.info("User Token is not valid");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .headers(securityConfigService.getHttpHeaders())
            .body("User Token is not valid");
      }
      if (statusDAO.deleteStatus(status_id, token.getUserId(), token.getUserToken())) {
        log.info("Status deleted with id: " + status_id);
        return ResponseEntity.ok()
            .headers(securityConfigService.getHttpHeaders())
            .body("Status Deleted");
      }
      log.info("User not allowed to delete status");
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(securityConfigService.getHttpHeaders())
          .body("Deletion denied");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.debug("Could not delete Status: " + status_id);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to delete Status");
  }

  public boolean checkLikedStatus(int id, int status_id) {
    return statusDAO.checkLikedStatus(id, status_id);
  }

  public ResponseEntity likeStatus(int status_id, Token token) {
    try {
      if (!authenticationService.checkUserToken(token)) {
        log.info("User Token is not valid");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .headers(securityConfigService.getHttpHeaders())
            .body("User Token is not valid");
      }
      if (statusDAO.likeStatus(token.getUserId(), status_id, token.getUserToken())) {
        log.info("Status Liked with id: " + status_id + " by User: " + token.getUserId());
        return ResponseEntity.ok()
            .headers(securityConfigService.getHttpHeaders())
            .body("Status Liked");
      }
      log.info("User not allowed to like status");
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(securityConfigService.getHttpHeaders())
          .body("Like denied");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.debug("Could not Like Status: " + status_id);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to Like Status");
  }

  public ResponseEntity unlikeStatus(int status_id, Token token) {
    try {
      if (!authenticationService.checkUserToken(token)) {
        log.info("User Token is not valid");
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .headers(securityConfigService.getHttpHeaders())
            .body("User Token is not valid");
      }
      if (statusDAO.unlikeStatus(token.getUserId(), status_id, token.getUserToken())) {
        log.info("Status Unliked with id: " + status_id + " by User: " + token.getUserId());
        return ResponseEntity.ok()
            .headers(securityConfigService.getHttpHeaders())
            .body("Status Liked");
      }
      log.info("User not allowed to Unliked status");
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(securityConfigService.getHttpHeaders())
          .body("Like denied");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.debug("Could not Unliked Status: " + status_id);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to Unliked Status");
  }
}
