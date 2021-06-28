package com.algaworks.alganews.users.api.model;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import com.algaworks.alganews.common.api.model.BankAccountModel;
import com.algaworks.alganews.common.api.model.LocationModel;
import com.algaworks.alganews.users.domain.model.Role;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDetailedModel {

	private Long id;
	private String name;
	private String email;
	private ImageUrlsModel avatarUrls;
	private String bio;
	private Role role;
	private LocalDate birthdate;
	private String phone;
	private String taxpayerId;
	private BigDecimal pricePerWord;
	private boolean active;
	private BankAccountModel bankAccount;
	private LocationModel location;
	private List<SkillModel> skills = new ArrayList<>();
	private UserMetricsModel metrics = new UserMetricsModel();
	private OffsetDateTime createdAt;
	private OffsetDateTime updatedAt;
	private UserMinimalModel updatedBy;
	private UserMinimalModel createdBy;
	private boolean canBeActivated;
	private boolean canBeDeactivated;
	private boolean canSensitiveDataBeUpdated;
	
}
