package ie.sesh.Services.Comments;

import ie.sesh.Models.Comments.Comment;
import ie.sesh.Models.Comments.CommentDAO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private static final Logger log = Logger.getLogger(CommentService.class);

    @Autowired
    CommentDAO commentDAO;

    public CommentService() {
    }

    public Comment getComment(int id){
        return commentDAO.getComment(id);
    }

    public List<Comment> getAllStatusComments(int id){
        return commentDAO.getAllStatusComments(id);
    }

    public void updateComment(Comment comment){
        commentDAO.updateComment(comment);
    }

    public void createComment(Comment comment){
        commentDAO.createComment(comment);
    }

    public void deleteComment(int id){
        commentDAO.deleteComment(id);
    }

    public boolean checkLikedComment(int id, int comment_id){
        return commentDAO.checkLikedComment(id,comment_id);
    }
}
