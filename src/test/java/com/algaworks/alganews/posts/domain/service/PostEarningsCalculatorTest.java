package com.algaworks.alganews.posts.domain.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.posts.domain.model.PostEarnings;
import com.algaworks.alganews.users.domain.model.User;

class PostEarningsCalculatorTest {
	
	private final PostEarningsCalculator calculator = new PostEarningsCalculator();
	
	@Test
	public void shouldCalculateTheCorrectEarning_1() {
		
		User editor = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.password(new BCryptPasswordEncoder().encode("#123456"))
				.pricePerWord(BigDecimal.ONE)
				.build();
		
		Post post = Post.builder()
				.editor(editor)
				.body("Teste ## post ### com  ||||||  |||||||  |||||||   `7` palavras ### abc ## abc")
				.build();
		
		PostEarnings postEarnings = calculator.calculate(post.getBody(), post.getEditor());
		
		Assertions.assertEquals(postEarnings.getTotalAmount(), new BigDecimal(7));
		
	}
	
	@Test
	public void shouldCalculateTheCorrectEarning_2() {
		
		User editor = User.builder()
				.id(1L)
				.active(true)
				.email("alganews@alganews.com")
				.createdAt(OffsetDateTime.now())
				.updatedAt(OffsetDateTime.now())
				.password(new BCryptPasswordEncoder().encode("#123456"))
				.pricePerWord(new BigDecimal("0.99"))
				.build();
		
		Post post = Post.builder()
				.editor(editor)
				.body("Teste ## post ### com `5` palavras ### abc ## abc")
				.build();
		
		PostEarnings postEarnings = calculator.calculate(post.getBody(), post.getEditor());
		
		Assertions.assertEquals(postEarnings.getTotalAmount(), new BigDecimal("6.93"));
		
	}
	
}