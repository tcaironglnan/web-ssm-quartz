<%--
  Created by IntelliJ IDEA.
  User: FeeMo
  Date: 2018/9/22
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>修改版本界面</title>
    <script src="/js/jquery.js"></script>
</head>
<body>

<form action="/updateVersion" method="post" id="updateVersion">
    <input type="hidden" value="${versionModel.id}" name="id"/>
    <table>
        <tr>
            <th>
                版本号
            </th>
            <td>
                <input type="text" id="version" name="version" value="${versionModel.version}"/>
            </td>
        </tr>
        <tr>
            <th>
                版本描述
            </th>
            <td>
                <textarea name="content" id="content" cols="30" rows="10">${versionModel.content}</textarea>
            </td>
        </tr>
        <tr>
            <th>
                <input type="reset" value="取消"/>
            </th>
            <td>
                <input type="button" id="add" value="提交"/>
            </td>
        </tr>
    </table>
</form>
</body>

<script>

    $("#add").click(function () {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "/updateVersion",
            data: $('#updateVersion').serialize(),
            success: function (data) {
                if (data === 1) {
                    alert("修改成功")
                } else {
                    alert("修改失败")
                }
            },
            error: function (data) {
                console.log("error:" + data.responseText);
            }
        });
    });
</script>
</html>
