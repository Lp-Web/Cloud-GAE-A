package fr.sebastien.leonard.bootstrap;

import com.googlecode.objectify.ObjectifyService;
import fr.sebastien.leonard.model.Book;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Bootstrap implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ObjectifyService.register(Book.class);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

}
