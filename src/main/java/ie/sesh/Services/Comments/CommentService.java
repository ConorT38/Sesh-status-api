package ie.sesh.Services.Comments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ie.sesh.Models.Comments.Comment;
import ie.sesh.Models.Comments.CommentDAO;

import ie.sesh.Models.Token;
import ie.sesh.Services.AuthenticationService;
import ie.sesh.Services.SecurityConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired CommentDAO commentDAO;

  @Autowired AuthenticationService authenticationService;

  @Autowired SecurityConfigService securityConfigService;

  public CommentService() {}

  public Comment getComment(int id) {
    return commentDAO.getComment(id);
  }

  public ResponseEntity getAllStatusComments(int id) {
    List<Comment> commentFeed;
    try {

      commentFeed = commentDAO.getAllStatusComments(id);
      if (commentFeed != null) {
        log.info("Getting comments feed for " + id);
        return ResponseEntity.ok()
            .headers(securityConfigService.getHttpHeaders())
            .body(commentFeed);
      }
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .headers(securityConfigService.getHttpHeaders())
          .body("Could not get Comment Feed");
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .headers(securityConfigService.getHttpHeaders())
        .body("Failed to get Comments");
  }

  public ResponseEntity updateComment(String comment_data) {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
    Comment comment = gson.fromJson(comment_data, Comment.class);
    try {
      if (!commentDAO.updateComment(comment)) {
        return new ResponseEntity<>("Failed to update Comment", HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>("Comment updated", HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return new ResponseEntity<>("Failed to update Comment", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public ResponseEntity createComment(Comment comment) {
    try {
      if (!commentDAO.createComment(comment)) {
        return new ResponseEntity<>("Failed to create Comment", HttpStatus.INTERNAL_SERVER_ERROR);
      }
      return new ResponseEntity<>("Comment created", HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return new ResponseEntity<>("Failed to create Comment", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public void deleteComment(int id) {
    commentDAO.deleteComment(id);
  }

  public boolean checkLikedComment(int id, int comment_id) {
    return commentDAO.checkLikedComment(id, comment_id);
  }
}
