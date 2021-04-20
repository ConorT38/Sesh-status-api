package ie.sesh.Models.impl;

import ie.sesh.Models.Status;
import ie.sesh.Models.StatusDAO;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
public class StatusDAOImpl implements StatusDAO {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private String COLLECTION = "status";

  @Autowired private JdbcTemplate jdbcTemplate;
  @Autowired private MongoTemplate mongoTemplate;

  public Status getStatus(ObjectId id) {
    log.info("Getting status by id " + id);
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));
    return (Status) mongoTemplate.find(query, Status.class, COLLECTION);
  }

  public List<Status> getLiveFeed(int userId) {
    log.info("Getting statuses by id " + userId);
    List<Status> statuses = new ArrayList<Status>();
    List<Map<String, Object>> statusList =
        jdbcTemplate.queryForList(GET_LIVE_FEED, new Object[] {userId, userId, userId});

    for (Map status : statusList) {
      Status s = new Status();
      // s.setId(toIntExact((Long) (status.get("id"))));
      s.setUser_id(toIntExact((Long) (status.get("user_id"))));
      s.setFirst_name((String) status.get("first_name"));
      s.setLast_name((String) status.get("last_name"));
      s.setUsername((String) status.get("username"));
      s.setProfile_pic((String) status.get("profile_pic"));
      s.setMessage((String) status.get("message"));
      s.setLocation((int) status.get("location"));
      s.setLikes((int) status.get("likes"));
      s.setLiked(checkLikedStatus(userId, ((Long) (status.get("id"))).intValue()));
      s.setDate((Timestamp) status.get("uploaded"));
      s.setNumComments((int) status.get("comments"));
      s.setHasImage((int) status.get("has_image") > 0);
      s.setImageLocation((String) status.get("media"));
      s.setNumReposts((int) status.get("reposts"));

      boolean isRepost = status.get("reposter_id") != null;
      s.setIsRepost(isRepost);
      if (isRepost) {
        s.setReposterName(
            (String) status.get("repost_first_name")
                + " "
                + (String) status.get("repost_last_name"));
        s.setReposterUsername((String) status.get("repost_username"));
        s.setRepostDate((Timestamp) status.get("repost_time"));
        s.setUserDidRepost(checkUserRepostedStatus((int) status.get("status_id"), userId));
      }

      statuses.add(s);
    }
    return statuses;
  }

  public List<Status> getProfileLiveFeed(String username) {
    log.info("Getting statuses by username " + username);
    List<Status> statuses = new ArrayList<Status>();
    List<Map<String, Object>> statusList =
        jdbcTemplate.queryForList(GET_STATUS_BY_USERNAME, new Object[] {username});

    for (Map status : statusList) {
      Status s = new Status();

      // s.setId(toIntExact((Long) (status.get("status_id"))));
      s.setUser_id(toIntExact((Long) (status.get("id"))));
      s.setFirst_name((String) status.get("first_name"));
      s.setLast_name((String) status.get("last_name"));
      s.setUsername((String) status.get("username"));
      s.setProfile_pic((String) status.get("profile_pic"));
      s.setMessage((String) status.get("message"));
      s.setLocation((int) status.get("location"));
      s.setLikes((int) status.get("likes"));
      s.setNumComments((int) status.get("comments"));
      s.setLiked(false);
      s.setDate((Timestamp) status.get("uploaded"));
      s.setHasImage((int) status.get("has_image") > 0);
      s.setImageLocation((String) status.get("media"));
      s.setNumReposts((int) status.get("reposts"));
      s.setIsRepost(status.get("reposter_id") == null);

      statuses.add(s);
    }
    return statuses;
  }

  public List<Status> getProfileLiveFeed(String username, int userId) {
    log.info("Getting statuses by username " + username + ", user id: " + userId);
    List<Status> statuses = new ArrayList<Status>();
    List<Map<String, Object>> statusList =
        jdbcTemplate.queryForList(GET_STATUS_BY_USERNAME, new Object[] {username});

    for (Map status : statusList) {
      Status s = new Status();

      // s.setId(toIntExact((Long) (status.get("status_id"))));
      s.setUser_id(toIntExact((Long) (status.get("id"))));
      s.setFirst_name((String) status.get("first_name"));
      s.setLast_name((String) status.get("last_name"));
      s.setUsername((String) status.get("username"));
      s.setProfile_pic((String) status.get("profile_pic"));
      s.setMessage((String) status.get("message"));
      s.setLocation((int) status.get("location"));
      s.setLikes((int) status.get("likes"));
      s.setNumComments((int) status.get("comments"));
      s.setLiked(checkLikedStatus(userId, ((Long) (status.get("status_id"))).intValue()));
      s.setDate((Timestamp) status.get("uploaded"));
      s.setHasImage((int) status.get("has_image") > 0);
      s.setImageLocation((String) status.get("media"));
      s.setNumReposts((int) status.get("reposts"));
      s.setIsRepost(status.get("reposter_id") == null);

      statuses.add(s);
    }
    return statuses;
  }

  public boolean updateStatus(Status status) {
    log.info("Updating status");
    KeyHolder holder = new GeneratedKeyHolder();
    try {
      int updated =
          jdbcTemplate.update(
              connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(UPDATE_STATUS, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, status.getUser_id());
                ps.setString(2, status.getMessage());
                ps.setInt(3, status.getLocation());
                ps.setInt(4, status.getLikes());
                ps.setTimestamp(5, status.getDate());
                // ps.setInt(6, status.getId());
                return ps;
              },
              holder);
      return updated > 0;
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean createStatus(Status status) {
    log.info("Inserting status for user -- " + status.getUser_id());

    try {
      mongoTemplate.insert(status, "status");
      return true;
    } catch (Exception ex) {
      log.error("Error occured while inserting status -- " + ex);
    }
    return false;
  }

  public boolean deleteStatus(int id, int userId) {
    log.info("Deleting status with id: " + id + " , user_id: " + userId);
    try {
      KeyHolder holder = new GeneratedKeyHolder();
      int numberRowsAffected =
          jdbcTemplate.update(
              connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(DELETE_STATUS, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, id);
                ps.setInt(2, userId);
                return ps;
              },
              holder);

      if (numberRowsAffected > 0) {
        return decrementUserNumPosts(userId);
      }
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean checkLikedStatus(int id, int status_id) {
    int check =
        jdbcTemplate.queryForObject(
            CHECK_LIKED_STATUS, new Object[] {id, status_id}, Integer.class);

    return check == 1;
  }

  public boolean likeStatus(int user_id, int status_id) {
    log.info("User: " + user_id + " likes status: " + status_id);
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(LIKE_STATUS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, status_id);
              return ps;
            },
            holder);

    if (check > 0) {
      int liked =
          jdbcTemplate.update(
              connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(
                        INSERT_LIKED_STATUS, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user_id);
                ps.setInt(2, status_id);
                return ps;
              },
              holder);
      return liked > 0;
    }
    return false;
  }

  public boolean unlikeStatus(int user_id, int status_id) {
    log.info("User: " + user_id + " unliked status: " + status_id);

    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(UNLIKE_STATUS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, status_id);
              return ps;
            },
            holder);

    if (check > 0) {
      int liked =
          jdbcTemplate.update(
              connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(
                        DELETE_LIKED_STATUS, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user_id);
                ps.setInt(2, status_id);
                return ps;
              },
              holder);
      return liked > 0;
    }
    return false;
  }

  public boolean repostStatus(int id, int userId) {
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(REPOST_STATUS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, userId);
              ps.setInt(2, id);
              return ps;
            },
            holder);

    if (check > 0) {
      log.info("Status reposted - incrementing reposts");

      int check2 =
          jdbcTemplate.update(
              connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(
                        INCREMENT_REPOST_STATUS, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, id);
                return ps;
              },
              holder);
      return check2 > 0;
    }
    log.info("Repost failed");
    return false;
  }

  public boolean unrepostStatus(int id, int userId) {
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(UNREPOST_STATUS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, userId);
              ps.setInt(2, id);
              return ps;
            },
            holder);

    return check > 0;
  }

  private boolean checkUserRepostedStatus(int repostedStatusId, int userId) {
    int check =
        jdbcTemplate.queryForObject(
            CHECK_REPOSTED_STATUS, new Object[] {repostedStatusId, userId}, Integer.class);
    return check == 1;
  }

  private boolean incrementUserNumPosts(int userId) {
    log.info("incrementing num posts for user " + userId);
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(
                      INCREMENT_USER_POSTS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, userId);
              return ps;
            },
            holder);
    return check == 1;
  }

  private boolean decrementUserNumPosts(int userId) {
    log.info("decrementing num posts for user " + userId);
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(
                      DECREMENT_USER_POSTS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, userId);
              return ps;
            },
            holder);
    return check == 1;
  }
}

class StatusMapper implements RowMapper {

  @Override
  public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
    Status status = new Status();
    status.setUser_id(rs.getInt("user_id"));
    status.setMessage(rs.getString("message"));
    status.setLocation(rs.getInt("location"));
    status.setLikes(rs.getInt("likes"));
    status.setDate(rs.getTimestamp("uploaded"));

    return status;
  }
}
