package ru.otus.homework16;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import java.net.URI;
import java.net.URL;
import java.util.logging.Logger;

public class Main {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final static int DEFAULT_PORT = 9999;

    public static void main(String[] args) throws Exception {
        Server server;

        if(args.length != 0){
            int port = Integer.parseInt(args[0]);
            server = new Server(port);
        }else{
            server = new Server(DEFAULT_PORT);
        }

        ResourceHandler resourceHandler = new ResourceHandler();

        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase("./public_html");

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addEventListener(new MyServletContextListener());

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context, new DefaultHandler()});
        server.setHandler(handlers);
        WebSocketServerContainerInitializer.configureContext(context);

        server.start();
        server.join();
    }

}
