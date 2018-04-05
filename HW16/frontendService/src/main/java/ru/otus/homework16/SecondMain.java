package ru.otus.homework16;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class SecondMain {

    public static void main(String[] args) throws Exception{
        Server server = new Server(8080);

        ResourceHandler resourceHandler = new ResourceHandler();

        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[] {"index.html"});
        resourceHandler.setResourceBase(".");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, new DefaultHandler()});
        server.setHandler(handlers);

        server.start();
        server.join();
    }
}
