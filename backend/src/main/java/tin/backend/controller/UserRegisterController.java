package tin.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tin.backend.model.User;
import tin.backend.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class UserRegisterController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        User registered = userService.register(user);
        return ResponseEntity.ok().body(registered);
    }
}