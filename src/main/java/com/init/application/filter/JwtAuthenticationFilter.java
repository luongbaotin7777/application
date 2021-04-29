package com.init.application.filter;

import com.init.application.entity.TokenEntity;
import com.init.application.security.CustomUserDetails;
import com.init.application.security.TokenProvider;
import com.init.application.security.UserDetailService;
import com.init.application.service.token.ITokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final UserDetailService userService;
    private final ITokenService tokenService;

    public JwtAuthenticationFilter(TokenProvider tokenProvider, UserDetailService userService,
                                   ITokenService tokenService) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            UUID userId;
            String jwtToken = getJwtFromRequest(httpServletRequest);
            if (StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken)) {
                userId = UUID.fromString(tokenProvider.getUserIdFromToken(jwtToken));
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    CustomUserDetails customUserDetails = this.userService.loadUserById(userId);
                    TokenEntity tokenEntity = tokenService.findByAccessToken(jwtToken);
                    boolean checkExpired = tokenEntity.getExpiredAccess().after(new Date());
                    if (checkExpired) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                                = new UsernamePasswordAuthenticationToken(
                                customUserDetails, null, customUserDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                                .buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
        } catch (Exception ex) {
            log.error("failed on set user authentication", ex);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public static String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        String[] AUTH_WHITELIST = {
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/",
                "/v3/api-docs/",
                "/swagger-ui/",
                "/user/login",
                "/user/register",
                "/user/refresh-token"
        };

        for (String url : AUTH_WHITELIST) {
            if (path.startsWith(url)) {
                return true;
            }
        }

        return super.shouldNotFilter(request);
    }
}
