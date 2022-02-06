package org.entityc.tutorial.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import org.entityc.tutorial.model.Role;
import org.entityc.tutorial.model.User;

@Component
public class UserDetailsMapper {

    UserDetails toUserDetails(User user) {

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncodedPassword(), grantedAuthorities);
    }

    public Set<Role> rolesForUserDetails(UserDetails userDetails) {
        Set <Role> roles = new HashSet<>();
        for(GrantedAuthority authority : userDetails.getAuthorities()) {
            String role = authority.getAuthority();
            if (role.startsWith("ROLE_")) {
                role = role.substring(5); // strip that off
                roles.add(Role.valueOf(role));
            }
        }
        return roles;
    }
}
