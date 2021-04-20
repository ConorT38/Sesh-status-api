package ie.sesh.Utils;

public class SecurityConstants {

  public static final long EXPIRATION_TIME = 900_000; // 15 mins
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String ALLOWED_URL = "/api/v1/public/**";
}
