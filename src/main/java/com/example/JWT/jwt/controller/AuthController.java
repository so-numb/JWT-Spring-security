package com.example.JWT.jwt.controller;

import com.example.JWT.jwt.CustomException;
import com.example.JWT.jwt.model.User;
import com.example.JWT.jwt.model.request.LogInRequest;
import com.example.JWT.jwt.model.request.SignUpRequest;
import com.example.JWT.jwt.model.response.LogInResponse;
import com.example.JWT.jwt.repository.RolesRepository;
import com.example.JWT.jwt.repository.UserRepository;
import com.example.JWT.jwt.security.Impl.UserDetailsImpl;
import com.example.JWT.jwt.security.jwt.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RolesRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;


    @Autowired
    JwtHelper jwtHelper;

    @PostMapping("/log-In")
    public LogInResponse authenticateUser(@RequestBody LogInRequest loginRequest) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtHelper.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new CustomException("user", "not exists"));

        LogInResponse loginResponse = new LogInResponse(jwt, user);

        return loginResponse;
    }

    @PostMapping("/signup")
    public Object registerUser( @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getContact())) {
            return ResponseEntity.badRequest()
                    .body("Error: This contact number is already registered!");
        }

        User user = new User(signUpRequest.getContact(), signUpRequest.getContact(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getContact(), true, true);


        Set<String> roles = new HashSet<>();
        roles.add("USER");
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        savedUser.setActivated(true);

        userRepository.save(savedUser);

        return ResponseEntity.ok("User registered successfully!");
    }
}
