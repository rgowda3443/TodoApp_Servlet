package org.jsp.totoapp.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.print.DocFlavor.BYTE_ARRAY;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.jsp.todoapp.dao.TodoDao;
import org.jsp.todoapp.dto.Task;
import org.jsp.todoapp.dto.User;

public class TodoService {

	TodoDao dao = new TodoDao();
	

	public void signUp(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String password = req.getParameter("password");
		String dob = req.getParameter("dob");
		String gender = req.getParameter("gender");

		User user = new User();
		user.setDob(LocalDate.parse(dob));
		user.setEmail(email);
		user.setGender(gender);
		user.setMobile(Long.parseLong(mobile));
		user.setName(name);
		user.setPassword(password);

		List<User> users = dao.findUserByEmail(user.getEmail());
		List<User> users1 = dao.findUserByMobile(user.getMobile());

		if (users.isEmpty() && users1.isEmpty()) {
			dao.saveUser(user);
			resp.getWriter().print("<h1 style='color:green'> Account created successfully</h1>");
			req.getRequestDispatcher("login.html").include(req, resp);
		} else {
			if (users.isEmpty()) {
				resp.getWriter().print("<h1 style='color:red'> Mobile " + "<span style='color:green'>"
						+ user.getMobile() + "</span>" + " already exists</h1>");
			} else if (users1.isEmpty()) {
				resp.getWriter().print("<h1 style='color:red'> email " + "<span style='color:green'>" + user.getEmail()
						+ "</span>" + " already exists</h1>");
			} else {
				resp.getWriter().print("<h1 align='center', style='color:red'> mobile and email already exists</h1>");
			}
			req.getRequestDispatcher("signup.html").include(req, resp);
		}
	}

	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String emph = req.getParameter("emph");
		String password = req.getParameter("password");
		List<User> list = null;

		try {
			long mobile = Long.parseLong(emph);
			list = dao.findUserByMobile(mobile);
			if (list.isEmpty())
				resp.getWriter().print("<h1 style='color:red'>Incorrect Mobile number </h1>");

		} catch (NumberFormatException e) {
			String email = emph;
			list = dao.findUserByEmail(email);
			if (list.isEmpty())
				resp.getWriter().print("<h1 style='color:red'>Incorrect E-mail </h1>");
		}

		if (!list.isEmpty()) {
			if (list.get(0).getPassword().equals(password)) {
				req.getSession().setAttribute("userLogin", list.get(0));
				resp.getWriter().print("<h1 style='color:green' align='center'> Login Success </h1>");
				
				List<Task> tasks=dao.fetchTasksByUserId(list.get(0).getId());
				req.setAttribute("tasks", tasks);
				
				req.getRequestDispatcher("home.jsp").include(req, resp);
			} else {
				resp.getWriter().print("<h1 style='color:red','align:center'> Incorrect Password </h1>");
				req.getRequestDispatcher("login.html").include(req, resp);
			}
		} else {
			req.getRequestDispatcher("login.html").include(req, resp);
		}
	}

	public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getSession().removeAttribute("userLogin");
		resp.getWriter().print("<h1 style='color:red'>Logged Out Successfully</h1>");
		req.getRequestDispatcher("login.html").include(req, resp);
	}

	public void addTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	
		Task t = new Task();
		String name = req.getParameter("tname");
		String desc = req.getParameter("tdesc");
		Part img = req.getPart("timg");

		t.setName(name);
		t.setDesc(desc);
		t.setStatus(false);
		t.setAddedtime(LocalDateTime.now());

		// To convert Part to byte Array
		byte[] pic = new byte[img.getInputStream().available()];
		img.getInputStream().read(pic);

		t.setImage(pic);

		User user = (User) req.getSession().getAttribute("userLogin"); //return type of gatAttr is Object so need to downcaste
		t.setUser(user);
		
		dao.saveTask(t);
		
		resp.getWriter().print("<h1 align='center' style='color:green'>Task Added Success</h1>");
		
		List<Task> tasks=dao.fetchTasksByUserId(user.getId());
		req.setAttribute("tasks", tasks);
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
	}

	public void complete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		int id=Integer.parseInt(req.getParameter("id"));
		Task task=dao.findById(id);
		task.setStatus(true);
		dao.updateTask(task);
		
		resp.getWriter().print("<h1 align='center' style='color:green'>Status Changed Success</h1>");
		User user=(User) req.getSession().getAttribute("userLogin");
		List<Task> tasks=dao.fetchTasksByUserId(user.getId());
		req.setAttribute("tasks",tasks);
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
		
	}
	
	public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id=Integer.parseInt(req.getParameter("id"));
		Task task=dao.findById(id);
		
		dao.deleteTask(task);
		
		resp.getWriter().print("<h1 align='center' style='color:green'>Task Deleted Success</h1>");
		User user=(User) req.getSession().getAttribute("userLogin");
		List<Task> tasks=dao.fetchTasksByUserId(user.getId());
		req.setAttribute("tasks",tasks);
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
	}
	
	public void updateTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Task task=new Task();
		String name=req.getParameter("t_name");
		String description=req.getParameter("t_description");
		Part img=req.getPart("t_img");
		int id=Integer.parseInt(req.getParameter("id"));
		
		User user=(User) req.getSession().getAttribute("userLogin");
		
		task.setId(id);
		
		byte[] pic = new byte[img.getInputStream().available()];
		img.getInputStream().read(pic);
		if(pic.length==0)
			task.setImage(dao.findById(id).getImage());
		else
			task.setImage(pic);
		task.setName(name);
		task.setDesc(description);
		task.setAddedtime(LocalDateTime.now());

		task.setStatus(false);
		task.setUser(user);
		
		dao.updateTask(task);
		
		
		List<Task> tasks=dao.fetchTasksByUserId(user.getId());
		req.setAttribute("tasks",tasks);
		resp.getWriter().print("<h1 align='center' style='color:green'>Task Updated Success</h1>");
		
		req.getRequestDispatcher("home.jsp").include(req, resp);
	}
}
