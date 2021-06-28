package com.algaworks.alganews.metrics.api.infra;

import com.algaworks.alganews.metrics.api.view.EditorTagRatioProjection;
import com.algaworks.alganews.metrics.api.view.EditorMonthlyEarningsProjection;
import com.algaworks.alganews.metrics.api.view.MonthlyRevenueExpenseProjection;
import lombok.AllArgsConstructor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
class MetricsDao {
	
	private final EntityManager entityManager;
	
	List<EditorMonthlyEarningsProjection> findEditorMonthlyEarningsUntilDate(Long editorId, YearMonth endsOn) {
		Query query = getNamedQuery("getMonthlyEarnings", EditorMonthlyEarningsProjection.class);
		query.setParameter("editorId", editorId);
		query.setParameter("endOn", endsOn.atDay(1));
		return (List<EditorMonthlyEarningsProjection>) query.getResultList();
	}
	
	List<EditorTagRatioProjection> findTop3TagsByEditorId(Long editorId) {
		Query query = getNamedQuery("getTop3TagsByEditorId", EditorTagRatioProjection.class);
		query.setParameter("editorId", editorId);
		return Collections.unmodifiableList((List<EditorTagRatioProjection>) query.getResultList());
	}
	
	List<MonthlyRevenueExpenseProjection> findMonthlyRevenuesAndExpenses(YearMonth endsOn) {
		Query query = getNamedQuery("getMonthlyRevenuesAndExpenses", MonthlyRevenueExpenseProjection.class);
		query.setParameter("endOn", endsOn.atDay(endsOn.lengthOfMonth()));
		return (List<MonthlyRevenueExpenseProjection>) query.getResultList();
	}
	
	private Query getNamedQuery(String namedQuery, Class resultClass) {
		return entityManager.createNamedQuery(namedQuery)
				.unwrap(org.hibernate.query.NativeQuery.class)
				.setResultTransformer(Transformers.aliasToBean(resultClass));
	}
}
