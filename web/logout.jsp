<%-- 
    Document   : logout
    Created on : 12/06/2019, 19:17:27
    Author     : Tommy
--%>
<%
    session.invalidate();
    response.sendRedirect("../BibliotecaBootstrap/");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Sesion invalidada</h1>
    </body>
</html>
