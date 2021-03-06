<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Quartz</title>
    <script src="${pageContext.request.contextPath}/js/jquery.js" type="application/javascript"></script>
</head>

<body class="bgf7">
<br>
<div class="w100 marginM bgfff tx-c paddingtb2">
<h1>
    <input type="button" url="${pageContext.request.contextPath}/user/startTask" value="开始定时扫描" id="startTask"/>
    <input type="button" url="${pageContext.request.contextPath}/user/modifyTask" value="修改定时扫描" id="modifyTask"/>
    <input type="button" url="${pageContext.request.contextPath}/user/shutTask" value="关闭指定定时扫描" id="shutTask"/>
    <input type="button" url="${pageContext.request.contextPath}/user/shutAllTask" value="关闭所有定时扫描" id="shutAllTask"/>
</h1>
</div>
</body>
<script>
    $("#startTask").click(function () {
        $.ajax({
            url: $("#startTask").attr("url"),
            async: false,
            data: {
                "jobName": "ab",
                "jobGroupName": "cd",
                "triggerName": "v",
                "triggerGroupName": "vb",
                "cron": "0/2 * * * * ?"
            },
            type: "POST",
            success: function (data) {
                alert("启动指定定时器成功");
            }
        });
    });

    $("#modifyTask").click(function () {
        $.ajax({
            url: $("#modifyTask").attr("url"),
            async: false,
            data: {
                "jobName": "ab",
                "jobGroupName": "cd",
                "triggerName": "v",
                "triggerGroupName": "vb",
                "cron": "0/10 * * * * ?"
            },
            type: "POST",
            success: function (data) {
                alert("修改指定定时器成功！");
            }
        });
    });

    $("#shutTask").click(function () {
        $.ajax({
            url: $("#shutTask").attr("url"),
            async: false,
            data: {
                "jobName": "ab",
                "jobGroupName": "cd",
                "triggerName": "v",
                "triggerGroupName": "vb"
            },
            type: "POST",
            success: function (data) {
                alert("关闭指定定时器成功！");
            }
        });
    });

    $("#shutAllTask").click(function () {
        $.ajax({
            url: $("#shutAllTask").attr("url"),
            async: false,
            data: {},
            type: "POST",
            success: function (data) {
                alert("关闭所有定时器成功！");
            }
        });
    });
</script>

