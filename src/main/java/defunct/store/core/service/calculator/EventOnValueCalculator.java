package defunct.store.core.service.calculator;

import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValuePolicy;

@StoreValueCalculatorTag(sectionCode = "onEvent")
public class EventOnValueCalculator implements StoreValueSectionCalculate {

	@Override
	public float calculate(Store store, StoreValuePolicy storeValuePolicy) {

		float ratio = Float.valueOf(storeValuePolicy.getRatio());
		boolean onEvent = store.getOnEvent();
		int score = getScore(onEvent);

		return score * (ratio / 100);
	}

	private int getScore(boolean onEvent) {
		if (onEvent) {
			return 3;
		} else {
			return 0;
		}
	}
}
