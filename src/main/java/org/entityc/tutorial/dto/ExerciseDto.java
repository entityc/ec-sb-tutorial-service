//
// This is the DTO class for entity:
//
//   Entity
//     Name:        Exercise
//     Description: Represents an exercise within a session. A session typically only
//                  has one exercise but it can have more than one if the session is big. An
//                  exercise gives the student some hands on experience with the
//                  material covered by its session.
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
public class ExerciseDto
{
    // PRIMARY KEY
    private UUID id;

    // When the object was created.
    private Date createdOn;

    // When the object was last modified.
    private Date modifiedOn;

    // The exercise number.
    private Integer number;

    // Exercise overview.
    private String overview;

    // RELATIONSHIPS
    // The session to which this exercise belongs.
    private SessionDto session;
    // The steps of this exercise.
    private Set<StepDto> steps;

    public void adjustUpdateForRoles(Set<Role> roles) {
        this.createdOn = null;
        this.modifiedOn = null;
    }
}
