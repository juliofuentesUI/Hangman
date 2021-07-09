<%--
  Created by IntelliJ IDEA.
  User: Julio
  Date: 7/7/21
  Time: 11:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Reset Game JSP</title>
    <link rel="stylesheet" type="text/css" href="resources/main.css">
</head>
<body>
    <div class="center">
        <h1>Reset Game JSP</h1>
        <c:choose>
            <c:when test="${sessionScope.gameInstance.checkGameWon()}">
                <p>YOU WIN!</p>
            </c:when>
            <c:otherwise>
                <p>YOU LOSE!</p>
            </c:otherwise>
        </c:choose>
        <a href="/HangmanGame">Click here to start again</a>
    </div>
</body>
</html>
