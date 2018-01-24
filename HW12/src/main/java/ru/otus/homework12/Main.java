package ru.otus.homework12;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.homework12.database.dataSet.AddressDataSet;
import ru.otus.homework12.database.dataSet.PhoneDataSet;
import ru.otus.homework12.database.dataSet.UserDataSet;
import ru.otus.homework12.database.dbService.DBException;
import ru.otus.homework12.database.dbService.DBService;
import ru.otus.homework12.database.dbService.hibernateService.HibernateCachedUserDBService;
import ru.otus.homework12.webserver.servlets.SignInServlet;
import ru.otus.homework12.webserver.servlets.SignUpServlet;
import ru.otus.homework12.webserver.servlets.StatisticServlet;

public class Main {
    public static void main(String[] args) throws Exception{
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("./html");

        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(SignInServlet.class, "/signin");
        servletHandler.addServlet(SignUpServlet.class, "/signup");
        servletHandler.addServlet(StatisticServlet.class, "/statistic");

        Server server = new Server(8090);
        HandlerList handlerList = new HandlerList(resourceHandler, servletHandler);
        server.setHandler(handlerList);

        server.start();
        server.join();
    }
}
