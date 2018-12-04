<%--
  Created by IntelliJ IDEA.
  User: FeeMo
  Date: 2018/9/22
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" language="java" %>
<html>
<head>
    <title>添加版本界面</title>
    <script src="/js/jquery.js"></script>
</head>
<body>

<form action="/addVersion" method="post" id="addVersion">
    <table>
        <tr>
            <th>
               版本号
            </th>
            <td>
                <input type="text" id="version" name="version"/>
            </td>
        </tr>
        <tr>
            <th>
                版本描述
            </th>
            <td>
                <textarea name="content" id="content" cols="30" rows="10">请输入...</textarea>
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
            url:"/addVersion",
            data: $('#addVersion').serialize(),
            success: function (data) {
                if (data === 1) {
                    alert("添加成功")
                }else {
                    alert("添加失败")
                }
            },
            error: function(data) {
                alert("error:"+data.responseText);
            }
        });
    });
</script>
</html>
