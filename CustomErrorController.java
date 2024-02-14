package com.example.jhajeecodes.Employee.Management.System.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		// Get the HTTP status code and message
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

		// Customize the error message or handle it as needed
		model.addAttribute("statusCode", status);
		model.addAttribute("errorMessage", message);

		// Return the path to your custom error page
		return "error"; // Assuming your error page is named "error.html" in the templates folder
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}