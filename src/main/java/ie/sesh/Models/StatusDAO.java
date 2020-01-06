package ie.sesh.Models;

import java.util.List;

public interface StatusDAO {

  Status getStatus(int id);

  List<Status> getLiveFeed(int id, String token);

  List<Status> getProfileLiveFeed(String username);

  boolean createStatus(Status status);

  boolean updateStatus(Status status);

  boolean deleteStatus(int id, int user_id, String token);

  boolean checkLikedStatus(int id, int status_id);

  boolean likeStatus(int id, int status_id, String token);

  boolean unlikeStatus(int id, int status_id, String token);
}
