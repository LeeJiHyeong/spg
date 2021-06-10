package com.cnu.spg.handler;

import com.cnu.spg.login.domain.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserDetailsService userSerivce;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("logined");

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
