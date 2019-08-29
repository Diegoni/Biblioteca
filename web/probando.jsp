<%-- 
    Document   : probando
    Created on : 22/12/2018, 17:45:46
    Author     : Tommy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
    if(session.getAttribute("user") == null){
        out.print("asdasd");
    } else {
        out.print(session.getAttribute("user"));
        out.print(session.getAttribute("type"));
    }
    
        %>
    </body>
</html>
