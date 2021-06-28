package com.algaworks.alganews.common.api.modelmapper;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import com.algaworks.alganews.common.storage.ImageResizeType;
import com.algaworks.alganews.posts.domain.service.PostHeaderImageService;
import com.algaworks.alganews.users.api.model.UserInput;
import com.algaworks.alganews.users.domain.service.UserAvatarService;
import com.algaworks.alganews.users.domain.view.UserMinimalProjection;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.alganews.posts.api.model.PostDetailedModel;
import com.algaworks.alganews.posts.api.model.PostSummaryModel;
import com.algaworks.alganews.posts.api.model.PostWithEarningsModel;
import com.algaworks.alganews.posts.domain.model.Post;
import com.algaworks.alganews.users.api.model.EditorDetailedModel;
import com.algaworks.alganews.users.api.model.EditorSummaryModel;
import com.algaworks.alganews.users.api.model.UserDetailedModel;
import com.algaworks.alganews.users.api.model.UserMinimalModel;
import com.algaworks.alganews.users.api.model.UserSummaryModel;
import com.algaworks.alganews.users.domain.model.User;

import lombok.AllArgsConstructor;

import java.net.URI;

import static com.algaworks.alganews.common.util.URIExtractor.extractFileName;

@Configuration
@AllArgsConstructor
public class ModelMapperConfig {
	
	private final PostHeaderImageService postHeaderImageService;
	private final UserAvatarService userAvatarService;
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		mapUserModels(modelMapper);
		mapPostsModels(modelMapper);
		
		return modelMapper;
	}
	
	private void mapUserModels(ModelMapper modelMapper) {
		Converter<String, ImageUrlsModel> toUserAvatarUrl = ctx -> getUserAvatarUrl(ctx.getSource());
		Converter<String, String> userAvatarUrlToFileName = ctx -> extractFileName(ctx.getSource());

		modelMapper.createTypeMap(User.class, UserMinimalModel.class)
			.addMappings(mapper -> mapper.using(toUserAvatarUrl)
					.map(User::getAvatar, UserMinimalModel::setAvatarUrls));
		
		modelMapper.createTypeMap(UserInput.class, User.class)
				.addMappings(mapper -> mapper.using(userAvatarUrlToFileName)
						.map(UserInput::getAvatarUrl, User::setAvatar));
		
		modelMapper.createTypeMap(UserMinimalProjection.class, UserMinimalModel.class)
				.addMappings(mapper -> mapper.using(toUserAvatarUrl)
						.map(UserMinimalProjection::getAvatar, UserMinimalModel::setAvatarUrls));

		modelMapper.createTypeMap(User.class, UserSummaryModel.class)
			.addMappings(mapper -> mapper.using(toUserAvatarUrl)
					.map(User::getAvatar, UserSummaryModel::setAvatarUrls));

		modelMapper.createTypeMap(User.class, UserDetailedModel.class)
			.addMappings(mapper -> mapper.using(toUserAvatarUrl)
					.map(User::getAvatar, UserDetailedModel::setAvatarUrls));
		
		modelMapper.createTypeMap(User.class, EditorDetailedModel.class)
			.addMappings(mapper -> mapper.using(toUserAvatarUrl)
					.map(User::getAvatar, EditorDetailedModel::setAvatarUrls));

		modelMapper.createTypeMap(User.class, EditorSummaryModel.class)
			.addMappings(mapper -> mapper.using(toUserAvatarUrl)
					.map(User::getAvatar, EditorSummaryModel::setAvatarUrls));
	}
	
	private void mapPostsModels(ModelMapper modelMapper) {
		
		Converter<String, ImageUrlsModel> toPostImageUrl = ctx -> getPostImageUrl(ctx.getSource());

		modelMapper.createTypeMap(Post.class, PostDetailedModel.class)
			.addMappings(mapper -> mapper.using(toPostImageUrl)
					.map(Post::getImage, PostDetailedModel::setImageUrls));
		
		modelMapper.createTypeMap(Post.class, PostSummaryModel.class)
			.addMappings(mapper -> mapper.using(toPostImageUrl)
					.map(Post::getImage, PostSummaryModel::setImageUrls));
		
		modelMapper.createTypeMap(Post.class, PostWithEarningsModel.class)
			.addMappings(mapper -> mapper.using(toPostImageUrl)
					.map(Post::getImage, PostWithEarningsModel::setImageUrls));
	}
	
	private ImageUrlsModel getUserAvatarUrl(String fileName) {
		if (StringUtils.isBlank(fileName))
			return new ImageUrlsModel();
		
		URI downloadUrl = userAvatarService.getAvatarDownloadUrl(fileName);
		
		return ImageResizeType.createImageVersionUrls(downloadUrl);
	}
	
	private ImageUrlsModel getPostImageUrl(String fileName) {
		if (StringUtils.isBlank(fileName))
			return new ImageUrlsModel();
		
		URI downloadUrl = postHeaderImageService.getPostImageDownloadUri(fileName);
		
		return ImageResizeType.createImageVersionUrls(downloadUrl);
	}
	
}
