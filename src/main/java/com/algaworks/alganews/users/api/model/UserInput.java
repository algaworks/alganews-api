package com.algaworks.alganews.users.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.algaworks.alganews.common.validation.EmailWithDomain;
import com.algaworks.alganews.common.validation.PositiveInteger;
import com.algaworks.alganews.users.api.validation.ValidPricePerWordPerByRole;
import com.algaworks.alganews.users.api.validation.ValidSkillsByRole;
import org.hibernate.validator.constraints.br.CPF;

import com.algaworks.alganews.common.api.model.BankAccountModel;
import com.algaworks.alganews.common.api.model.LocationModel;
import com.algaworks.alganews.users.domain.model.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ValidPricePerWordPerByRole
@ValidSkillsByRole
public class UserInput {

	@NotBlank
	@Size(max = 255)
	private String name;

	@NotBlank
	@Size(max = 255)
	@EmailWithDomain
	private String email;

	private String avatarUrl;
	
	@NotBlank
	@Size(max = 255)
	private String bio;
	
	@NotNull
	private Role role;
	
	@NotNull
	private LocalDate birthdate;
	
	@Size(max = 20)
	@PositiveInteger
	private String phone;
	
	@NotBlank
	@CPF
	private String taxpayerId;
	
	@Nullable
	@DecimalMin("0.00")
	private BigDecimal pricePerWord;
	
	@Valid
	@NotNull
	private BankAccountModel bankAccount;
	
	@Valid
	@NotNull
	private LocationModel location;
	
	@Valid
	private List<SkillModel> skills;
	
}
