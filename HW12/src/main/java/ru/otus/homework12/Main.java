package ru.otus.homework12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.homework12.webserver.servlets.LogOutServlet;
import ru.otus.homework12.webserver.servlets.SignInServlet;
import ru.otus.homework12.webserver.servlets.SignUpServlet;
import ru.otus.homework12.webserver.servlets.StatisticServlet;

public class Main {
    public static void main(String[] args) throws Exception{
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("./html");

        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.addServlet(SignInServlet.class, "/signin");
        servletHandler.addServlet(SignUpServlet.class, "/signup");
        servletHandler.addServlet(StatisticServlet.class, "/statistic");
        servletHandler.addServlet(LogOutServlet.class, "/logout");

        Server server = new Server(8090);
        HandlerList handlerList = new HandlerList(resourceHandler, servletHandler);
        server.setHandler(handlerList);

        server.start();
        server.join();
    }
}
