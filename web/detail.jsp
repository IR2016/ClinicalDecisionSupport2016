<%-- 
    Document   : detail.jsp
    Created on : Apr 24, 2016, 5:04:01 PM
    Author     : Carol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail</title>
    </head>
<body>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List"%>
<%@page import="org.json.*"%>
    <%
        String title = (String)request.getAttribute("currentTitle");
        String content = (String)request.getAttribute("currentContent");
    
    %>
    <div style="width:1200px;margin:auto;">
    <h1><%=title %></h1>
    
    <p><%=content %></p>
    </div>
</body>
</html>
