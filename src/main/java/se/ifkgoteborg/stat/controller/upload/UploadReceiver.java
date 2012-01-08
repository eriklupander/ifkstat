package se.ifkgoteborg.stat.controller.upload;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.vaadin.ui.Upload.Receiver;

public class UploadReceiver implements Receiver {

	private static final long serialVersionUID = -3688499851447366218L;
	private String fileName;
	private String mtype;
	private ByteArrayOutputStream uploadedFile = null;

	/**
	 * Callback method to begin receiving the upload.
	 */
	public OutputStream receiveUpload(String filename, String MIMEType) {
		this.fileName = filename;
		this.mtype = MIMEType;

		uploadedFile = new ByteArrayOutputStream();
		return uploadedFile;
	}

	/**
	 * Returns the filename
	 * 
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Returns the filetyp
	 * 
	 * @return
	 */
	public String getMimeType() {
		return mtype;
	}

	/**
	 * Returns the uploaded file as a byteArrayOutputStream
	 * 
	 * @return
	 */
	public ByteArrayOutputStream getUploadedFile() {
		return uploadedFile;
	}
}
