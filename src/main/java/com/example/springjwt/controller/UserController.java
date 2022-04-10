package com.example.springjwt.controller;

import com.example.springjwt.entity.User;
import com.example.springjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.net.URI;
import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username, Principal principal) {

        var user = userService.getUser(username, principal);

        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/users").toUriString());

        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, Principal principal) {

        userService.updateUser(user, principal);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<?> deleteUser(@Size(max = 64) @PathVariable("username") String username,
                                        Principal principal) {


        userService.deleteUser(username, principal);

        return ResponseEntity.ok().build();
    }


}