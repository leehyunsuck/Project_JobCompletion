package Shingu.JobCompletion.basic;

import Shingu.JobCompletion.entity.Questions;
import Shingu.JobCompletion.repository.QuestionsRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@WebServlet(name = "ShowQuestions", value = "/show/history")
public class ShowHistory extends HttpServlet {
    @Autowired
    private QuestionsRepository questionsRepository;

    public List<Questions> getQuestions(HttpSession session) {
        String email = (String) session.getAttribute("loginEmail");
        List<Questions> history = questionsRepository.findByEmail(email);

        return history;
    }
}
