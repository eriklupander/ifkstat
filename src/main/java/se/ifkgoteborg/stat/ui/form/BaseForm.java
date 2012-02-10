package se.ifkgoteborg.stat.ui.form;

import java.util.Collection;
import java.util.HashSet;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.ui.editor.CloseWindowListener;

import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;

public abstract class BaseForm<T> extends Form {
	
	private static final long serialVersionUID = 1L;

	Collection<String> fields = new HashSet<String>();

	protected RegistrationDAO dao;

	protected BeanItem<T> item;
	
	protected Button saveBtn;
	protected Button cancelBtn;
	protected HorizontalLayout hl;
	
	protected BaseForm(RegistrationDAO dao, BeanItem<T> item) {
		this.dao = dao;
		this.item = item;		
	}

	protected void init() {
		hl = new HorizontalLayout();
		saveBtn = new Button("Spara");
		saveBtn.addListener(getSaveListener());
		cancelBtn = new Button("Avbryt");
		cancelBtn.addListener(new Button.ClickListener() {			
			@Override
			public void buttonClick(ClickEvent event) {
				getWindow().getParent().removeWindow(getWindow());
			}
		});
		hl.addComponent(saveBtn);
		hl.addComponent(cancelBtn);
		setWidth(500, UNITS_PIXELS);
		// Set up buffering
		setWriteThrough(false); // we want explicit 'apply'
		setInvalidCommitted(false); // no invalid values in datamodel
		
	}

	protected abstract ClickListener getSaveListener();
	
	public HorizontalLayout getButtons() {
		return hl;
	}
}
