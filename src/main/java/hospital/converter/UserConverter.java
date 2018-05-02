package hospital.converter;

import hospital.dto.UserDto;
import hospital.entity.User;

import java.util.List;

public interface UserConverter {
    User fromDto(UserDto userDto);
    UserDto toDto (User user);
    List<User> fromDto(List<UserDto> userDtos);
    List<UserDto> toDto(List<User> users);
}
