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
import java.util.HashMap;

@WebServlet(name = "GPTCall", value = "/GPTCall")
public class GPTCall extends HttpServlet {
    @Autowired
    CustomBotController customBotController;

    private HashMap<String, Integer> ipMap = new HashMap<>();

    //00시 지나면 Map 초기화
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetIpMap() {
        ipMap.clear();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        String temp = req.getParameter("count");
        if (temp == null) {
            res.sendRedirect("basic/index.jsp");
            return;
        }
        //받는 파라미터 변수에 저장
        String keyword = req.getParameter("keyword");
        Integer count = Integer.parseInt(req.getParameter("count"));
        String lang = req.getParameter("lang");
        String notQuestion = req.getParameter("notQuestion").isEmpty() ? "" : req.getParameter("notQuestion");
        Boolean diff = req.getParameterMap().containsKey("diff") && req.getParameter("diff").equals("true");

        HttpSession session = req.getSession();
        if (keyword.isEmpty()) {
            session.setAttribute("mainErrorMsg", "키워드를 입력하지 않았습니다.");
            res.sendRedirect("basic/index.jsp");
            return;
        } else if (keyword.length() > 30) {
            session.setAttribute("mainErrorMsg", "키워드의 길이는 30 이하로 입력해주세요");
            res.sendRedirect("basic/index.jsp");
            return;
        } else if (notQuestion.length() > 100) {
            session.setAttribute("notQuestionsErrorMsg", "받지 않을 키워드 길이는 100 이하로 입력해주세요");
            res.sendRedirect("basic/index.jsp");
            return;
        }

        //IP
        String ipAddress = GetClientIP.getClientIP(req);
        Integer useCount = ipMap.containsKey(ipAddress) ? ipMap.get(ipAddress) : 0;
        if (useCount == 0) ipMap.put(ipAddress, useCount);

        if (useCount > 5) {
            System.out.println(ipAddress + "사용자가 5번 넘게 사용함");
        }


        //프롬프트에 받은 파라미터값 넣기
        String prompt = """
                GPT, You're an interviewer, I'm an interviewer. 
                All questions are provided in [언어]. 
                The types of questions are related to [키워드]. 
                Generate exactly [개수] [challenging] questions. 
                You don't answer the question.
                Do not write unnecessary sentences other than interview questions.
                [중복제거 앞 문장] [중복제거 질문] [중복제거 뒷 문장]
                The last question is to judge the person, not the knowledgeable information, 
                in the occupation associated with [키워드]. 
                The total number of questions must be as many as [개수] questions. 
                The result is JSON format "Question Number": "Content" Move it like this!!
                """
                .replace("[언어]", lang)
                .replace("[키워드]", keyword)
                .replace("[개수]", String.valueOf(count))
                .replace("[challenging]", diff ? "challenging" : "")
                .replace("[중복제거 앞 문장]", notQuestion.isEmpty() ? "" : "Ask questions so that they don't overlap with the following questions.")
                .replace("[중복제거 질문]", notQuestion)
                .replace("[중복제거 뒷 문장]", notQuestion.isEmpty() ? "" : "Generate new questions that do not overlap with the above.");


        log(prompt);

        //GPT API에 응답 호출
        ChatGPTResponse gptResponse = customBotController.chat(prompt);
        log(gptResponse.getChoices().get(0).getMessage().getContent());

        ipMap.put(ipAddress, ipMap.get(ipAddress) + 1);

        String s = gptResponse.getChoices().get(0).getMessage().getContent();

        //웹에 보내기 테스트
        JSONObject sendJson = new JSONObject(s);
        String[] questions = new String[count];
        String[] answers = new String[count];

        for (int i = 1; i <= count; i++) {
            String questionKey = "Question " + i;
            if (sendJson.has(questionKey)) {
                questions[i - 1] = sendJson.getString(questionKey);
            } else {
                questions[i - 1] = sendJson.getString(String.valueOf(i));  // i 값을 문자열로 변환하여 할당
            }
        }



        session.setAttribute("questions", questions);
        session.setAttribute("keyword", keyword);
        session.setAttribute("answers", answers);
        session.setAttribute("index", 0);
        session.setAttribute("lang", lang);

        res.sendRedirect("/basic/showQuestions.jsp");
    }
}
