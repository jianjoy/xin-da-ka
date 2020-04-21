package com.dragon3.security.util;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dragon3.infrastructure.model.RestResponse;
import com.dragon3.infrastructure.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        RestResponse rr401 = RestResponse.error(RestResponse.HttpCode.UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(JsonUtil.stringify(rr401));
        response.getWriter().flush();
        response.getWriter().close();
    }
}