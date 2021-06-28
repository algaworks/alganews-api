package com.algaworks.alganews.users.domain.service;

import com.algaworks.alganews.common.storage.config.StorageProperties;
import com.algaworks.alganews.common.storage.domain.ImageStorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.algaworks.alganews.users.domain.model.User;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class UserAvatarService {
	
	private final ImageStorageService imageStorageService;
	private final StorageProperties storageProperties;
	
	public void updateImage(User user) {
		if (user.hasAvatar()) {
			imageStorageService.makePermanent(generatePermanentStoragePath(user.getAvatar()));
		}
	}
	
	public void updateImage(User user, User existingUser) {
		if (avatarWasRemoved(user, existingUser)) {
			imageStorageService.removeFile(generatePermanentStoragePath(existingUser.getAvatar()));
			
		} else if (avatarWasChanged(user, existingUser)) {
			imageStorageService.removeFile(generatePermanentStoragePath(existingUser.getAvatar()));
			imageStorageService.makePermanent(generatePermanentStoragePath(user.getAvatar()));
			
		} else if (avatarWasAdded(user, existingUser)) {
			imageStorageService.makePermanent(generatePermanentStoragePath(user.getAvatar()));
		}
	}
	
	public URI getAvatarDownloadUrl(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return null;
		
		return URI.create(String.format("%s/%s/%s",
				imageStorageService.getDownloadUri(),
				storageProperties.getAvatarImageFolder(),
				fileName));
	}
	
	private Path generatePermanentStoragePath(String fileName) {
		if (fileName == null)
			return null;
		return Paths.get(String.format("%s/%s", storageProperties.getAvatarImageFolder(), fileName));
	}
	
	private boolean avatarWasAdded(User user, User existingUser) {
		return user.hasAvatar() && existingUser.doesNotHaveAvatar();
	}
	
	private boolean avatarWasRemoved(User user, User existingUser) {
		return user.doesNotHaveAvatar() && existingUser.hasAvatar();
	}
	
	private boolean avatarWasChanged(User user, User existingUser) {
		return user.hasAvatar() && existingUser.hasAvatar()
				&& !user.getAvatar().equals(existingUser.getAvatar());
	}
}
