package com.apress.service.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;

import com.apress.domain.User;
import com.apress.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

	public UserDTO toUserDTO(User user);

	public User toUser(UserDTO userDTO);

	public Collection<UserDTO> toUserDTOs(Collection<User> users);

}
