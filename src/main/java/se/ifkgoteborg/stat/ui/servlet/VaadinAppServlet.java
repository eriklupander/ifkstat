package se.ifkgoteborg.stat.ui.servlet;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import se.ifkgoteborg.stat.ui.StatApp;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.AbstractApplicationServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/*")
public class VaadinAppServlet extends AbstractApplicationServlet {

    @Inject
    StatApp application;

    @Override
    protected Class<? extends Application> getApplicationClass() throws ClassNotFoundException {
        return StatApp.class;
    }

    @Override
    protected Application getNewApplication(HttpServletRequest request) throws ServletException {
        return application;
    }
}
