package Shingu.JobCompletion.controller;

import Shingu.JobCompletion.entity.Questions;
import Shingu.JobCompletion.repository.QuestionsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShowHistoryController {

    @Autowired
    private QuestionsRepository questionsRepository;

    @GetMapping("/show/history")
    public String showHistory(HttpSession session, Model model) {
        String email = (String) session.getAttribute("loginEmail");
        List<Questions> history = questionsRepository.findByEmail(email);
        model.addAttribute("history", history);
        return "views/showHistory";
    }
}
