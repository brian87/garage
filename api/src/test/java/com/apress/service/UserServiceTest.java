package com.apress.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apress.domain.User;
import com.apress.dto.UserDTO;
import com.apress.repository.UserRepository;
import com.apress.service.defaulter.UserDefaulter;
import com.apress.service.mappers.UserMapper;
import com.apress.validation.UserValidator;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserMapper userMapper;
	@Mock
	private UserValidator userValidator;
	@Mock
	private UserDefaulter userDefaulter;

	@Test
	void shouldReturnAllUsers() {
		UserDTO userDTO = UserDTO.builder().id(1L).mobile("12345678").build();
		when(userMapper.toUserDTOs(any())).thenReturn(Arrays.asList(userDTO));

		Collection<UserDTO> userDTOs = service.findAll();

		assertThat(userDTOs.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnUserById() {
		UserDTO userDTO = UserDTO.builder().id(1L).mobile("12345678").build();
		when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
		when(userMapper.toUserDTO(any())).thenReturn(userDTO);

		Optional<UserDTO> returnedUserDTO = service.findById(1L);

		assertThat(returnedUserDTO.isPresent()).isTrue();
		assertThat(returnedUserDTO).isEqualTo(Optional.of(userDTO));
	}

	@Test
	void shouldSaveUser() {
		UserDTO userDTO = UserDTO.builder().id(1L).mobile("12345678").build();
		when(userMapper.toUserDTO(any())).thenReturn(userDTO);

		UserDTO returnedUserDTO = service.save(userDTO);

		assertThat(returnedUserDTO).isNotNull();
		verify(userRepository).save(any());
	}

	@Test
	void shouldDeleteUserById() {
		service.deleteById(1L);

		verify(userRepository).deleteById(1L);
	}

}
