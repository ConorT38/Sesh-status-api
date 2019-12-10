package ie.sesh.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    public static final String EMPTY_STRING = "";

    public static Gson convertDate(String user_data){
        return new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm").create();
    }

    public static boolean isNullEmpty(Object value){
        return (value ==null || value.equals(null)  || value.toString().isEmpty() || value.toString() ==null);
    }

    public static int parseIntFromJSON(String data, String key){
        int result = (int) new JSONObject(data).get(key);
        if(CommonUtils.isNullEmpty(result)){
            return 0;
        }
        return result;
    }

    public static String parseStringFromJSON(String data, String key){
        String result = new JSONObject(data).get(key).toString();
        if(CommonUtils.isNullEmpty(result)){
            return CommonUtils.EMPTY_STRING;
        }
        return result;
    }
}