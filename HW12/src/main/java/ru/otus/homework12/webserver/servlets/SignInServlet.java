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

public class SignInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        DBService<PasswordDataSet, String> dbService = new HibernatePasswordDBService();
        try{
            PasswordDataSet user = dbService.findById(login);
            if(user == null){
                resp.sendRedirect("./username_does_not_exist.html");
            }else{
                PrintWriter writer = resp.getWriter();
                if(user.getPassword() != null && user.getPassword().equals(password)) {
                    resp.sendRedirect("./statistic");
                }else{
                    writer.println("WRONG PASSWORD");
                }

            }
        }catch (DBException e){
            e.printStackTrace();
        }
    }
}
