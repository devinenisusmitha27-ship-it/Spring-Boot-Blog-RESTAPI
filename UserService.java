package com.example.blog.service;

import com.example.blog.dto.UserDto;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public UserDto createUser(UserDto dto) {
        User user = new User(dto.getUsername(), dto.getFullName(), dto.getEmail());
        User saved = userRepository.save(user);
        return toDto(saved);
    }

    public UserDto getUser(Long id) {
        return userRepository.findById(id).map(this::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public List<UserDto> listUsers() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        return toDto(userRepository.save(user));
    }

    public void deleteUser(Long id) { userRepository.deleteById(id); }

    private UserDto toDto(User u) {
        return new UserDto(u.getId(), u.getUsername(), u.getFullName(), u.getEmail());
    }
}
