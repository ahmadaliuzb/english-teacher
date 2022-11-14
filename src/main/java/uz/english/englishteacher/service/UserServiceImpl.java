package uz.english.englishteacher.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.english.englishteacher.dto.UserDto;
import uz.english.englishteacher.entity.User;
import uz.english.englishteacher.exception.NotFoundException;
import uz.english.englishteacher.repository.UserRepository;
import uz.english.englishteacher.service.impl.UserService;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public User register(UserDto userDto) {

        User user = new User();
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return null;
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return null;
        }


        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());

        Random random = new Random();
        int randomNumber = random.nextInt() % 9000 + 1000;
        user.setVerificationCode(String.valueOf(randomNumber));
        user.setEnabled(false);

        User registeredUser = userRepository.save(user);
        emailSenderService.sendSimpleEmail(user.getEmail(),"English Teacher",String.valueOf(randomNumber));

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);
        return result.get();
    }

    @Override
    public User update(UUID id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException("User not found");
        }

        User updatingUser = optionalUser.get();

        if (userDto.getFirstName() != null) {
            updatingUser.setFirstName(userDto.getFirstName());
        }

        if (userDto.getLastName() != null) {
            updatingUser.setLastName(userDto.getLastName());
        }

        if (userDto.getUsername() != null) {
            updatingUser.setUsername(userDto.getUsername());
        }

        if (userDto.getPassword() != null) {
            updatingUser.setPassword(userDto.getPassword());
        }


        userRepository.save(updatingUser);

        return updatingUser;
    }

    @Override
    public User findById(UUID id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            throw new NotFoundException("User not found");
        }

//        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    @Override
    public User delete(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) return null;
        User user = optionalUser.get();
        userRepository.deleteById(id);
        return user;
    }


}
