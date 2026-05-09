package com.example.springbootmusic.service;

import com.example.springbootmusic.model.dto.RegisterUserDTO;
import com.example.springbootmusic.model.entity.Role;
import com.example.springbootmusic.model.entity.RoleEnum;
import com.example.springbootmusic.model.entity.User;
import com.example.springbootmusic.repository.RoleRepository;
import com.example.springbootmusic.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthentificationService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User signup(RegisterUserDTO input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User()
                .setEmail(input.getEmail())
                .setUsername(input.getUsername())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User register(RegisterUserDTO dto, HttpServletRequest request) {
        User user = new User();

        if (userRepository.findUserByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findUserByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            try {
                new SaveUserImageService().saveUserImage(dto.getImage(), dto.getUsername());
                user.setImage(dto.getUsername() + ".jpg");
            } catch (Exception e) {
                throw new RuntimeException("Failed to save user image", e);
            }
        }

        Role roleUser = roleRepository.findByName(RoleEnum.USER).orElseThrow(()->new RuntimeException("User role not found"));
        user.setRole(roleUser);

        User savedUser = userRepository.save(user);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(savedUser, null, savedUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );


        return savedUser;

    }
}
