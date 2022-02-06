//
// This is the Model class for entity:
//
//   Name:        Module
//   Description: Represents a module within the tutorial. A module is a big partition of
//                the tutorial.
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
@Table(name = "tutorial_module")
public class Module
{
    @Id
    @Type(type="pg-uuid")
    @Column(name = "module_id")
    private UUID id;

    // When the object was created.
    @Column(name = "created_on", insertable = false, updatable = false)
    private Date createdOn;

    // When the object was last modified.
    @Column(name = "modified_on", insertable = false, updatable = false)
    private Date modifiedOn;

    // Represents the module number.
    @Column(name = "number")
    private Integer number;

    // The localized title of the module.
    @Column(name = "title")
    private String title;

    // The localized summary of the module used when sumarizing all modules of a tutorial.
    @Column(name = "summary")
    private String summary;

    // Module overview.
    @Column(name = "overview")
    private String overview;

    @Column(name = "tutorial_id") // relationship
    UUID tutorialId;

}
