package defunct.store.core.service.dto;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.google.common.collect.Lists;

import lombok.Data;


@Data
public class RangeResult<T> {

	private final long total;

	private final List<T> data = Lists.newArrayList();

	private RangeResult(long total, List<T> result) {
		this.total = total;
		this.data.addAll(result);
	}
	
	
	public static <T> RangeResult<T> create(long total, List<T> items) {
		return new RangeResult<T>(total, items);
	}

	public static <T> RangeResult<T> create(List<T> items) {
		return new RangeResult<T>(items.size(), items);
	}

	public static <T> RangeResult<T> create(Page<T> page) {
		return new RangeResult<T>(page.getTotalElements(), page.getContent());
	}

	public static <I, O> RangeResult<O> create(Page<I> page, Function<I, O> converter) {
		List<O> converted = Lists.newArrayList();
		for (I each : page.getContent()) {
			converted.add(converter.apply(each));
		}
		return new RangeResult<O>(page.getTotalElements(), converted);
	}	
}