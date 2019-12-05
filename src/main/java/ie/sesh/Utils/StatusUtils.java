package ie.sesh.Utils;

import ie.sesh.Models.Status;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class StatusUtils {

    public Status buildStatus(String status_data){
        int user_id =  (int) getItem(status_data,"user_id", 0);
        String message = (String) getItem(status_data,"message", "");
        int location = (int) getItem(status_data,"location", 0);
        Timestamp date = new Timestamp(new java.util.Date().getTime());

        return new Status(user_id,message,location,date);
    }

    public String getUserToken(String data){
        return (String) getItem(data,"user_token", "");
    }

    private Object checkIsNullEmpty(Object value, Object def){
        return (!value.toString().isEmpty() || value.toString() !=null) ? value : def;
    }

    private Object getItem(String status_data, String item, Object def){
        if(def instanceof String) {
            return checkIsNullEmpty(new JSONObject(status_data).getJSONArray(item).get(0).toString(), def);
        }else{
            return checkIsNullEmpty(Integer.parseInt(new JSONObject(status_data).getJSONArray(item).get(0).toString()), def);
        }
    }
}
