<!DOCTYPE html>
<%@page import="org.jsp.todoapp.dto.Task"%>
<%@page import="java.util.List"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<style>
div {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
}

tr, th {
	margin: 10px;
	padding: 20px;
}

.extra {
	margin-top: 50px;
	margin-left: 400px;
}

body {
	height: 100%;
	background-image:
		url("https://images.pexels.com/photos/355508/pexels-photo-355508.jpeg?cs=srgb&dl=pexels-pixabay-355508.jpg&fm=jpg");
	background-repeat: no-repeat;
	background-size: cover;
}

table {
	backdrop-filter: blur(15px);
}
</style>
</head>
<body>
	<h1 align="center">Welcome to home Page</h1>
	<%
	List<Task> tasks = (List<Task>) request.getAttribute("tasks");
	%>
	<%
	if (!tasks.isEmpty()) {
	%>
	<div>
		<table border="1">
			<tr>
				<th>Task image</th>
				<th>Task Name</th>
				<th>Task Description</th>
				<th>Created Time</th>
				<th>Status</th>
				<th>Delete</th>
				<th>Edit</th>
			</tr>
			<%
			for (Task task : tasks) {
			%>
			<tr>
				<th><img height="200px" width="200px"
					src="data:/image/png;base64,<%=task.getEncodedImage()%>"></th>
				<th><%=task.getName()%></th>
				<th><%=task.getDesc()%></th>
				<th><%=task.getAddedtime()%></th>
				<th>
					<%
					if (task.isStatus()) {
					%> Completed <%
					} else {
					%> <a href="complete?id=<%=task.getId()%>"><button>Complete</button></a>
					<%
					}
					%>
				</th>
				<th><a href="delete?id=<%=task.getId()%>"><button>Delete</button></a></th>
				<th><a href="edit-task.jsp?id=<%=task.getId()%>"><button>Edit</button></a></th>
			</tr>
			<%
			}
			%>
		</table>
	</div>
	<%
	}
	%>
	<a href="add-task.html"><button class="extra">Add Task</button></a>
	<a href="logout"><button class="extra">Logout</button></a>
</body>
</html>