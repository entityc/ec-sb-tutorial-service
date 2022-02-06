//
// This is the Model class for entity:
//
//   Name:        Tutorial
//   Description: Represents an entire tutorial with modules and sessions.
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
@Table(name = "tutorial")
public class Tutorial
{
    @Id
    @Type(type="pg-uuid")
    @Column(name = "tutorial_id")
    private UUID id;

    // When the object was created.
    @Column(name = "created_on", insertable = false, updatable = false)
    private Date createdOn;

    // When the object was last modified.
    @Column(name = "modified_on", insertable = false, updatable = false)
    private Date modifiedOn;

    // A unique identifier associated with this tutorial.
    @Column(name = "identifier")
    private String identifier;

    // The localized title of the tutorial.
    @Column(name = "title")
    private String title;

    // The localized summary of the tutorial used when summarizing all tutorials in a view.
    @Column(name = "summary")
    private String summary;

    // Tutorial overview.
    @Column(name = "overview")
    private String overview;

    @Column(name = "created_user_id") // relationship
    UUID createdUserId;

}
