package com.gdula.foodieshub.service.mapper;

import com.gdula.foodieshub.model.User;
import com.gdula.foodieshub.service.dto.CreateUserDto;
import com.gdula.foodieshub.service.dto.UpdateUserDto;
import com.gdula.foodieshub.service.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDtoMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getLogin(), user.getName(), user.getSurname(), user.getPassword(),
                user.getAddress(), user.getCity(), user.getZip(), user.getMail(), user.getRecipes());
    }

    public User toModel(CreateUserDto dto) {
        String randomId = UUID.randomUUID().toString();

        return new User(randomId, dto.getLogin(), dto.getName(), dto.getSurname(), dto.getPassword(),
                dto.getAddress(), dto.getCity(), dto.getZip(), dto.getMail(), dto.getRecipes());
    }

    public UpdateUserDto toUpdateDto(UserDto userById) {
        return new UpdateUserDto(userById.getName(), userById.getSurname(), userById.getPassword(),
                userById.getAddress(), userById.getCity(), userById.getZip(), userById.getMail(), userById.getRecipes());
    }
}

