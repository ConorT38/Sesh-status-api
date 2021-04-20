package ie.sesh.Models.Comments.impl;

import ie.sesh.Models.Comments.Comment;
import ie.sesh.Models.Comments.CommentDAO;
import ie.sesh.Models.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ie.sesh.Database.SQLConstants.*;
import static java.lang.Math.toIntExact;

@Component
public class CommentDAOImpl implements CommentDAO {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private JdbcTemplate jdbcTemplate;

  public Comment getComment(int id) {
    log.info("Getting comment: " + id);
    return (Comment)
        jdbcTemplate.queryForObject(
            GET_STATUS_COMMENT_BY_ID, new Object[] {id}, new CommentMapper());
  }

  public List<Comment> getAllStatusComments(int id) {
    log.info("Getting comments by id " + id);
    List<Comment> comments = new ArrayList<Comment>();
    List<Map<String, Object>> commentList =
        jdbcTemplate.queryForList(GET_ALL_STATUS_COMMENTS_BY_ID, new Object[] {id});

    for (Map comment : commentList) {
      Comment c = new Comment();
      c.setId(toIntExact((Long) (comment.get("id"))));
      c.setUser_id(toIntExact((Long) (comment.get("user_id"))));
      c.setName(comment.get("first_name") + " " + comment.get("last_name"));
      c.setUsername((String) comment.get("username"));
      c.setMessage((String) comment.get("message"));
      c.setLikes((int) comment.get("likes"));
      c.setDate((Timestamp) comment.get("uploaded"));
      c.setProfilePic((String) comment.get("profile_pic"));
      comments.add(c);
    }
    return comments;
  }

  public boolean updateComment(Comment comment) {
    log.info("Updating comment: " + comment.getId());
    KeyHolder holder = new GeneratedKeyHolder();
    try {
      jdbcTemplate.update(
          connection -> {
            PreparedStatement ps =
                connection.prepareStatement(UPDATE_STATUS_COMMENT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, comment.getUser_id());
            ps.setInt(2, comment.getStatus_id());
            ps.setString(3, comment.getMessage());
            ps.setInt(4, comment.getLikes());
            return ps;
          },
          holder);
      return true;
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean createComment(Comment comment) {
    log.info(
        "User: "
            + comment.getUser_id()
            + " inserting comment into status: "
            + comment.getStatus_id());
    KeyHolder holder = new GeneratedKeyHolder();
    try {
      int check =
          jdbcTemplate.update(
              connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(
                        INSERT_STATUS_COMMENT, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, comment.getUser_id());
                ps.setInt(2, comment.getStatus_id());
                ps.setString(3, comment.getMessage());
                return ps;
              },
              holder);
      if (check > 0) {
        int done =
            jdbcTemplate.update(
                connection -> {
                  PreparedStatement ps =
                      connection.prepareStatement(
                          INCREMENT_STATUS_COMMENTS, Statement.RETURN_GENERATED_KEYS);
                  ps.setInt(1, comment.getStatus_id());
                  return ps;
                },
                holder);
        return done > 0;
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public void deleteComment(int id) {
    log.info("Deleting comment: " + id);
    KeyHolder holder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(DELETE_STATUS_COMMENT, Statement.RETURN_GENERATED_KEYS);
          ps.setInt(1, id);
          return ps;
        },
        holder);
  }

  public boolean checkLikedComment(int id, int comment_id) {
    int check =
        jdbcTemplate.queryForObject(
            CHECK_LIKED_COMMENT, new Object[] {id, comment_id}, Integer.class);

    if (check == 1) {
      return true;
    }
    return false;
  }
}

class CommentMapper implements RowMapper {

  @Override
  public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
    Comment comment = new Comment();
    comment.setStatus_id(rs.getInt("status_id"));
    comment.setUser_id(rs.getInt("user_id"));
    comment.setMessage(rs.getString("message"));
    comment.setLikes(rs.getInt("likes"));
    comment.setDate(rs.getTimestamp("uploaded"));
    return comment;
  }
}
