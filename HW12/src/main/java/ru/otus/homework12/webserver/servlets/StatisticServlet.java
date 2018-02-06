package ru.otus.homework12.webserver.servlets;

import freemarker.template.Template;
import ru.otus.homework12.database.QueryGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class StatisticServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession(false);
        if(session != null) {
            QueryGenerator queryGenerator = QueryGenerator.instance();
            String generatorLauncher = req.getParameter("check");
            if (generatorLauncher != null) {
                if (generatorLauncher.equals("on")) {
                    if (!queryGenerator.isStarted()) {
                        new Thread(queryGenerator).start();
                    }
                }
            } else {
                queryGenerator.stop();
            }
            sendResponse(queryGenerator, resp);
        }else{
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("Please login first");
            out.println("<a href=/index.html> BACK TO MAIN </a>");
        }
    }

    private void sendResponse(QueryGenerator queryGenerator, HttpServletResponse resp) throws IOException{
        Map<String, Object> variables = new HashMap<>();
        variables.put("hits", queryGenerator.getCacheHit());
        variables.put("misses", queryGenerator.getCacheMiss());

        PrintWriter writer = resp.getWriter();
        writer.println(PageGenerator.instance().generatePage("statistic.html", variables));
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
