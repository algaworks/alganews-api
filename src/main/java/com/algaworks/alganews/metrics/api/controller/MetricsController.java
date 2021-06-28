package com.algaworks.alganews.metrics.api.controller;

import java.time.YearMonth;
import java.util.List;

import com.algaworks.alganews.metrics.api.assembler.EditorMonthlyEarningsModelAssembler;
import com.algaworks.alganews.metrics.api.model.EditorMonthlyEarningsModel;
import com.algaworks.alganews.metrics.api.service.MetricsService;
import com.algaworks.alganews.metrics.api.view.EditorMonthlyEarningsProjection;
import com.algaworks.alganews.security.AlgaSecurity;
import com.algaworks.alganews.users.domain.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.alganews.metrics.api.view.MonthlyRevenueExpenseProjection;
import com.algaworks.alganews.metrics.api.assembler.MonthlyRevenuesExpensesChartJsModelAssembler;
import com.algaworks.alganews.metrics.api.assembler.MonthlyRevenuesExpensesModelAssembler;
import com.algaworks.alganews.metrics.api.model.MonthlyRevenuesExpensesChartJsModel;
import com.algaworks.alganews.metrics.api.model.MonthlyRevenuesExpensesModel;
import com.algaworks.alganews.metrics.api.view.EditorTagRatioProjection;
import com.algaworks.alganews.security.CheckSecurity;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/metrics")
@AllArgsConstructor
public class MetricsController {
	
	private final MetricsService metricsService;
	private final AlgaSecurity algaSecurity;
	
	private final EditorMonthlyEarningsModelAssembler editorMonthlyEarningsModelAssembler;
	
	private final MonthlyRevenuesExpensesModelAssembler defaultAssembler;
	private final MonthlyRevenuesExpensesChartJsModelAssembler chartJsModelAssembler;
	
	@CheckSecurity.Metrics.CanGetAdministrativeMetrics
	@GetMapping(value = "/monthly-revenues-expenses")
	public List<MonthlyRevenuesExpensesModel> getMonthlyRevenuesExpensesAsJson(YearMonth yearMonth) {
		if (yearMonth == null)
			yearMonth = YearMonth.now();
		List<MonthlyRevenueExpenseProjection> revenuesAndExpenses = metricsService.getMonthlyRevenuesAndExpenses(yearMonth);
		return defaultAssembler.toModelCollection(revenuesAndExpenses);
	}

	@CheckSecurity.Metrics.CanGetAdministrativeMetrics
	@GetMapping(value = "/monthly-revenues-expenses", produces = "application/vnd.alganews.chartjs+json")
	public MonthlyRevenuesExpensesChartJsModel getMonthlyRevenuesExpensesAsChartjs(YearMonth yearMonth) {
		if (yearMonth == null)
			yearMonth = YearMonth.now();
		List<MonthlyRevenueExpenseProjection> revenuesAndExpenses = metricsService.getMonthlyRevenuesAndExpenses(yearMonth);
		return chartJsModelAssembler.toModel(revenuesAndExpenses);
	}
	
	@CheckSecurity.Metrics.CanGetEditorMetrics
	@GetMapping(value = "/editor/monthly-earnings")
	public List<EditorMonthlyEarningsModel> getEditorMonthlyEarnings(YearMonth yearMonth) {
		if (yearMonth == null)
			yearMonth = YearMonth.now();
		User authenticatedUser = algaSecurity.getAuthenticatedUserOrFail();
		List<EditorMonthlyEarningsProjection> earnings = metricsService.getEditorMonthlyEarningsUntilDate(authenticatedUser, yearMonth);
		return editorMonthlyEarningsModelAssembler.toModelCollection(earnings);
	}
	
	@CheckSecurity.Metrics.CanGetEditorMetrics
	@GetMapping(value = "/editor/top3-tags")
	public List<EditorTagRatioProjection> getEditorTop3Tags() {
		return metricsService.getTop3TagsByEditorId(algaSecurity.getUserId());
	}
	
}
