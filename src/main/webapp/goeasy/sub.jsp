<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2019/11/4
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>
        var goEasy = new goEasy({
            appkey: "BC-e891702436bc4f33b60b17739844b6b2"
        });
        goEasy.subscribe({
            channel:"test-channel",
            onMessage:function (message) {
                alert("channel:"+message.channel+"content:"+conten.message)
            }
        })
    </script>
</head>
<body>

</body>
</html>
