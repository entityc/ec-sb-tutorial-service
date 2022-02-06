//
// This Service class is responsible for higher level functions for entity:
//
//   Name:        Exercise
//   Description: Represents an exercise within a session. A session typically only has
//                one exercise but it can have more than one if the session is big. An
//                exercise gives the student some hands on experience with the material
//                covered by its session.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//

package org.entityc.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.entityc.tutorial.model.Exercise;
import org.entityc.tutorial.dto.ExerciseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.entityc.tutorial.repository.ExerciseRepository;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.entityc.tutorial.exception.ServiceException;
import org.springframework.dao.DataAccessException;
import org.entityc.tutorial.exception.DaoException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.entityc.tutorial.exception.EntityNotFoundException;
import org.entityc.tutorial.mapper.ExerciseMapper;
import org.entityc.tutorial.model.Session;
import org.entityc.tutorial.dto.SessionDto;
import org.entityc.tutorial.model.Step;
import org.entityc.tutorial.dto.StepDto;
import java.util.HashSet;
import org.entityc.tutorial.service.StepService;
import org.entityc.tutorial.util.MarkdownUtils;
import org.entityc.tutorial.util.MarkdownBuilder;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.model.User;
import java.util.Set;
import org.entityc.tutorial.model.Role;

@Service
@Transactional
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final StepService stepService;
    @Autowired SecurityService securityService;
    @Autowired PersistentUserDetailsService userDetailsService;



    @Autowired
    public ExerciseService(
        ExerciseRepository exerciseRepository,
        ExerciseMapper exerciseMapper,
        StepService stepService
    ) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
        this.stepService = stepService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Exercise createExercise(Exercise object) throws ServiceException {
        object.setId(UUID.randomUUID());
        Exercise savedObject = this.saveExercise(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Exercise createExerciseWithSession(UUID sessionId, Exercise object) throws ServiceException {
        object.setId(UUID.randomUUID());
        object.setSessionId(sessionId);
        Exercise savedObject = this.saveExercise(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Exercise updateExercise(Exercise object) throws ServiceException {
        return saveExercise(object);
    }

    private Exercise saveExercise(Exercise object) throws ServiceException {
        try {
            Exercise savedObject = exerciseRepository.saveExercise(object);
            return savedObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }
    public Exercise getExerciseById(UUID id) throws ServiceException {
        try {
            Exercise responseObject = null;
            if (responseObject == null) {
                Optional<Exercise> modelObjectOptional = exerciseRepository.getById(id);
                if (!modelObjectOptional.isPresent()) {
                    throw new EntityNotFoundException("Exercise not found.");
                }

                Exercise modelObject = modelObjectOptional.get();
                responseObject = modelObject;
            }
            return responseObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public ExerciseDto getExerciseDtoById(UUID id) throws ServiceException {
        Exercise object = getExerciseById(id);
        return dtoFromModel(object);
    }

    public List<Exercise> getExerciseList(int start, int limit) throws ServiceException {
        try {
            return exerciseRepository.getExerciseList(start, limit);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<ExerciseDto> getExerciseDtoList(int start, int limit, boolean hierarchical) throws ServiceException {
        try {
            List<ExerciseDto> dtoList = new ArrayList<>();
            List<Exercise> list = exerciseRepository.getExerciseList(start, limit);
            for(Exercise modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<Exercise> getExerciseListBySession(
        UUID sessionId,
        int start,
        int limit,
        boolean hierarchical) throws ServiceException {

        try {
            List<Exercise> list = exerciseRepository.getExerciseListBySession(sessionId, start, limit);
            return list;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<ExerciseDto> getExerciseDtoListBySession(
        UUID sessionId,
        int start,
        int limit,
        boolean hierarchical) throws ServiceException {

        try {
            List<ExerciseDto> dtoList = new ArrayList<>();
            List<Exercise> list = exerciseRepository.getExerciseListBySession(sessionId, start, limit);
            for(Exercise modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public UUID getParentId(UUID objId) {
        Optional<Exercise> objOptional = exerciseRepository.getById(objId);
        if (objOptional.isPresent()) {
            Exercise obj = objOptional.get();
            UUID parentId = obj.getSessionId();
            return parentId;
        }
        return null;
    }

    public void deleteExerciseBySessionId(UUID sessionId) throws ServiceException {
        try {
            exerciseRepository.deleteExerciseBySessionId(sessionId);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public void deleteExerciseById(UUID id) throws ServiceException {
        try {
            exerciseRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public ExerciseDto dtoFromModel(Exercise modelObject, boolean hierarchical) throws ServiceException {
        ExerciseDto dtoObject = exerciseMapper.toDtoFromModel(modelObject);
        if (hierarchical) {
            dtoObject.setSteps(new HashSet<>(stepService.getStepDtoListByExercise(modelObject.getId(), 0, 10000, true)));
        }
        return dtoObject;
    }
    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public ExerciseDto dtoFromModel(Exercise modelObject) throws ServiceException {
        return dtoFromModel(modelObject, true);
    }
    /**
        Builds a markdown document for a specified object that represents a level in the document.
        [Template] ServicePublisher:methods < DocumentBuilderAuthor
     */
    public String buildMarkdownDocSection(final int level, final ExerciseDto object, final long count) throws ServiceException {
        MarkdownBuilder sectionBuilder = new MarkdownBuilder();

        Integer number = count != 1 ? object.getNumber() : null;
        sectionBuilder.appendHeading(level, "Exercise", number, ":", null);

        sectionBuilder.appendPara(object.getOverview());
        List<StepDto> list = stepService.getStepDtoListByExercise(object.getId(), 0, 1000, true);
        for( StepDto subObj : list) {
             sectionBuilder.append(stepService.buildMarkdownDocSection(level + 1, subObj, list.size()));
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
