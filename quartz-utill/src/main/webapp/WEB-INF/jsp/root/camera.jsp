<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Camera</title>
    <script src="${pageContext.request.contextPath}/js/jquery.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/camera/webcam.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            var w = 320, h = 240;
            var pos = 0, ctx = null, saveCB, image = [];

            var canvas = document.createElement("canvas");
            canvas.setAttribute('width', w);
            canvas.setAttribute('height', h);

            if (canvas.toDataURL) {
                ctx = canvas.getContext("2d");
                image = ctx.getImageData(0, 0, w, h);

                saveCB = function (data) {
                    var col = data.split(";");
                    var img = image;

                    for (var i = 0; i < w; i++) {
                        var tmp = parseInt(col[i]);
                        img.data[pos + 0] = (tmp >> 16) & 0xff;
                        img.data[pos + 1] = (tmp >> 8) & 0xff;
                        img.data[pos + 2] = tmp & 0xff;
                        img.data[pos + 3] = 0xff;
                        pos += 4;
                    }

                    if (pos >= 4 * w * h) {
                        ctx.putImageData(img, 0, 0);
                        $.ajax({
                            type: "post",
                            url: "/upload?t=" + new Date().getTime(),
                            data: {type: "pixel", image: canvas.toDataURL("image/png")},
                            dataType: "html",
                            success: function (data) {
                                if (data !== "") {
                                    alert("保存成功！");
                                } else {
                                    alert("保存失败！");
                                }
                                pos = 0;
                                $("#img").attr("src", "");
                                $("#img").attr("src", data);
                            }
                        });
                    }
                };
            } else {
                saveCB = function (data) {
                    image.push(data);
                    pos += 4 * w;

                    if (pos >= 4 * w * h) {
                        $.ajax({
                            type: "post",
                            url: "/upload",
                            data: {type: "pixel", image: image.join('|')},
                            dataType: "json",
                            success: function (data) {
                                console.log("不保存+++++" + eval(msg));
                                pos = 0;
                                $("#img").attr("src", msg + "");
                            }
                        });
                    }
                };
            }
            $("#webcam").webcam({
                width: w,
                height: h,
                mode: "callback",
                swffile: "camera/jscam_canvas_only.swf",
                onSave: saveCB,
                onCapture: function () {
                    webcam.save();
                },
                debug: function (type, string) {
                    console.log(type + ": " + string);
                }
            });
        });

        //拍照
        function savePhoto() {
            webcam.capture();
        }

        //删除照片
        function delPhoto() {
            var filePath = $("img").attr("src");
            $.ajax({
                type: "post",
                url: "/delPhoto",
                data: {"filePath": filePath},
                dataType: "json",
                success: function (data) {
                    if (data == "1") {
                        $("#img").attr("src", "");
                        alert("删除成功！");
                    } else {
                        alert("删除失败！");
                    }
                }
            });
        }
    </script>

    <style type="text/css">
        #webcam {
            border: 1px solid #666666;
            width: 320px;
            height: 240px;
        }

        #photos {
            border: 1px solid #666666;
            width: 320px;
            height: 240px;
        }

        .btn {
            width: 320px;
            height: auto;
            margin: 5px 0px;
        }

        .btn input[type=button] {
            width: 150px;
            height: 50px;
            line-height: 50px;
            margin: 3px;
        }
    </style>
</head>

<body>
<div id="webcam"></div>
<div class="btn">
    <input type="button" value="删除" id="delBtn" onclick="delPhoto();"/>
    <input type="button" value="拍照" id="saveBtn" onclick="savePhoto();"/>
</div>
<div id="photos">
    <img src="" id="img" alt="查看图片">
</div>
</body>
