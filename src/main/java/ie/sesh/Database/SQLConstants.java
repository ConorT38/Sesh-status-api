package ie.sesh.Database;

public class SQLConstants {

  public static final String INSERT_STATUS =
      "INSERT INTO status(user_id,message,location,uploaded, has_image, media) values(?,?,?,?,?,?)";
  public static final String UPDATE_STATUS =
      "UPDATE status SET user_id=?,message=?,location=?,likes=?,uploaded=? WHERE id=?";
  public static final String GET_STATUS_BY_ID = "SELECT * FROM status WHERE id = ?";
  public static final String GET_STATUS_BY_USERNAME =
      "SELECT * ,status.id as status_id, users.username, users.first_name, users.last_name FROM status INNER JOIN users ON users.id=status.user_id WHERE users.username = ? ORDER BY status.id DESC";
  public static final String DELETE_STATUS =
      "DELETE s "
          + "FROM status s "
          + "INNER JOIN user_logged_in u "
          + "ON s.user_id=u.user_id "
          + "WHERE s.id=? "
          + "AND u.user_id=? "
          + "AND u.token=? "
          + "AND u.logged_out IS NULL;";
  public static final String CHECK_LIKED_STATUS =
      "SELECT EXISTS(SELECT 1 FROM status_likes where user_id =? and status_id = ?);";
  public static final String LIKE_STATUS = "CALL like_status(?,?,?);";
  public static final String UNLIKE_STATUS = "CALL unlike_status(?,?,?);";

  public static final String INSERT_STATUS_COMMENT = "CALL create_comment(?,?,?,?)";
  public static final String UPDATE_STATUS_COMMENT =
      "UPDATE comments SET status_id,user_id=?,message=?,likes=? WHERE id=?";
  public static final String GET_STATUS_COMMENT_BY_ID = "SELECT * FROM comments WHERE id = ?";
  public static final String GET_ALL_STATUS_COMMENTS_BY_ID =
      "SELECT * FROM comments INNER JOIN users ON comments.user_id=users.id WHERE comments.status_id = ? ORDER BY comments.id DESC";
  public static final String DELETE_STATUS_COMMENT = "DELETE FROM comments WHERE id=?";
  public static final String CHECK_LIKED_COMMENT =
      "SELECT EXISTS(SELECT 1 FROM comments_likes where user_id =? and comment_id = ?);";

  public static final String GET_LIVE_FEED = "CALL get_live_feed(?,?)";

  public static final String GET_LIVE_FEED_OLDER =
      "SELECT status.*,users.username,users.profile_pic, users.first_name, users.last_name FROM status "
          + "LEFT JOIN user_relationship "
          + "ON status.user_id=user_relationship.friend_id "
          + "INNER JOIN users "
          + "ON users.id=status.user_id "
          + "WHERE user_relationship.user_id=? "
          + "AND user_relationship.type='friend' OR status.user_id=?  "
          + "AND id < ?"
          + "ORDER BY uploaded DESC "
          + "LIMIT 20";

  public static final String CHECK_USER_TOKEN =
      "SELECT 1 FROM user_logged_in WHERE token=? AND user_id=?";
}
