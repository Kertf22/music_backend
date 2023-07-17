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
        UserDetails existUserWithEmail = this.repository.findByEmail(registerDTO.email());

        if (existUserWithEmail != null) {
            throw new CustomException("Email / Username already used!!", HttpStatus.BAD_REQUEST);
        }


        UserDetails existUserWithUsername = this.repository.findByUsername(registerDTO.username());

        if (existUserWithUsername != null) {
            throw new CustomException("Email / Username already used!!", HttpStatus.BAD_REQUEST);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        UserModel user = new UserModel();

        BeanUtils.copyProperties(registerDTO, user);
        user.setPassword(encryptedPassword);
        user.setPoints(0);

        this.repository.save(user);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<Object> profile(@PathVariable(name = "id") String id) {
        Optional userO = repository.findById(id);

        if(userO.isEmpty()) {
            throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
        }

        UserModel user = (UserModel) userO.get();
        DomainUser domainUser = new DomainUser(user);
        return ResponseEntity.ok().body(domainUser);
    }
    @GetMapping("/me")
    public ResponseEntity<Object> me() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserModel user = repository.findByUsername(auth.getName());

        DomainUser domainUser = new DomainUser(user);

        return ResponseEntity.ok().body(domainUser);
    }

    @GetMapping("/follow/{id}")
    public ResponseEntity<Object> follow(@PathVariable(name = "id") String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserModel user = repository.findByUsername(auth.getName());
        Optional<UserModel> followedO = repository.findById(id);

        if(followedO.isEmpty()) {
            throw new CustomException("User does not exist!", HttpStatus.NOT_FOUND);
        }

        UserModel followed = followedO.get();

        Optional<?> followOptional = followRepository.findByFollowedIdAndFollowingId(followed.getId(), user.getId());
        if(followOptional.isPresent()) {
            FollowModel follow = (FollowModel) followOptional.get();
            followRepository.delete(follow);
            return ResponseEntity.ok().body("Unfollowed user " + followed.getArtist_name() + "!");
        }

        FollowModel follow = new FollowModel(followed, user,LocalDateTime.now());

        followRepository.save(follow);

        return ResponseEntity.ok().body( "Followed user " + followed.getArtist_name() + "!");
    }
}
