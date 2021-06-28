package com.algaworks.alganews.metrics.api.model;

import java.math.BigDecimal;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.algaworks.alganews.metrics.api.view.MonthlyRevenueExpenseProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
public class MonthlyRevenuesExpensesChartJsModel {
	
	private DataSet dataSetRevenues = new MonthlyRevenuesExpensesChartJsModel.DataSet("Receita");
	private DataSet dataSetExpenses = new MonthlyRevenuesExpensesChartJsModel.DataSet("Despesa");
	private List<String> labels = new ArrayList<>();
	
	public List<DataSet> getDatasets() {
		return Arrays.asList(dataSetExpenses, dataSetRevenues);
	}
	
	public List<String> getLabels() {
		return labels;
	}
	
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class DataSet {
		
		private String label;
		private List<BigDecimal> data = new ArrayList<>();
		public DataSet(String label) {
			this.label = label;
		}
		
	}
	
	public void addData(MonthlyRevenueExpenseProjection monthlyRevenuesExpenses) {
		getLabels().add(asLabel(monthlyRevenuesExpenses));
		dataSetExpenses.getData().add(monthlyRevenuesExpenses.getTotalExpenses());
		dataSetRevenues.getData().add(monthlyRevenuesExpenses.getTotalRevenues());
	}
	
	public String asLabel(MonthlyRevenueExpenseProjection monthlyRevenuesExpenses) {
		String localizedMonth = monthlyRevenuesExpenses.getMonth()
				.getDisplayName(TextStyle.SHORT_STANDALONE, new Locale("pt", "BR"));
		return  StringUtils.capitalize(localizedMonth) + " " + monthlyRevenuesExpenses.getYear();
	}

}
