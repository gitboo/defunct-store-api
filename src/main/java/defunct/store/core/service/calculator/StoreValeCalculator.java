package defunct.store.core.service.calculator;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import defunct.store.core.error.FailedStoreValueCalculateException;
import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValuePolicy;
import defunct.store.core.model.type.SectionType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StoreValeCalculator {

	private static final String DEFAULT_CALCULATOR_PACKAGE = "defunct.store.core.service.calculator";

	// 산정 규칙이 구현된 클래스의 객체가 저장된 자료 구조이다.
	private Map<String, Object> calculatorBySectionCode;

	// 산정 규칙이 구현된 클래스의 패키지이다.
	@Value("${store_value.calculator.package}")
	private String calcPackage;
	

	

	@PostConstruct
	public void init() {
		if (calcPackage == null) {
			calcPackage = DEFAULT_CALCULATOR_PACKAGE;
		}
		initCalculators();
	}

	/*
	 * Reflections 을 사용하여 @StoreValueCalculatorTag 이 명명된 클래스를 찾아서 생성 한다. -
	 * Reflections 사용한 이유는 변경에 유연하게 하기 위해서 이다.
	 */
	private void initCalculators() {
		calculatorBySectionCode = Maps.newHashMap();
		Reflections reflections = new Reflections(calcPackage);
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(StoreValueCalculatorTag.class);

		for (Class<?> clazz : annotated) {
			for (Annotation each : clazz.getDeclaredAnnotations()) {
				if (each.annotationType() == StoreValueCalculatorTag.class) {
					StoreValueCalculatorTag tag = (StoreValueCalculatorTag) each;
					try {
						calculatorBySectionCode.put(tag.sectionCode(), clazz.newInstance());
						log.info("put calculate : {}", tag.sectionCode());

					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
						throw new FailedStoreValueCalculateException("init exception!");
					}
				}
			}
		}
	}
	
	
	public float calculate(Store store, List<StoreValuePolicy> policys) {
		
		Map<String, Float> rootSectionRatiosByCode = initStoverValueRatios(policys);
		Map<String, Float> storeValueSumsByCode = initStoverValueSums(policys);
		

		for (StoreValuePolicy storeValuePolicy : policys) {
			if (storeValuePolicy.getSectionType() != SectionType.SUB) {
				continue;
			}

			float value = calculateSubSection(store, storeValuePolicy);
			Float rootSum = storeValueSumsByCode.get(storeValuePolicy.getRootSectionCode());
			storeValueSumsByCode.put(storeValuePolicy.getRootSectionCode(), value + rootSum);
		}
		float sum = 0f;
		for (String rootCode : storeValueSumsByCode.keySet()) {
			float sumRoot = storeValueSumsByCode.get(rootCode);
			float ratio = rootSectionRatiosByCode.get(rootCode) / 100f;
			sum += sumRoot * ratio;
		}
		return sum;
	}

	/*
	 * -상점규칙의 계산은 Reflections의 Method 를 사용하여 계산된다. 
	 * -상점규칙의 코드(sectionCode)를 사용하여 Map에서 method 를 얻어 수행한후 가치점수를 반환한다.
	 */
	public float calculateSubSection(Store store, StoreValuePolicy storeValuePolicy) {

		Object object = calculatorBySectionCode.get(storeValuePolicy.getSectionCode());
		try {
			Method method = object.getClass().getMethod("calculate", Store.class, StoreValuePolicy.class);
			return (float) method.invoke(object, store, storeValuePolicy);

		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		throw new FailedStoreValueCalculateException(storeValuePolicy.getSectionCode() + "calc exception");
	}
	
	
	private Map<String, Float> initStoverValueRatios(List<StoreValuePolicy> policys) {
		Map<String, Float> rootSectionRatiosByCode = Maps.newHashMap();
		for (StoreValuePolicy each : policys) {
			if (each.getSectionType() == SectionType.ROOT) {				
				rootSectionRatiosByCode.put(each.getSectionCode(), Float.valueOf(each.getRatio()));
			}
		}
		return rootSectionRatiosByCode;
	}

	
	private Map<String, Float> initStoverValueSums(List<StoreValuePolicy> policys) {
		Map<String, Float> storeValueSumsByCode = Maps.newHashMap();
		for (StoreValuePolicy each : policys) {
			if (each.getSectionType() == SectionType.ROOT) {
				storeValueSumsByCode.put(each.getSectionCode(), 0f);
			}
		}
		return storeValueSumsByCode; 
	}
}