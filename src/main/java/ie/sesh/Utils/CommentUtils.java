package ie.sesh.Utils;

import ie.sesh.Models.Comments.Comment;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CommentUtils {

    public Comment buildComment(String comment_data){
        int status_id = (int) checkIsNullEmpty(Integer.parseInt(new JSONObject(comment_data).getJSONArray("status_id").get(0).toString()),0);
        int user_id =  (int) checkIsNullEmpty(Integer.parseInt(new JSONObject(comment_data).getJSONArray("user_id").get(0).toString()),0);
        String message = (String) checkIsNullEmpty(new JSONObject(comment_data).getJSONArray("message").get(0).toString(),"");
        Timestamp date = new Timestamp(new java.util.Date().getTime());

        return new Comment(status_id,user_id,message,date);
    }

    public Object checkIsNullEmpty(Object value, Object def){
        return (!value.toString().isEmpty() || value.toString() !=null) ? value : def;
    }
}
