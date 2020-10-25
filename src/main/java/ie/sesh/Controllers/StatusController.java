package ie.sesh.Controllers;

import ie.sesh.Models.Status;
import ie.sesh.Models.Token;
import ie.sesh.Services.StatusService;
import ie.sesh.Utils.AuthUtils;
import ie.sesh.Utils.CommonUtils;
import ie.sesh.Utils.StatusUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/")
@RestController
public class StatusController {

  private static final Logger log = Logger.getLogger(StatusController.class);

  @Autowired StatusService statusService;

  @Autowired StatusUtils statusUtils;

  @CrossOrigin(origins = "*")
  @GetMapping("/status/{status_id}")
  @ResponseBody
  public ResponseEntity<Status> getStatus(
      @PathVariable(name = "status_id") int id, @RequestHeader HttpHeaders headers) {
    log.info("Get status " + id);
    return new ResponseEntity<>(statusService.getStatus(id), HttpStatus.OK);
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/live/feed/{user_id}")
  @ResponseBody
  public ResponseEntity<List<Status>> getLiveFeed(
      @PathVariable(name = "user_id") int id, @RequestHeader HttpHeaders headers) {
    log.info("Get all live feed for user " + id);
    Token token = AuthUtils.buildToken(headers.getFirst("Authorization"));
    return statusService.getLiveFeed(id, token);
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/profile/live/feed/{username}")
  @ResponseBody
  public ResponseEntity<List<Status>> getProfileLiveFeed(
      @PathVariable(name = "username") String username, @RequestHeader HttpHeaders headers) {
    log.info("Get all live feed for user " + username);
    Token token = AuthUtils.buildToken(headers.getFirst("Authorization"));
    return statusService.getProfileLiveFeed(username, token);
  }

  @CrossOrigin(origins = "*")
  @PutMapping("/status/{id}")
  @ResponseBody
  public ResponseEntity updateStatus(
      @RequestBody String status_data, @RequestHeader HttpHeaders headers) {
    String user_token = headers.getFirst("Authorization");
    return statusService.updateStatus(status_data);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/status")
  @ResponseBody
  public ResponseEntity createStatus(
      @RequestBody String status_data, @RequestHeader HttpHeaders headers) {
    Status status = statusUtils.buildStatus(status_data);
    Token token = AuthUtils.buildToken(headers.getFirst("Authorization"));
    return statusService.createStatus(status, token);
  }

  @CrossOrigin(origins = "*")
  @DeleteMapping("/status/{id}")
  @ResponseBody
  public ResponseEntity deleteStatus(
      @PathVariable(name = "id") int id, @RequestHeader HttpHeaders headers) {
    Token token = AuthUtils.buildToken(headers.getFirst("Authorization"));
    log.debug("User Token: " + token.getUserToken());
    return statusService.deleteStatus(id, token);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/check/liked/status")
  @ResponseBody
  public ResponseEntity checkLikedStatus(
      @RequestBody String status_data, @RequestHeader HttpHeaders headers) {
    String user_token = headers.getFirst("Authorization");
    int id = CommonUtils.parseIntFromJSON(status_data, "id");
    int status_id = CommonUtils.parseIntFromJSON(status_data, "status_id");

    try {
      boolean check = statusService.checkLikedStatus(id, status_id);
      log.info("Checking if status is liked");

      return new ResponseEntity<>(check, HttpStatus.OK);

    } catch (Exception e) {
      log.error(e.getMessage());
      return new ResponseEntity<>(
          "Failed to Check if Status " + status_id + " is liked", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/like/status/{status_id}")
  @ResponseBody
  public ResponseEntity likeStatus(
      @RequestBody String status_data,
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "status_id") int status_id) {
    Token token = AuthUtils.buildToken(headers.getFirst("Authorization"));
    return statusService.likeStatus(status_id, token);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/unlike/status/{status_id}")
  @ResponseBody
  public ResponseEntity unlikeStatus(
      @RequestBody String status_data,
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "status_id") int status_id) {
    Token token = AuthUtils.buildToken(headers.getFirst("Authorization"));
    return statusService.unlikeStatus(status_id, token);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/repost/status/{status_id}")
  @ResponseBody
  public ResponseEntity repostStatus(
      @RequestBody String status_data,
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "status_id") int status_id) {
    Token token = AuthUtils.buildToken(headers.getFirst("Authorization"));
    return statusService.repostStatus(status_id, token);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/unrepost/status/{status_id}")
  @ResponseBody
  public ResponseEntity unrepostStatus(
      @RequestBody String status_data,
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "status_id") int status_id) {
    Token token = AuthUtils.buildToken(headers.getFirst("Authorization"));
    return statusService.unrepostStatus(status_id, token);
  }
}
