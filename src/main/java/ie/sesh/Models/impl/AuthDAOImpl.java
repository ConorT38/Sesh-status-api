package ie.sesh.Models.impl;

import ie.sesh.Models.AuthDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static ie.sesh.Database.SQLConstants.CHECK_USER_TOKEN;

@Component
public class AuthDAOImpl implements AuthDAO {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private JdbcTemplate jdbcTemplate;

  public boolean checkUserToken(String token, int user_id) {
    log.info("Checking user exists by token: " + token);
    try {
      int result =
          jdbcTemplate.queryForObject(
              CHECK_USER_TOKEN, new Object[] {token, user_id}, Integer.class);
      return result > 0;
    } catch (DataAccessException e) {
      log.error(e.getMessage());
      return false;
    }
  }
}
