package se.ifkgoteborg.stat.ui.editor;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class CloseWindowListener implements ClickListener{

	private static final long serialVersionUID = 1L;
	private Window w;

	public CloseWindowListener(Window w) {
		this.w = w;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		w.getWindow().getParent().removeWindow(w.getWindow());
	}

}
