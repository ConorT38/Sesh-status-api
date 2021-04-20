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
          + "AND u.user_id=? ;";
  public static final String CHECK_LIKED_STATUS =
      "SELECT EXISTS(SELECT 1 FROM status_likes where user_id =? and status_id = ?);";
  public static final String LIKE_STATUS =
      "UPDATE "
          + "  `status` "
          + "SET "
          + "  `status`.likes = `status`.likes + 1 "
          + "WHERE "
          + "  `status`.id = ?;";
  public static final String INSERT_LIKED_STATUS =
      "INSERT INTO " + "  `status_likes` (user_id, status_id) " + "VALUES(?, ?);";

  public static final String UNLIKE_STATUS =
      "UPDATE "
          + "  `status` "
          + "SET "
          + "  `status`.likes = `status`.likes - 1 "
          + "WHERE "
          + "  `status`.id = ?;";

  public static final String DELETE_LIKED_STATUS =
      "DELETE FROM "
          + "  `status_likes`"
          + "WHERE "
          + "    `status_likes`.user_id = ? "
          + "AND "
          + "    `status_likes`.status_id = ?;";

  public static final String REPOST_STATUS =
      "INSERT INTO `reposts` (reposter_id, status_id) VALUES(?, ?);";

  public static final String INCREMENT_REPOST_STATUS =
      "UPDATE`status` SET `status`.reposts = `status`.reposts + 1 WHERE `status`.id = ?";

  public static final String UNREPOST_STATUS = "CALL unrepost_status(?,?,?);";
  public static final String CHECK_REPOSTED_STATUS =
      "SELECT EXISTS(SELECT 1 FROM reposts where status_id =? and reposter_id = ?);";

  public static final String INSERT_STATUS_COMMENT =
      "INSERT INTO comments (user_id, status_id, message, uploaded) VALUES(?, ?, ?, NOW());";
  public static final String INCREMENT_STATUS_COMMENTS =
      "UPDATE `status` SET `status`.comments = `status`.comments + 1 WHERE `status`.id = ?";

  public static final String UPDATE_STATUS_COMMENT =
      "UPDATE comments SET status_id,user_id=?,message=?,likes=? WHERE id=?";
  public static final String GET_STATUS_COMMENT_BY_ID = "SELECT * FROM comments WHERE id = ?";
  public static final String GET_ALL_STATUS_COMMENTS_BY_ID =
      "SELECT * FROM comments INNER JOIN users ON comments.user_id=users.id WHERE comments.status_id = ? ORDER BY comments.id DESC";
  public static final String DELETE_STATUS_COMMENT = "DELETE FROM comments WHERE id=?";
  public static final String CHECK_LIKED_COMMENT =
      "SELECT EXISTS(SELECT 1 FROM comments_likes where user_id =? and comment_id = ?);";

  // TODO: This only shows your statuses if you follow someone, if you don't and you create a status
  // you won't see it.
  public static final String GET_LIVE_FEED =
      "SELECT DISTINCT status.*, users.username, users.profile_pic, users.first_name, users.last_name, repost.status_id,"
          + "repost.reposter_id, repost.first_name as repost_first_name, repost.last_name as repost_last_name, repost.username as repost_username"
          + " FROM status "
          + " LEFT JOIN("
          + "  SELECT reposts.id as r_id,reposts.status_id,"
          + " reposts.reposter_id , ur.friend_id, u.*"
          + "  FROM user_relationship ur "
          + "  LEFT JOIN reposts "
          + "  ON reposts.reposter_id=ur.friend_id "
          + "  LEFT JOIN users u"
          + "  ON u.id=reposts.reposter_id"
          + "  where ur.user_id=? "
          + "  ) repost"
          + "  ON status.id=repost.status_id "
          + "  LEFT JOIN users "
          + "  ON users.id=status.user_id "
          + "  INNER JOIN user_relationship"
          + "  ON user_relationship.user_id=?"
          + "  where status.id=repost.status_id OR status.user_id=repost.friend_id OR status.user_id=user_relationship.friend_id OR status.user_id=?"
          + " ORDER BY uploaded DESC"
          + " LIMIT 20;";

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

  public static final String INCREMENT_USER_POSTS = "UPDATE users SET posts = posts+1 WHERE id = ?";
  public static final String DECREMENT_USER_POSTS = "UPDATE users SET posts = posts-1 WHERE id = ?";
}
