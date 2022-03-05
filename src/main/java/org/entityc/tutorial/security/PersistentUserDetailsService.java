package org.entityc.tutorial.security;

import org.entityc.tutorial.model.User;
import org.entityc.tutorial.repository.UserRepository;
import org.entityc.tutorial.service.UserService;
import org.entityc.tutorial.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.entityc.tutorial.exception.ServiceException;

import java.util.HashSet;
import java.util.HashMap;

@Service
@Transactional
@Qualifier("userDetailsService")
public class PersistentUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;
    private final HashMap<String,User> userByEmail = new HashMap<>();

    public PersistentUserDetailsService(UserRepository userRepository, UserDetailsMapper userDetailsMapper) {
        this.userRepository = userRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findByEmail(username);
        if (user == null) {
            return null;
        }
        return userDetailsMapper.toUserDetails(user);
    }

    public User findByEmail(String email) {
        if (userByEmail.containsKey(email)) {
            return userByEmail.get(email);
        }
        User obj = userRepository.findByEmail(email);
        userByEmail.put(email, obj);
        return obj;
    }

    public void updateUser(User user) {
        userByEmail.put(user.getEmail(), user);
    }
}