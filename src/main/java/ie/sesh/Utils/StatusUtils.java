package ie.sesh.Utils;

import ie.sesh.Models.Status;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class StatusUtils {

    public Status buildStatus(String status_data){
        int user_id =  CommonUtils.parseIntFromJSON(status_data,"user_id");
        String message = CommonUtils.parseStringFromJSON(status_data,"message");
        int location = CommonUtils.parseIntFromJSON(status_data,"location");
        Timestamp date = new Timestamp(new java.util.Date().getTime());

        return new Status(user_id,message,location,date);
    }

    public String getUserToken(String data){
        return CommonUtils.parseStringFromJSON(data,"user_token");
    }
    public int getUserId(String data){ return  CommonUtils.parseIntFromJSON(data,"user_id");
    }

    }
