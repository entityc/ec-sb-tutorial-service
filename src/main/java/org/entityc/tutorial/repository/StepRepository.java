//
// This Repository class is responsible for performing database accesses for the following entity:
//
//   Name:        Step
//   Description: An exercise is broken down into smaller steps where a single step has the
//                user perform a small task as part of the exercise.
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

import org.entityc.tutorial.util.LRUCache;
import java.util.Map;
import java.util.HashMap;
import org.entityc.tutorial.model.Step;

@Repository
public interface StepRepository extends CrudRepository<Step, UUID> {

    static final LRUCache<UUID, Step> modelCache = new LRUCache<>(100);
    static final Map<String,Long> cacheStats = new HashMap<>();

    default Optional<Step> getById(UUID id) {
        if (modelCache.containsKey(id)) {
            return Optional.of(modelCache.get(id));
        }
        Optional<Step> object = findById(id);
        if (object.isPresent()) {
            modelCache.put(object.get().getId(), object.get());
        }
        return object;
    }
    /**
     * Gets the full list of Step objects with paging.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Step objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM step " +
            "ORDER BY number ASC " +
            "LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<Step> getStepList(int start, int limit);

    /**
     * Gets the list of Step objects that all share the same reference to an object of Exercise.
     * @param exerciseId The ID of the Exercise object to which all results will share.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Step objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM step " +
            "WHERE exercise_id = ?1 " +
            "ORDER BY number ASC " +
            "LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Step> getStepListByExercise(UUID exerciseId, int start, int limit);

    default <S extends Step> S saveStep(S object) {
        S savedObject = save(object);
        modelCache.put(savedObject.getId(), savedObject);
        return savedObject;
    }

    /**
     * Deletes all Step objects that share the same reference to an object of Exercise.
     * @param exerciseId The ID of the Exercise object to which all objects to delete share.
     */
    @Modifying
    @Query(value = "DELETE FROM step WHERE exercise_id = ?1", nativeQuery = true)
    public void deleteStepByExerciseId(UUID exerciseId);

}
