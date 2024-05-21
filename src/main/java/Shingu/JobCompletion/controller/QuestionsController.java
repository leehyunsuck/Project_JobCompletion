package Shingu.JobCompletion.controller;

import Shingu.JobCompletion.dto.QuestionsForm;
import Shingu.JobCompletion.entity.Questions;
import Shingu.JobCompletion.repository.QuestionsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

//@RestController
//public class QuestionsController {
//
//    @Autowired
//    private QuestionsRepository questionsRepository;
//
//    @PostMapping(path = "/save/questions", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    public ResponseEntity<?> createQuestions(@ModelAttribute QuestionsForm form) {
//        Questions questions = form.toEntity();
//
//        String q1 = form.getQuestion1();
//        String q2 = form.getQuestion2();
//        String q3 = form.getQuestion3();
//        String q4 = form.getQuestion4();
//        String q5 = form.getQuestion5();
//        String q6 = form.getQuestion6();
//        String q7 = form.getQuestion7();
//        String q8 = form.getQuestion8();
//        String q9 = form.getQuestion9();
//        String q10 = form.getQuestion10();
//
//        String a1 = form.getAnswers1();
//        String a2 = form.getAnswers2();
//        String a3 = form.getAnswers3();
//        String a4 = form.getAnswers4();
//        String a5 = form.getAnswers5();
//        String a6 = form.getAnswers6();
//        String a7 = form.getAnswers7();
//        String a8 = form.getAnswers8();
//        String a9 = form.getAnswers9();
//        String a10 = form.getAnswers10();
//
//        questionsRepository.save(questions);
//        return ResponseEntity.ok().body("Questions saved successfully");
//    }
//}

@RestController
public class QuestionsController {

    @Autowired
    private QuestionsRepository questionsRepository;

    @PostMapping(path = "/save/questions", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> createQuestions(@ModelAttribute QuestionsForm form, HttpSession session) throws Exception {
        String loginEmail = (String) session.getAttribute("loginEmail");
        String[] questions = (String[]) session.getAttribute("questions");
        String[] answers = (String[]) session.getAttribute("answers");

        String keyword = (String) session.getAttribute("keyword");

        form.setKeyword(keyword);
        form.setEmail(loginEmail);
        for (int i = 0; i < questions.length; i++) {
            String questionMethodName = "setQuestion" + (i + 1);
            String answerMethodName = "setAnswers" + (i + 1);

            Method questionMethod = form.getClass().getMethod(questionMethodName, String.class);
            Method answerMethod = form.getClass().getMethod(answerMethodName, String.class);

            questionMethod.invoke(form, questions[i]);
            answerMethod.invoke(form, answers[i]);
        }

        Questions questionsEntity = form.toEntity();
        questionsRepository.save(questionsEntity);
        return ResponseEntity.ok().body("Questions saved successfully");
    }
}
