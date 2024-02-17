package home.kalinin.rest_notes.aspect;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public class HTTPInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("[3) HTTP Interceptor pre] Кто: " + request.getRemoteUser()
                + "; Куда: " + request.getRequestURL() + "; Контроллер: " + handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        System.out.println("[3) HTTP Interceptor post] ======================");
    }
}
