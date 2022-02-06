//
// This Repository class is responsible for performing database accesses for the following entity:
//
//   Name:        Session
//   Description: Represents a session within a module. A session typically tries to
//                teach a single concept.
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
import org.entityc.tutorial.model.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session, UUID> {

    static final LRUCache<UUID, Session> modelCache = new LRUCache<>(50);
    static final Map<String,Long> cacheStats = new HashMap<>();

    default Optional<Session> getById(UUID id) {
        if (modelCache.containsKey(id)) {
            return Optional.of(modelCache.get(id));
        }
        Optional<Session> object = findById(id);
        if (object.isPresent()) {
            modelCache.put(object.get().getId(), object.get());
        }
        return object;
    }
    /**
     * Gets the full list of Session objects with paging.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Session objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM session " +
            "ORDER BY number ASC " +
            "LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<Session> getSessionList(int start, int limit);

    /**
     * Gets the list of Session objects that all share the same reference to an object of Module.
     * @param moduleId The ID of the Module object to which all results will share.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Session objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM session " +
            "WHERE module_id = ?1 " +
            "ORDER BY number ASC " +
            "LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Session> getSessionListByModule(UUID moduleId, int start, int limit);

    default <S extends Session> S saveSession(S object) {
        S savedObject = save(object);
        modelCache.put(savedObject.getId(), savedObject);
        return savedObject;
    }

    /**
     * Deletes all Session objects that share the same reference to an object of Module.
     * @param moduleId The ID of the Module object to which all objects to delete share.
     */
    @Modifying
    @Query(value = "DELETE FROM session WHERE module_id = ?1", nativeQuery = true)
    public void deleteSessionByModuleId(UUID moduleId);

}
