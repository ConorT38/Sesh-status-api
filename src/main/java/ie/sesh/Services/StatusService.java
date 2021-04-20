package ie.sesh.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ie.sesh.Models.Status;
import ie.sesh.Models.StatusDAO;
import ie.sesh.Repository.StatusRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired StatusDAO statusDAO;

  @Autowired AuthenticationService authenticationService;

  @Autowired SecurityConfigService securityConfigService;

  @Autowired StatusRepository statusRepository;

  public StatusService() {}

  public Status getStatus(int id) {
    return statusDAO.getStatus(id);
  }

  public ResponseEntity getLiveFeed(int userId) {
    List<Status> liveFeed;

    try {
      liveFeed = statusDAO.getLiveFeed(userId);

      if (liveFeed != null) {
        log.info("Getting live feed for " + userId);
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

  public ResponseEntity getProfileLiveFeed(String username) {
    List<Status> liveFeed;

    try {

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

  public ResponseEntity getProfileLiveFeed(String username, int userId) {
    List<Status> liveFeed;

    try {

      liveFeed = statusDAO.getProfileLiveFeed(username, userId);

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

  public ResponseEntity createStatus(Status status) {
    HttpHeaders headers = securityConfigService.getHttpHeaders();

    try {
      if (statusDAO.createStatus(status)) {
        log.info("Created Status");
        return ResponseEntity.ok().headers(headers).body("Status Created");
      }

    } catch (Exception e) {
      log.error(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .headers(headers)
          .body("Create status request Failed.");
    }
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .headers(headers)
        .body("Create status request Forbidden.");
  }

  public ResponseEntity deleteStatus(int statusId, int userId) {

    try {

      if (statusDAO.deleteStatus(statusId, userId)) {
        log.info("Status deleted with id: " + statusId);
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
    log.debug("Could not delete Status: " + statusId);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to delete Status");
  }

  public boolean checkLikedStatus(int id, int status_id) {
    return statusDAO.checkLikedStatus(id, status_id);
  }

  public ResponseEntity likeStatus(int status_id, int userId) {
    try {

      if (statusDAO.likeStatus(userId, status_id)) {
        log.info("Status Liked with id: " + status_id + " by User: " + userId);
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

  public ResponseEntity unlikeStatus(int status_id, int userId) {
    try {

      if (statusDAO.unlikeStatus(userId, status_id)) {
        log.info("Status Unliked with id: " + status_id + " by User: " + userId);
        return ResponseEntity.ok()
            .headers(securityConfigService.getHttpHeaders())
            .body("Status Uniked");
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

  public ResponseEntity repostStatus(int status_id, int userId) {
    try {

      if (statusDAO.repostStatus(status_id, userId)) {
        log.info("Status Repost with id: " + status_id + " by User: " + userId);
        return ResponseEntity.ok()
            .headers(securityConfigService.getHttpHeaders())
            .body("Status Reposted");
      }
      log.info("User not allowed to Repost status");
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(securityConfigService.getHttpHeaders())
          .body("Repost denied");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.debug("Could not Repost Status: " + status_id);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to Repost Status");
  }

  public ResponseEntity unrepostStatus(int status_id, int userId) {
    try {

      if (statusDAO.unrepostStatus(status_id, userId)) {
        log.info("Status Unrepost with id: " + status_id + " by User: " + userId);
        return ResponseEntity.ok()
            .headers(securityConfigService.getHttpHeaders())
            .body("Status Reposted");
      }
      log.info("User not allowed to Unrepost status");
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(securityConfigService.getHttpHeaders())
          .body("Repost denied");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    log.debug("Could not Unrepost Status: " + status_id);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to Unrepost Status");
  }
}
