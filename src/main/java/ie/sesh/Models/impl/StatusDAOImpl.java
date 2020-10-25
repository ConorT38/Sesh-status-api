package ie.sesh.Models.impl;

import ie.sesh.Models.Status;
import ie.sesh.Models.StatusDAO;

import ie.sesh.Models.Token;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
  private static final Logger log = Logger.getLogger(StatusDAOImpl.class);

  @Autowired private JdbcTemplate jdbcTemplate;

  public Status getStatus(int id) {
    log.info("Getting status by id " + id);
    return (Status)
        jdbcTemplate.queryForObject(GET_STATUS_BY_ID, new Object[] {id}, new StatusMapper());
  }

  public List<Status> getLiveFeed(Token token) {
    log.info("Getting statuses by id " + token.getUserId());
    List<Status> statuses = new ArrayList<Status>();
    List<Map<String, Object>> statusList =
        jdbcTemplate.queryForList(
            GET_LIVE_FEED, new Object[] {token.getUserId(), token.getUserToken()});

    for (Map status : statusList) {
      Status s = new Status();
      s.setId(toIntExact((Long) (status.get("id"))));
      s.setUser_id(toIntExact((Long) (status.get("user_id"))));
      s.setFirst_name((String) status.get("first_name"));
      s.setLast_name((String) status.get("last_name"));
      s.setUsername((String) status.get("username"));
      s.setProfile_pic((String) status.get("profile_pic"));
      s.setMessage((String) status.get("message"));
      s.setLocation((int) status.get("location"));
      s.setLikes((int) status.get("likes"));
      s.setLiked(checkLikedStatus(token.getUserId(), ((Long) (status.get("id"))).intValue()));
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
        s.setUserDidRepost((int) status.get("reposter_id") == token.getUserId());
      }

      statuses.add(s);
    }
    return statuses;
  }

  public List<Status> getProfileLiveFeed(String username, Token token) {
    log.info(
        "Getting statuses by username "
            + username
            + ", user id: "
            + token.getUserId()
            + " and token: "
            + token.getUserToken());
    List<Status> statuses = new ArrayList<Status>();
    List<Map<String, Object>> statusList =
        jdbcTemplate.queryForList(GET_STATUS_BY_USERNAME, new Object[] {username});

    for (Map status : statusList) {
      Status s = new Status();

      s.setId(toIntExact((Long) (status.get("status_id"))));
      s.setUser_id(toIntExact((Long) (status.get("id"))));
      s.setFirst_name((String) status.get("first_name"));
      s.setLast_name((String) status.get("last_name"));
      s.setUsername((String) status.get("username"));
      s.setProfile_pic((String) status.get("profile_pic"));
      s.setMessage((String) status.get("message"));
      s.setLocation((int) status.get("location"));
      s.setLikes((int) status.get("likes"));
      s.setNumComments((int) status.get("comments"));
      s.setLiked(
          checkLikedStatus(token.getUserId(), ((Long) (status.get("status_id"))).intValue()));
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
                ps.setInt(6, status.getId());
                return ps;
              },
              holder);
      return updated > 0;
    } catch (Exception e) {
      log.error(e.getMessage());
    }
    return false;
  }

  public boolean createStatus(Status status, Token token) {
    log.info("Inserting status");
    KeyHolder holder = new GeneratedKeyHolder();
    try {
      int check =
          jdbcTemplate.update(
              connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(INSERT_STATUS, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, token.getUserId());
                ps.setString(2, status.getMessage());
                ps.setInt(3, status.getLocation());
                ps.setTimestamp(4, status.getDate());
                ps.setInt(5, status.isHasImage() ? 1 : 0);
                ps.setString(6, status.getImageLocation());
                return ps;
              },
              holder);
      return check > 0;
    } catch (DataAccessException e) {
      log.error(e.getMessage());
      return false;
    }
  }

  public boolean deleteStatus(int id, int user_id, String token) {
    log.info("Deleting status with id: " + id + " , user_id: " + user_id + ", token: " + token);
    try {
      KeyHolder holder = new GeneratedKeyHolder();
      int numberRowsAffected =
          jdbcTemplate.update(
              connection -> {
                PreparedStatement ps =
                    connection.prepareStatement(DELETE_STATUS, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, id);
                ps.setInt(2, user_id);
                ps.setString(3, token);
                return ps;
              },
              holder);

      return numberRowsAffected > 0;
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

  public boolean likeStatus(int user_id, int status_id, String token) {
    log.info("User: " + user_id + " likes status: " + status_id + " using token: " + token);
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(LIKE_STATUS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, user_id);
              ps.setInt(2, status_id);
              ps.setString(3, token);
              return ps;
            },
            holder);

    return check > 0;
  }

  public boolean unlikeStatus(int user_id, int status_id, String token) {
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(UNLIKE_STATUS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, user_id);
              ps.setInt(2, status_id);
              ps.setString(3, token);
              return ps;
            },
            holder);

    return check > 0;
  }

  public boolean repostStatus(int id, Token token) {
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(REPOST_STATUS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, token.getUserId());
              ps.setInt(2, id);
              ps.setString(3, token.getUserToken());
              return ps;
            },
            holder);

    return check > 0;
  }

  public boolean unrepostStatus(int id, Token token) {
    KeyHolder holder = new GeneratedKeyHolder();
    int check =
        jdbcTemplate.update(
            connection -> {
              PreparedStatement ps =
                  connection.prepareStatement(UNREPOST_STATUS, Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, token.getUserId());
              ps.setInt(2, id);
              ps.setString(3, token.getUserToken());
              return ps;
            },
            holder);

    return check > 0;
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
