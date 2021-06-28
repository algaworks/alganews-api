package com.algaworks.alganews.clashflow.domain.model.projection;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntryCategorySummaryProjection {
	
	private Long id;
	private String name;
	private EntryType type;
	private boolean systemGenerated;
	private long totalEntries;
	
	public boolean getCanBeDeleted() {
		return !systemGenerated && totalEntries == 0;
	}
}
