//
// This is the Model class for entity:
//
//   Name:        Session
//   Description: Represents a session within a module. A session typically tries to
//                teach a single concept.
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
@Table(name = "session")
public class Session
{
    @Id
    @Type(type="pg-uuid")
    @Column(name = "session_id")
    private UUID id;

    // When the object was created.
    @Column(name = "created_on", insertable = false, updatable = false)
    private Date createdOn;

    // When the object was last modified.
    @Column(name = "modified_on", insertable = false, updatable = false)
    private Date modifiedOn;

    // The title of the session.
    @Column(name = "title")
    private String title;

    // The objective of the session.
    @Column(name = "objective")
    private String objective;

    // The discussion section of the session.
    @Column(name = "discussion")
    private String discussion;

    // The session number.
    @Column(name = "number")
    private Integer number;

    @Column(name = "module_id") // relationship
    UUID moduleId;

}
