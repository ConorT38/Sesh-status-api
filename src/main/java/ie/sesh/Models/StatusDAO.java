package ie.sesh.Models;

import java.util.List;

public interface StatusDAO {

  Status getStatus(int id);

  List<Status> getLiveFeed(Token token);

  List<Status> getProfileLiveFeed(String username, Token token);

  boolean createStatus(Status status, Token token);

  boolean updateStatus(Status status);

  boolean deleteStatus(int id, int user_id, String token);

  boolean checkLikedStatus(int id, int status_id);

  boolean likeStatus(int id, int status_id, String token);

  boolean unlikeStatus(int id, int status_id, String token);

  boolean repostStatus(int id, Token token);

  boolean unrepostStatus(int id, Token token);
}
