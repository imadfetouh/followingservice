package com.imadelfetouh.followingservice.security;

import com.imadelfetouh.followingservice.jwt.ValidateJWTToken;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class CookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null) {
            Cookie cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("jwt-token")).findFirst().orElse(null);
            if(cookie == null) {
                httpServletResponse.setStatus(401);
                return;
            }
            else{
                String userData = ValidateJWTToken.getInstance().getUserData(cookie.getValue());

                httpServletRequest.setAttribute("userdata", userData);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
        }

        httpServletResponse.setStatus(401);
    }
}
