//
// This is the DTO class for entity:
//
//   Entity
//     Name:        Session
//     Description: Represents a session within a module. A session typically tries to
//                  teach a single concept.
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
public class SessionDto
{
    // PRIMARY KEY
    private UUID id;

    // When the object was created.
    private Date createdOn;

    // The discussion section of the session.
    private String discussion;

    // When the object was last modified.
    private Date modifiedOn;

    // The session number.
    private Integer number;

    // The objective of the session.
    private String objective;

    // The title of the session.
    private String title;

    // RELATIONSHIPS
    // The exercises of this session.
    private Set<ExerciseDto> exercises;
    // The module to which this session belongs.
    private ModuleDto module;

    public void adjustUpdateForRoles(Set<Role> roles) {
        this.createdOn = null;
        this.modifiedOn = null;
    }
}
