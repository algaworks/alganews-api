package com.algaworks.alganews.metrics.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.alganews.metrics.api.view.MonthlyRevenueExpenseProjection;
import com.algaworks.alganews.metrics.api.model.MonthlyRevenuesExpensesModel;

@Component
@AllArgsConstructor
public class MonthlyRevenuesExpensesModelAssembler {
	
	private final ModelMapper modelMapper;
	
	public MonthlyRevenuesExpensesModel toModel(MonthlyRevenueExpenseProjection monthlyRevenuesExpenses) {
		return modelMapper.map(monthlyRevenuesExpenses, MonthlyRevenuesExpensesModel.class);
	}
	
	public List<MonthlyRevenuesExpensesModel> toModelCollection(List<MonthlyRevenueExpenseProjection> monthlyRevenuesExpensesList) {
		return monthlyRevenuesExpensesList.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
}
