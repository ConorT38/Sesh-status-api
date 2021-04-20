package ie.sesh.Filters;

import static ie.sesh.Utils.SecurityConstants.HEADER_STRING;
import static ie.sesh.Utils.SecurityConstants.TOKEN_PREFIX;

import com.auth0.jwt.exceptions.TokenExpiredException;
import ie.sesh.Utils.JwtTokenUtil;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    String header = req.getHeader(HEADER_STRING);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      log.info("Authorization header did not exist, or did not start with 'Bearer' ");
      chain.doFilter(req, res);
      return;
    }

    try {
      if (!isTokenValid(req)) {
        log.info("Token is invalid");

        res.setStatus(HttpStatus.FORBIDDEN.value());
        chain.doFilter(req, res);
        return;
      }

      UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
      SecurityContextHolder.getContext().setAuthentication(authentication);

    } catch (TokenExpiredException e) {
      log.info("Token expired -- " + e.getLocalizedMessage());
      res.setStatus(HttpStatus.FORBIDDEN.value());
    } catch (Exception e) {
      log.error(e.getLocalizedMessage());
      res.setStatus(HttpStatus.FORBIDDEN.value());
      throw e;
    }

    log.info("Token is valid");
    chain.doFilter(req, res);
  }

  // Reads the JWT from the Authorization header, and then uses JWT to validate the token
  private boolean isTokenValid(HttpServletRequest request) {
    String token = jwtTokenUtil.getTokenFromRequest(request);
    log.info(token);

    if (token != null || !token.isEmpty()) {
      return jwtTokenUtil.validateToken(token);
    }
    return false;
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = jwtTokenUtil.getTokenFromRequest(request);

    if (token != null) {
      String user = jwtTokenUtil.getSubjectFromToken(token);

      if (user != null) {

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
      }
      return null;
    }
    return null;
  }
}
