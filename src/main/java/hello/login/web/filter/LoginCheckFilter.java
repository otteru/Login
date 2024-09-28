package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whitelist = {"/", "/member/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("인증 체크 필터 시작{}", requestURI);

            if(isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    //SessionConst.LOGIN_MEMBER -> session에 사용하는 key이니까 이게 없다 -> 사용자가 아무도 없다.
                    log.info("미인증 사용자 요청 {}", requestURI);

                    //로그인으로 redirect
                    //로그인 성공하면 로그인 성공페이지가 아니라 지금, 막혔던 페이지로 돌아올 수 있게 requestURI 붙임
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI);
                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 페크 필터 종료 {}", requestURI);
        }

    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     * requestURI가 whitelist에 있는지에 반대로 반환
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}
