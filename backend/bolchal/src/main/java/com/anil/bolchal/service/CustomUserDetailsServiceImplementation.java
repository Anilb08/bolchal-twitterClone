package com.anil.bolchal.service;

import com.anil.bolchal.model.User;
import com.anil.bolchal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user by email
        User user = userRepository.findByEmail(username);

        // Check if the user exists and is not using Google login
        if (user == null || user.isLogin_with_google()) {  // Use Lombok-generated getter
            throw new UsernameNotFoundException("Username not found with email: " + username);
        }

        // Create an empty authorities list (add roles if needed)
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Return a UserDetails instance
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
