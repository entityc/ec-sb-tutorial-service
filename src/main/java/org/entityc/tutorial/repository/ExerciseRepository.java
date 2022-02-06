//
// This Repository class is responsible for performing database accesses for the following entity:
//
//   Name:        Exercise
//   Description: Represents an exercise within a session. A session typically only has
//                one exercise but it can have more than one if the session is big. An
//                exercise gives the student some hands on experience with the material
//                covered by its session.
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
import org.entityc.tutorial.model.Exercise;

@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, UUID> {

    static final LRUCache<UUID, Exercise> modelCache = new LRUCache<>(50);
    static final Map<String,Long> cacheStats = new HashMap<>();

    default Optional<Exercise> getById(UUID id) {
        if (modelCache.containsKey(id)) {
            return Optional.of(modelCache.get(id));
        }
        Optional<Exercise> object = findById(id);
        if (object.isPresent()) {
            modelCache.put(object.get().getId(), object.get());
        }
        return object;
    }
    /**
     * Gets the full list of Exercise objects with paging.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Exercise objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM exercise " +
            "ORDER BY number ASC " +
            "LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<Exercise> getExerciseList(int start, int limit);

    /**
     * Gets the list of Exercise objects that all share the same reference to an object of Session.
     * @param sessionId The ID of the Session object to which all results will share.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Exercise objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM exercise " +
            "WHERE session_id = ?1 " +
            "ORDER BY number ASC " +
            "LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Exercise> getExerciseListBySession(UUID sessionId, int start, int limit);

    default <S extends Exercise> S saveExercise(S object) {
        S savedObject = save(object);
        modelCache.put(savedObject.getId(), savedObject);
        return savedObject;
    }

    /**
     * Deletes all Exercise objects that share the same reference to an object of Session.
     * @param sessionId The ID of the Session object to which all objects to delete share.
     */
    @Modifying
    @Query(value = "DELETE FROM exercise WHERE session_id = ?1", nativeQuery = true)
    public void deleteExerciseBySessionId(UUID sessionId);

}
