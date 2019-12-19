package ie.sesh.Models;

public interface AuthDAO {
  boolean checkUserToken(String token, int user_id);
}
