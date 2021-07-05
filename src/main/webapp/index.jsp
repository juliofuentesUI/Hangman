<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Hangman</title>
    <link rel="stylesheet" type="text/css" href="resources/main.css">
</head>
<body>
<h1>
</h1>
<br/>

<div class="hangManContainer" align="center">
    <p>Hello there!</p>
    <img src="${pageContext.request.contextPath}/resources/hangmanStart.jpeg"/>
</div>
    <div class="empty-letters-parent">
        <div class="flex-box-letters-horizontal">
            <c:forEach var = "i" begin="1" end="5">
                <div class="empty-letter">__</div>
            </c:forEach>
        </div>
    </div>
</body>
</html>