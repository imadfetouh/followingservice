package com.imadelfetouh.followingservice.security;

import com.imadelfetouh.followingservice.jwt.ValidateJWTToken;
import com.imadelfetouh.followingservice.rabbit.RabbitConfiguration;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

@Component
public class CookieFilter implements Filter {

    private static final Logger logger = Logger.getLogger(CookieFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("jwt-token")).findFirst().orElse(null);
            if(cookie == null) {
                logger.info("Cookie jwt-token not found");
                httpServletResponse.setStatus(401);
                return;
            }
            else{
                String userData = ValidateJWTToken.getInstance().getUserData(cookie.getValue());
                if(userData == null) {
                    httpServletResponse.setStatus(401);
                    return;
                }

                if(!RabbitConfiguration.getInstance().getConnection().isOpen() && !httpServletRequest.getMethod().equals("GET")) {
                    logger.info("Request made, but rabbit is down");
                    httpServletResponse.setStatus(503);
                    return;
                }

                httpServletRequest.setAttribute("userdata", userData);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }
        else{
            logger.info("No cookies found");
        }

        httpServletResponse.setStatus(401);
    }
}
