package ru.otus.homework12.webserver.servlets;

import ru.otus.homework12.database.dataSet.PasswordDataSet;
import ru.otus.homework12.database.dbService.DBException;
import ru.otus.homework12.database.dbService.DBService;
import ru.otus.homework12.database.dbService.hibernateService.HibernatePasswordDBService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    private static final String REGISTERED_PAGE_TEMPLATE = "registered_page.html";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        DBService<PasswordDataSet, String> dbService = new HibernatePasswordDBService();
        try{
            if(dbService.findById(login) != null){
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.sendRedirect("./already_exists_username.html");
            }else{
                dbService.save(new PasswordDataSet(login, password));

                PrintWriter writer = resp.getWriter();
                Map<String, Object> variables = new HashMap<>();
                variables.put("login", login);
                writer.println(PageGenerator.instance().generatePage(REGISTERED_PAGE_TEMPLATE, variables));
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        }catch(DBException e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
