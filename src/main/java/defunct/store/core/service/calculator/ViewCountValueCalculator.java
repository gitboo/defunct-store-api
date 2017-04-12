package defunct.store.core.service.calculator;

import defunct.store.core.error.FailedStoreValueCalculateException;
import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValuePolicy;


@StoreValueCalculatorTag(sectionCode = "viewCount")
public class ViewCountValueCalculator implements StoreValueSectionCalculate {

	@Override
	public float calculate(Store store, StoreValuePolicy storeValuePolicy) {

		float ratio = Float.valueOf(storeValuePolicy.getRatio());
		long count = store.getViewCount();
		int score = getScore(count);
		return score * (ratio / 100);
	}
	
	private int getScore(long count) {
		if (1000 <= count) {
			return 3;
		} else if (200 <= count && count <= 999) {
			return 2;
		} else if (count <= 199) {
			return 1;
		}
		throw new FailedStoreValueCalculateException("viewCount");
	}
}
