package com.algaworks.alganews.clashflow.domain.model;

import java.time.OffsetDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.algaworks.alganews.users.domain.model.User;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntryCategory {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private boolean systemGenerated;
	
	@CreatedDate
	private OffsetDateTime createdAt;
	
	@LastModifiedDate
	private OffsetDateTime updatedAt;
	
	@CreatedBy
	@ManyToOne
	@JsonIgnore
	private User createdBy;
	
	@LastModifiedBy
	@ManyToOne
	@JsonIgnore
	private User updatedBy;
	
	@Enumerated(EnumType.STRING)
	private EntryType type;
	
	public boolean isNew() {
		return this.id == null;
	}
	
}
