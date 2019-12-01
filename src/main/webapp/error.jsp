<%@ page isErrorPage="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
        <title>Show Error Page</title>
        <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
    <div class="container">
        <h1>Opps...</h1>
        Exception is: <%= exception %>

        <h4 class="text-center"><a href="${contextPath}/welcome">Go to home</a></h4>
    </div>
    <script src="${contextPath}/resources/js/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
    </body>
</html>
