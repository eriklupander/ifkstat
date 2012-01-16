package se.ifkgoteborg.stat.ui.editor;

import java.io.Serializable;
import java.lang.reflect.Method;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.ui.form.PlayerForm;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public class PlayerEditor extends Window  {

	private final RegistrationDAO dao;
	private String caption;

	public PlayerEditor(Item player, RegistrationDAO dao) {
		
		this.dao = dao;
		
		PlayerForm form = new PlayerForm(dao, player);
		setWidth(700, UNITS_PIXELS);
		this.caption =  (String) player.getItemProperty("name").getValue();
		 // Set form caption and description texts 
		setCaption(caption);
		addComponent(form);		
	}

	/**
	 * @return the caption of the editor window
	 */
	private String buildCaption() {
		return String.format("%s", caption);
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
