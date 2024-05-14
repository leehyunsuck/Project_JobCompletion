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

@Controller
@WebServlet(name = "Login", value = "/login")
public class UserInfoLoginController extends HttpServlet {
    private final UserInfoService userInfoService;

    public UserInfoLoginController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = (String) req.getSession().getAttribute("email");
        String password = req.getParameter("password");

        String hashPassword = JobCompletionEncode.encode(email, password);

        req.getSession().setAttribute("loginEmail", null);
        req.getSession().setAttribute("notFoundEmail", null);
        req.getSession().setAttribute("wrongPassword", null);

        if (userInfoService.isAccountExists(email, hashPassword)) {
            req.getSession().setAttribute("loginEmail", email);
            resp.sendRedirect("localhost:8080/basic/index.jsp");
        } else {
            if (userInfoService.isEmailExists(email)) {
                req.getSession().setAttribute("notFoundEmail", true);
            } else {
                req.getSession().setAttribute("wrongPassword", true);
            }
            resp.sendRedirect("localhost:8080/basic/login.jsp");
        }
    }
}