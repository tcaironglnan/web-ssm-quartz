<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2018/5/3
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>修改密码界面</title>
    <script src="/common/B-JUI/js/jquery-1.11.3.min.js"></script>
</head>
<body>

    <form action="" method="post">

        新密码:<input type="password" name="passWord" value="" id="passWord"><br/>
        确认密码:<input type="password" name="passWord" value="" id="passWord2"><br/>
        <input type="button" value="确认" id="btn"/>
    </form>

    <script>

        var auth = ${user.auth};

        $("#btn").click(function () {

            let pwd = $("#passWord").val();
            let pwd2 = $("#passWord2").val();
            var url = "";

            if (pwd === "" || pwd2 === "") {
                alert("未填写完整");
                return;
            }

            if (pwd !== pwd2) {
                alert("两次输入的密码不一致");
                return;
            }

            url = "/user/updatePassWord";
            if (auth === 1){
                url = "/admin/updatePassWord";
            }

            $.ajax({
                timeout: 20000,
                type: "POST",
                dataType: "JSON",
                url: url,
                async: false,
                data: {"passWord": pwd},
                success: function (data) {

                    if (data === 2) {
                        alert("请先登录系统")
                    }
                    if (data === 1) {
                        alert("密码修改成功");
                        window.location.href="/logout";
                    }
                    if (data === 0) {
                        alert("密码修改失败");
                    }
                }
            });
        });
    </script>
</body>
</html>
