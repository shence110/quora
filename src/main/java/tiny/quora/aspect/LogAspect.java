package tiny.quora.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

@Aspect
@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* tiny.quora.controller.*Controller.*(..))")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append("before -->> ");
        sb.append(request.getRequestURL().toString()).append(" | ");
        sb.append(request.getMethod()).append(" | ");
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String s = parameterNames.nextElement();
            sb.append(s).append("::").append(Arrays.toString(request.getParameterValues(s)));
        }
        logger.info(sb.toString());
    }

    @After("execution(* tiny.quora.controller.*Controller.*(..))")
    public void after(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        sb.append("after -->> ");
        for (Object arg: joinPoint.getArgs()) {
            sb.append(arg.toString()).append(" | ");
        }
        logger.info(sb.toString());
    }
}
