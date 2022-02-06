//
// This is the Model class for entity:
//
//   Name:        Step
//   Description: An exercise is broken down into smaller steps where a single step has the
//                user perform a small task as part of the exercise.
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "step")
public class Step
{
    @Id
    @Type(type="pg-uuid")
    @Column(name = "step_id")
    private UUID id;

    // When the object was created.
    @Column(name = "created_on", insertable = false, updatable = false)
    private Date createdOn;

    // When the object was last modified.
    @Column(name = "modified_on", insertable = false, updatable = false)
    private Date modifiedOn;

    // The step number.
    @Column(name = "number")
    private Integer number;

    // Step content.
    @Column(name = "instructions")
    private String instructions;

    @Column(name = "exercise_id") // relationship
    UUID exerciseId;

}
