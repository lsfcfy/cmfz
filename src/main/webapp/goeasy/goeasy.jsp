<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2019/11/4
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script>
        var goEasy = new GoEasy({
            //发布的appkey
            appkey:"BC-e891702436bc4f33b60b17739844b6b2"
        });
        goEasy.publish({
            //当前的channel名称
            channel:"test-channel",
            message:"你是否还喜欢哪个她！"
        })
    </script>
</head>
<body>

</body>
</html>
