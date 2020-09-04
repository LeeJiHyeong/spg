package com.example.demo.handler;

import com.example.demo.login.service.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    @Qualifier("loginService")
    private UserDetailsService userSerivce;

    private Logger logger = LoggerFactory.getLogger(UserAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        logger.info("logined");

        String userName = authentication.getName();

        UserPrincipal userPrincipal = (UserPrincipal) this.userSerivce.loadUserByUsername(userName);

        // now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", userPrincipal);
        session.setAttribute("userName", userPrincipal.getUsername());
        session.setAttribute("name", userPrincipal.getName());

        // forward to home page
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/");
    }
}
