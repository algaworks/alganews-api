package com.algaworks.alganews.posts.domain.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.algaworks.alganews.common.domain.BusinessException;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "deleted = false")
@EntityListeners(AuditingEntityListener.class)
public class Post {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreatedBy
	@ManyToOne
	private User editor;
	
	private String title;
	
	private String slug;
	
	private String body;
	
	private String image;
	
	@Builder.Default
	@ElementCollection(targetClass = String.class)
	@CollectionTable(name = "tag", joinColumns = @JoinColumn(name = "post_id"))
	@Column(name="name")
	private List<String> tags = new ArrayList<>();
	
	@Embedded
	private PostEarnings earnings;
	
	@Builder.Default
	private boolean deleted = false;
	
	@Builder.Default
	private boolean published = false;
	
	@CreatedDate
	private OffsetDateTime createdAt;
	
	@LastModifiedDate
	private OffsetDateTime updatedAt;
	
	@LastModifiedBy
	@ManyToOne(fetch = FetchType.LAZY)
	private User updatedBy;
	
	private boolean paid;
	
	public boolean hasImage() {
		return this.image != null;
	}
	
	public boolean doesNotHaveImage() {
		return !hasImage();
	}
	
	public boolean isNew() {
		return this.id == null;
	}
	
	public void delete() {
		if (cannotDelete())
			throw new BusinessException("Não é possível excluir este post");
		
		this.setDeleted(true);
	}
	
	public boolean canDelete() {
		return isNotPaid();
	}
	
	public boolean cannotDelete() {
		return !canDelete();
	}
	
	public boolean isNotPaid() {
		return !isPaid();
	}
	
	public void publish() {
		this.published = true;
	}
	
	public void unpublish() {
		this.published = false;
	}
	
	public boolean isOwnedBy(User user) {
		return getEditor().equals(user);
	}
	
	public boolean isNotNew() {
		return !isNew();
	}
	
	public Path getImageAsPath() {
		if (image == null) {
			return null;
		}
		return Paths.get(image);
	}
}
