package Controller;


import org.example.ristoranteprogetto.Model.Dto.UserDTO;
import Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    /**
     * Solo gli ADMIN possono vedere tutti gli utenti
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Un utente può vedere il proprio profilo, l'ADMIN può vedere qualsiasi profilo
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Un utente può aggiornare solo il proprio profilo, l'ADMIN può aggiornare chiunque
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        return userService.updateUser(id, dto);
    }

    /**
     * Solo l'ADMIN può cancellare un utente
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    /**
     * Solo l'ADMIN può creare nuovi utenti direttamente via API
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO createUser(@RequestBody UserDTO dto) {
        return userService.createUser(dto);
    }
}
