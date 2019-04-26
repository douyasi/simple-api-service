package com.douyasi.example.spring_demo.exception;

import com.douyasi.example.spring_demo.util.RespUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * GlobalExceptionHandler
 * 
 * - https://github.com/BNDong/spring-cloud-examples/
 * 
 * @author raoyc
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404 http error
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView errorHandler(HttpServletRequest request, NoHandlerFoundException exception, HttpServletResponse response) {
        return commonHandler(request, response,
            exception.getClass().getSimpleName(),
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage());
    }

    /**
     * 405 http error
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ModelAndView errorHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException exception, HttpServletResponse response) {
        return commonHandler(request, response,
            exception.getClass().getSimpleName(),
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            exception.getMessage());
    }

    /**
     * 415 http error
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ModelAndView errorHandler(HttpServletRequest request, HttpMediaTypeNotSupportedException exception, HttpServletResponse response) {
        return commonHandler(request, response,
            exception.getClass().getSimpleName(),
            HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
            exception.getMessage());
    }

    /**
     * 500 http error
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView errorHandler(HttpServletRequest request, Exception exception, HttpServletResponse response) {
        return commonHandler(request, response,
            exception.getClass().getSimpleName(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.getMessage());
    }

    /**
     * 500 http error for IOException
     */
    @ExceptionHandler(value = IOException.class)
    public ModelAndView errorHandler(HttpServletRequest request, IOException exception, HttpServletResponse response) {
        return commonHandler(request, response,
            exception.getClass().getSimpleName(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.getMessage());
    }

    /**
     * 500 http error for RuntimeException
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView errorHandler(HttpServletRequest request, RuntimeException exception, HttpServletResponse response) {
        return commonHandler(request, response,
            exception.getClass().getSimpleName(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.getMessage());
    }

    /**
     * 500 http error for AppException
     */
    @ExceptionHandler(value = AppException.class)
    public ModelAndView errorHandler(HttpServletRequest request, AppException exception, HttpServletResponse response) {
        return commonHandler(request, response,
            exception.getClass().getSimpleName(),
            Integer.valueOf(exception.getCode()),
            exception.getMessage());
    }

    private ModelAndView commonHandler(HttpServletRequest request, HttpServletResponse response,
                                        String error, int code, String message) {
        ExceptionEntity entity = new ExceptionEntity();
        entity.setPath(request.getRequestURI());
        entity.setError(error);
        entity.setCode(String.valueOf(code));
        entity.setMessage(message);
        return renderView(request, response, entity);
    }

    /**
     * renderView
     */
    private ModelAndView renderView(HttpServletRequest request, HttpServletResponse response, ExceptionEntity entity) {
        boolean isRawJson = (request.getHeader(HttpHeaders.CONTENT_TYPE) != null) && request.getHeader(HttpHeaders.CONTENT_TYPE).contains("application/json");
        boolean wantJson = (request.getHeader(HttpHeaders.ACCEPT) != null) && request.getHeader(HttpHeaders.ACCEPT).contains("application/json");
        boolean isAjax = (request.getHeader("X-Requested-With") != null) && request.getHeader("X-Requested-With").contains("XMLHttpRequest");
        if (!(isRawJson || wantJson || isAjax)) {
            // render view as html
            ModelAndView modelAndView = new ModelAndView("exception");
//            ModelAndView modelAndView = new ModelAndView("exception", "exception", entity);
            modelAndView.addObject("exception", entity);
            return modelAndView;
        } else {
            // render view as json
            int code = Integer.parseInt(entity.getCode());
            if (code >= HttpStatus.BAD_REQUEST.value() && code < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                response.setStatus(code);
            } else {
                // 500
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            String jsonInString = RespUtil.objectToJsonInString(entity);
            try {
                response.getWriter().write(jsonInString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}