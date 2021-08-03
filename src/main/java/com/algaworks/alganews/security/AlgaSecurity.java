package com.algaworks.alganews.security;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.algaworks.alganews.payroll.domain.model.Payment;
import com.algaworks.alganews.users.domain.model.Role;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.posts.domain.repository.PostRepository;
import com.algaworks.alganews.users.domain.model.User;
import com.algaworks.alganews.users.domain.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AlgaSecurity {

	public static final String SCOPE_ALL_WRITE = "SCOPE_all:write";
	public static final String SCOPE_ALL_READ = "SCOPE_all:read";
	public static final String ROLE_MANAGER = "MANAGER";
	public static final String ROLE_ASSISTANT = "ASSISTANT";
	public static final String ROLE_EDITOR = "EDITOR";
	
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	
	public boolean canListUsers() {
		return hasAllReadScope() && hasAdminisrativeRoles();
	}
	
	public boolean canGetUser(Long userId) {
		return canListUsers() || (isAuthenticated() && isAuthenticatedUserEqual(userId));
	}
	
	public boolean canActivateUser(User user) {
		return canActivateUserOfRole(user.getRole())
				&& !user.isActive()
				&& !user.getId().equals(getAuthenticatedUserOrFail().getId());
	}
	
	public boolean canDeactivateUser(User user) {
		return canActivateUserOfRole(user.getRole())
				&& user.isActive()
				&& !user.getId().equals(getAuthenticatedUserOrFail().getId());
	}
	
	public boolean canCreateOrEditUser() {
		return hasAllWriteScope() && hasAdminisrativeRoles();
	}
	
	public boolean canActivateUserOfRole(Role role) {
		return this.getAuthenticatedUserOrFail().canActivateUserOfRole(role);
	}
	
	public boolean canSensitiveDataBeUpdated(User user) {
		if (Objects.equals(this.getUserId(), user.getId())) {
			return true;
		}
		
		User authenticatedUser = getAuthenticatedUserOrFail();
		
		return authenticatedUser.canChangeSensitiveFields(user);
	}
	
	public boolean canCreatePost() {
		return hasAllWriteScope() && hasEditorRole() && isAuthenticated();
	}
	
	public boolean canEditPost(Long postId) {
		return hasAllWriteScope() && hasEditorRole() && isPostOwnedByAuthenticatedUser(postId);
	}
	
	public boolean canEditPost(Post post) {
		return hasAllWriteScope() && hasEditorRole() && isPostOwnedByAuthenticatedUser(post);
	}
	
	public boolean canPublishPost(Long postId) {
		return hasAllWriteScope() && (hasAdminisrativeRoles() || isPostOwnedByAuthenticatedUser(postId));
	}
	
	public boolean canPublishPost(Post post) {
		if (post.isPublished()) {
			return false;
		}
		return hasAllWriteScope() && (hasAdminisrativeRoles() || isPostOwnedByAuthenticatedUser(post));
	}
	
	public boolean canUnpublishPost() {
		return hasAllWriteScope() && hasAdminisrativeRoles();
	}
	
	public boolean canPostBeUnpublished(Post post) {
		if (!post.isPublished()) {
			return false;
		}
		return hasAllWriteScope() && hasAdminisrativeRoles();
	}
	
	public boolean canDeletePost() {
		return hasAllWriteScope() && hasAdminisrativeRoles();
	}
	
	public boolean canPostBeDeleted(Post post) {
		return canDeletePost() && post.canDelete();
	}
	
	public boolean canViewPostEarnings(Post post) {
		return hasAdminisrativeRoles() || isPostOwnedByAuthenticatedUser(post);
	}
	
	public boolean isPostOwnedByAuthenticatedUser(Long postId) {
		return isAuthenticated() && getUserId() != null && postRepository.existsByIdAndEditorId(postId, getUserId());
	}
	
	public boolean isPostOwnedByAuthenticatedUser(Post post) {
		return isAuthenticated() && getUserId() != null && post.isOwnedBy(this.getAuthenticatedUserOrFail());
	}
	
	public boolean canListPayment() {
		return hasAllReadScope() && hasAdminisrativeRoles();
	}
	
	public boolean canCreateOrEditPayment() {
		return hasAllWriteScope() && hasAdminisrativeRoles();
	}
	
	public boolean canApprovePayment() {
		return hasAllWriteScope() && hasManagerRole();
	}

	public boolean canPaymentBeApproved(Payment payment) {
		return payment.getCanBeApproved() && canApprovePayment();
	}
	
	public boolean canListCashFlow() {
		return hasAllReadScope() && hasAdminisrativeRoles();
	}
	
	public boolean canCreateOrEditCashFlow() {
		return hasAllWriteScope() && hasAdminisrativeRoles();
	}
	
	public boolean canListCategory() {
		return hasAllReadScope() && hasAdminisrativeRoles();
	}
	
	public boolean canCreateOrEditCategory() {
		return hasAllWriteScope() && hasAdminisrativeRoles();
	}
	
	public boolean canGetAdministrativeMetrics() {
		return hasAllReadScope() && hasAdminisrativeRoles();
	}
	
	public boolean canGetEditorMetrics() {
		return hasAllReadScope() && isAuthenticated();
	}
	
	public boolean canCreateUploadRequest() {
		return hasAllWriteScope() && isAuthenticated();
	}
	
	@Nullable
	public abstract Long getUserId();
	
	public boolean isAuthenticated() {
		return getAuthentication().isAuthenticated();
	}
	
	public boolean isAuthenticatedUserEqual(Long id) {
		if (id == null) {
			return false;
		}
		return id.equals(getUserId());
	}

	protected Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	public boolean hasAuthority(String authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> authority.getAuthority().equals(authorityName));
	}
	
	public boolean hasAnyAuthority(String... authorityName) {
		return getAuthentication().getAuthorities().stream()
				.anyMatch(authority -> Arrays.asList(authorityName).contains(authority.getAuthority()));
	}
	
	public Optional<User> getAuthenticatedUser() {
		Long userId = getUserId();
		
		if (userId == null) {
			return Optional.empty();
		}
		
		return userRepository.findById(userId);
	}

	public User getAuthenticatedUserOrFail() {
		return getAuthenticatedUser().orElseThrow(()-> new AccessDeniedException("Usuário não autenticado"));
	}
	
	public boolean hasAdminisrativeRoles() {
		return hasManagerRole() || hasAssistantRole();
	}

	public boolean hasAssistantRole() {
		return hasAuthority(ROLE_ASSISTANT);
	}

	public boolean hasManagerRole() {
		return hasAuthority(ROLE_MANAGER);
	}

	public boolean hasEditorRole() {
		return hasAuthority(ROLE_EDITOR);
	}
	
	public boolean hasAllWriteScope() {
		return hasAuthority(SCOPE_ALL_WRITE);
	}
	
	public boolean hasAllReadScope() {
		return hasAuthority(SCOPE_ALL_READ);
	}
	
	public boolean canViewPost(Post post) {
		return post.isPublished() || hasAdminisrativeRoles() || isPostOwnedByAuthenticatedUser(post);
	}
	
	public boolean canNotViewPost(Post post) {
		return !canViewPost(post);
	}
}
