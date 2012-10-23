package se.ifkgoteborg.stat.importer.ejb;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface SeasonImporter {

	@TransactionAttribute(value=TransactionAttributeType.REQUIRES_NEW)
	void importSeason(String playerData, String season, String data);
}
