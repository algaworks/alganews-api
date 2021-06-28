package com.algaworks.alganews.clashflow.domain.model.projection;

import com.algaworks.alganews.clashflow.domain.model.EntryType;
import com.algaworks.alganews.users.domain.model.User;
import com.algaworks.alganews.users.domain.view.UserMinimalProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EntryCategoryDetailedProjection {
	
	private Long id;
	private String name;
	private EntryType type;
	private boolean systemGenerated;
	private long totalEntries;
	
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	
	private UserMinimalProjection createdBy;
	private UserMinimalProjection updatedBy;
	
	public EntryCategoryDetailedProjection(Long id, String name, EntryType type, boolean systemGenerated,
										   long totalEntries, OffsetDateTime createdAt, OffsetDateTime updatedAt,
										   User createdBy, User updatedBy) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.systemGenerated = systemGenerated;
		this.totalEntries = totalEntries;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.createdBy = new UserMinimalProjection(createdBy);
		this.updatedBy = new UserMinimalProjection(updatedBy);
	}
	
	public boolean getCanBeDeleted() {
		return !systemGenerated && totalEntries == 0;
	}
}
