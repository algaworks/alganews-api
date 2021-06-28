package com.algaworks.alganews.metrics.api.infra;

import com.algaworks.alganews.metrics.api.view.MonthlyRevenueExpenseProjection;
import com.algaworks.alganews.metrics.api.service.MetricsService;
import com.algaworks.alganews.metrics.api.view.EditorMonthlyEarningsProjection;
import com.algaworks.alganews.metrics.api.view.EditorTagRatioProjection;
import com.algaworks.alganews.users.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class MetricsServiceImpl implements MetricsService {
	
	private final MetricsDao metricsDao;
	
	@Override
	public List<EditorTagRatioProjection> getTop3TagsByEditorId(Long editorId) {
		return metricsDao.findTop3TagsByEditorId(editorId);
	}
	
	@Override
	public List<MonthlyRevenueExpenseProjection> getMonthlyRevenuesAndExpenses(YearMonth endsOn) {
		List<MonthlyRevenueExpenseProjection> revenuesAndExpenses = metricsDao.findMonthlyRevenuesAndExpenses(endsOn);
		return addMissingRevenuesAndExpensesUntilDate(revenuesAndExpenses, endsOn);
	}
	
	private List<MonthlyRevenueExpenseProjection> addMissingRevenuesAndExpensesUntilDate(List<MonthlyRevenueExpenseProjection> revenuesAndExpenses,
																						 YearMonth endsOn) {
		YearMonth startOn = endsOn.minusMonths(11);
		Set<YearMonth> yearMonthsFromPeriod = getAllYearMonthsBetween(startOn, endsOn);
		revenuesAndExpenses = addMissingRevenuesAndExpensesByYearMonth(revenuesAndExpenses, yearMonthsFromPeriod);
		return revenuesAndExpenses;
	}
	
	private List<MonthlyRevenueExpenseProjection> addMissingRevenuesAndExpensesByYearMonth(List<MonthlyRevenueExpenseProjection> revenuesAndExpenses,
																						   Set<YearMonth> yearMonths) {
		Set<YearMonth> missingYearMonth = new HashSet<>(yearMonths);
		
		missingYearMonth.removeAll(revenuesAndExpenses.stream()
				.map(MonthlyRevenueExpenseProjection::getYearMonth)
				.collect(Collectors.toSet()));
		
		if (missingYearMonth.isEmpty())
			return new ArrayList<>(revenuesAndExpenses);
		
		List<MonthlyRevenueExpenseProjection> missingRevenuesAndExpenses = missingYearMonth.stream()
				.map(MonthlyRevenueExpenseProjection::of)
				.collect(Collectors.toList());
		
		return Stream.of(missingRevenuesAndExpenses, revenuesAndExpenses)
				.flatMap(Collection::stream)
				.sorted(Comparator.comparing(MonthlyRevenueExpenseProjection::getYearMonth))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<EditorMonthlyEarningsProjection> getEditorMonthlyEarningsUntilDate(User editor, YearMonth endDate) {
		List<EditorMonthlyEarningsProjection> earnings = metricsDao.findEditorMonthlyEarningsUntilDate(editor.getId(), endDate);
		earnings = addMissingEarningsUntilDate(earnings, endDate);
		if (isUserCreatedAtYear(editor, endDate.getYear()))
			earnings = removeEarningsBeforeUserCreationDate(editor, earnings);
		return earnings;
	}
	
	private List<EditorMonthlyEarningsProjection> addMissingEarningsUntilDate(List<EditorMonthlyEarningsProjection> earnings,
																			  YearMonth endsOn) {
		YearMonth startOn = endsOn.minusMonths(11);
		Set<YearMonth> yearMonthsFromPeriod = getAllYearMonthsBetween(startOn, endsOn);
		earnings = addMissingEarningsByYearMonth(earnings, yearMonthsFromPeriod);
		return earnings;
	}
	
	private List<EditorMonthlyEarningsProjection> removeEarningsBeforeUserCreationDate(User editor,
																					   List<EditorMonthlyEarningsProjection> earnings) {
		YearMonth createdOn = YearMonth.of(editor.getCreatedAt().getYear(), editor.getCreatedAt().getMonth());
		earnings = removeEarningsBefore(earnings, createdOn);
		return earnings;
	}
	
	private List<EditorMonthlyEarningsProjection> addMissingEarningsByYearMonth(List<EditorMonthlyEarningsProjection> earnings,
																				Set<YearMonth> yearMonths) {
		Set<YearMonth> missingYearMonth = new HashSet<>(yearMonths);
		missingYearMonth.removeAll(extractAllYearMonthFrom(earnings));
		if (missingYearMonth.isEmpty())
			return new ArrayList<>(earnings);
		List<EditorMonthlyEarningsProjection> missingEarnings = generateMissingProjectionsByYearMonth(missingYearMonth);
		return mergeAndSort(earnings, missingEarnings);
	}
	
	private Set<YearMonth> extractAllYearMonthFrom(List<EditorMonthlyEarningsProjection> earnings) {
		return earnings.stream()
				.map(EditorMonthlyEarningsProjection::getYearMonth)
				.collect(Collectors.toSet());
	}
	
	private List<EditorMonthlyEarningsProjection> generateMissingProjectionsByYearMonth(Set<YearMonth> missingYearMonth) {
		return missingYearMonth.stream()
			.map(EditorMonthlyEarningsProjection::of)
			.collect(Collectors.toList());
	}
	
	private List<EditorMonthlyEarningsProjection> mergeAndSort(List<EditorMonthlyEarningsProjection> earnings,
															   List<EditorMonthlyEarningsProjection> missingEarnings) {
		return Stream.of(missingEarnings, earnings)
				.flatMap(Collection::stream)
				.sorted(Comparator.comparing(EditorMonthlyEarningsProjection::getYearMonth))
				.collect(Collectors.toList());
	}
	//Receber uma lista de earnings, percorrer e adicionar apenas os faltantes, utilizar iterator
	private Set<YearMonth> getAllYearMonthsBetween(YearMonth startsOn, YearMonth endsOn) {
		YearMonth currentYearMonth = startsOn;
		Set<YearMonth> yearMonthsBetween = new LinkedHashSet<>();
		while (isBeforeOrEqual(currentYearMonth, endsOn)) {
			yearMonthsBetween.add(currentYearMonth);
			currentYearMonth = currentYearMonth.plusMonths(1);
		}
		return yearMonthsBetween;
	}
	
	private boolean isBeforeOrEqual(YearMonth currentYearMonth, YearMonth endsOn) {
		return currentYearMonth.isBefore(endsOn) || currentYearMonth.equals(endsOn);
	}
	
	private boolean isUserCreatedAtYear(User user, int year) {
		return user.getCreatedAt().getYear() == year;
	}
	
	private List<EditorMonthlyEarningsProjection> removeEarningsBefore(List<EditorMonthlyEarningsProjection> earnings,
																	   YearMonth starOn) {
		return earnings.stream()
				.filter(earning -> earning.getYearMonth().isAfter(starOn))
				.collect(Collectors.toList());
	}
	
	
}
