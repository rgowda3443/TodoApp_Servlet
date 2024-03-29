package org.jsp.todoapp.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter({"/add-task","/update-task","/delete","/complete","/home.jsp","/add-task.html","/edit-task.jsp"})
public class SessionFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req= (HttpServletRequest) request;
		if(req.getSession().getAttribute("userLogin")==null) {
			response.getWriter().print("<h1 'align:center' style='color:red'> Inavlid Session </h1>");
			req.getRequestDispatcher("login.html").include(request, response);
		}
		else {
			chain.doFilter(request, response);
		}
		
	}

}
