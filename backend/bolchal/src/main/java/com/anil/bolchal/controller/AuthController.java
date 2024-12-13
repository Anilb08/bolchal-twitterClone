package com.anil.bolchal.controller;

import com.anil.bolchal.config.JwtProvider;
import com.anil.bolchal.exception.UserException;
import com.anil.bolchal.model.Varification;
import com.anil.bolchal.repository.UserRepository;
import com.anil.bolchal.response.AuthResponse;
import com.anil.bolchal.service.CustomUserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anil.bolchal.model.User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsServiceImplementation cutomerUserDetails;

    // Signup API
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String fullname = user.getFullName();
        String birthDate = user.getBirthDate();

        // Check if email already exists
        User isEmailExist = userRepository.findByEmail(email);
        if (isEmailExist != null) {
            throw new UserException("Email is already used with another account");
        }

        // Create and save new user
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullname);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setBirthDate(birthDate);
        createdUser.setVarification(new Varification());

        User savedUser = userRepository.save(createdUser);

        // Authenticate and generate JWT token
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        // Return token in response
        AuthResponse res = new AuthResponse(token, true);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    // Signin API
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User user) {
        String username = user.getEmail();
        String password = user.getPassword();

        // Authenticate the user
        Authentication authentication = authenticate(username, password);

        // Generate JWT token
        String token = jwtProvider.generateToken(authentication);

        // Return token in response
        AuthResponse res = new AuthResponse(token, true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // Method to authenticate user
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = cutomerUserDetails.loadUserByUsername(username);

        // Check if user exists
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username...");
        }

        // Check if password matches
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        // Return authenticated token
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
