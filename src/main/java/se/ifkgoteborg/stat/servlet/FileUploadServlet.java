package se.ifkgoteborg.stat.servlet;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import se.ifkgoteborg.stat.controller.RegistrationDAO;

//@WebServlet(name = "FileUploadServlet", urlPatterns = { "/upload" })
//@MultipartConfig
public class FileUploadServlet extends HttpServlet {
	
	@Inject
	private RegistrationDAO dao;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// Create path components to save the file
		final String playerIdStr = request.getParameter("playerId");
		final Part filePart = request.getPart("file");
		//final String fileName = getFileName(filePart);

		InputStream filecontent = null;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {

			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				buffer.write(bytes, 0, read);
			}
			
			byte[] data = buffer.toByteArray();
			
			dao.savePlayerImage(Long.parseLong(playerIdStr), data);
		} catch (FileNotFoundException fne) {
			
		} finally {
			
			if (filecontent != null) {
				filecontent.close();
			}
			
		}
	}
}