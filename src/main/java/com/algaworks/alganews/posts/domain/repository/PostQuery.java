package com.algaworks.alganews.posts.domain.repository;

import com.algaworks.alganews.posts.domain.model.Post;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PostQuery {
	
	List<Post> findAllByPaymentId(Long paymentId, Sort sort);
	
}
