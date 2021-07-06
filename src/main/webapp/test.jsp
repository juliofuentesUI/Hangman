<%--
  Created by IntelliJ IDEA.
  User: Julio
  Date: 7/3/21
  Time: 2:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TESTING THIS JSP</title>
</head>
<body>
    <p>hi hi </p>
    <h1>Value of gameStarted Attribute is : ${game.getWord()}   </h1>
    <p>This is TEST.JSP, controller updated the view</p>
    <p>request parameter in url is : ${param.startGame} </p>
</body>
</html>
