package com.douyasi.example.spring_demo.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HandleErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public ModelAndView error(HttpServletRequest req) {
        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = "Error or exception occurs !";
        int httpStatusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        switch (httpStatusCode) {
            case 400: {
                errorMsg = "400 - Bad Request";
                break;
            }
            case 401: {
                errorMsg = "401 - Unauthorized";
                break;
            }
            case 404: {
                errorMsg = "404 - Not found";
                break;
            }
            case 405: {
                errorMsg = "405 - Method Not Allowed";
                break;
            }
            case 415: {
                errorMsg = "415 - Unsupported Media Type";
                break;
            }
            case 500: {
                errorMsg = "500 - Internal Server Error";
                break;
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
