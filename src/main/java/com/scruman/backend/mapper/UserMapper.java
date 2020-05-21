package com.scruman.backend.mapper;

import com.scruman.backend.dto.UserDTO;
import com.scruman.backend.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    List<UserDTO> toDto(List<User> users);

    User toEntity(UserDTO userDTO);
}
