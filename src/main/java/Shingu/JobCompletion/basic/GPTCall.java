package Shingu.JobCompletion.basic;

import Shingu.JobCompletion.controller.CustomBotController;
import Shingu.JobCompletion.dto.ChatGPTResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GPTCall", value = "/GPTCall")
public class GPTCall extends HttpServlet {
    @Autowired
    CustomBotController customBotController;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setStatus(HttpServletResponse.SC_OK);
        res.setHeader("Content-Type", "text/plain;charset=utf-8");
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // cache 무효화
        PrintWriter writer = res.getWriter();

        //받는 파라미터 변수에 저장
        String email = req.getParameter("email");
        String keyword = req.getParameter("keyword");
        Integer count = Integer.parseInt(req.getParameter("count"));
        String lang = req.getParameter("lang");
        String notQuestion = req.getParameter("notQuestion").isEmpty() ? "" : req.getParameter("notQuestion");
        Boolean diff = req.getParameterMap().containsKey("diff") && req.getParameter("diff").equals("true");

        if (keyword.isEmpty()) {
            HttpSession session = req.getSession();
            session.setAttribute("error", "키워드를 입력하세요");
            res.sendRedirect("basic/index.jsp");
            return;
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

        //임시 웹
        res.setContentType("text/html; charset=utf-8");
        writer.println("<h2> 질문 응답 </h2>");
        if (email != null) writer.println("<form method=\"post\" action=\"http://localhost:8080/save/questions\">");

        String s = gptResponse.getChoices().get(0).getMessage().getContent();

        //웹에 보내기 테스트
        JSONObject sendJson = new JSONObject(s);
        String[] sendQuestions = new String[count];
        for (int i = 1; i <= count; i++) sendQuestions[i - 1] = sendJson.getString("Question " + i);
        req.setAttribute("sendQuestions", sendQuestions);
        req.setAttribute("email", email);
        req.setAttribute("keyword", keyword);
        req.setAttribute("count", count);
        //여기에 페이지로 이동하는거

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            for (int i = 1; i <= count; i++) {
                writer.println("<h3> Question " + i + " : " + jsonObject.getString("Question " + i) + "</h3>");
                if (email != null) writer.println("<input type=\"text\" name=\"answers" + i + "\" size=\"10\"> <br>");
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        if (email != null) {
            writer.println("<input type=\"hidden\" name=\"email\" value=\"" + email + "\">");
            writer.println("<input type=\"hidden\" name=\"keyword\" value=\"" + keyword + "\">");
            for (int i = 1; i <= count; i++) writer.println("<input type=\"hidden\" name=\"question" + i + "\" value=\"" + jsonObject.getString("Question " + i) + "\">");
            writer.println("<input type=\"submit\" value=\"Submit\">");
            writer.println("</form>");
        }
    }
}
