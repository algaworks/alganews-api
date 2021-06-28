package com.algaworks.alganews.posts.domain.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.algaworks.alganews.common.util.MarkdownAnalyser;
import com.algaworks.alganews.posts.domain.model.PostEarnings;
import com.algaworks.alganews.users.domain.model.User;

@Service
public class PostEarningsCalculator {
	
	public PostEarnings calculate(String postBody, User editor) {
		int wordCount = MarkdownAnalyser.countWordsIgnoringTags(postBody);
		BigDecimal pricePerWord = editor.getPricePerWord();
		
		BigDecimal totalAmount;
		if (pricePerWord == null || BigDecimal.ZERO.equals(pricePerWord)) {
			totalAmount = BigDecimal.ZERO;
		} else {
			totalAmount = pricePerWord.multiply(new BigDecimal(wordCount));
		}
		
		return PostEarnings.builder()
				.words(wordCount)
				.pricePerWord(pricePerWord)
				.totalAmount(totalAmount)
				.build();
	}
	
}
