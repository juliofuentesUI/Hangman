<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" type="text/css" href="resources/main.css">
</head>
<body>
<h1>
</h1>
<br/>

<div align="center">
    <p>Hello there!</p>
    <a href="HangmanGame">Click here to start game</a>
    <p>PAGE CONTEXT IS:</p>
    <p>${pageContext.request.contextPath}</p>
    <img src="${pageContext.request.contextPath}/resources/hangman0.jpeg"/>
</div>
</body>
</html>