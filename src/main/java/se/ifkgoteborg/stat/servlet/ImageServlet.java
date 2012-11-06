package se.ifkgoteborg.stat.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.ifkgoteborg.stat.controller.RegistrationDAO;

public class ImageServlet extends HttpServlet {
	
	@Inject
	private RegistrationDAO dao;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String playerIdStr = request.getParameter("playerId");
		
	}

}
