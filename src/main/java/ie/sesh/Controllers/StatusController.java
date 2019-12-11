package ie.sesh.Controllers;

import com.google.gson.Gson;

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

    @CrossOrigin(origins = "*")
    @GetMapping("/status/{status_id}")
    @ResponseBody
    public ResponseEntity<Status> getStatus(@PathVariable(name="status_id") int id) {
        log.info("Get status " + id);
        return new ResponseEntity<>(statusService.getStatus(id),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/live/feed/{user_id}")
    @ResponseBody
    public ResponseEntity<List<Status>> getLiveFeed(@PathVariable(name="user_id") int id) {
        log.info("Get all live feed for user " + id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET");

        return new ResponseEntity<>(statusService.getLiveFeed(id), headers, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/status/{user_id}")
    @ResponseBody
    public ResponseEntity<List<Status>>  getAllUserStatus(@PathVariable(name="user_id") int id) {
        log.info("Get all statuses from user " + id);
        return new ResponseEntity<>(statusService.getAllUserStatus(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/user/status/@{username}")
    @ResponseBody
    public ResponseEntity<List<Status>>  getAllUserProfileStatus(@PathVariable("username") String username) {
        log.info("Get all statuses from user " + username);
        return new ResponseEntity<>(statusService.getAllUserProfileStatus(username), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/status/{id}")
    @ResponseBody
    public ResponseEntity updateStatus(@RequestBody String status_data) {
        Gson gson = CommonUtils.convertDate(status_data);
        Status status = gson.fromJson(status_data, Status.class);
        try {
            statusService.updateStatus(status);
            log.info("Updated status " + status.getId());

            return new ResponseEntity<>("Status Updated", HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return new ResponseEntity<>("Failed to update status", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/status")
    @ResponseBody
    public ResponseEntity createStatus(@RequestBody String status_data){
        Status status = statusUtils.buildStatus(status_data);
        return statusService.createStatus(status, statusUtils.getUserToken(status_data));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/status/{id}")
    @ResponseBody
    public ResponseEntity deleteStatus(@PathVariable(name="id") int id, @RequestBody String user_data) {
        return statusService.deleteStatus(id,statusUtils.getUserId(user_data),statusUtils.getUserToken(user_data));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/check/liked/status")
    @ResponseBody
    public ResponseEntity checkLikedStatus(@RequestBody String status_data) {
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

    @CrossOrigin(origins = "*")
    @PostMapping("/like/status")
    @ResponseBody
    public ResponseEntity likeStatus(@RequestBody String status_data){
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

    @CrossOrigin(origins = "*")
    @PostMapping("/unlike/status")
    @ResponseBody
    public ResponseEntity unlikeStatus(@RequestBody String status_data){
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
