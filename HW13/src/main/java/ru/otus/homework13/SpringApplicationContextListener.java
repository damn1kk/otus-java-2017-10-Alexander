package ru.otus.homework13;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SpringApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

        sce.getServletContext().setAttribute("applicationContext", ac);
        System.out.println("CONTEXT INITIALIZED");
    }
}
