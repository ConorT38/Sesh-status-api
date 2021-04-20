package ie.sesh.Repository.Impl;

import ie.sesh.Models.Status;
import ie.sesh.Repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatusRepositoryImpl implements StatusRepository {

  @Autowired MongoTemplate mongoTemplate;

  @Override
  public boolean createStatus(Status status) {
    mongoTemplate.insert(status, "status");
    return false;
  }
}
