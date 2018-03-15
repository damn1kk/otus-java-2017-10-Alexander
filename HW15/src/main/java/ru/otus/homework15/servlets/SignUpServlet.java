package ru.otus.homework15.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.otus.homework15.database.dataSet.PasswordDataSet;
import ru.otus.homework15.database.dbService.DBException;
import ru.otus.homework15.database.dbService.DBService;
import ru.otus.homework15.database.dbService.hibernateService.HibernatePasswordDBService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class SignUpServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final String REGISTERED_PAGE_TEMPLATE = "registered_page.html";

    private HibernatePasswordDBService dbService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        WebApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.dbService = (HibernatePasswordDBService) ac.getBean("hibernatePasswordDBService");
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
                if (dbService.findPasswordByLogin(login) != null) {
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
