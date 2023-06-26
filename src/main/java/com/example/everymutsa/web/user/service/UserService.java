package com.example.everymutsa.web.user.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.everymutsa.api.file.dto.FileResponse;
import com.example.everymutsa.api.file.service.FileService;
import com.example.everymutsa.common.exception.AuthException;
import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.user.domain.User;
import com.example.everymutsa.web.user.dto.UserMapper;
import com.example.everymutsa.web.user.dto.UserResponse;
import com.example.everymutsa.web.user.dto.UserSaveRequest;
import com.example.everymutsa.web.user.dto.UserUpdateRequest;
import com.example.everymutsa.web.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final FileService fileService;
	private final UserMapper userMapper;

	public UserResponse save(UserSaveRequest userSaveRequest) {
		try {
			User user = userMapper.toEntity(userSaveRequest);
			FileResponse fileResponse = fileService.storeAsHash(userSaveRequest.getProfileImage());
			user.changeProfile(fileResponse.getFileName());
			return userMapper.toDto(userRepository.save(user));
		} catch (DataIntegrityViolationException de) {
			throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
		}
	}

	public User findById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
	}

	public User findByEmail(String email) {
		return userRepository.findByEmailOrThrow(email);
	}

	public UserResponse findOne(Long id) {
		return userMapper.toDto(findById(id));
	}

	@Transactional
	public UserResponse updateOne(String email, UserUpdateRequest updateRequest) {
		User foundUser = userRepository.findByEmailOrThrow(email);
		FileResponse fileResponse = fileService.storeAsHash(updateRequest.getProfileImage());
		String hashedName = fileResponse.getFileName();
		foundUser.updateInfo(updateRequest.getName(), updateRequest.getNickName(), hashedName);
		return userMapper.toDto(foundUser);
	}

	public void remove(String email) {
		if (userRepository.findByEmail(email).isEmpty()) {
			throw new EntityNotFoundException(ErrorCode.USER_NOT_FOUND);
		}
		userRepository.deleteByEmail(email);
	}
}
