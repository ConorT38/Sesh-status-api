package ie.sesh.Controllers.Comments;

import ie.sesh.Models.Comments.Comment;
import ie.sesh.Models.Token;
import ie.sesh.Services.Comments.CommentService;
import ie.sesh.Utils.AuthUtils;
import ie.sesh.Utils.CommentUtils;

import ie.sesh.Utils.JwtTokenUtil;
import org.json.JSONObject;
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
public class CommentsController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired CommentService commentService;

  @Autowired CommentUtils commentUtils;

  @Autowired JwtTokenUtil jwtTokenUtil;

  @CrossOrigin(origins = "*")
  @GetMapping("/comment/{id}")
  @ResponseBody
  public ResponseEntity<Comment> getComment(@PathVariable(name = "id") int id) {
    return new ResponseEntity<>(commentService.getComment(id), HttpStatus.OK);
  }

  @CrossOrigin(origins = "*")
  @GetMapping("/comments/{id}")
  @ResponseBody
  public ResponseEntity<List<Comment>> getAllStatusComments(
      @PathVariable("id") String id, @RequestHeader HttpHeaders headers) {
    return commentService.getAllStatusComments(Integer.parseInt(id));
  }

  @CrossOrigin(origins = "*")
  @PutMapping("/comment/{id}")
  @ResponseBody
  public ResponseEntity updateComment(@RequestBody String comment_data) {
    return commentService.updateComment(comment_data);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/comment")
  @ResponseBody
  public ResponseEntity createComment(
      @RequestBody String comment_data, @RequestHeader HttpHeaders headers) {
    log.info("Comment: " + comment_data);
    Comment comment = commentUtils.buildComment(comment_data);
    int userId = jwtTokenUtil.getUserIdFromToken(jwtTokenUtil.getTokenFromHeaders(headers));
    return commentService.createComment(comment);
  }

  @CrossOrigin(origins = "*")
  @DeleteMapping("/comment/{id}")
  @ResponseBody
  public ResponseEntity deleteComment(@PathVariable(name = "id") int id) {
    commentService.deleteComment(id);
    return new ResponseEntity<>("Comment Deleted", HttpStatus.OK);
  }

  @CrossOrigin(origins = "*")
  @PostMapping("/check/liked/comment")
  @ResponseBody
  public ResponseEntity checkLikedComment(@RequestBody String comment_data) {
    int id = Integer.parseInt(new JSONObject(comment_data).getJSONArray("id").get(0).toString());
    int comment_id =
        Integer.parseInt(new JSONObject(comment_data).getJSONArray("comment_id").get(0).toString());

    return new ResponseEntity(commentService.checkLikedComment(id, comment_id), HttpStatus.OK);
  }
}
