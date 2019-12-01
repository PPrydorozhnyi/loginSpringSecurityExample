<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: T1
  Date: 12/1/2019
  Time: 12:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Oauth2 Login</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css"/>
</head>

<body>
<div class="container">
    <div class="col-sm-3 well">
        <h3>Login with:</h3>
        <div class="list-group">
            <c:forEach items="${urls}" var="url">
                <a href="${url.value}" class="list-group-item active">${url.key}</a>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
