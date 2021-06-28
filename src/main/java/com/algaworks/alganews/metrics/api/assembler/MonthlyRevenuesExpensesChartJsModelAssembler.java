package com.algaworks.alganews.metrics.api.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.algaworks.alganews.metrics.api.view.MonthlyRevenueExpenseProjection;
import com.algaworks.alganews.metrics.api.model.MonthlyRevenuesExpensesChartJsModel;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MonthlyRevenuesExpensesChartJsModelAssembler {
	
	public MonthlyRevenuesExpensesChartJsModel toModel(List<MonthlyRevenueExpenseProjection> monthlyRevenuesExpensesList) {
		MonthlyRevenuesExpensesChartJsModel model = new MonthlyRevenuesExpensesChartJsModel();
		for (MonthlyRevenueExpenseProjection monthlyRevenuesExpenses : monthlyRevenuesExpensesList)
			model.addData(monthlyRevenuesExpenses);
		return model;
	}
	
}
