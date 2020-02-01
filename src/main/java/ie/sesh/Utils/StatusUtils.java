package ie.sesh.Utils;

import ie.sesh.Models.Status;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class StatusUtils {

  public Status buildStatus(String status_data) {
    String message = CommonUtils.parseStringFromJSON(status_data, "message");
    int location = Integer.parseInt(CommonUtils.parseStringFromJSON(status_data, "location"));
    Timestamp date = new Timestamp(new java.util.Date().getTime());
    boolean hasImage =
        Boolean.parseBoolean(CommonUtils.parseStringFromJSON(status_data, "has_image"));
    String imageLocation = CommonUtils.parseStringFromJSON(status_data, "image_location");

    return new Status(message, location, date, hasImage, imageLocation);
  }
}
