package com.dragon3.security.config;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dragon3.infrastructure.util.MapUtil;
import com.dragon3.security.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtils.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                String[] params = handleTokenExpired(jwtToken, response);
                if(params!=null){
                    jwtToken=params[0];
                    username=params[1];
                    response.setHeader("Access-Control-Expose-Headers", "new-authorization");
                }
            }
            //防止token过期但可刷新的时候通过request.headers.token取username报错，也防止多次解析token时跨跃过期时间
            request.setAttribute("current-login-user-id", username);
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtils.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    public String[] handleTokenExpired(String token, HttpServletResponse response){
        Date now = new Date();
        Date expireTime;
        try {
            expireTime = jwtTokenUtils.getExpirationDateFromToken(token);
        }catch(ExpiredJwtException e){
            expireTime = e.getClaims().getExpiration();
        }
        //过期时间大于可刷新时间
        if(now.getTime()-expireTime.getTime()>JwtTokenUtils.JWT_TOKEN_CAN_REFRESH_TIME){
            return null;
        }
        long loginTime;
        try {
            loginTime = (long)jwtTokenUtils.getAllClaimsFromToken(token).get("loginTime");
        }catch(ExpiredJwtException e){
            loginTime = (long)e.getClaims().get("loginTime");
        }
        //上一次登录时间距今 大于 强制登录时长
        if(now.getTime()-loginTime>JwtTokenUtils.JWT_TOKEN_FORCE_LOGIN_TIME){
            return null;
        }
        String username;
        try {
            username = jwtTokenUtils.getUsernameFromToken(token);
        }catch(ExpiredJwtException e){
            username = e.getClaims().getSubject();
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Map<String, Object> params = MapUtil.paramsToMap("loginTime", loginTime);
            String newToken = jwtTokenUtils.generateToken(jwtUserDetailsService.loadUserByUsername(username), params);
            response.setHeader("new-authorization", newToken);
            return new String[]{newToken, username};
        }else{
            return null;
        }
    }

}