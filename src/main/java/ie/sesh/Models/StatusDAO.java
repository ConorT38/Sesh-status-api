package ie.sesh.Models;

import java.util.List;

public interface StatusDAO {

  Status getStatus(int id);

  List<Status> getLiveFeed(int userId);

  List<Status> getProfileLiveFeed(String username);

  List<Status> getProfileLiveFeed(String username, int userId);

  boolean createStatus(Status status);

  boolean updateStatus(Status status);

  boolean deleteStatus(int id, int userId);

  boolean checkLikedStatus(int id, int status_id);

  boolean likeStatus(int id, int status_id);

  boolean unlikeStatus(int id, int status_id);

  boolean repostStatus(int id, int userId);

  boolean unrepostStatus(int id, int userId);
}
