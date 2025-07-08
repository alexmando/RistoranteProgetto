package Service;

import Mapper.UserMapper;
import Model.Dto.UserDTO;
import Model.Entity.Role;
import Model.Entity.UserEntity;
import org.example.ristoranteprogetto.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    //dovrà avere l'oggetto repository di riferimento per poter effettuare query sulla tabella user
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Optional<UserEntity> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public void changePassword(Long userId, String newPassword) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        UserEntity user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        userRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }



    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

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

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity user = new UserEntity();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Aggiungi altri campi se necessario
        UserEntity savedUser = userRepository.save(user);
        return new UserDTO(savedUser.getEmail(), null); // restituisce DTO senza password
    }

    public UserDTO updateUser(Long id, UserDTO updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(updatedUser.getName());
                    existingUser.setEmail(updatedUser.getEmail());
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    existingUser.setRole(updatedUser.getRole());
                    return userMapper.toDto(userRepository.save(existingUser));
                })
                .orElse(null);
    }

}
