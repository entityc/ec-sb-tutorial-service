//
// This Repository class is responsible for performing database accesses for the following entity:
//
//   Name:        Module
//   Description: Represents a module within the tutorial. A module is a big partition of
//                the tutorial.
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
import org.entityc.tutorial.model.Module;

@Repository
public interface ModuleRepository extends CrudRepository<Module, UUID> {

    static final LRUCache<UUID, Module> modelCache = new LRUCache<>(15);
    static final Map<String,Long> cacheStats = new HashMap<>();

    default Optional<Module> getById(UUID id) {
        if (modelCache.containsKey(id)) {
            return Optional.of(modelCache.get(id));
        }
        Optional<Module> object = findById(id);
        if (object.isPresent()) {
            modelCache.put(object.get().getId(), object.get());
        }
        return object;
    }
    /**
     * Gets the full list of Module objects with paging.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Module objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM tutorial_module " +
            "ORDER BY number ASC " +
            "LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<Module> getModuleList(int start, int limit);

    /**
     * Gets the list of Module objects that all share the same reference to an object of Tutorial.
     * @param tutorialId The ID of the Tutorial object to which all results will share.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of Module objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM tutorial_module " +
            "WHERE tutorial_id = ?1 " +
            "ORDER BY number ASC " +
            "LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Module> getModuleListByTutorial(UUID tutorialId, int start, int limit);

    default <S extends Module> S saveModule(S object) {
        S savedObject = save(object);
        modelCache.put(savedObject.getId(), savedObject);
        return savedObject;
    }

    /**
     * Deletes all Module objects that share the same reference to an object of Tutorial.
     * @param tutorialId The ID of the Tutorial object to which all objects to delete share.
     */
    @Modifying
    @Query(value = "DELETE FROM tutorial_module WHERE tutorial_id = ?1", nativeQuery = true)
    public void deleteModuleByTutorialId(UUID tutorialId);

}
