<!DOCTYPE html>

<%@page import="org.jsp.todoapp.dto.Task"%>
<%@page import="org.jsp.todoapp.dao.TodoDao"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Task</title>
<style>
div {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

body {
	height: 100%;
	background-image:
		url("https://wallpaper.dog/large/411312.jpg");
	background-repeat: no-repeat;
	background-size: cover;
}
form {
	backdrop-filter: blur(15px);
}
</style>
</head>
<body>
	<div>
		<%
		int id = Integer.parseInt(request.getParameter("id"));
		TodoDao dao = new TodoDao();
		Task task = dao.findById(id);
		%>
		<h1>Edit Task</h1>
		<form action="update-task" method="post" enctype="multipart/form-data">
			<input type="hidden" name="id" value="<%=task.getId()%>">
			<fieldset>
				<legend>Enter Task here</legend>
				<table>
					<tr>
						<th>Task Name:</th>
						<th><input type="text" name="t_name"
							value="<%=task.getName()%>"></th>
					</tr>
					<tr>
						<th>Task Description:</th>
						<th><input type="text" name="t_description"
							value="<%=task.getDesc()%>"></th>
					</tr>
					<tr>
						<th>Task Image:</th>
						<th><input type="file" name="t_img"><img height="100px" width="100px"
					src="data:/image/png;base64,<%=task.getEncodedImage()%>" /></th>
					</tr>
					<tr>
						<th><button>Update</button></th>
						<th><button type="reset">Cancel</button></th>
					</tr>
				</table>
			</fieldset>
		</form>
		
	</div>
</body>
</html>