<%--
  Created by IntelliJ IDEA.
  User: 如三秋兮
  Date: 2022/4/25
  Time: 23:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login</title>
</head>
<body>
    <form action="/smbms/login" method="post">
        用户名:<input type="text" name="username" /> <br/>
        密 码：<input type="password" name="password" /> <br/>

        <input type="submit" />
    </form>
</body>
</html>
