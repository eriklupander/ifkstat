package se.ifkgoteborg.stat.ui;

import java.io.UnsupportedEncodingException;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.controller.upload.UploadReceiver;
import se.ifkgoteborg.stat.importer.MasterImporter;
import se.ifkgoteborg.stat.importer.NotesImporter;

import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.VerticalLayout;

public class ImportView extends VerticalLayout {

	private Label result = new Label();

    private Upload upload = new Upload();
    private Upload upload2 = new Upload();
   
    private UploadReceiver receiver = new UploadReceiver();
    private UploadReceiver receiver2 = new UploadReceiver();
    
	private final RegistrationDAO dao;

    public ImportView(RegistrationDAO dao) {
    	this.dao = dao;
		upload.setReceiver(receiver);
		upload2.setReceiver(receiver2);

		addComponent(new Label("Säsongsfil"));
        addComponent(upload);
        addComponent(result);
        
        addComponent(new Label("Notfil"));
        addComponent(upload2);
        
        
        upload.addListener(new Upload.FinishedListener() {
            public void uploadFinished(FinishedEvent event) {
                
                String data = readFile();                
                new MasterImporter(ImportView.this.dao).importMasterFile(data);                                      
                result.setValue("Import file uploaded");
            }
        });  
        
        upload2.addListener(new Upload.FinishedListener() {
            public void uploadFinished(FinishedEvent event) {
                String data = readFile();                
                new NotesImporter(ImportView.this.dao).importNotes(data);                                      
                result.setValue("Notes file uploaded");
            }
        });   

    }


	private String readFile() {
		String file;
		try {
			file = new String(receiver2.getUploadedFile().toByteArray(), "UTF-8");
			return file;
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
