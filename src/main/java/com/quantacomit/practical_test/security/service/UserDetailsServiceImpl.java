package com.quantacomit.practical_test.security.service;

import com.quantacomit.practical_test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.quantacomit.practical_test.models.User;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        if (user.getIsActive() == 0){
            throw new DisabledException("User is Disabled");
        }
        return UserDetailsImpl.build(user);
    }

}
