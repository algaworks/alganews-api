package com.algaworks.alganews.posts.domain.filter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostFilter {
	
	private Long editorId;
	private boolean showAll;
	
	public boolean showPublishedOnly() {
		return !showAll;
	}
	
}
