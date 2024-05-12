package Shingu.JobCompletion.controller;

import Shingu.JobCompletion.entity.UserInfo;
import Shingu.JobCompletion.servic.UserInfoService;
import Shingu.JobCompletion.tool.JobCompletionEncode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.security.MessageDigest;

@Controller
@WebServlet(name = "Register", value = "/register")
public class UserInfoController extends HttpServlet {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String) req.getSession().getAttribute("email");
        String password = req.getParameter("password");
        String checkPassword = req.getParameter("checkPassword");

        req.getSession().setAttribute("passwordLength", false);
        req.getSession().setAttribute("notEqualsPassword", false);

        if (password.equals(checkPassword)) {
            //비밀번호 최대 최소 길이 설정
            if (password.length() > 30 || password.length() < 7) {
                req.getSession().setAttribute("passwordLength", true);
                resp.sendRedirect("http://localhost:8080/basic/register.jsp");
                return;
            }
            
            //암호화 값 받기
            String hashPassword = JobCompletionEncode.encode(email, password);

            if (hashPassword == null) {
                resp.sendRedirect("localhost:8080/basic/index.jsp");
                return;
            }

            //DB에 저장 후 페이지 이동
            UserInfo user = new UserInfo();
            user.setEmail(email);
            user.setPassword(hashPassword);
            userInfoService.saveUser(user);
            resp.sendRedirect("http://localhost:8080/basic/login.jsp");
        } else {
            req.getSession().setAttribute("notEqualsPassword", true);
            resp.sendRedirect("http://localhost:8080/basic/register.jsp");
        }
    }
}