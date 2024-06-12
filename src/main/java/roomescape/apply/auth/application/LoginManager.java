package roomescape.apply.auth.application;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import roomescape.apply.auth.application.exception.TokenNotFoundException;
import roomescape.apply.auth.ui.dto.LoginCheckResponse;
import roomescape.apply.auth.ui.dto.LoginResponse;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Service
public class LoginManager {

    private static final String COOKIE_NAME = "token";
    private final JwtTokenManager jwtTokenManager;

    public LoginManager(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }


    public void addTokenToCookie(LoginResponse loginResponse, HttpServletResponse servletResponse) {
        String token = jwtTokenManager.generateToken(loginResponse);
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        servletResponse.addCookie(cookie);
    }

    public LoginCheckResponse findRoleNameByToken(HttpServletRequest servletRequest) {
        Cookie[] cookies = servletRequest.getCookies();
        if (cookies == null || cookies.length == 0) {
            throw new TokenNotFoundException();
        }

        String token = Arrays.stream(cookies)
                .filter(it -> COOKIE_NAME.equals(it.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);

        jwtTokenManager.validateToken(token);
        return new LoginCheckResponse(jwtTokenManager.getRoleNameFromToken(token));
    }
}
