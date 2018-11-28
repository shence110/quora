package tiny.quora.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tiny.quora.interceptor.LoginInterceptor;
import tiny.quora.interceptor.TicketInterceptor;

@Component
public class QuoraWebConfig implements WebMvcConfigurer {
    @Autowired
    TicketInterceptor ticketInterceptor;
    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ticketInterceptor);
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/*");
    }
}
