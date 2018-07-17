package com.example.springsecurityboot.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
        throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext()
                                                   .getAuthentication();
        if (auth != null) {
            LOGGER.info(String.format("User %s tried to access a protected page, through the URL: %s", auth.getName(),
                request.getRequestURI()));
        }

        response.setStatus(HttpStatus.FORBIDDEN.value());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/accessDenied");
        dispatcher.forward(request, response);
    }
}
