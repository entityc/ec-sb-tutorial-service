//
// This Service class is responsible for higher level functions for entity:
//
//   Name:        User
//   Description: Represents a user in the system.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//

package org.entityc.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.entityc.tutorial.model.User;
import org.entityc.tutorial.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.entityc.tutorial.repository.UserRepository;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.entityc.tutorial.exception.ServiceException;
import org.springframework.dao.DataAccessException;
import org.entityc.tutorial.exception.DaoException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.entityc.tutorial.exception.EntityNotFoundException;
import org.entityc.tutorial.mapper.UserMapper;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import java.util.Set;
import org.entityc.tutorial.model.Role;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired SecurityService securityService;
    @Autowired PersistentUserDetailsService userDetailsService;



    @Autowired
    public UserService(
        UserRepository userRepository,
        UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User createUser(User object) throws ServiceException {
        object.setId(UUID.randomUUID());
        User savedObject = this.saveUser(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User updateUser(User object) throws ServiceException {
        return saveUser(object);
    }

    private User saveUser(User object) throws ServiceException {
        try {
            User savedObject = userRepository.saveUser(object);
            return savedObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }
    public User getUserById(UUID id) throws ServiceException {
        try {
            User responseObject = null;
            if (responseObject == null) {
                Optional<User> modelObjectOptional = userRepository.getById(id);
                if (!modelObjectOptional.isPresent()) {
                    throw new EntityNotFoundException("User not found.");
                }

                User modelObject = modelObjectOptional.get();
                responseObject = modelObject;
            }
            return responseObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public UserDto getUserDtoById(UUID id) throws ServiceException {
        User object = getUserById(id);
        return dtoFromModel(object);
    }

    public List<User> getUserList(int start, int limit) throws ServiceException {
        try {
            return userRepository.getUserList(start, limit);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<UserDto> getUserDtoList(int start, int limit, boolean hierarchical) throws ServiceException {
        try {
            List<UserDto> dtoList = new ArrayList<>();
            List<User> list = userRepository.getUserList(start, limit);
            for(User modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public void deleteUserById(UUID id) throws ServiceException {
        try {
            userRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public UserDto dtoFromModel(User modelObject, boolean hierarchical) throws ServiceException {
        UserDto dtoObject = userMapper.toDtoFromModel(modelObject);
        if (hierarchical) {
        }
        return dtoObject;
    }
    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public UserDto dtoFromModel(User modelObject) throws ServiceException {
        return dtoFromModel(modelObject, true);
    }
    /**
        Determines if a the logged user has permission to create an object of this entity.
        [Template] ServicePublisher:methods < AuthorizationAuthor
     */
    public boolean canCreate() throws ServiceException {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();
        if (roles.contains(Role.ADMINISTRATOR)) {
            return true;
        }
        return false;
    }

    /**
        Determines if a the logged user has permission to view any object of this entity.
        [Template] ServicePublisher:methods < AuthorizationAuthor
     */
    public boolean canView() throws ServiceException {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();

        if (roles.contains(Role.ADMINISTRATOR)) {
            return true;
        }
        return false;
    }

    /**
        Determines if a the logged user has permission to edit a specified object of this entity.
        [Template] ServicePublisher:methods < AuthorizationAuthor
     */
    public boolean canEdit(UUID id) throws ServiceException {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();

        boolean _sameUser = user.getId().equals(id);

        if (roles.contains(Role.ADMINISTRATOR) || _sameUser) {
            return true;
        }
        return false;
    }

    /**
        Determines if the logged user has permission to update the specified attribute.
        [Template] ServicePublisher:methods < AuthorizationAuthor
     */
    public boolean canUpdateAttribute(UserDto userDto, String attributeName) throws ServiceException {
        if (!this.canEdit(userDto.getId())) {
            return false;
        }
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();

        if (attributeName.equals("roles") && !(roles.contains(Role.ADMINISTRATOR))) {
            return false;
        }

        if (attributeName.equals("enabled") && !(roles.contains(Role.ADMINISTRATOR))) {
            return false;
        }
        return true;
    }

    /**
        Determines if a user by the specified username exists and is allowed to login.
        [Template] ServicePublisher:methods < AuthorizationAuthor
     */
    public boolean canLogin(String username) {
        User user = userDetailsService.findByEmail(username);
        if (user == null) {
            return false;
        }
        return user.getEnabled();
    }
    /**
        Gets a user object by its username.
        [Template] ServicePublisher:methods < AuthorizationAuthor
     */
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
