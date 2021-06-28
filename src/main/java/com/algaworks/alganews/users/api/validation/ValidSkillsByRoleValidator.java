package com.algaworks.alganews.users.api.validation;

import com.algaworks.alganews.users.api.model.SkillModel;
import com.algaworks.alganews.users.api.model.UserInput;
import com.algaworks.alganews.users.domain.model.Role;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ValidSkillsByRoleValidator
		implements ConstraintValidator<ValidSkillsByRole, UserInput> {
	
	private int uniqueSkillCount;
	private Role requiredRole;
	private String messageTemplate;
	
	@Override
	public final void initialize(final ValidSkillsByRole annotation) {
		this.uniqueSkillCount = annotation.uniqueSkillCount();
		this.requiredRole = annotation.role();
		this.messageTemplate = annotation.message();
	}
	
	public final boolean isValid(final UserInput user,
								 final ConstraintValidatorContext context) {
		
		((ConstraintValidatorContextImpl) context).addMessageParameter("uniqueSkillCount", uniqueSkillCount);
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode("skills")
				.addConstraintViolation();
		
		if (!requiredRole.equals(user.getRole())) {
			return true; //Ignora se o usuário não tem a role especifica.
		}
		
		List<SkillModel> skills = user.getSkills();
		
		if (skills == null || skills.isEmpty()){
			return false;
		}
		
		return skills.stream()
				.filter(skillModel -> StringUtils.isNotBlank(skillModel.getName()))
				.filter(distinctByKey(SkillModel::getName))
				.count() == uniqueSkillCount;
	}
	
	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}

