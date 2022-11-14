package uz.english.englishteacher.service.impl;


import uz.english.englishteacher.dto.UserDto;
import uz.english.englishteacher.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User register(UserDto userDto);

    List<User> getAll();

    User findByUsername(String username);

    User findById(UUID id);


    User delete(UUID id);


    User update(UUID id, UserDto userDto);

}
