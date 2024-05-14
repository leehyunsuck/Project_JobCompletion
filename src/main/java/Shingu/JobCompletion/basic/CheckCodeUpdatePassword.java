package Shingu.JobCompletion.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CheckCodeUpdatePassword", value = "/checkCodeUpdatePassword")
public class CheckCodeUpdatePassword extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputCode = req.getParameter("inputCode");
        Integer code = (Integer) req.getSession().getAttribute("code");

        if (inputCode.equals(String.valueOf(code))) {
            req.getSession().setAttribute("check", true);
        } else {
            req.getSession().setAttribute("invalidCode", true);
        }
        resp.sendRedirect("/basic/findAccount.jsp");
    }
}