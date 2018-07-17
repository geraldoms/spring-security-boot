package com.example.springsecurityboot.service;

import com.example.springsecurityboot.model.User;
import com.example.springsecurityboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        final User user = userRepository.findByEmail(userName)
                                        .orElseThrow(
                                            () -> new UsernameNotFoundException("Email " + userName + " not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
            getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {

        String[] roles = user.getRoles()
                             .stream()
                             .map((r) -> r.getName())
                             .toArray(String[]::new);
        return AuthorityUtils.createAuthorityList(roles);
    }
}
