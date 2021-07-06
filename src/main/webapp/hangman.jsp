<%--
  Created by IntelliJ IDEA.
  User: Julio
  Date: 7/5/21
  Time: 10:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Hangman Game page</title>
    <link rel="stylesheet" type="text/css" href="resources/main.css">
</head>
<body>
    <div>
        <p>THIS IS WHERE HANGMAN WILL BE PLAYED</p>
        <div class="hangManContainer" align="center">
            <a href="/HangmanGame?startGame=true">Start Game</a>
            <p>Hello there!</p>
            <img alt="Hangman hanging picture" src="${pageContext.request.contextPath}/resources/hangmanStart.jpeg"/>
        </div>
        <div class="empty-letters-parent">
            <div class="flex-box-letters-horizontal">
                <c:forEach var = "i" begin="1" end="5">
                    <div class="empty-letter">__</div>
                </c:forEach>
            </div>
        </div>
    </div>
</body>
</html>
