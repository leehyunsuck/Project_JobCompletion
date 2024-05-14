<%--
  Created by IntelliJ IDEA.
  User: 5talk
  Date: 2024-04-30
  Time: 오후 2:46
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
<body class="is-preload" style="background-color: black">

<!-- Header -->
<header id="header">
    <a class="logo" href="index.jsp">취업완</a>
    <%
        String loginEmail = (String) session.getAttribute("loginEmail");
        if (loginEmail == null) {
            session.invalidate();
    %>
    <a class="logo" href="login.jsp">로그인</a>
    <% } else { %>
    <p style="color: white;"><%=loginEmail%> 님 환영합니다.</p>
    <% } %>
</header>

<%--<div class="w-4/5 mx-auto bg-white dark:bg-zinc-800 shadow-md rounded-lg overflow-hidden">--%>
<%--    <div class="flex flex-col h-[400px]">--%>
<%--        <div class="px-4 py-3 border-b dark:border-zinc-700">--%>
<%--            <div class="flex justify-between items-center">--%>
<%--                <h2 class="text-lg font-semibold text-zinc-800 dark:text-white">--%>
<%--                    모의 면접--%>
<%--                </h2>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div class="flex-1 p-3 overflow-y-auto flex flex-col space-y-2" id="chatDisplay">--%>
<%--            <div class="chat-message self-end bg-blue-500 text-white max-w-xs rounded-lg px-3 py-1.5 text-sm">--%>
<%--                안녕하세요! 오늘 어떻게 도와드릴까요?--%>
<%--            </div>--%>
<%--            <div class="chat-message self-start bg-zinc-500 text-white max-w-xs rounded-lg px-3 py-1.5 text-sm">--%>
<%--                안녕하세요! 챗봇이 필요해요!--%>
<%--            </div>--%>
<%--        </div>--%>


<%--        <div class="px-3 py-2 border-t dark:border-zinc-700">--%>
<%--            <div class="flex gap-2">--%>
<%--                <input--%>
<%--                        placeholder="메시지를 입력하세요..."--%>
<%--                        class="flex-1 p-2 border rounded-lg dark:bg-zinc-700 dark:text-white dark:border-zinc-600 text-sm"--%>
<%--                        id="chatInput"--%>
<%--                        type="text"--%>
<%--                />--%>
<%--                <button--%>
<%--                        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1.5 px-3 rounded-lg transition duration-300 ease-in-out text-sm"--%>
<%--                        id="sendButton"--%>
<%--                >--%>
<%--                    보내기--%>
<%--                </button>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<div style="width: 80%; margin: auto; background-color: #171c24; box-shadow: 0px 0px 10px 0px rgba(0,0,0,0.15); border-radius: 10px; overflow: hidden;">
    <div style="display: flex; flex-direction: column; height: 80%;">
        <div style="padding: 16px; border-bottom: 1px solid #ccc;">
            <div style="display: flex; justify-content: space-between; align-items: center;">
                <h2 style="font-size: 1.125rem; font-weight: 600; color: white;">
                    모의 면접
                </h2>
            </div>
        </div>
        <div style="flex: 1; height: 80%; padding: 16px; overflow-y: auto; display: flex; flex-direction: column; gap: 8px;" id="chatDisplay">
            <div style="align-self: flex-start; background-color: #717171; color: white; max-width: 20rem; border-radius: 10px; padding: 12px 16px; font-size: 0.875rem;">
                질문
            </div>
            <div style="align-self: flex-end; background-color: #007bff; color: white; max-width: 20rem; border-radius: 10px; padding: 12px 16px; font-size: 0.875rem;">
                대답
            </div>
        </div>
        <div style="padding: 16px; border-top: 1px solid #ccc;">
            <form method="POST" action="/"  style="display: flex; gap: 8px;">
                <input
                        placeholder="메시지를 입력하세요..."
                        style="flex: 1; padding: 8px; border: 1px solid #ccc; border-radius: 10px; font-size: 0.875rem;"
                        id="chatInput"
                        type="text"
                        name="answer"
                />
                <input type="submit" value="전송" class="primary" id="submitBtn"/>
            </form>
        </div>
    </div>
</div>



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
<%--<script src="https://cdn.tailwindcss.com"></script>--%>
<script>
    document.querySelector('form').addEventListener('submit', function() {
        document.getElementById('submitBtn').disabled = true;
    });
</script>

</body>
</html>