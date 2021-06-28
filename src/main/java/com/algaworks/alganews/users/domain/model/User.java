package com.algaworks.alganews.users.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static com.algaworks.alganews.common.util.URIExtractor.extractFileName;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String email;
	
	private String avatar;
	
	private String bio;
	
	private String password;
	
	private LocalDate birthdate;
	
	private String phone;
	
	private String taxpayerId;
	
	private BigDecimal pricePerWord;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	private boolean active;
	
	@Builder.Default
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Skill> skills = new ArrayList<>();
	
	@Embedded
	private Location location;
	
	@Embedded
	private BankAccount bankAccount;
	
	private OffsetDateTime lastLogin;
	
	@CreatedDate
	private OffsetDateTime createdAt;

	@LastModifiedDate
	private OffsetDateTime updatedAt;

	@CreatedBy
	@ManyToOne(fetch = FetchType.LAZY)
	private User createdBy;

	@LastModifiedBy
	@ManyToOne(fetch = FetchType.LAZY)
	private User updatedBy;
	
	public void activate() {
		setActive(true);
	}
	
	public void deactivate() {
		setActive(false);
	}
	
	public boolean isNew() {
		return getId() == null;
	}
	
	public boolean isNotNew() {
		return !isNew();
	}
	
	public boolean hasAvatar() {
		return StringUtils.isNotEmpty(getAvatar());
	}
	
	public boolean doesNotHaveAvatar() {
		return !hasAvatar();
	}
	
	public boolean hasHigherOrEqualRole(Role role) {
		return this.getRole().isHigherOrEqual(role);
	}
	
	public boolean hasLowerRole(Role role) {
		return this.getRole().isLower(role);
	}
	
	public boolean canGrantRole(Role newRole, Role oldRoleToBeReplaced) {
		return (newRole.equals(oldRoleToBeReplaced))  && isNotEditor()
				|| hasHigherOrEqualRole(newRole) && isNotEditor();
	}
	
	public boolean cannotGrantRole(Role newRole, Role oldRoleToBeReplaced) {
		return !canGrantRole(newRole, oldRoleToBeReplaced);
	}
	
	public boolean isSensitiveFieldsNotEquals(User userToCompare) {
		return ObjectUtils.notEqual(getEmail(), userToCompare.getEmail())
				|| ObjectUtils.notEqual(getPhone(), userToCompare.getPhone())
				|| ObjectUtils.notEqual(getRole(), userToCompare.getRole());
	}
	
	public boolean canChangeSensitiveFields(User ofUser) {
		return ofUser.hasLowerRole(Role.MANAGER) || hasHigherOrEqualRole(ofUser.getRole());
	}
	
	public boolean cannotChangeSensitiveFieldsOf(User ofUser) {
		return !canChangeSensitiveFields(ofUser);
	}
	
	public boolean isSensitiveFieldsUpdateViolated(User updaterUser, User existingUser) {
		return isNotNew() && updaterUser.cannotChangeSensitiveFieldsOf(existingUser) 
				&& existingUser.isSensitiveFieldsNotEquals(this);
	}
	
	public boolean hasAlreadyOnboarded() {
		return getLastLogin() != null;
	}
	
	public boolean canActivateUserOfRole(Role role) {
		return hasHigherOrEqualRole(role) && isNotEditor();
	}
	
	public boolean cannotActivateUserOfRole(Role role) {
		return !canActivateUserOfRole(role);
	}
	
	private boolean isNotEditor() {
		return !Role.EDITOR.equals(this.getRole());
	}
	
	public boolean isNotActive() {
		return !this.isActive();
	}
}
