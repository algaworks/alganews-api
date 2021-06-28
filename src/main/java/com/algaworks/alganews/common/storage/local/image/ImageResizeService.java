package com.algaworks.alganews.common.storage.local.image;

import com.algaworks.alganews.common.storage.ImageResizeType;
import com.algaworks.alganews.common.storage.domain.StorageException;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ImageResizeService {
	
	public void createResizedVersions(Path localStoragePath) {
		BufferedImage originalBufferedImage = readImage(localStoragePath);
		for (ImageResizeType resizeType : ImageResizeType.values())
			resizeAndStoreImage(originalBufferedImage, resizeType, localStoragePath);
	}
	
	public void deleteResizedVersions(Path filePath) {
		List<Path> versionFilePaths = getAllResizedVersionFilePaths(filePath);
		versionFilePaths.forEach(this::deleteIfExists);
	}
	
	private static List<Path> getAllResizedVersionFilePaths(Path filePath) {
		return Arrays.stream(ImageResizeType.values())
				.map(type -> type.addFolderToPath(filePath))
				.collect(Collectors.toList());
	}
	
	private void resizeAndStoreImage(BufferedImage originalBufferedImage,
	                                 ImageResizeType resizeType, Path localStoragePath) {
		BufferedImage resizedImage = resize(originalBufferedImage, resizeType);
		Path storagePath = resizeType.addFolderToPath(localStoragePath);
		writeImageOnPath(storagePath, resizedImage);
	}
	
	private BufferedImage readImage(Path localStoragePath) {
		try {
			return ImageIO.read(localStoragePath.toFile());
		} catch (IOException e) {
			throw new StorageException(String.format("Não foi possível gerar o ler o arquivo %s.", localStoragePath), e);
		}
	}
	
	private void writeImageOnPath(Path localStoragePath, BufferedImage resizedImage) {
		createParentDirectories(localStoragePath);
		writeImage(localStoragePath, resizedImage);
	}
	
	private void createParentDirectories(Path localStoragePath){
		try {
			Files.createDirectories(localStoragePath.getParent());
		} catch (IOException e) {
			throw new StorageException(String.format("Não foi armazenar criar a pasta %s.", localStoragePath.getParent()), e);
		}
	}
	
	private void writeImage(Path localStoragePath, BufferedImage resizedImage) {
		try {
			ImageIO.write(resizedImage, getFormat(localStoragePath), localStoragePath.toFile());
		} catch (IOException e) {
			throw new StorageException(String.format("Não foi armazenar o arquivo %s.", localStoragePath), e);
		}
	}
	
	private void deleteIfExists(Path storagePath) {
		try {
			Files.deleteIfExists(storagePath);
		} catch (Exception e) {
			throw new StorageException(String.format("Não foi possível excluir o arquivo %s.", storagePath), e);
		}
	}
	
	public BufferedImage resize(BufferedImage originalImage, ImageResizeType resizeType) {
		int resizedWidth = calculateResizedWidth(originalImage, resizeType);
		
		Image resizeImage = originalImage.getScaledInstance(resizedWidth, resizeType.getResizedHeight(), Image.SCALE_SMOOTH);
		
		BufferedImage bufferedResizedImage = new BufferedImage(resizedWidth, resizeType.getResizedHeight(), BufferedImage.TYPE_INT_RGB);
		bufferedResizedImage.getGraphics().drawImage(resizeImage, 0, 0 , null);
		
		return bufferedResizedImage;
	}
	
	private int calculateResizedWidth(BufferedImage originalImage, ImageResizeType resizeType) {
		float heightResizedPercent = resizeType.getResizedHeight() / (float) originalImage.getHeight();
		return (int) (originalImage.getWidth() * heightResizedPercent);
	}
	
	private String getFormat(Path filePath) {
		String extension = FilenameUtils.getExtension(filePath.getFileName().toString());
		extension = extension.toLowerCase();
		
		if ("jpg".equals(extension))
			extension = "jpeg";
		
		return extension;
	}
	
}
