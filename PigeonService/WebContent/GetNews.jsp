<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%@page import="org.json.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GetNews</title>
</head>
<body>
	<form id="from" action="GetNews" method="post">  
	<table>  
	<tr><td>email</td><td><input type="text" name="EM"></td></tr>  
	<tr><td>password</td><td><input type="text" name="PW"></td></tr>
	<tr><td>websiteid</td><td><input type="text" name="WEBSITEID"></td></tr>
	<tr><td>page</td><td><input type="text" name="PAGE"></td></tr>
	<tr><td colspan="2" align="center"><input type="submit"  value="get news"></td></tr>  
	</table>  
	</form>  
</body>
</html>