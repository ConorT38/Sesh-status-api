package ie.sesh.Utils;

import static ie.sesh.Utils.SecurityConstants.HEADER_STRING;
import static ie.sesh.Utils.SecurityConstants.TOKEN_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

  private static final long serialVersionUID = -2550185165626007488L;

  public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

  @Value("${jwt.secret}")
  private String secret;

  // retrieve username from jwt token
  public String getSubjectFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public String getUsernameFromToken(String token) {
    JSONObject obj = new JSONObject(getSubjectFromToken(token));
    return obj.getString("username");
  }

  public int getUserIdFromToken(String token) {
    System.out.println("-----------------" + getSubjectFromToken(token));
    JSONObject obj = new JSONObject(getSubjectFromToken(token));
    return obj.getInt("userId");
  }

  public String getRolesFromToken(String token) {
    return getClaimFromToken(token, Claims::getAudience);
  }

  // retrieve expiration date from jwt token
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }
  // for retrieveing any information from token we will need the secret key
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
  }

  // check if the token has expired
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  // validate token
  public Boolean validateToken(String token) {
    return !getUsernameFromToken(token).isEmpty() && !isTokenExpired(token);
  }

  public String getTokenFromRequest(HttpServletRequest request) {
    return request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");
  }

  public String getTokenFromHeaders(HttpHeaders headers) {
    return headers.get("Authorization").get(0).replace("Bearer ", "");
  }
}
