package me.kertf22.music_backend.controllers;

import jakarta.validation.Valid;
import me.kertf22.music_backend.dtos.AuthenticationDTO;
import me.kertf22.music_backend.dtos.RegisterDTO;
//import me.kertf22.music_backend.infra.security.TokenService;
import me.kertf22.music_backend.exceptions.CustomException;
import me.kertf22.music_backend.infra.security.TokenService;
import me.kertf22.music_backend.domain.DomainUser;
import me.kertf22.music_backend.model.FollowModel;
import me.kertf22.music_backend.model.UserModel;
import me.kertf22.music_backend.repositories.FollowRepository;
import me.kertf22.music_backend.repositories.UserRepository;
import me.kertf22.music_backend.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserService userService;

    public UserController() {
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {

        userService.save(registerDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Object> profile(@PathVariable(name = "id") String id) {
        UserModel user = userService.getUserProfile(id);

        DomainUser domainUser = new DomainUser(user);

        return ResponseEntity.ok().body(domainUser);
    }
    @GetMapping("/me")
    public ResponseEntity<Object> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserModel user = userService.getUser(auth.getName());

        DomainUser domainUser = new DomainUser(user);

        return ResponseEntity.ok().body(domainUser);
    }

    @GetMapping("/follow/{id}")
    public ResponseEntity<Object> follow(@PathVariable(name = "id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String message = userService.follow(auth.getName(), id);
        return ResponseEntity.ok().body(message);
    }
}
