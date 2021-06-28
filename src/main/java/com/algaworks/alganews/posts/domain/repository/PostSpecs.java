package com.algaworks.alganews.posts.domain.repository;

import com.algaworks.alganews.posts.domain.filter.PostFilter;
import com.algaworks.alganews.posts.domain.model.Post;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PostSpecs {
	
	private PostSpecs() {
	
	}

	public static Specification<Post> usingFilter(PostFilter filter) {
		return (root, query, builder) -> {
			if (Post.class.equals(query.getResultType()))
				fetchEditorAndTags(root);
			
			List<Predicate> predicates = new ArrayList<>();
			
			if (filter.getEditorId() != null)
				predicates.add(builder.equal(root.get("editor"), filter.getEditorId()));
			
			if (filter.showPublishedOnly())
				predicates.add(builder.isTrue(root.get("published")));
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
	private static void fetchEditorAndTags(Root<Post> root) {
		root.fetch("tags");
		root.fetch("editor");
	}
	
}
