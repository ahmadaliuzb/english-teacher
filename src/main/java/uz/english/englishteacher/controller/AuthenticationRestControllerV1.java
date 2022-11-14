package uz.english.englishteacher.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.english.englishteacher.dto.*;
import uz.english.englishteacher.entity.User;
import uz.english.englishteacher.exception.NotFoundException;
import uz.english.englishteacher.jwt.JwtUtils;
import uz.english.englishteacher.repository.UserRepository;
import uz.english.englishteacher.service.EmailSenderService;
import uz.english.englishteacher.service.UserDetailsImpl;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestControllerV1 {

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticationRequestDto authenticationRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("Error: Username is already taken!", false));
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse("Error: Email is already in use!", false));
        }

        // Create new user's account
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        Random random = new Random();
        int randomNumber = Math.abs(random.nextInt()) % 9000 + 1000;
        user.setVerificationCode(String.valueOf(randomNumber));
        user.setEnabled(false);
        emailSenderService.sendSimpleEmail(user.getEmail(), "From English Teacher", String.valueOf(randomNumber));


        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponse(String.valueOf(randomNumber), true));
    }

    @PostMapping("/emailPermission")
    public ResponseEntity<?> permissionEmail(@RequestBody EmailDto emailDto) {
        Optional<User> optionalUser = userRepository.findByEmail(emailDto.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok("Success");
        }
        throw new NotFoundException();
    }
}