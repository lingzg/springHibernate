<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
	<title>depts</title>
</head>
<body>
	<h1>depts</h1>
	<table border="1" cellspacing="0" cellpadding="2">
		<tr>
			<td>deptno</td>
			<td>dname</td>
			<td>loc</td>
		</tr>
		<c:forEach var="dept" items="${depts}">
			<tr>
				<td>${dept.deptno}</td>
				<td>${dept.dname}</td>
				<td>${dept.loc}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>