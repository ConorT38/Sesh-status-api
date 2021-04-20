package ie.sesh.Filters;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class MDCFilter extends HttpFilter {

  @Override
  protected void doFilter(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    try {
      MDC.put("x-correlation-id", getCorrelationId(request));
      filterChain.doFilter(request, response);
    } finally {
      MDC.remove("x-correlation-id");
    }
  }

  private String getCorrelationId(HttpServletRequest request) {
    String correlationId = request.getHeader("x-correlation-id");
    if (correlationId != null) {
      return correlationId;
    }
    return UUID.randomUUID().toString().toUpperCase().replace("-", "");
  }
}
