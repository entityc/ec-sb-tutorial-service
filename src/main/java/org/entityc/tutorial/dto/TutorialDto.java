//
// This is the DTO class for entity:
//
//   Entity
//     Name:        Tutorial
//     Description: Represents an entire tutorial with modules and sessions.
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TutorialDto
{
    // PRIMARY KEY
    private UUID id;

    // When the object was created.
    private Date createdOn;

    // A unique identifier associated with this tutorial.
    private String identifier;

    // When the object was last modified.
    private Date modifiedOn;

    // Tutorial overview.
    private String overview;

    // The localized summary of the tutorial used when summarizing all tutorials in a view.
    private String summary;

    // The localized title of the tutorial.
    private String title;

    // RELATIONSHIPS
    // The modules of a tutorial.
    private Set<ModuleDto> modules;
    // The user that created the tutorial.
    private UserDto createdUser;

    public void adjustUpdateForRoles(Set<Role> roles) {
        this.createdOn = null;
        this.modifiedOn = null;
    }
}
