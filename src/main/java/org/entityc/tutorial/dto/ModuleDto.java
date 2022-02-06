//
// This is the DTO class for entity:
//
//   Entity
//     Name:        Module
//     Description: Represents a module within the tutorial. A module is a big partition
//                  of the tutorial.
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
public class ModuleDto
{
    // PRIMARY KEY
    private UUID id;

    // When the object was created.
    private Date createdOn;

    // When the object was last modified.
    private Date modifiedOn;

    // Represents the module number.
    private Integer number;

    // Module overview.
    private String overview;

    // The localized summary of the module used when sumarizing all modules of a tutorial.
    private String summary;

    // The localized title of the module.
    private String title;

    // RELATIONSHIPS
    // The sessions of this module.
    private Set<SessionDto> sessions;
    // The tutorial to which this module belongs.
    private TutorialDto tutorial;

    public void adjustUpdateForRoles(Set<Role> roles) {
        this.createdOn = null;
        this.modifiedOn = null;
    }
}
