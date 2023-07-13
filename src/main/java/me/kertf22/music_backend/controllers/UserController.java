package me.kertf22.music_backend.controllers;

import jakarta.validation.Valid;
import me.kertf22.music_backend.dtos.AuthenticationDTO;
import me.kertf22.music_backend.dtos.RegisterDTO;
//import me.kertf22.music_backend.infra.security.TokenService;
import me.kertf22.music_backend.model.UserModel;
import me.kertf22.music_backend.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repository;

//    private final TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
       var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());
       var auth = authenticationManager.authenticate(usernamePassword);
//       var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok().build();
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDTO) {
        UserDetails existUserWithEmail = this.repository.findByEmail(registerDTO.email());

        if(existUserWithEmail != null) {
            return ResponseEntity.badRequest().build();
        }

        UserDetails existUserWithUsername = this.repository.findByUsername(registerDTO.username());

        if(existUserWithUsername != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        UserModel user = new UserModel();
        BeanUtils.copyProperties(registerDTO, user);
        user.setPassword(encryptedPassword);
        user.setPoints(0);

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }
}
