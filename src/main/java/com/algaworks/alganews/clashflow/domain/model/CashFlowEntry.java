package com.algaworks.alganews.clashflow.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CashFlowEntry {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate transactedOn;
	
	@Enumerated(EnumType.STRING)
	private EntryType type;
	
	private String description;
	
	private boolean systemGenerated;
	
	@ManyToOne
	private EntryCategory category;
	
	private BigDecimal amount;
	
	@CreatedDate
	private OffsetDateTime createdAt;
	
	@LastModifiedDate
	private OffsetDateTime updatedAt;
	
	@CreatedBy
	@ManyToOne
	private User createdBy;
	
	@LastModifiedBy
	@ManyToOne
	private User updatedBy;
	
	public boolean isNew() {
		return this.id == null;
	}
	
	public boolean isNotNew() {
		return !isNew();
	}
	
	public boolean getCanBeDeleted() {
		return !this.isSystemGenerated();
	}
	
	public boolean getCanBeEdited() {
		return !this.isSystemGenerated();
	}
	
}
