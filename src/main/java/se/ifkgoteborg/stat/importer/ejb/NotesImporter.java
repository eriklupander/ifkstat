package se.ifkgoteborg.stat.importer.ejb;

import javax.ejb.Local;

@Local
public interface NotesImporter {

	void importNotes(String data);

}
