package ru.otus.homework16;

import ru.otus.homework16.frontService.FrontendServiceMsgClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

public class MyServletContextListener implements ServletContextListener {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void contextInitialized(ServletContextEvent event){
        ServerContainer container = (ServerContainer) event.getServletContext().getAttribute(ServerContainer.class.getName());
        try{
            container.addEndpoint(FrontendServiceMsgClient.class);
        }catch(DeploymentException e){
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
