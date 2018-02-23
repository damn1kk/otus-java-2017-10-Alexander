package ru.otus.homework13.servlets;

import org.springframework.context.ApplicationContext;
import ru.otus.homework13.database.dataSet.PasswordDataSet;
import ru.otus.homework13.database.dbService.DBException;
import ru.otus.homework13.database.dbService.DBService;
import ru.otus.homework13.database.dbService.hibernateService.HibernatePasswordDBService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class SignInServlet extends HttpServlet {

    private DBService<PasswordDataSet, String> dbService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute("applicationContext");

        this.dbService = (DBService) ac.getBean("hibernatePasswordDBService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");

        try{
            PasswordDataSet user = dbService.findById(login);
            if(user == null){
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                resp.sendRedirect("./username_does_not_exist.html");
            }else{
                PrintWriter writer = resp.getWriter();
                if(user.getPassword() != null && user.getPassword().equals(password)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("name", login);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.sendRedirect("./statistic");
                }else{
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    writer.println("WRONG PASSWORD");
                }

            }
        }catch (DBException e){
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
