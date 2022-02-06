//
// This is the DTO class for entity:
//
//   Entity
//     Name:        Step
//     Description: An exercise is broken down into smaller steps where a single step has
//                  the user perform a small task as part of the exercise.
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
public class StepDto
{
    // PRIMARY KEY
    private UUID id;

    // When the object was created.
    private Date createdOn;

    // Step content.
    private String instructions;

    // When the object was last modified.
    private Date modifiedOn;

    // The step number.
    private Integer number;

    // RELATIONSHIPS
    // The exercise to which this step belongs.
    private ExerciseDto exercise;

    public void adjustUpdateForRoles(Set<Role> roles) {
        this.createdOn = null;
        this.modifiedOn = null;
    }
}
