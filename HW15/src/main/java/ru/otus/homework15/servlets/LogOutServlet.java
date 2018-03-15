package ru.otus.homework15.servlets;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.otus.homework15.queryGenerator.QueryGeneratorImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LogOutServlet extends HttpServlet {

    private QueryGeneratorImpl queryGeneratorImpl;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        WebApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.queryGeneratorImpl = (QueryGeneratorImpl) ac.getBean("queryGeneratorImpl");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        queryGeneratorImpl.stop();
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession();
        session.invalidate();

        out.println("you are successfully logged out");
        out.println("<a href=/index.html>Go to main page</a>");
    }
}
