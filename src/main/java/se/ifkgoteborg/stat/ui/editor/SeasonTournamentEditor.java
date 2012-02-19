package se.ifkgoteborg.stat.ui.editor;

import se.ifkgoteborg.stat.controller.RegistrationDAO;
import se.ifkgoteborg.stat.model.TournamentSeason;
import se.ifkgoteborg.stat.ui.form.SeasonTournamentForm;

import com.vaadin.data.util.AbstractContainer;
import com.vaadin.data.util.BeanItem;

public class SeasonTournamentEditor extends BaseEditor {
	public SeasonTournamentEditor(RegistrationDAO dao, BeanItem<TournamentSeason> item, AbstractContainer ac) {
		super(dao);
		
		SeasonTournamentForm form = new SeasonTournamentForm(dao, item, ac);
		
		setCaption("Välj turnering");
		
		addComponent(form);
		addComponent(form.getButtons());
	}

}
