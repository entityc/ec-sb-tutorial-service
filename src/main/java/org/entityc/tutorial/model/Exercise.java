//
// This is the Model class for entity:
//
//   Name:        Exercise
//   Description: Represents an exercise within a session. A session typically only has
//                one exercise but it can have more than one if the session is big. An
//                exercise gives the student some hands on experience with the material
//                covered by its session.
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
@Table(name = "exercise")
public class Exercise
{
    @Id
    @Type(type="pg-uuid")
    @Column(name = "exercise_id")
    private UUID id;

    // When the object was created.
    @Column(name = "created_on", insertable = false, updatable = false)
    private Date createdOn;

    // When the object was last modified.
    @Column(name = "modified_on", insertable = false, updatable = false)
    private Date modifiedOn;

    // The exercise number.
    @Column(name = "number")
    private Integer number;

    // Exercise overview.
    @Column(name = "overview")
    private String overview;

    @Column(name = "session_id") // relationship
    UUID sessionId;

}
