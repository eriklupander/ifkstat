package se.ifkgoteborg.stat.ui.editor;

import java.io.Serializable;
import java.lang.reflect.Method;

import se.ifkgoteborg.stat.controller.RegistrationDAO;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Window;

public abstract class BaseEditor extends Window {

	private static final long serialVersionUID = 1L;
	
	protected RegistrationDAO dao;
	
	protected BaseEditor(RegistrationDAO dao) {
		this.dao = dao;
		setWidth(500, UNITS_PIXELS);
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
			System.out.println("INSIDE - EditorSavedEvent ");
		}

		public Item getSavedItem() {
			return savedItem;
		}
	}

	public interface EditorSavedListener extends Serializable {
		public void editorSaved(EditorSavedEvent event);
	}

}
