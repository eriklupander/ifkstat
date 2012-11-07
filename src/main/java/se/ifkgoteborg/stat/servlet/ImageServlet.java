package se.ifkgoteborg.stat.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.PlayerImage;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private RegistrationDAO dao;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final String playerIdStr = request.getParameter("playerId");
		
		Long playerId = Long.parseLong(playerIdStr);
		
		PlayerImage pi = dao.getPlayerImage(playerId);
		
		if(pi != null) {
			response.getOutputStream().write(pi.getImageData());
			response.getOutputStream().flush();
		} else {
			System.err.println("No image available...");
		}
	}

}
