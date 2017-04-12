package defunct.store.core.service.calculator;

import defunct.store.core.error.FailedStoreValueCalculateException;
import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValuePolicy;


@StoreValueCalculatorTag(sectionCode = "likeCount")
public class LikeCountValueCalculator implements StoreValueSectionCalculate {

	@Override
	public float calculate(Store store, StoreValuePolicy storeValuePolicy) {

		float ratio = Float.valueOf(storeValuePolicy.getRatio());
		long count = store.getLikeCount();
		int score = getScore(count);
		return score * (ratio / 100);
	}
	
	private int getScore(long count) {
		if (400 <= count) {
			return 3;
		} else if (110 <= count && count <= 399) {
			return 2;
		} else if (count <= 109) {
			return 1;
		}
		throw new FailedStoreValueCalculateException("likeCount");
	}
}
