package com.algaworks.alganews.common.storage.config;

import java.net.URI;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("alganews.storage")
public class StorageProperties {
	
	public enum Type {
		LOCAL, GCP
	}
	
	private Local local = new Local();
	private GCP gcp = new GCP();
	
	private Type type = Type.LOCAL;
	
	private String postImageFolder;
	private String postBodyImageFolder;
	private String avatarImageFolder;
	private String tempFolder;
	
	private Set<String> allowedExtensions;
	private Long fileLengthLimit;
	
	private String photosBucket;
	private String tempBucket;
	
	@Getter
	@Setter
	public class Local {
		
		private String storagePath;
		private URI downloadUrl;
		private URI uploadUrl;
		
	}
	
	@Getter
	@Setter
	public class GCP {
		
		private String storagePath;
		private URI downloadUrl;
		private URI uploadUrl;
		
	}
	
}
