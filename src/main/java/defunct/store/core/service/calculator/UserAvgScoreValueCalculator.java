package defunct.store.core.service.calculator;

import defunct.store.core.error.FailedStoreValueCalculateException;
import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValuePolicy;


@StoreValueCalculatorTag(sectionCode = "userAvgScore")
public class UserAvgScoreValueCalculator implements StoreValueSectionCalculate {

	@Override
	public float calculate(Store store, StoreValuePolicy storeValuePolicy) {

		float ratio = Float.valueOf(storeValuePolicy.getRatio());
		int score = getScore(store);
		
		return score * (ratio / 100);
	}
	
	private int getScore(Store store) {
		
		float userAvgScore = store.getUserAvgScore();
		if (userAvgScore > 9) {
			return 3;
		} else if (5 <= userAvgScore && userAvgScore <= 9) {
			return 2;
		} else if (userAvgScore < 5) {
			return 1;
		}
		throw new FailedStoreValueCalculateException("userAvgScore: " + store.getStoreId());
	}
}
