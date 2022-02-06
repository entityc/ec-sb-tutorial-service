package org.entityc.tutorial.security;

import org.entityc.tutorial.model.User;
import org.entityc.tutorial.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PersistentUserDetailsPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;

    public PersistentUserDetailsPasswordService(UserRepository userRepository, UserDetailsMapper userDetailsMapper) {
        this.userRepository = userRepository;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        User user = userRepository.findByEmail(userDetails.getUsername());
        user.setEncodedPassword(newPassword);
        userRepository.save(user);
        return userDetailsMapper.toUserDetails(user);
    }
}
