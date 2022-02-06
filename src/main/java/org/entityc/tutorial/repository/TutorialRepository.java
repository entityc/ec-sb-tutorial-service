//
// This Repository class is responsible for performing database accesses for the following entity:
//
//   Name:        Tutorial
//   Description: Represents an entire tutorial with modules and sessions.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//
package org.entityc.tutorial.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Date;

import org.entityc.tutorial.model.Tutorial;

@Repository
public interface TutorialRepository extends CrudRepository<Tutorial, UUID> {


    default Optional<Tutorial> getById(UUID id) {
        Optional<Tutorial> object = findById(id);
        return object;
    }
    /**
     * Gets the full list of Tutorial objects with paging.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Tutorial objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM tutorial " +
            "ORDER BY created_on DESC " +
            "LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<Tutorial> getTutorialList(int start, int limit);

    default <S extends Tutorial> S saveTutorial(S object) {
        S savedObject = save(object);
        return savedObject;
    }

}
