package com.apress.service.defaulter;

import org.springframework.stereotype.Component;

import com.apress.dto.UserDTO;

@Component
public class UserDefaulter {

	public void populateDefaults(UserDTO userDTO) {
		populateRole(userDTO);
	}

	public void populateRole(UserDTO userDTO) {
		if (userDTO.getRole().isEmpty()) {
		userDTO.setRole("customer");
		}
	}

}
