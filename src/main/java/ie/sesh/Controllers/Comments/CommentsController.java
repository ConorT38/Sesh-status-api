package ie.sesh.Controllers.Comments;

import com.google.gson.Gson;

import ie.sesh.Models.Comments.Comment;
import ie.sesh.Services.Comments.CommentService;
import ie.sesh.Utils.CommentUtils;
import ie.sesh.Utils.CommonUtils;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentsController {
    private static final Logger log = Logger.getLogger(CommentsController.class);

    @Autowired
    CommentService commentService;

    @Autowired
    CommentUtils commentUtils;

    @GetMapping("/get/comment/{id}")
    @ResponseBody
    public ResponseEntity<Comment> getComment(@PathVariable(name="id") int id) {
        return new ResponseEntity<>(commentService.getComment(id), HttpStatus.OK);
    }

    @GetMapping("/get/status/comments/{id}")
    @ResponseBody
    public ResponseEntity<List<Comment>> getAllStatusComments(@PathVariable("id") String id) {
        return new ResponseEntity<>(commentService.getAllStatusComments(Integer.parseInt(id)), HttpStatus.OK);
    }

    @PutMapping("/update/comment")
    @ResponseBody
    public ResponseEntity updateComment(@RequestBody String comment_data) {
        Gson gson = CommonUtils.convertDate(comment_data);
        Comment comment = gson.fromJson(comment_data, Comment.class);
        commentService.updateComment(comment);
        return new ResponseEntity("Comment Updated", HttpStatus.OK);
    }

    @PostMapping("/create/comment")
    @ResponseBody
    public ResponseEntity createComment(@RequestBody String comment_data){
        log.info("Comment: "+comment_data);
        try {
            Comment comment = commentUtils.buildComment(comment_data);
            commentService.createComment(comment);
            return new ResponseEntity<>("Comment created", HttpStatus.OK);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>("Failed to create Comment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/comment/{id}")
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
