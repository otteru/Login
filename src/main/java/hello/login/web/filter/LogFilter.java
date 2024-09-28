    package hello.login.web.filter;

    import lombok.extern.slf4j.Slf4j;

    import javax.servlet.*;
    import javax.servlet.http.HttpServletRequest;
    import java.io.IOException;
    import java.util.UUID;

    @Slf4j
    public class LogFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            log.info("log.filter init");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
            log.info("log.filter doFilter");

            // ServletRequest는 HttpServletRequest의 부모인데 기능이 적어서 다운캐스팅 해줘야 함
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String requestURI = httpRequest.getRequestURI();

            String uuid = UUID.randomUUID().toString();

            try{
                log.info("REQUEST [{}][{}]", uuid, requestURI);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                throw e;
            } finally {
                log.info("RESPONSE [{}][{}]", uuid, requestURI);
            }

        }

        @Override
        public void destroy() {
            log.info("log.filter destroy");
        }
    }
