package com.algaworks.alganews.users.domain.view;

import java.math.BigDecimal;

public interface UserMetricsProjection {
	
	BigDecimal getWeeklyEarnings();
	BigDecimal getMonthlyEarnings();
	BigDecimal getLifetimeEarnings();
	
	int getWeeklyWords();
	int getMonthlyWords();
	int getLifetimeWords();

}
