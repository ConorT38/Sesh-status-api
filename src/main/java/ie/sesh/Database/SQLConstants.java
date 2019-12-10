package ie.sesh.Database;

public class SQLConstants {

    public static final String INSERT_STATUS = "INSERT INTO status(user_id,message,location,uploaded) values(?,?,?,?)";
    public static final String UPDATE_STATUS = "UPDATE status SET user_id=?,message=?,location=?,likes=?,uploaded=?,going=?,maybe=?,not_going=? WHERE id=?";
    public static final String GET_STATUS_BY_ID = "SELECT * FROM status WHERE id = ?";
    public static final String GET_STATUS_BY_USERNAME= "SELECT * FROM status INNER JOIN users ON users.id=status.user_id WHERE users.username = ?";
    public static final String DELETE_STATUS =  "DELETE s " +
                                                "FROM status s " +
                                                "INNER JOIN user_logged_in u " +
                                                "ON s.user_id=u.user_id " +
                                                "WHERE s.id=? " +
                                                "AND u.user_id=? " +
                                                "AND u.token=? " +
                                                "AND u.logged_out IS NULL;";
    public static final String CHECK_LIKED_STATUS = "SELECT EXISTS(SELECT 1 FROM status_likes where user_id =? and status_id = ?);";
    public static final String LIKE_STATUS = "INSERT INTO status_likes(user_id, status_id) VALUES(?,?)";
    public static final String UNLIKE_STATUS = "DELETE FROM status_likes WHERE user_id=? AND status_id=?";
    public static final String LIKE_STATUS_INCREMENT = "CALL likeStatus(?)";
    public static final String UNLIKE_STATUS_DECREMENT = "CALL unlikeStatus(?)";

    public static final String INSERT_STATUS_COMMENT = "INSERT INTO comments (user_id, status_id, message, uploaded) VALUES(?,?,?, NOW());";
    public static final String UPDATE_STATUS_COMMENT = "UPDATE comments SET status_id,user_id=?,message=?,likes=?,uploaded=? WHERE id=?";
    public static final String GET_STATUS_COMMENT_BY_ID = "SELECT * FROM comments WHERE id = ?";
    public static final String GET_ALL_STATUS_COMMENTS_BY_ID = "SELECT * FROM comments INNER JOIN users ON comments.user_id=users.id WHERE comments.status_id = ?";
    public static final String DELETE_STATUS_COMMENT = "DELETE FROM comments WHERE id=?";
    public static final String CHECK_LIKED_COMMENT = "SELECT EXISTS(SELECT 1 FROM comments_likes where user_id =? and comment_id = ?);";

    public static final String GET_LIVE_FEED = "SELECT status.*,users.username,users.profile_pic, users.first_name, users.last_name FROM status " +
                                                "LEFT JOIN user_relationship " +
                                                "ON status.user_id=user_relationship.friend_id " +
                                                "INNER JOIN users " +
                                                "ON users.id=status.user_id " +
                                                "WHERE user_relationship.user_id=? AND user_relationship.type='friend' OR status.user_id=? " +
                                                "ORDER BY uploaded DESC";

    public static final String GET_USER_POSTS = "SELECT status.*,users.username,users.profile_pic,users.first_name, users.last_name " +
                                                "FROM status  " +
                                                "INNER JOIN users " +
                                                "ON users.id=status.user_id " +
                                                "WHERE users.id=? " +
                                                "ORDER BY uploaded DESC";

    public static final String CHECK_USER_TOKEN = "SELECT 1 FROM user_logged_in WHERE token=? AND user_id=?";
}
