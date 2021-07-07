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
    <script
            src="https://code.jquery.com/jquery-3.6.0.min.js"
            integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
            crossorigin="anonymous"></script>
    <script src="resources/hangmanAjax.js"></script>
</head>
<body>
<c:choose >
    <c:when test="${sessionScope.gameInstance.checkGameWon() && sessionScope.gameInstance.getLivesRemaining() > 0}">
        <a href="/">
            <button class="btn-true">RESET GAME</button>
        </a>
        <p>YOU WIN</p>
    </c:when>
    <c:when test="${sessionScope.gameInstance.getLivesRemaining() <= 0}">
        <a href="/">
            <button class="btn-true">RESET GAME</button>
        </a>
        <p>YOU LOSE</p>
    </c:when>
    <c:otherwise>
        <div>
            <div class="hangManContainer" align="center">
                <p>Hello there!</p>
                <p>Your session ID is: ${sessionScope.sessionId}</p>
                <p>Your game status is currently : ${sessionScope.hasStarted}</p>
                <h1>Lives remaining: ${sessionScope.gameInstance.getLivesRemaining()}</h1>
                <img alt="Hangman hanging picture" src="${pageContext.request.contextPath}/resources/hangman${sessionScope.gameInstance.getImageIndex()}.jpeg"/>
            </div>
            <div class="empty-letters-parent">
                <div class="flex-box-letters-horizontal">
                    <c:forEach var="i" items="${sessionScope.gameInstance.getPlayerAnswers()}" varStatus="loop">
                        <div class="empty-letter" id="${loop.index}" data-letter="${i}">${i}</div>
                    </c:forEach>
                </div>
            </div>
            <div class="available-letters-parent">
                <c:forEach var="letter" items="${sessionScope.gameInstance.getAvailLetterMap()}" >
                    <c:if test="${letter.value == true}">
                        <button type="button" class="btn-true letter-button" data-letterValue="${letter.key}">${letter.key}</button>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
