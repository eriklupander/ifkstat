package se.ifkgoteborg.stat.ui;

import java.io.UnsupportedEncodingException;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.controller.upload.UploadReceiver;
import se.ifkgoteborg.stat.importer.MasterImporter;

import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.VerticalLayout;

public class ImportView extends VerticalLayout {

	private Label result = new Label();

    private Upload upload = new Upload();
   
    private UploadReceiver receiver = new UploadReceiver();

	private final RegistrationDAO dao;

    public ImportView(RegistrationDAO dao) {
    	this.dao = dao;
		upload.setReceiver(receiver);

		addComponent(new Label("Säsongsfil"));
        addComponent(upload);
        addComponent(result);
        
        
        upload.addListener(new Upload.FinishedListener() {
            public void uploadFinished(FinishedEvent event) {
                
                System.out.println("Uploaded bytes: " + receiver.getUploadedFile().size());

                String data = readFile();
                
                new MasterImporter(ImportView.this.dao).importMasterFile(data);
                                      
                result.setValue("File uploaded");
            }
        });       

    }


	private String readFile() {
		String file;
		try {
			file = new String(receiver.getUploadedFile().toByteArray(), "UTF-8");
			return file;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
