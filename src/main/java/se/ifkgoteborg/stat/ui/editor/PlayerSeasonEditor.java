package se.ifkgoteborg.stat.ui.editor;

import java.io.Serializable;
import java.lang.reflect.Method;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.PlayedForClub;
import se.ifkgoteborg.stat.model.SquadSeason;
import se.ifkgoteborg.stat.ui.SeasonView;
import se.ifkgoteborg.stat.ui.form.PlayerSeasonForm;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class PlayerSeasonEditor extends Window {

	private final RegistrationDAO dao;
	private String caption;

	public PlayerSeasonEditor(PlayedForClub pfc, SquadSeason selectedSeason, RegistrationDAO dao, final SeasonView parent) {

		this.dao = dao;
		pfc.setSeason(selectedSeason);
		PlayerSeasonForm form = new PlayerSeasonForm(dao, pfc);
		setWidth(700, UNITS_PIXELS);
		if(pfc.getPlayer() != null) {
			this.caption =  (String) pfc.getPlayer().getName();
		} else {
			this.caption = "Lägg till spelare säsongen " + selectedSeason.getName();
		}
		
		 // Set form caption and description texts 
		setCaption(caption);
		addComponent(form);	
		
		addListener(new Window.CloseListener() {
			
			@Override
			public void windowClose(CloseEvent e) {
				parent.reloadTable();
			}
		});
	}

	
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