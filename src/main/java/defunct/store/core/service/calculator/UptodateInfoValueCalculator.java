package defunct.store.core.service.calculator;

import java.util.Date;

import defunct.store.core.error.FailedStoreValueCalculateException;
import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValuePolicy;


@StoreValueCalculatorTag(sectionCode = "upToDateInfo")
public class UptodateInfoValueCalculator implements StoreValueSectionCalculate {

	@Override
	public float calculate(Store store, StoreValuePolicy storeValuePolicy) {

		float ratio = Float.valueOf(storeValuePolicy.getRatio());
		Date lastUpdateDate = store.getLastUpdateDate();
		int score = getScore(lastUpdateDate);
		
		return score * (ratio / 100);
	}
	
	private int getScore(Date lastUpdateDate) {
		Date now = new Date();
		long diff = now.getTime() - lastUpdateDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		
		if (diffDays <= 1) {
			return 3;
		} else if (1 < diffDays && diffDays <= 7) {
			return 2;
		} else if (diffDays > 7) {
			return 1;
		}
		throw new FailedStoreValueCalculateException("upToDateInfo");
	}
}
