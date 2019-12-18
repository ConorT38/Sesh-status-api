package ie.sesh.Services;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfigService {

    public HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, DELETE, PUT, GET");

        return headers;
    }
}
