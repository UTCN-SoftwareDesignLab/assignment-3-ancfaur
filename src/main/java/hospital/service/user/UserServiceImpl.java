package hospital.service.user;

import hospital.converter.UserConverter;
import hospital.dto.UserDto;
import hospital.entity.User;
import hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserConverter userConverter;
    //private final BCryptPasswordEncoder encoder =  new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter){
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }


    @Override
    public void update(UserDto userDto) {
        //userDto.password = encoder.encode(userDto.password);
        userRepository.save(userConverter.fromDto(userDto));
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElse(null);
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = userConverter.toDto(users);
        return userDtos;
    }

    @Override
    public UserDto register(UserDto userDto) {
        //userDto.password = encoder.encode(userDto.password);
        User user = userConverter.fromDto(userDto);
        User back = userRepository.save(user);
        userDto.setId(back.getId());
        return userDto;
    }

    // for testing purposes
    @Override
    public UserDto findById(Long id){
        User user =userRepository.findById(id).orElse(null);
        return userConverter.toDto(user);
    }

    @Override
    public void deleteAll(){
        userRepository.deleteAll();
    }

//    @Override
//    public boolean checkPasswords(String rawPass, String encodedPass){
//        return encoder.matches(rawPass, encodedPass);
//    }
}
