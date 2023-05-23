package web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String log4jConfigFile = sce.getServletContext().getRealPath("/") + "WEB-INF/classes/log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Do nothing
    }
}
