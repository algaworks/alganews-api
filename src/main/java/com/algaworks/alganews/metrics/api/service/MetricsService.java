package com.algaworks.alganews.metrics.api.service;

import com.algaworks.alganews.metrics.api.view.MonthlyRevenueExpenseProjection;
import com.algaworks.alganews.metrics.api.view.EditorMonthlyEarningsProjection;
import com.algaworks.alganews.metrics.api.view.EditorTagRatioProjection;
import com.algaworks.alganews.users.domain.model.User;

import java.time.YearMonth;
import java.util.List;

public interface MetricsService {
	
	List<EditorTagRatioProjection> getTop3TagsByEditorId(Long editorId);
	
	List<EditorMonthlyEarningsProjection> getEditorMonthlyEarningsUntilDate(User editor, YearMonth endsOn);
	
	List<MonthlyRevenueExpenseProjection> getMonthlyRevenuesAndExpenses(YearMonth endsOn);
	
}
