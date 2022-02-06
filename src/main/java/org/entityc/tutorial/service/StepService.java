//
// This Service class is responsible for higher level functions for entity:
//
//   Name:        Step
//   Description: An exercise is broken down into smaller steps where a single step has the
//                user perform a small task as part of the exercise.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//

package org.entityc.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.entityc.tutorial.model.Step;
import org.entityc.tutorial.dto.StepDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.entityc.tutorial.repository.StepRepository;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.entityc.tutorial.exception.ServiceException;
import org.springframework.dao.DataAccessException;
import org.entityc.tutorial.exception.DaoException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.entityc.tutorial.exception.EntityNotFoundException;
import org.entityc.tutorial.mapper.StepMapper;
import org.entityc.tutorial.model.Exercise;
import org.entityc.tutorial.dto.ExerciseDto;
import org.entityc.tutorial.util.MarkdownUtils;
import org.entityc.tutorial.util.MarkdownBuilder;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.model.User;
import java.util.Set;
import org.entityc.tutorial.model.Role;

@Service
@Transactional
public class StepService {

    private final StepRepository stepRepository;
    private final StepMapper stepMapper;
    @Autowired SecurityService securityService;
    @Autowired PersistentUserDetailsService userDetailsService;



    @Autowired
    public StepService(
        StepRepository stepRepository,
        StepMapper stepMapper
    ) {
        this.stepRepository = stepRepository;
        this.stepMapper = stepMapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Step createStep(Step object) throws ServiceException {
        object.setId(UUID.randomUUID());
        Step savedObject = this.saveStep(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Step createStepWithExercise(UUID exerciseId, Step object) throws ServiceException {
        object.setId(UUID.randomUUID());
        object.setExerciseId(exerciseId);
        Step savedObject = this.saveStep(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Step updateStep(Step object) throws ServiceException {
        return saveStep(object);
    }

    private Step saveStep(Step object) throws ServiceException {
        try {
            Step savedObject = stepRepository.saveStep(object);
            return savedObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }
    public Step getStepById(UUID id) throws ServiceException {
        try {
            Step responseObject = null;
            if (responseObject == null) {
                Optional<Step> modelObjectOptional = stepRepository.getById(id);
                if (!modelObjectOptional.isPresent()) {
                    throw new EntityNotFoundException("Step not found.");
                }

                Step modelObject = modelObjectOptional.get();
                responseObject = modelObject;
            }
            return responseObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public StepDto getStepDtoById(UUID id) throws ServiceException {
        Step object = getStepById(id);
        return dtoFromModel(object);
    }

    public List<Step> getStepList(int start, int limit) throws ServiceException {
        try {
            return stepRepository.getStepList(start, limit);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<StepDto> getStepDtoList(int start, int limit, boolean hierarchical) throws ServiceException {
        try {
            List<StepDto> dtoList = new ArrayList<>();
            List<Step> list = stepRepository.getStepList(start, limit);
            for(Step modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<Step> getStepListByExercise(
        UUID exerciseId,
        int start,
        int limit,
        boolean hierarchical) throws ServiceException {

        try {
            List<Step> list = stepRepository.getStepListByExercise(exerciseId, start, limit);
            return list;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<StepDto> getStepDtoListByExercise(
        UUID exerciseId,
        int start,
        int limit,
        boolean hierarchical) throws ServiceException {

        try {
            List<StepDto> dtoList = new ArrayList<>();
            List<Step> list = stepRepository.getStepListByExercise(exerciseId, start, limit);
            for(Step modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public UUID getParentId(UUID objId) {
        Optional<Step> objOptional = stepRepository.getById(objId);
        if (objOptional.isPresent()) {
            Step obj = objOptional.get();
            UUID parentId = obj.getExerciseId();
            return parentId;
        }
        return null;
    }

    public void deleteStepByExerciseId(UUID exerciseId) throws ServiceException {
        try {
            stepRepository.deleteStepByExerciseId(exerciseId);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public void deleteStepById(UUID id) throws ServiceException {
        try {
            stepRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public StepDto dtoFromModel(Step modelObject, boolean hierarchical) throws ServiceException {
        StepDto dtoObject = stepMapper.toDtoFromModel(modelObject);
        if (hierarchical) {
        }
        return dtoObject;
    }
    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public StepDto dtoFromModel(Step modelObject) throws ServiceException {
        return dtoFromModel(modelObject, true);
    }
    /**
        Builds a markdown document for a specified object that represents a level in the document.
        [Template] ServicePublisher:methods < DocumentBuilderAuthor
     */
    public String buildMarkdownDocSection(final int level, final StepDto object, final long count) throws ServiceException {
        MarkdownBuilder sectionBuilder = new MarkdownBuilder();
        sectionBuilder.appendHeading(level, "Step", object.getNumber(), ":", null);

        sectionBuilder.appendPara(object.getInstructions());
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
