package org.jsp.todoapp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsp.todoapp.dto.Task;
import org.jsp.totoapp.service.TodoService;

@WebServlet("/add-task")
@MultipartConfig
public class AddTaskServlet extends HttpServlet{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	TodoService service = new TodoService();
	service.addTask(req, resp);

}
}
