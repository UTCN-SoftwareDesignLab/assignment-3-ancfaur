package hospital.service.user;

import hospital.dto.UserDto;

import java.util.List;

public interface UserService {
    void update(UserDto userDto);
    void delete(Long id);
    List<UserDto> findAll();
    UserDto register(UserDto userDto);

    // for testing purposes
    UserDto findById(Long id);
    void deleteAll();
    //boolean checkPasswords(String rawPass, String encodedPass);
}
