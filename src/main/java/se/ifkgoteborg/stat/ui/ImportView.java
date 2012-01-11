package se.ifkgoteborg.stat.ui;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.controller.adapter.SquadPlayer;
import se.ifkgoteborg.stat.controller.upload.UploadReceiver;
import se.ifkgoteborg.stat.importer.GameImporter;
import se.ifkgoteborg.stat.importer.PlayerImporter;
import se.ifkgoteborg.stat.ui.control.ComboBoxFactory;
import se.ifkgoteborg.stat.util.StringUtil;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.AbstractSelect.Filtering;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.VerticalLayout;

public class ImportView extends VerticalLayout {

	private Label result = new Label();

    private Upload upload = new Upload();
   
    private UploadReceiver receiver = new UploadReceiver();

	private final RegistrationDAO dao;

//	private boolean seasonSet = false;
//	private boolean tournamentSet = false;
//
	//protected Integer season;
//	protected String tournamentName;

    public ImportView(RegistrationDAO dao) {
    	this.dao = dao;
		upload.setReceiver(receiver);

//		addComponent(new Label("Turnering"));
//		addComponent(getTournamentComboBox());
//		
//		addComponent(new Label("Säsong"));
//		addComponent(getSeasonComboBox());
		
		addComponent(new Label("Säsongsfil"));
        addComponent(upload);
        addComponent(result);
        
        
        upload.addListener(new Upload.FinishedListener() {
            public void uploadFinished(FinishedEvent event) {
                
                System.out.println("Uploaded bytes: " + receiver.getUploadedFile().size());

                String data = readFile();
                Integer year = Integer.parseInt(StringUtil.getLines(data, 0, 1).trim());
                String playerData = StringUtil.getLines(data, 1, 3);
                
                List<SquadPlayer> players = new PlayerImporter().importPlayers(playerData);
                ImportView.this.dao.importPlayers(players, year);
             
                String gamesData = StringUtil.getLines(data, 3);
                
                // Split by ####
                String[] tournaments = gamesData.split("####");
                for(String dataSet : tournaments) {
                	if(dataSet.trim().length() == 0) {
                		continue;
                	}
                	// First row tournament name
                	String tournamentNameAndStartingYear = StringUtil.getLines(dataSet, 0, 1);
                	String tournamentData = StringUtil.getLines(dataSet, 1);
                	
                	String [] parts = tournamentNameAndStartingYear.split("\t");
                	String tournamentName = parts[0];                	
                	Integer tournamentSeason = Integer.parseInt(parts[1]);
                	
                	new GameImporter(ImportView.this.dao).importSeason(tournamentData, tournamentSeason, ImportView.this.dao.loadSquad(year), tournamentName);          
                }
                
                      
                result.setValue("File uploaded");
            }
        });       

    }
        
    

//	private ComboBox getSeasonComboBox() {
//		ComboBox l = new ComboBoxFactory(dao).getSeasonComboBox();
//		
//        l.addListener(new ValueChangeListener() {
//			
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//				seasonSet = true;
//				season = Integer.parseInt( (String) event.getProperty().getValue());
//				upload.setEnabled(seasonSet && tournamentSet);
//			}
//		});
//		return l;
//	}
//
//	private ComboBox getTournamentComboBox() {
//		ComboBox l = new ComboBoxFactory(dao).getTournamentComboBox();
//        l.addListener(new ValueChangeListener() {
//			
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//				tournamentSet = true;
//				tournamentName = (String) event.getProperty().getValue();
//				upload.setEnabled(seasonSet && tournamentSet);
//			}
//		});
//        
//        return l;
//	}

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
