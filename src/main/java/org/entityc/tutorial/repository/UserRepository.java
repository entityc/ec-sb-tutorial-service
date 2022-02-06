//
// This Repository class is responsible for performing database accesses for the following entity:
//
//   Name:        User
//   Description: Represents a user in the system.
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
import org.entityc.tutorial.model.Role;

import org.entityc.tutorial.util.LRUCache;
import java.util.Map;
import java.util.HashMap;
import org.entityc.tutorial.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    static final LRUCache<UUID, User> modelCache = new LRUCache<>(10);
    static final Map<String,Long> cacheStats = new HashMap<>();

    default Optional<User> getById(UUID id) {
        if (modelCache.containsKey(id)) {
            return Optional.of(modelCache.get(id));
        }
        Optional<User> object = findById(id);
        if (object.isPresent()) {
            modelCache.put(object.get().getId(), object.get());
        }
        return object;
    }
    /**
     * Gets the full list of User objects with paging.
     * @param start The starting index.
     * @param limit The maximum number or results to return.
     * @return List of User objects fetched.
     */
    @Query(value =
            "SELECT * " +
            "FROM platform_user " +
            "ORDER BY created_on DESC " +
            "LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<User> getUserList(int start, int limit);

    default <S extends User> S saveUser(S object) {
        S savedObject = save(object);
        modelCache.put(savedObject.getId(), savedObject);
        return savedObject;
    }

    /**
        Finds a user object by its username.
        [Template] RepositoryPublisher:methods < AuthorizationAuthor
     */
    User findByEmail(String email);

}
