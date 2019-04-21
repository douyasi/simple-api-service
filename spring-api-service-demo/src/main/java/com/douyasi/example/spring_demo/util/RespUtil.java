package com.douyasi.example.spring_demo.util;

import com.douyasi.example.spring_demo.exception.AppException;
import com.douyasi.tinyme.common.model.CommonResult;
import com.douyasi.tinyme.common.util.ResultUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RespUtil {
    
    public static void respException(HttpServletResponse resp, AppException e) throws IOException {
        if (e != null) {
            Integer code = Integer.parseInt(e.getCode());
            if (code > 300 && code < 500) {
                resp.setStatus(code);
            } else {
                resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());  // 500
            }
        } else {
            resp.setStatus(HttpStatus.OK.value());
        }
        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        CommonResult<Object> result = ResultUtil.returnError(e.getMessage(), e.getCode());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        // mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonInString = "{\"code\": \"50000\", \"message\": \"Error or exception occurs !\"}";
        try {
            jsonInString = mapper.writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        resp.getWriter().write(jsonInString);
    }
}
