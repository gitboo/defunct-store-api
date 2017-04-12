package defunct.store.core.service.dto;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import defunct.store.core.error.IllegalValuePolicyException;
import lombok.Data;


@Data
public class StoreValuePolicySection {


	@Size(min = 4, max = 100) 
	List<PolicySection> sections = Lists.newArrayList();

	public String toJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(this);
	}

	@JsonIgnore
	public void checkValidStoreValuePolicy() {

		Map<String, Integer> sumRatiosByRootKey = Maps.newHashMap();
		
		for (PolicySection each : sections) {
			addVale(sumRatiosByRootKey, each.getRootSectionCode(), each.getRatio());
		}

		for (String each : sumRatiosByRootKey.keySet()) {
			if (sumRatiosByRootKey.get(each) != 100) {
				throw new IllegalValuePolicyException(each);
			}
		}
	}

	@JsonIgnore
	private Map<String, Integer> addVale(Map<String, Integer> section, String key, Integer value) {
		
		if (section.containsKey(key)) {
			Integer sum = section.get(key);
			section.put(key, sum += value);
		} else {
			section.put(key, value);
		}
		return section;
	}
}
