package com.rafee.residenthub.mapper;

import com.rafee.residenthub.dto.response.UserResponse;
import com.rafee.residenthub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "username")
    UserResponse toDTO(User user);

    List<UserResponse> toListDTO(List<User> userList);
}
