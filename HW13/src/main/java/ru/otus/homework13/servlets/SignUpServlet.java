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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SignUpServlet extends HttpServlet {

    private static final String REGISTERED_PAGE_TEMPLATE = "registered_page.html";
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

        if(login.length() == 0 || password.length() == 0){
            PrintWriter writer = resp.getWriter();
            writer.println("You entered empty login or empty password");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {

            try {
                if (dbService.findById(login) != null) {
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    resp.sendRedirect("./already_exists_username.html");
                } else {
                    dbService.save(new PasswordDataSet(login, password));

                    PrintWriter writer = resp.getWriter();
                    Map<String, Object> variables = new HashMap<>();
                    variables.put("login", login);
                    writer.println(PageGenerator.instance().generatePage(REGISTERED_PAGE_TEMPLATE, variables));
                    resp.setStatus(HttpServletResponse.SC_OK);
                }
            } catch (DBException e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }
    }
}
