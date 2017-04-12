package defunct.store.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValuePolicy;
import defunct.store.core.repository.StoreRepository;
import defunct.store.core.repository.StoreValuePolicyRepository;
import defunct.store.core.service.calculator.StoreValeCalculator;
import defunct.store.core.service.calculator.StoreValueCalculatorTag;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreCalcReflectionTest {

	private static final Logger log = LoggerFactory.getLogger(StoreCalcReflectionTest.class);

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreValeCalculator calc;

	@Autowired
	private StoreValuePolicyRepository storeValuePolicyRepository;

	
	@Test
	@Ignore
	@Transactional
	public void reflectionTest3() throws Exception {

		Store store = storeRepository.findOne(1l);
		List<StoreValuePolicy> policys = storeValuePolicyRepository.findAll();

		for (StoreValuePolicy each : policys) {
			if (each.getSectionCode().equals("viewCount")) {
				log.info("{}", calc.calculateSubSection(store, each));
			}
		}
	}

	@Test
	@Ignore
	public void reflectionTest2() throws Exception {
		Reflections reflections = new Reflections("defunct.store.core.service.calculator");
		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(StoreValueCalculatorTag.class);

		for (Class<?> class1 : annotated) {
			for (Annotation each : class1.getDeclaredAnnotations()) {
				if (each.annotationType() == StoreValueCalculatorTag.class) {
					StoreValueCalculatorTag tag = (StoreValueCalculatorTag) each;
					log.info("{}", tag.sectionCode());
				}
			}
			log.info("{}", class1.getName());
		}
	}

	@Test
	@Ignore
	public void reflectionTest() throws Exception {

		Reflections reflections = new Reflections("defunct.store.core.service.calculator",
				new MethodAnnotationsScanner());
		Set<Method> set = reflections.getMethodsAnnotatedWith(StoreValueCalculatorTag.class);
		for (Method method : set) {

			for (Annotation each : method.getDeclaredAnnotations()) {
				if (each.annotationType() == StoreValueCalculatorTag.class) {
					StoreValueCalculatorTag tag = (StoreValueCalculatorTag) each;
					log.info("{}", tag.sectionCode());
				}
			}
		}
	}
}
