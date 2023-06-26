package com.example.everymutsa.api.file.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.everymutsa.api.file.dto.FileResponse;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.common.exception.FileProcessingException;
import com.example.everymutsa.common.exception.InvalidValueException;

@Service
public class FileService {
	private final Path root;

	public FileService(@Value("${file.upload-dir}") String uploadDir) {
		this.root = Paths.get(uploadDir);
		try {
			Files.createDirectories(root);
		} catch (IOException ie) {
			throw new FileProcessingException(ErrorCode.FILE_STORAGE_FAILED, "디렉토리 생성 실패");
		}
	}

	public FileResponse store(MultipartFile file) {
		String fileName = validateFileName(file.getOriginalFilename());
		validateFileSize(file.getSize());
		Path filePath = saveFile(file, fileName);
		return buildFileResponse(filePath, file.getSize(), file.getContentType());
	}

	private Path saveFile(MultipartFile file, String fileName) {
		Path targetLocation = root.resolve(fileName);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ie) {
			throw new FileProcessingException(ErrorCode.FILE_UPLOAD_FAILED);
		}
		return targetLocation;
	}

	public FileResponse storeAsHash(MultipartFile file) {
		try {
			validateFileSize(file.getSize());
			byte[] fileBytes = file.getBytes();

			String originalFileName = file.getOriginalFilename();
			validateFileName(originalFileName);
			String fileExtension = getFileExtention(originalFileName);
			String hashedName = hashFile(fileBytes);
			hashedName += fileExtension;
			Path filePath = saveFile(file, hashedName);
			return buildFileResponse(filePath, file.getSize(), file.getContentType());
		} catch (IOException ie) {
			throw new FileProcessingException(ErrorCode.FILE_STORAGE_FAILED, "파일명 : " + file.getOriginalFilename());
		}
	}

	public Resource load(String fileName) {
		Path filePath = root.resolve(fileName);
		Resource resource;
		try {
			resource = new UrlResource(filePath.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new FileProcessingException(ErrorCode.FILE_NOT_FOUND, "파일 명 : " + fileName);
		} catch (MalformedURLException ex) {
			throw new FileProcessingException(ErrorCode.FILE_NOT_FOUND, "파일 명 : " + fileName);
		}
	}

	private String getFileExtention(String originalFileName) {
		int dotIndex = originalFileName.lastIndexOf(".");
		return dotIndex < 0 ? "" : originalFileName.substring(dotIndex);
	}

	private FileResponse buildFileResponse(Path filePath, long fileSize, String contentType) {
		return FileResponse.builder()
			.fileName(filePath.getFileName().toString())
			.fileSize(fileSize)
			.contentType(contentType)
			.uploadTimeStamp(LocalDateTime.now())
			.build();
	}

	private String validateFileName(String originalFileName) {
		if (originalFileName == null || originalFileName.isEmpty()) {
			throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "잘못된 요청 값 입니다.");
		}
		return StringUtils.cleanPath(originalFileName);
	}

	private void validateFileSize(long fileSize) {
		if (fileSize == 0) {
			throw new FileProcessingException(ErrorCode.FILE_SIZE_ZERO);
		}
	}

	private String hashFile(byte[] fileData) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(fileData);
			return bytesToHex(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new FileProcessingException(ErrorCode.FILE_HASHING_FAILED);
		}
	}

	private String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(2 * bytes.length);
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
