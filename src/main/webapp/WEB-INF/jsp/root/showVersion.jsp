<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: FeeMo
  Date: 2018/9/22
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>显示版本界面</title>
    <script src="/js/jquery.js"></script>
</head>
<body>

<select id="search" name="version">
    <c:forEach var="one" items="${list}">
        <option value="${one.id}">${one.version}</option>
    </c:forEach>
</select>
<br/>
<textarea readonly="readonly" cols="30" rows="10" id="val">${one.content}</textarea>
</body>

<script>
    $("#search").change(function () {

        var search = $("#search").val();
        $.ajax({
            type: "POST",
            dataType: "json",
            url:"/getVersionList",
            data: {"id":search},
            success: function (data) {
                console.log(data)
                $("#val").val(data.content);
            },
            error: function(data) {
                alert("error:"+data.responseText);
            }
        });
    });
</script>
</html>
