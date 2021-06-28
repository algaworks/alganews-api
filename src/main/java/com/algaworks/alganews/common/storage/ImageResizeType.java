package com.algaworks.alganews.common.storage;

import com.algaworks.alganews.common.api.model.ImageUrlsModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.algaworks.alganews.common.util.SystemEnvUtil.getEnvOrDefault;

@Getter
@AllArgsConstructor
public enum ImageResizeType {
	
	SMALL(getEnvOrDefault("SMALL_SIZE", 360)),
	MEDIUM(getEnvOrDefault("MEDIUM_SIZE", 720)),
	LARGE(getEnvOrDefault("LARGER_SIZE", 1080));
	
	private final int resizedHeight;
	
	public Path addFolderToPath(Path filePath) {
		return Paths.get(filePath.getParent().toString(),
				asFolder(), filePath.getFileName().toString());
	}
	
	public URI addPathToUrl(URI downloadUrl) {
		String hostName = getHostWithPortAndProtocol(downloadUrl);
		String imagePathWithVersion = addFolderToPath(Paths.get(downloadUrl.getPath())).toString();
		
		return UriComponentsBuilder.fromHttpUrl(hostName)
				.path(imagePathWithVersion)
				.build()
				.toUri();
	}
	
	public String asFolder() {
		return toString().toLowerCase();
	}
	
	public static ImageUrlsModel createImageVersionUrls(URI downloadUrl) {
		return ImageUrlsModel.builder()
				.defaultUrl(downloadUrl.toString())
				.small(ImageResizeType.SMALL.addPathToUrl(downloadUrl).toString())
				.medium(ImageResizeType.MEDIUM.addPathToUrl(downloadUrl).toString())
				.large(ImageResizeType.LARGE.addPathToUrl(downloadUrl).toString())
				.build();
	}
	
	private String getHostWithPortAndProtocol(URI uri) {
		return uri.toString().replace(uri.getPath(), "");
	}
}
