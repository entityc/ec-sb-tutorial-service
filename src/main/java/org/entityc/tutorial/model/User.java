//
// This is the Model class for entity:
//
//   Name:        User
//   Description: Represents a user in the system.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//
package org.entityc.tutorial.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.entityc.tutorial.model.Role;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "platform_user")
public class User
{
    @Id
    @Type(type="pg-uuid")
    @Column(name = "user_id")
    private UUID id;

    // The roles assigned to a user.
    @ElementCollection(fetch=FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(
            name = "platform_user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "value")
    private Set<Role> roles = new HashSet<>();

    // The user's first (given) name.
    @Column(name = "first_name")
    private String firstName;

    // The user's last (family) name.
    @Column(name = "last_name")
    private String lastName;

    // The user's Email address that is also their username.
    @Column(name = "email")
    private String email;

    // The user's password encoded so not in plain text.
    @Column(name = "encoded_password")
    private String encodedPassword;

    // If set the user is allowed to login, otherwise they cannot log in.
    @Column(name = "enabled")
    private Boolean enabled = true;

    // When the user account was created.
    @Column(name = "created_on", insertable = false, updatable = false)
    private Date createdOn;

    // When the user account was last modified.
    @Column(name = "modified_on", insertable = false, updatable = false)
    private Date modifiedOn;

}
