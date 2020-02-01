package ie.sesh.Models.Comments;

import ie.sesh.Models.Token;
import java.util.List;

public interface CommentDAO {

  Comment getComment(int id);

  List<Comment> getAllStatusComments(int id);

  boolean createComment(Comment comment, Token token);

  boolean updateComment(Comment comment);

  void deleteComment(int id);

  boolean checkLikedComment(int id, int comment_id);
}
