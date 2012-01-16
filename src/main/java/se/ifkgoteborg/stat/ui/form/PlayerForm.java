package se.ifkgoteborg.stat.ui.form;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Player;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.terminal.FileResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Window;

public class PlayerForm extends Form implements Receiver, Upload.SucceededListener {
	
	private static final File IMAGE_PATH = new File("c:\\data\\images\\");

	Collection<String> fields = new HashSet<String>();
	
	private Panel imagePanel;
	
	private final RegistrationDAO dao;
	private GridLayout ourLayout;

	private final Item playerItem;

	private Button saveButton;
	private Button saveAndCloseButton;
	private Button cancelButton;
	
	private Embedded image;

	private GridLayout gl;

	private Label noImageLabel;

	//private final Window window;

	public PlayerForm(RegistrationDAO dao, Item playerItem) {
		this.dao = dao;
		this.playerItem = playerItem;
		//this.window = window;
		
		fields.add("name");
		fields.add("fullName");
		fields.add("imageUrl");
		fields.add("length");
		fields.add("weight");
		fields.add("positionType");
		fields.add("dateOfBirth");
		fields.add("biography");
		fields.add("motherClub");
		fields.add("playedForClubs");
		fields.add("otherCompetitiveGames");
		fields.add("otherPracticeGames");
		
		// Create our layout (3x3 GridLayout)
		ourLayout = new GridLayout(3, 8);

		// Use top-left margin and spacing
		ourLayout.setMargin(true, false, false, true);
		ourLayout.setSpacing(true);

		setLayout(ourLayout);
		
		// Set up buffering
		setWriteThrough(false); // we want explicit 'apply'
		setInvalidCommitted(false); // no invalid values in datamodel

		// FieldFactory for customizing the fields and adding validators
		setFormFieldFactory(new PlayerFormFieldFactory(dao));
		setItemDataSource(playerItem); // bind to POJO via BeanItem

		// Determines which properties are shown, and in which order:
		setVisibleItemProperties(fields);
		saveButton = new Button("Spara");
	        saveButton.addListener(new Button.ClickListener() {

	            @Override
	            public void buttonClick(ClickEvent event) {
	                // Save...	            	
	            	commit();
	            	PlayerForm.this.dao.updatePlayer( (Player) ((BeanItem) getItemDataSource()).getBean());
	            }
	        });
	        
	     saveAndCloseButton = new Button("Spara och stäng");
	     saveAndCloseButton.addListener(new Button.ClickListener() {

	            @Override
	            public void buttonClick(ClickEvent event) {
	                // Save... and close	            	
	            	commit();
	            	PlayerForm.this.dao.updatePlayer( (Player) ((BeanItem) getItemDataSource()).getBean());
	            	getWindow().getParent().removeWindow(getWindow());
	            }
	        });
	     
	     cancelButton = new Button("Stäng");
	     cancelButton.addListener(new Button.ClickListener() {
	            @Override
	            public void buttonClick(ClickEvent event) {	 
	            	getWindow().getParent().removeWindow(getWindow());
	            }
	        });
	     
	    HorizontalLayout buttonList = new HorizontalLayout();
	    buttonList.addComponent(saveButton);
	    buttonList.addComponent(saveAndCloseButton);
	    
		ourLayout.addComponent(buttonList, 0, 6);
		ourLayout.addComponent(cancelButton, 2, 6);
		ourLayout.setComponentAlignment(cancelButton, Alignment.MIDDLE_RIGHT);
		imagePanel = new Panel("Spelarporträtt");
		gl = new GridLayout(1, 2);
		imagePanel.setLayout(gl);
		imagePanel.setWidth(230.0f, UNITS_PIXELS);
		imagePanel.setHeight(420.0f, UNITS_PIXELS);
		Upload upload = new Upload("", this);
        upload.setImmediate(true);
        upload.setButtonCaption("Välj bildfil");
        upload.addListener(this);
        
        gl.addComponent(upload, 0, 1);
        gl.setComponentAlignment(upload, Alignment.BOTTOM_RIGHT);
        ourLayout.addComponent(imagePanel, 2, 0, 2, 5);
	}
	
	@Override
	public void attach() {
		super.attach();
		String imageUrl = (String) playerItem.getItemProperty("imageUrl").getValue();
        System.out.println("Got imageUrl: " + imageUrl);
		if(imageUrl != null && imageUrl.trim().length() > 0) {
			image = new Embedded("", new FileResource(new File(IMAGE_PATH + "\\" + imageUrl), getApplication()));
			image.setWidth("200px");
			gl.addComponent(image, 0, 0);
			gl.setComponentAlignment(image, Alignment.TOP_CENTER);
		} else {
			noImageLabel = new Label("Ingen bild har laddats upp");
			gl.addComponent(noImageLabel
                         , 0, 0);
		}
	}

	/*
     * Override to get control over where fields are placed.
     */
    @Override
    protected void attachField(Object propertyId, Field field) {
        if (propertyId.equals("name")) {
            ourLayout.addComponent(field, 0, 0);
        } 
        else if (propertyId.equals("fullName")) {
            ourLayout.addComponent(field, 1, 0);
        } 
        else if (propertyId.equals("length")) {
            ourLayout.addComponent(field, 0, 1);
        } 
        else if (propertyId.equals("weight")) {
            ourLayout.addComponent(field, 1, 1);
        }
        else if (propertyId.equals("positionType")) {
            ourLayout.addComponent(field, 0, 2);
        }
        else if (propertyId.equals("dateOfBirth")) {
            ourLayout.addComponent(field, 1, 2);
        }
        else if (propertyId.equals("biography")) {
            ourLayout.addComponent(field, 0, 3, 1, 3);
        }
        else if (propertyId.equals("motherClub")) {
            ourLayout.addComponent(field, 0, 4);
        } 
        else if (propertyId.equals("playedForClubs")) {
            ourLayout.addComponent(field, 1, 4);
        }
        else if (propertyId.equals("otherCompetitiveGames")) {
            ourLayout.addComponent(field, 0, 5);
        } 
        else if (propertyId.equals("otherPracticeGames")) {
            ourLayout.addComponent(field, 1, 5);
        }
    }

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		FileOutputStream fos = null; // Output stream to write to
        File file = new File(IMAGE_PATH + "\\" + filename);
        try {
            // Open the file for writing.
            fos = new FileOutputStream(file);
            ((Player)((BeanItem<Player>) playerItem).getBean()).setImageUrl(filename);
        } catch (final java.io.FileNotFoundException e) {
            // Error while opening the file. Not reported here.
            e.printStackTrace();
            return null;
        }

        return fos; // Return the output stream to write to
	}
	
	// This is called if the upload is finished.
	@Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
    	if(image != null)
    		gl.removeComponent(image);
    	if(noImageLabel != null)
    		gl.removeComponent(noImageLabel);
    	System.out.println("In uploadSucceeded");
    	image = new Embedded("Spelarporträtt", new FileResource(new File(IMAGE_PATH + "\\" + event.getFilename()), getApplication()));
    	image.setWidth("220px");
    	gl.addComponent(image, 0, 0);
    }

}
