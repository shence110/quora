package tiny.quora.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tiny.quora.dao.TicketDao;
import tiny.quora.dao.UserDao;
import tiny.quora.model.CurrentUser;
import tiny.quora.model.LoginTicket;
import tiny.quora.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TicketInterceptor implements HandlerInterceptor {
    @Autowired
    TicketDao ticketDao;
    @Autowired
    UserDao userDao;
    @Autowired
    CurrentUser currentUser;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String ticket = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("ticket")) {
                ticket = cookie.getValue();
                break;
            }
        }
        if (ticket != null) {
            LoginTicket loginTicket = ticketDao.selectByTicket(ticket);
            //ticket有效
            if (loginTicket != null && loginTicket.getStatus() == 0 &&
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(loginTicket.getExpired()).after(new Date())) {
                currentUser.setUserId(loginTicket.getUserId());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Integer userId = currentUser.getUserId();
            if (userId != null) {
                User user = userDao.searchById(userId);
                if (user != null) {
                    user.setId(userId);
                    modelAndView.addObject("user", user);
                }
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        currentUser.clear();
    }
}
