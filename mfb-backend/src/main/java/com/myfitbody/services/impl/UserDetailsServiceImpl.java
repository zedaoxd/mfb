package com.myfitbody.services.impl;

import com.myfitbody.domain.exceptions.ResourceNotFoundException;
import com.myfitbody.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {
        return userRepository
                .findByEmailIgnoreCase(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
