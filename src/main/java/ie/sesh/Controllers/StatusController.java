package ie.sesh.Controllers;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import ie.sesh.Models.Status;
import ie.sesh.Services.StatusService;
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

    @Autowired
    StatusService statusService;

    @Autowired
    StatusUtils statusUtils;

    @GetMapping("/status/{status_id}")
    @ResponseBody
    public ResponseEntity<Status> getStatus(@PathVariable(name="status_id") int id, @RequestHeader HttpHeaders headers) {
        log.info("Get status " + id);
        return new ResponseEntity<>(statusService.getStatus(id),HttpStatus.OK);
    }

    @GetMapping("/live/feed/{user_id}")
    @ResponseBody
    public ResponseEntity<List<Status>> getLiveFeed(@PathVariable(name="user_id") int id, @RequestHeader HttpHeaders headers) {
        log.info("Get all live feed for user " + id);
        String user_token = headers.getFirst("Authorization");
        return statusService.getLiveFeed(id,user_token);
    }

    @GetMapping("/user/status/{user_id}")
    @ResponseBody
    public ResponseEntity<List<Status>>  getAllUserStatus(@PathVariable(name="user_id") int id, @RequestHeader HttpHeaders headers) {
        log.info("Get all statuses from user " + id);
        return new ResponseEntity<>(statusService.getAllUserStatus(id), HttpStatus.OK);
    }

    @GetMapping("/user/status/@{username}")
    @ResponseBody
    public ResponseEntity<List<Status>>  getAllUserProfileStatus(@PathVariable("username") String username, @RequestHeader HttpHeaders headers) {
        log.info("Get all statuses from user " + username);
        return new ResponseEntity<>(statusService.getAllUserProfileStatus(username), HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    @ResponseBody
    public ResponseEntity updateStatus(@RequestBody String status_data) {
       return statusService.updateStatus(status_data);
    }

    @PostMapping("/status")
    @ResponseBody
    public ResponseEntity createStatus(@RequestBody String status_data, @RequestHeader HttpHeaders headers){
        Status status = statusUtils.buildStatus(status_data);
        String user_token = headers.getFirst("Authorization");
        return statusService.createStatus(status, user_token);
    }

    @DeleteMapping("/status/{id}")
    @ResponseBody
    public ResponseEntity deleteStatus(@PathVariable(name="id") int id, @RequestHeader HttpHeaders headers) {
        String user_token = headers.getFirst("Authorization");
        return statusService.deleteStatus(id,user_token);
    }

    @PostMapping("/check/liked/status")
    @ResponseBody
    public ResponseEntity checkLikedStatus(@RequestBody String status_data, @RequestHeader HttpHeaders headers) {
        int id = CommonUtils.parseIntFromJSON(status_data,"id");
        int status_id = CommonUtils.parseIntFromJSON(status_data,"status_id");

        try{
            boolean check = statusService.checkLikedStatus(id,status_id);
            log.info("Checking if status is liked");

            return new ResponseEntity<>(check, HttpStatus.OK);

        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>("Failed to Check if Status "+ status_id + " is liked", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/like/status")
    @ResponseBody
    public ResponseEntity likeStatus(@RequestBody String status_data, @RequestHeader HttpHeaders headers){
        int id = CommonUtils.parseIntFromJSON(status_data,"id");
        int status_id = CommonUtils.parseIntFromJSON(status_data,"status_id");

        try{
            statusService.likeStatus(id,status_id);
            log.info("Liked status " + status_id);

            return new ResponseEntity<>(true, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>("Failed to like status "+ status_id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/unlike/status")
    @ResponseBody
    public ResponseEntity unlikeStatus(@RequestBody String status_data, @RequestHeader HttpHeaders headers){
        int id = CommonUtils.parseIntFromJSON(status_data,"id");
        int status_id = CommonUtils.parseIntFromJSON(status_data,"status_id");

        try{
            statusService.unlikeStatus(id,status_id);
            log.info("Unliked status " + status_id);

            return new ResponseEntity<>(true, HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>("Failed to unlike status "+ status_id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
