package ie.sesh.Controllers;

import ie.sesh.Models.Status;
import ie.sesh.Services.StatusService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/public/")
@RestController
public class PublicStatusController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired StatusService statusService;

  @CrossOrigin(origins = "*")
  @GetMapping("/status/{status_id}")
  @ResponseBody
  public ResponseEntity<Status> getStatus(
      @PathVariable(name = "status_id") int id, @RequestHeader HttpHeaders headers) {
    log.info("Get status " + id);
    return new ResponseEntity<>(statusService.getStatus(id), HttpStatus.OK);
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/profile/live/feed/{username}")
  @ResponseBody
  public ResponseEntity<List<Status>> getProfileLiveFeed(
      @PathVariable(name = "username") String username, @RequestHeader HttpHeaders headers) {
    log.info("Get all live feed for user " + username);
    return statusService.getProfileLiveFeed(username);
  }
}
