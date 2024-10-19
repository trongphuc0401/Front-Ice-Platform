package vn.edu.likelion.front_ice.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.edu.likelion.front_ice.common.exceptions.ErrorCode;
import vn.edu.likelion.front_ice.common.exceptions.SuccessCode;

@Component
public class ResponseUtil {
    @Autowired
    private ObjectMapper mapper;

    @Autowired
    public ResponseUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Create HTTP Response
     * @param restApiStatus
     * @param data
     * @return
     */
    private RestAPIResponse<Object> _createResponse(RestAPIStatus restApiStatus,Object data) {
        return new RestAPIResponse<>(restApiStatus, data);
    }


    private RestAPIResponse<Object> _createResponse(RestAPIStatus restApiStatus, ErrorCode errorCode) {
        return new RestAPIResponse<>(restApiStatus, errorCode);
    }

    private RestAPIResponse<Object> _createResponse(RestAPIStatus restApiStatus, ErrorCode errorCode,HttpStatus httpStatus) {
        return new RestAPIResponse<>(restApiStatus, null, errorCode, errorCode.getMessageEng());
    }

    private RestAPIResponse<Object> _createResponse(RestAPIStatus restAPIStatus, Object data, SuccessCode successCode){
        return  new RestAPIResponse<>(restAPIStatus, data, successCode);
    }

    /**
     * Build HTTP Response
     * @param restApiStatus
     * @param data
     * @param httpStatus
     * @return
     */
    public ResponseEntity<RestAPIResponse<Object>> buildResponse(RestAPIStatus restApiStatus, Object data, HttpStatus httpStatus) {
        return new ResponseEntity<>(_createResponse(restApiStatus, data), httpStatus);
    }

    public ResponseEntity<RestAPIResponse<Object>> buildResponse(RestAPIStatus restApiStatus, Object data, HttpStatus httpStatus, String responseCookies) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.SET_COOKIE,responseCookies);

        return new ResponseEntity<>(_createResponse(restApiStatus,data),httpHeaders,httpStatus);
    }

    /**
     * Build HTTP Response using SuccessCode
     * @param restApiStatus
     * @param successCode
     * @param data
     * @return
     */
    public ResponseEntity<RestAPIResponse<Object>> buildResponse(RestAPIStatus restApiStatus, SuccessCode successCode, Object data) {
        return new ResponseEntity<>(_createResponse(restApiStatus, data, successCode), HttpStatus.OK);
    }


    /**
     *
     * @param restApiStatus
     * @param errorCode
     * @param httpStatus
     * @return
     */
    public ResponseEntity<RestAPIResponse<Object>> buildResponse(RestAPIStatus restApiStatus, ErrorCode errorCode, HttpStatus httpStatus) {
        return new ResponseEntity<>(_createResponse(restApiStatus, errorCode), httpStatus);
    }

    /**
     * Return success HTTP Request
     * @param data
     * @return
     */
    public ResponseEntity<RestAPIResponse<Object>> successResponse(Object data,String responseCookies) {
        return buildResponse(RestAPIStatus.OK, data,HttpStatus.OK,responseCookies);
    }

    public ResponseEntity<RestAPIResponse<Object>> successResponse(ErrorCode errorCode) {
        return buildResponse(RestAPIStatus.OK, errorCode, HttpStatus.OK);
    }

    public ResponseEntity<RestAPIResponse<Object>> successResponse(SuccessCode successCode, Object data) {
        return buildResponse(RestAPIStatus.OK, successCode, data);
    }

    // A default successResponse method for generic success cases without specific success code
    public ResponseEntity<RestAPIResponse<Object>> successResponse(Object data) {
        return successResponse(SuccessCode.OK, data);
    }
}
