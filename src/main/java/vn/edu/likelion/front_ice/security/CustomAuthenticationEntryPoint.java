package vn.edu.likelion.front_ice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import vn.edu.likelion.front_ice.common.api.RestAPIResponse;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;

import java.io.IOException;

/**
 * CustomAuthenticationEntryPoint -
 *
 * @param
 * @return
 * @throws
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final AuthenticationEntryPoint delegate = new BearerTokenAuthenticationEntryPoint();

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        delegate.commence(request, response, authException);

        response.setContentType("application/json;charset=UTF-8");

        // Create the error response
        RestAPIResponse<Object> res = createUnauthorizedResponse(authException);

        // Write the response
        mapper.writeValue(response.getWriter(), res);
    }

    /**
     * Create a response.
     *
     * @param authException exception.
     * @return RestAPIResponse<Object>
     */
    private RestAPIResponse<Object> createUnauthorizedResponse(AuthenticationException authException) {
        RestAPIResponse<Object> response = new RestAPIResponse<>();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String errorMessage = authException.getMessage();
        if (errorMessage.contains("expired")) {
            response.setCode(ErrorCode.INVALID_REFRESH_TOKEN.getCodeError());
            setErrorMessage(response, "The token has expired.", "Token đã hết hạn.");
        } else if (errorMessage.contains("invalid")) {
            response.setCode(ErrorCode.INVALID_JWT_TOKEN.getCodeError());
            setErrorMessage(response, "The token is invalid.", "Token không hợp lệ.");
        } else {
            response.setCode(ErrorCode.ACCOUNT_UNAUTHENTICATED.getCodeError());
            setErrorMessage(response, "Authentication is required to access this resource.", "Yêu cầu xác thực để truy cập tài nguyên này.");
        }

        return response;
    }

    /**
     * Helper method to set the error message.
     *
     * @param response The response object to modify.
     * @param messageEng The English error message.
     * @param messageVN The Vietnamese error message.
     */
    private void setErrorMessage(RestAPIResponse<Object> response, String messageEng, String messageVN) {
        response.setMessageEng(messageEng);
        response.setMessageVN(messageVN);
    }
}
