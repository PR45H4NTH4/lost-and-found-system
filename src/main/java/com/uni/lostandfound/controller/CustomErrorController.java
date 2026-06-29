package com.uni.lostandfound.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        String errorMessage = "An unexpected error occurred.";
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
        
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                errorMessage = "The page you are looking for was not found.";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorMessage = "Internal server error. Please try again later.";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value() || statusCode == HttpStatus.UNAUTHORIZED.value()) {
                errorMessage = "You are not authorized to view this page.";
            }
        }
        
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
