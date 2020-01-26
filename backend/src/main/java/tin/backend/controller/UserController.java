package tin.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tin.backend.model.RoleNames;
import tin.backend.model.User;
import tin.backend.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Secured({RoleNames.ROLE_ADMIN, RoleNames.ROLE_EMPLOYEE})
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    @Secured({RoleNames.ROLE_ADMIN, RoleNames.ROLE_EMPLOYEE})
    public ResponseEntity<User> findById(@PathVariable long userId) {
        return ResponseEntity.of(userService.findById(userId));
    }

    @GetMapping("/me")
    @Secured({RoleNames.ROLE_ADMIN, RoleNames.ROLE_EMPLOYEE, RoleNames.ROLE_CLIENT})
    public ResponseEntity<User> findMe(Authentication authentication) {
        String login = authentication.getName();
        return ResponseEntity.of(Optional.of(userService.findByLogin(login)));
    }

    @PostMapping("/me")
    @Secured({RoleNames.ROLE_ADMIN, RoleNames.ROLE_EMPLOYEE, RoleNames.ROLE_CLIENT})
    public ResponseEntity<User> updateMe(Authentication authentication, @RequestBody @Valid User user) {
        String login = authentication.getName();
        User updated = userService.updateByLogin(login, user);
        return ResponseEntity.of(Optional.of(updated));
    }

}