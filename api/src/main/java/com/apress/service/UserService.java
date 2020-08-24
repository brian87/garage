package com.apress.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apress.domain.User;
import com.apress.dto.UserDTO;
import com.apress.repository.UserRepository;
import com.apress.service.defaulter.UserDefaulter;
import com.apress.service.mappers.UserMapper;
import com.apress.validation.UserValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private UserDefaulter userDefaulter;

	public Collection<UserDTO> findAll() {
		Collection<User> users = userRepository.findAll();
		return userMapper.toUserDTOs(users);
	}

	public Optional<UserDTO> findById(long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return Optional.of(userMapper.toUserDTO(user.get()));
		}
		return Optional.empty();
	}

	public boolean existsById(Long id) {
		return userRepository.existsById(id);
	}

	@Transactional
	public UserDTO save(UserDTO userDTO) {
		userDefaulter.populateDefaults(userDTO);
		userValidator.validate(userDTO);
		if (userDTO.hasErrors()) {
			return userDTO;
		}
		User user = userRepository.save(userMapper.toUser(userDTO));
		return userMapper.toUserDTO(user);
	}

	@Transactional
	public void deleteById(Long userId) {
		userRepository.deleteById(userId);
	}

}