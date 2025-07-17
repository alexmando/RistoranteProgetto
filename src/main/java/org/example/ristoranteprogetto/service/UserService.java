package org.example.ristoranteprogetto.service;

import jakarta.transaction.Transactional;
import org.example.ristoranteprogetto.mapper.UserMapper;
import org.example.ristoranteprogetto.model.dto.UserDTO;
import org.example.ristoranteprogetto.model.entity.UserEntity;
import org.example.ristoranteprogetto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.example.ristoranteprogetto.model.entity.Role;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void changePassword(UUID userId, String newPassword) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        UserEntity user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        userRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }



    public UserDTO findById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public UserDTO save(UserDTO dto) {
        UserEntity entity = userMapper.toEntity(dto);
        return userMapper.toDto(userRepository.save(entity));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(userDTO.getEmail());
        user.setRuolo(userDTO.getRuolo() != null ? userDTO.getRuolo() : Role.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if(user.getVersion() == null) {
            user.setVersion(0L);
        }
        user.setUsername(userDTO.getUsername());
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }


    @Transactional
    public UserDTO updateUser(UUID id, UserDTO updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    existingUser.setRole(updatedUser.getRuolo());
                    existingUser.setUsername(updatedUser.getUsername());
                    return userMapper.toDto(userRepository.save(existingUser));
                })
                .orElse(null);
    }

}
