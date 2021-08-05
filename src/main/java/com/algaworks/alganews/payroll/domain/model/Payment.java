package com.algaworks.alganews.payroll.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.alganews.common.domain.BusinessException;
import com.algaworks.alganews.payroll.domain.event.PaymentApprovedEvent;
import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.users.domain.model.BankAccount;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Payment extends AbstractAggregateRoot<Payment> {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Setter(AccessLevel.NONE)
	@Builder.Default
	private BigDecimal grandTotalAmount = BigDecimal.ZERO;
	
	@Embedded
	private PaymentEarnings earnings;
	
	@Embedded
	private BankAccount bankAccount;
	
	@Embedded
	private Period accountingPeriod;
	
	@ManyToOne
	private User payee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User approvedBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@CreatedBy
	private User createdBy;
	
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "payment_posts",
			joinColumns = @JoinColumn(name = "payment_id"),
			inverseJoinColumns = @JoinColumn(name = "post_id")
	)
	private List<Post> posts = new ArrayList<>();
	
	@Builder.Default
	@ElementCollection
	@CollectionTable(name = "payment_bonus", joinColumns = @JoinColumn(name = "payment_id"))
	private List<Bonus> bonuses = new ArrayList<>();
	
	private LocalDate scheduledTo;
	
	private OffsetDateTime approvedAt;
	
	@CreatedDate
	private OffsetDateTime createdAt;
	
	@LastModifiedDate
	private OffsetDateTime updatedAt;
	
	@ManyToOne
	@LastModifiedBy
	private User updatedBy;
	
	public boolean isApproved() {
		return this.approvedAt != null;
	}
	
	public boolean isNotApproved() {
		return !isApproved();
	}
	
	public void setPayee(User payee) {
		this.payee = payee;
		this.bankAccount = payee.getBankAccount();
	}
	
	public void calculateGrandTotalAmount() {
		this.earnings = new PaymentEarnings();
		this.grandTotalAmount = BigDecimal.ZERO;
		
		this.posts.forEach(this::sumPostEarnings);
		
		if (this.bonuses == null) {
			return;
		}
		
		BigDecimal bonusesTotalAmount = this.bonuses.stream()
				.filter(Objects::nonNull)
				.map(Bonus::getAmount)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		addToTotalAmount(bonusesTotalAmount);
	}
	
	public void approve(User approver) {
		if (this.isApproved()) {
			throw new BusinessException(String.format(
					"Não foi possível aprovar o pagamento de ID %s, já está aprovado.", this.getId()));
		}
		
		this.setApprovedBy(approver);
		this.setApprovedAt(OffsetDateTime.now());
		
		registerEvent(new PaymentApprovedEvent(this));
	}
	
	public boolean getCanBeDeleted() {
		return this.isNotApproved();
	}
	
	public boolean getCanBeApproved() {
		return !this.isApproved();
	}
	
	private void sumPostEarnings(Post post) {
		earnings.addPostEarnings(post.getEarnings());
		addToTotalAmount(post.getEarnings().getTotalAmount());
	}
	
	private void addToTotalAmount(BigDecimal totalAmount) {
		this.grandTotalAmount = this.grandTotalAmount.add(totalAmount);
	}
}
