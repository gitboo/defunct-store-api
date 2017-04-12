package defunct.store.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.querydsl.jpa.JPQLQuery;

import defunct.store.core.model.QStore;
import defunct.store.core.model.QStoreValue;
import defunct.store.core.model.StoreValue;

public class StoreValueRepositoryImpl extends QueryDslRepositorySupport implements StoreValueRepositoryCustom {

	public StoreValueRepositoryImpl() {
		super(StoreValue.class);
	}

	@Override
	public Page<StoreValue> search(Pageable pageable) {

		QStoreValue qStoreValue = QStoreValue.storeValue;
		QStore qStore = QStore.store;

		
		 //1+N 이슈를 피하기 위해 fetch 조인한다.
		JPQLQuery<StoreValue> query = from(qStoreValue).innerJoin(qStoreValue.store, qStore).fetchJoin();

		List<StoreValue> storeValues = getQuerydsl().applyPagination(pageable, query).fetch();
		long totalCount = query.fetchCount();

		return new PageImpl<>(storeValues, pageable, totalCount);
	}
}
