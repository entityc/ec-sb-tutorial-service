//
// This Service class is responsible for higher level functions for entity:
//
//   Name:        Session
//   Description: Represents a session within a module. A session typically tries to
//                teach a single concept.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//

package org.entityc.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.entityc.tutorial.model.Session;
import org.entityc.tutorial.dto.SessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.entityc.tutorial.repository.SessionRepository;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.entityc.tutorial.exception.ServiceException;
import org.springframework.dao.DataAccessException;
import org.entityc.tutorial.exception.DaoException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.entityc.tutorial.exception.EntityNotFoundException;
import org.entityc.tutorial.mapper.SessionMapper;
import org.entityc.tutorial.model.Exercise;
import org.entityc.tutorial.dto.ExerciseDto;
import java.util.HashSet;
import org.entityc.tutorial.service.ExerciseService;
import org.entityc.tutorial.model.Module;
import org.entityc.tutorial.dto.ModuleDto;
import org.entityc.tutorial.util.MarkdownUtils;
import org.entityc.tutorial.util.MarkdownBuilder;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.model.User;
import java.util.Set;
import org.entityc.tutorial.model.Role;

@Service
@Transactional
public class SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final ExerciseService exerciseService;
    @Autowired SecurityService securityService;
    @Autowired PersistentUserDetailsService userDetailsService;



    @Autowired
    public SessionService(
        SessionRepository sessionRepository,
        SessionMapper sessionMapper,
        ExerciseService exerciseService
    ) {
        this.sessionRepository = sessionRepository;
        this.sessionMapper = sessionMapper;
        this.exerciseService = exerciseService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Session createSession(Session object) throws ServiceException {
        object.setId(UUID.randomUUID());
        Session savedObject = this.saveSession(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Session createSessionWithModule(UUID moduleId, Session object) throws ServiceException {
        object.setId(UUID.randomUUID());
        object.setModuleId(moduleId);
        Session savedObject = this.saveSession(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Session updateSession(Session object) throws ServiceException {
        return saveSession(object);
    }

    private Session saveSession(Session object) throws ServiceException {
        try {
            Session savedObject = sessionRepository.saveSession(object);
            return savedObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }
    public Session getSessionById(UUID id) throws ServiceException {
        try {
            Session responseObject = null;
            if (responseObject == null) {
                Optional<Session> modelObjectOptional = sessionRepository.getById(id);
                if (!modelObjectOptional.isPresent()) {
                    throw new EntityNotFoundException("Session not found.");
                }

                Session modelObject = modelObjectOptional.get();
                responseObject = modelObject;
            }
            return responseObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public SessionDto getSessionDtoById(UUID id) throws ServiceException {
        Session object = getSessionById(id);
        return dtoFromModel(object);
    }

    public List<Session> getSessionList(int start, int limit) throws ServiceException {
        try {
            return sessionRepository.getSessionList(start, limit);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<SessionDto> getSessionDtoList(int start, int limit, boolean hierarchical) throws ServiceException {
        try {
            List<SessionDto> dtoList = new ArrayList<>();
            List<Session> list = sessionRepository.getSessionList(start, limit);
            for(Session modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<Session> getSessionListByModule(
        UUID moduleId,
        int start,
        int limit,
        boolean hierarchical) throws ServiceException {

        try {
            List<Session> list = sessionRepository.getSessionListByModule(moduleId, start, limit);
            return list;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<SessionDto> getSessionDtoListByModule(
        UUID moduleId,
        int start,
        int limit,
        boolean hierarchical) throws ServiceException {

        try {
            List<SessionDto> dtoList = new ArrayList<>();
            List<Session> list = sessionRepository.getSessionListByModule(moduleId, start, limit);
            for(Session modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public UUID getParentId(UUID objId) {
        Optional<Session> objOptional = sessionRepository.getById(objId);
        if (objOptional.isPresent()) {
            Session obj = objOptional.get();
            UUID parentId = obj.getModuleId();
            return parentId;
        }
        return null;
    }

    public void deleteSessionByModuleId(UUID moduleId) throws ServiceException {
        try {
            sessionRepository.deleteSessionByModuleId(moduleId);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public void deleteSessionById(UUID id) throws ServiceException {
        try {
            sessionRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public SessionDto dtoFromModel(Session modelObject, boolean hierarchical) throws ServiceException {
        SessionDto dtoObject = sessionMapper.toDtoFromModel(modelObject);
        if (hierarchical) {
            dtoObject.setExercises(new HashSet<>(exerciseService.getExerciseDtoListBySession(modelObject.getId(), 0, 10000, true)));
        }
        return dtoObject;
    }
    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public SessionDto dtoFromModel(Session modelObject) throws ServiceException {
        return dtoFromModel(modelObject, true);
    }
    /**
        Builds a markdown document for a specified object that represents a level in the document.
        [Template] ServicePublisher:methods < DocumentBuilderAuthor
     */
    public String buildMarkdownDocSection(final int level, final SessionDto object, final long count) throws ServiceException {
        MarkdownBuilder sectionBuilder = new MarkdownBuilder();
        sectionBuilder.appendHeading(level, "Session", object.getNumber(), ":", object.getTitle());

        sectionBuilder.appendHeading(level+1, "Objective");
        sectionBuilder.appendPara(object.getObjective());
        sectionBuilder.appendHeading(level+1, "Discussion");
        sectionBuilder.appendPara(object.getDiscussion());
        List<ExerciseDto> list = exerciseService.getExerciseDtoListBySession(object.getId(), 0, 1000, true);
        for( ExerciseDto subObj : list) {
             sectionBuilder.append(exerciseService.buildMarkdownDocSection(level + 1, subObj, list.size()));
        }
        return sectionBuilder.toString();
    }
    /**
        Determines if a the logged user has permission to create an object of this entity.
        [Template] ServicePublisher:methods < AuthorizationAuthor
     */
    public boolean canCreate() throws ServiceException {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();
        if (roles.contains(Role.INSTRUCTOR)) {
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

        if (roles.contains(Role.INSTRUCTOR) || roles.contains(Role.STUDENT)) {
            return true;
        }
        return false;
    }

    /**
        Determines if a the logged user has permission to edit any object of this entity.
        [Template] ServicePublisher:methods < AuthorizationAuthor
     */
    public boolean canEdit(UUID id) throws ServiceException {
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        Set<Role> roles = user.getRoles();

        if (roles.contains(Role.INSTRUCTOR)) {
            return true;
        }
        return false;
    }

}
