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
        <div class="hangManContainer" align="center">
            <p>Hello there!</p>
            <p>Your session ID is: ${sessionScope.sessionId}</p>
            <p>Your game status is currently : ${sessionScope.hasStarted}</p>
            <img alt="Hangman hanging picture" src="${pageContext.request.contextPath}/resources/hangman0.jpeg"/>
        </div>
        <div class="empty-letters-parent">
            <div class="flex-box-letters-horizontal">
                <c:forEach var="i" items="${sessionScope.gameInstance.GetCurrentWord().toCharArray()}" varStatus="loop">
                    <div class="empty-letter" id="${loop.index}" data-letter="${i}">__</div>
                </c:forEach>
            </div>
        </div>
        <div class="available-letters-parent">
            <c:forEach var="letter" items="${sessionScope.gameInstance.GetAllLetters()}" >
                <button type="button" class="btn-true">${letter}</button>
            </c:forEach>
        </div>
    </div>
</body>
</html>
