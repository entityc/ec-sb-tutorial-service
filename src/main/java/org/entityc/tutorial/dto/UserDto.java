//
// This is the DTO class for entity:
//
//   Entity
//     Name:        User
//     Description: Represents a user in the system.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//
package org.entityc.tutorial.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.UUID;
import org.entityc.tutorial.model.Role;
import org.entityc.tutorial.model.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.entityc.tutorial.model.Role;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto
{
    // PRIMARY KEY
    private UUID id;

    // When the user account was created.
    private Date createdOn;

    // The user's Email address that is also their username.
    private String email;

    // If set the user is allowed to login, otherwise they cannot log in.
    private Boolean enabled;

    // The user's first (given) name.
    private String firstName;

    // The user's last (family) name.
    private String lastName;

    // When the user account was last modified.
    private Date modifiedOn;

    // The roles assigned to a user.
    private Set<Role> roles;


    public void adjustUpdateForRoles(User user, Set<Role> roles) {
        this.createdOn = null;
        this.modifiedOn = null;
        if (!(roles.contains(Role.ADMINISTRATOR))) {
            this.roles = null;
        }
        if (!(roles.contains(Role.ADMINISTRATOR))) {
            this.enabled = null;
        }
    }
}
