package se.ifkgoteborg.stat.ui.editor;

import java.io.Serializable;
import java.lang.reflect.Method;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.Game;
import se.ifkgoteborg.stat.model.TournamentSeason;
import se.ifkgoteborg.stat.ui.GameDetailsView;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

public class GameEditor extends Window  {

	private final RegistrationDAO dao;
	private String caption;
	//private final Game game;
	
	GameDetailsView gdw;
	
	private Button saveBtn;
	private Button cancelBtn;
	
	public GameEditor(Game game, RegistrationDAO dao, TournamentSeason ts) {		
		
		this.dao = dao;
		gdw = new GameDetailsView(game, dao, ts.getSeason().getId());
		
		setWidth(700, UNITS_PIXELS);
		 // Set form caption and description texts 
		if(game.getHomeTeam() != null && game.getAwayTeam() != null) {
			setCaption(game.getHomeTeam().getName() + " - " + game.getAwayTeam().getName());
		} else {
			setCaption("Ny match");
		}
		addComponent(gdw);
		addComponent(getButtons());
	}
	
	
	
	
	private Component getButtons() {
		HorizontalLayout hl = new HorizontalLayout();
		saveBtn = new Button("Spara och stäng");
		cancelBtn = new Button("Stäng");
		saveBtn.addListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                // Save...            		
            	Game g = gdw.getForm().getEntityForSave();      	
            	dao.updateGame(g);
            	getWindow().getParent().removeWindow(getWindow());
            }
        });
		
		cancelBtn.addListener(new CloseWindowListener(this));
		
		hl.addComponent(saveBtn);
		hl.addComponent(cancelBtn);
		return hl;
	}




	// Anyone actually use this stuff?
	public void addListener(EditorSavedListener listener) {
		try {
			Method method = EditorSavedListener.class.getDeclaredMethod(
					"editorSaved", new Class[] { EditorSavedEvent.class });
			addListener(EditorSavedEvent.class, listener, method);
		} catch (final java.lang.NoSuchMethodException e) {
			// This should never happen
			throw new java.lang.RuntimeException(
					"Internal error, editor saved method not found");
		}
	}

	public void removeListener(EditorSavedListener listener) {
		removeListener(EditorSavedEvent.class, listener);
	}

	public static class EditorSavedEvent extends Component.Event {

		private Item savedItem;

		public EditorSavedEvent(Component source, Item savedItem) {
			super(source);
			this.savedItem = savedItem;
		}

		public Item getSavedItem() {
			return savedItem;
		}
	}

	public interface EditorSavedListener extends Serializable {
		public void editorSaved(EditorSavedEvent event);
	}
}
