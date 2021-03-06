package ru.otus.homework13.servlets;

import org.springframework.context.ApplicationContext;
import ru.otus.homework13.database.QueryGenerator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LogOutServlet extends HttpServlet {

    private QueryGenerator queryGenerator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");

        this.queryGenerator = (QueryGenerator) ac.getBean("queryGenerator");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        queryGenerator.stop();
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession();
        session.invalidate();

        out.println("you are successfully logged out");
        out.println("<a href=/index.html>Go to main page</a>");
    }
}
