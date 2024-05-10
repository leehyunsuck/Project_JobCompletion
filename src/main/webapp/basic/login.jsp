<%--
  Created by IntelliJ IDEA.
  User: 5talk
  Date: 2024-04-30
  Time: 오후 2:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Shingu-Project</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <meta name="description" content="" />
    <meta name="keywords" content="" />
    <link rel="stylesheet" href="../basic/assets/css/main.css" />

</head>
<body class="is-preload">

<!-- Header -->
<header id="header">
    <a class="logo" href="index.jsp">취업완</a>
</header>

<!-- Banner -->
<section id="banner">
    <div class="inner">
        <h2>로그인</h2>
        <%
            String loginEmail = (String) session.getAttribute("loginEmail");
            if (loginEmail == null) {
                session.invalidate();
        %>
        <form method="post" action="http://localhost:8080/login">

            <div class="col-9 col-12-xsmall">
                <input type="text" name="id" value="" placeholder="아이디 입력란" />
            </div>


            <div class="col-9 col-12-xsmall">
                <input type="text" name="password" value="" placeholder="패스워드 입력란" />
            </div>

            <br>
            <div class="col-3 col-12-xsmall">
                <input type="submit" value="Login" class="primary" />
            </div>

        </form>
        <% } %>
        <a href="register.jsp">회원가입</a>
        <a href="findAccount.jsp">회원정보 찾기</a>
    </div>
    <video autoplay loop muted playsinline src="../basic/images/banner.mp4"></video>
</section>

<!-- Footer -->
<footer id="footer">
    <div class="inner">
        <div class="copyright">
            <a>
                Industrious by TEMPLATED
                templated.co @templatedco
                Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
            </a>
        </div>
    </div>
</footer>

<!-- Scripts -->
<script src="../basic/assets/js/jquery.min.js"></script>
<script src="../basic/assets/js/browser.min.js"></script>
<script src="../basic/assets/js/breakpoints.min.js"></script>
<script src="../basic/assets/js/util.js"></script>
<script src="../basic/assets/js/main.js"></script>


</body>
</html>
