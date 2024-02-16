package org.jsp.todoapp.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsp.todoapp.dao.TodoDao;
import org.jsp.todoapp.dto.User;
import org.jsp.totoapp.service.TodoService;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet
{
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	TodoService service =new TodoService();
	service.signUp(req, resp);
}

}
