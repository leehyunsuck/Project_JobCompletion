package Shingu.JobCompletion.basic;

import Shingu.JobCompletion.entity.Questions;
import Shingu.JobCompletion.repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Show {

    @Autowired
    private QuestionsRepository questionsRepository;

//    @PostMapping("/showQuestionsInputAnswer")
//    public List<Questions> showQuestions(@RequestParam String email) {
//        return questionsRepository.findByEmail(email);
//    }

    @PostMapping("/showQuestionsInputAnswer")
    public String showQuestions(@RequestParam String email) {
        List<Questions> questions = questionsRepository.findByEmail(email);
        StringBuilder html = new StringBuilder("<html><body>");
        if (questions.isEmpty()) {
            html.append("<p>저장된 데이터 없음</p>");

        }
        for (Questions question : questions) {
            html.append("<p>");
            html.append("키워드 : " + question.getKeyword());

            String q1 = question.getQuestion1();
            String q2 = question.getQuestion2();
            String q3 = question.getQuestion3();
            String q4 = question.getQuestion4();
            String q5 = question.getQuestion5();
            String q6 = question.getQuestion6();
            String q7 = question.getQuestion7();
            String q8 = question.getQuestion8();
            String q9 = question.getQuestion9();
            String q10 = question.getQuestion10();
            String a1 = question.getAnswers1();
            String a2 = question.getAnswers2();
            String a3 = question.getAnswers3();
            String a4 = question.getAnswers4();
            String a5 = question.getAnswers5();
            String a6 = question.getAnswers6();
            String a7 = question.getAnswers7();
            String a8 = question.getAnswers8();
            String a9 = question.getAnswers9();
            String a10 = question.getAnswers10();
            String f1 = question.getFeedback1();
            String f2 = question.getFeedback2();
            String f3 = question.getFeedback3();
            String f4 = question.getFeedback4();
            String f5 = question.getFeedback5();
            String f6 = question.getFeedback6();
            String f7 = question.getFeedback7();
            String f8 = question.getFeedback8();
            String f9 = question.getFeedback9();
            String f10 = question.getFeedback10();

            String[] questionsArray = {q1, q2, q3, q4, q5, q6, q7, q8, q9, q10};
            String[] answersArray = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10};
            String[] feedbackArray = {f1, f2, f3, f4, f5, f6, f7, f8, f9, f10};

            for (int i = 0; i < 10; i++) {
                if (questionsArray[i] == null) break;
                html.append("<br>" + questionsArray[i] + " : " + answersArray[i] + " : " + feedbackArray[i]);
            }


            html.append("</p>");
        }
        html.append("</body></html>");
        return html.toString();
    }
}
