package com.algaworks.alganews.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {
	
	@Retention(RUNTIME)
	@Target(METHOD)
	@interface Public { }
	
	@PreAuthorize("isAuthenticated()")
	@Retention(RUNTIME)
	@Target(METHOD)
	@interface Authenticated { }

	@interface Users {
		
		@PreAuthorize("@algaSecurity.canListUsers()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanList { }
		
		@PreAuthorize("@algaSecurity.canGetUser(#userId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanGetOne { }
		
		@PreAuthorize("@algaSecurity.canCreateOrEditUser()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanCreateOrEdit { }
		
	}
	
	@interface Posts {
		
		@PreAuthorize("@algaSecurity.canCreatePost()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanCreate { }
		
		@PreAuthorize("@algaSecurity.canEditPost(#postId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanEdit { }
		
		@PreAuthorize("@algaSecurity.canDeletePost()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanDelete { }
		
		@PreAuthorize("@algaSecurity.canUnpublishPost()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanUnpublish { }
		
		@PreAuthorize("@algaSecurity.canPublishPost(#postId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanPublish { }
		
	}
	
	@interface Payroll {
		
		@PreAuthorize("@algaSecurity.canListPayment()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanList { }
		
		@PreAuthorize("@algaSecurity.canCreateOrEditPayment()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanCreateOrEdit { }
		
		@PreAuthorize("@algaSecurity.canApprovePayment()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanApprove { }
		
	}
	
	@interface CashFlow {
	
		@interface Entry {
			
			@PreAuthorize("@algaSecurity.canListCashFlow()")
			@Retention(RUNTIME)
			@Target(METHOD)
			@interface CanList { }
			
			@PreAuthorize("@algaSecurity.canCreateOrEditCashFlow()")
			@Retention(RUNTIME)
			@Target(METHOD)
			@interface CanCreateOrEdit { }
			
		}
		
		@interface Category {
			
			@PreAuthorize("@algaSecurity.canListCategory()")
			@Retention(RUNTIME)
			@Target(METHOD)
			@interface CanList { }
			
			@PreAuthorize("@algaSecurity.canCreateOrEditCategory()")
			@Retention(RUNTIME)
			@Target(METHOD)
			@interface CanCreateOrEdit { }
			
		}
	
	}
	
	@interface Metrics {
		
		@PreAuthorize("@algaSecurity.canGetAdministrativeMetrics()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanGetAdministrativeMetrics { }
		
		@PreAuthorize("@algaSecurity.canGetEditorMetrics()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanGetEditorMetrics { }
		
	}
	
	@interface UploadRequest {
		
		@PreAuthorize("@algaSecurity.canCreateUploadRequest()")
		@Retention(RUNTIME)
		@Target(METHOD)
		@interface CanCreate { }
		
	}
	
}
