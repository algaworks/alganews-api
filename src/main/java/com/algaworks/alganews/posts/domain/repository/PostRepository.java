package com.algaworks.alganews.posts.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.alganews.common.jpa.CustomJpaRepository;
import com.algaworks.alganews.posts.domain.model.Post;

@Repository
public interface PostRepository extends CustomJpaRepository<Post, Long>, JpaSpecificationExecutor<Post>, PostQuery {
	
	List<Post> findUnpaid(@Param("startsOn") LocalDate startsOn, @Param("endsOn") LocalDate endsOn,
						  @Param("editor") Long editor);
	
	boolean existsByIdAndEditorId(Long postId, Long editorId);
	
	Optional<Post> findByIdFetchTagsAndEditor(@Param("id") Long id);
	
}
