package Shingu.JobCompletion.basic;

import Shingu.JobCompletion.controller.CustomBotController;
import Shingu.JobCompletion.dto.ChatGPTResponse;
import Shingu.JobCompletion.tool.GetClientIP;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

@WebServlet(name = "GPTFeedBack", value = "/GPTFeedBack")
public class GPTFeedBack extends HttpServlet {
    @Autowired
    CustomBotController customBotController;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String temp = (String) req.getSession().getAttribute("lang");
        if (temp == null) {
            res.sendRedirect("/basic/index.jsp");
            return;
        }

        String[] questions = (String[]) req.getSession().getAttribute("questions");
        String[] answers = (String[]) req.getSession().getAttribute("answers");
        String lang = (String) req.getSession().getAttribute("lang");

        String prompt = """       
        GPT You are a professor who gives feedback on the following questions and answers.
        First the questions are [질문] and the answers are [대답].
        The total number of questions must be as many as [개수] questions. 
        Look at the questions and answers above and give feedback.
        Additionally, if there is a mistake, please let me know that it is incorrect and the correct information, and if it needs to be supplemented, let me know what needs to be supplemented.
        Give me the answer in [언어].
        The result is JSON format "Feedback Number": "feedback content" Move it like this!!
        """.replace("[개수]", questions.length + "")
                .replace("[질문]", Arrays.toString(questions))
                .replace("[대답]", Arrays.toString(answers))
                .replace("[언어]", lang);
        log(prompt);

        //GPT API에 응답 호출
        ChatGPTResponse gptResponse = customBotController.chat(prompt);
        log(gptResponse.getChoices().get(0).getMessage().getContent());

        String s = gptResponse.getChoices().get(0).getMessage().getContent();

        //웹에 보내기 테스트
        JSONObject sendJson = new JSONObject(s);
        String[] feedbacks = new String[questions.length];

        for (int i = 1; i <= questions.length; i++) {
            String feedbackKey = "Feedback " + i;
            if (sendJson.has(feedbackKey)) {
                feedbacks[i - 1] = sendJson.getString(feedbackKey);
            } else {
                feedbacks[i - 1] = sendJson.getString(String.valueOf(i));  // i 값을 문자열로 변환하여 할당
            }
        }


        req.getSession().setAttribute("feedbacks", feedbacks);

        res.sendRedirect("/save/questions");
    }
}
