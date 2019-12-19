package ie.sesh.Utils;

import ie.sesh.Models.Comments.Comment;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CommentUtils {

  public Comment buildComment(String comment_data) {
    int status_id = CommonUtils.parseIntFromJSON(comment_data, "status_id");
    int user_id = CommonUtils.parseIntFromJSON(comment_data, "user_id");
    String message = CommonUtils.parseStringFromJSON(comment_data, "message");
    Timestamp date = new Timestamp(new java.util.Date().getTime());

    return new Comment(status_id, user_id, message, date);
  }
}
