package ie.sesh.Controllers.Comments;

import ie.sesh.Models.Comments.Comment;
import ie.sesh.Services.Comments.CommentService;
import ie.sesh.Utils.CommentUtils;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/")
@RestController
public class CommentsController {
    private static final Logger log = Logger.getLogger(CommentsController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    CommentUtils commentUtils;

    @GetMapping("/comment/{id}")
    @ResponseBody
    public ResponseEntity<Comment> getComment(@PathVariable(name="id") int id) {
        return new ResponseEntity<>(commentService.getComment(id), HttpStatus.OK);
    }

    @GetMapping("/status/comments/{id}")
    @ResponseBody
    public ResponseEntity<List<Comment>> getAllStatusComments(@PathVariable("id") String id) {
        return new ResponseEntity<>(commentService.getAllStatusComments(Integer.parseInt(id)), HttpStatus.OK);
    }

    @PutMapping("/comment/{id}")
    @ResponseBody
    public ResponseEntity updateComment(@RequestBody String comment_data) {
       return commentService.updateComment(comment_data);
    }

    @PostMapping("/comment")
    @ResponseBody
    public ResponseEntity createComment(@RequestBody String comment_data){
        log.info("Comment: "+comment_data);
        Comment comment = commentUtils.buildComment(comment_data);
        return commentService.createComment(comment);
    }

    @DeleteMapping("/comment/{id}")
    @ResponseBody
    public ResponseEntity deleteComment(@PathVariable(name="id") int id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>("Comment Deleted", HttpStatus.OK);
    }

    @PostMapping("/check/liked/comment")
    @ResponseBody
    public ResponseEntity checkLikedComment(@RequestBody String comment_data) {
        int id = Integer.parseInt(new JSONObject(comment_data).getJSONArray("id").get(0).toString());
        int comment_id = Integer.parseInt(new JSONObject(comment_data).getJSONArray("comment_id").get(0).toString());

        return  new ResponseEntity(commentService.checkLikedComment(id,comment_id), HttpStatus.OK);
    }
}
