package Shingu.JobCompletion.controller;

import Shingu.JobCompletion.entity.UserInfo;
import Shingu.JobCompletion.servic.UserInfoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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

        if (password.equals(checkPassword)) {
            UserInfo user = new UserInfo();
            user.setEmail(email);
            user.setPassword(password);
            userInfoService.saveUser(user);
            resp.sendRedirect("http://localhost:8080/basic/login.jsp");
        } else {
            req.getSession().setAttribute("notEqualsPassword", true);
            resp.sendRedirect("http://localhost:8080/basic/register.jsp");
        }
    }
}