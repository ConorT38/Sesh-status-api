package ie.sesh.Controllers;

import ie.sesh.Models.Status;
import ie.sesh.Models.Token;
import ie.sesh.Services.StatusService;
import ie.sesh.Utils.AuthUtils;
import ie.sesh.Utils.CommonUtils;
import ie.sesh.Utils.JwtTokenUtil;
import ie.sesh.Utils.StatusUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/")
@RestController
public class StatusController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired StatusService statusService;

  @Autowired StatusUtils statusUtils;

  @Autowired JwtTokenUtil jwtTokenUtil;

  @CrossOrigin(origins = "*")
  @GetMapping("/live/feed/{user_id}")
  @ResponseBody
  public ResponseEntity<List<Status>> getLiveFeed(
      @PathVariable(name = "user_id") int id, @RequestHeader HttpHeaders headers) {
    log.info("Get all live feed for user " + id);
    return statusService.getLiveFeed(id);
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/profile/live/feed/{username}/{userId}")
  @ResponseBody
  public ResponseEntity<List<Status>> getProfileLiveFeed(
      @PathVariable(name = "username") String username,
      @PathVariable int userId,
      @RequestHeader HttpHeaders headers) {
    log.info("Get all live feed for user " + username);
    return statusService.getProfileLiveFeed(username, userId);
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
    int userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getTokenFromHeaders(headers));
    Status status = statusUtils.buildStatus(status_data);
    status.setUser_id(userId);
    log.info("Status created");

    return statusService.createStatus(status);
  }

  @CrossOrigin(origins = "*")
  @DeleteMapping("/status/{id}")
  @ResponseBody
  public ResponseEntity deleteStatus(
      @PathVariable(name = "id") int id, @RequestHeader HttpHeaders headers) {
    int userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getTokenFromHeaders(headers));
    return statusService.deleteStatus(id, userId);
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
    int userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getTokenFromHeaders(headers));
    return statusService.likeStatus(status_id, userId);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/unlike/status/{status_id}")
  @ResponseBody
  public ResponseEntity unlikeStatus(
      @RequestBody String status_data,
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "status_id") int status_id) {
    int userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getTokenFromHeaders(headers));
    return statusService.unlikeStatus(status_id, userId);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/repost/status/{status_id}")
  @ResponseBody
  public ResponseEntity repostStatus(
      @RequestBody String status_data,
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "status_id") int status_id) {
    int userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getTokenFromHeaders(headers));
    return statusService.repostStatus(status_id, userId);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/unrepost/status/{status_id}")
  @ResponseBody
  public ResponseEntity unrepostStatus(
      @RequestBody String status_data,
      @RequestHeader HttpHeaders headers,
      @PathVariable(name = "status_id") int status_id) {
    int userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getTokenFromHeaders(headers));
    return statusService.unrepostStatus(status_id, userId);
  }
}
