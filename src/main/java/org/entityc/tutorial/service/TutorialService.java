//
// This Service class is responsible for higher level functions for entity:
//
//   Name:        Tutorial
//   Description: Represents an entire tutorial with modules and sessions.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//

package org.entityc.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.entityc.tutorial.model.Tutorial;
import org.entityc.tutorial.dto.TutorialDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.entityc.tutorial.repository.TutorialRepository;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.entityc.tutorial.exception.ServiceException;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.model.User;
import org.springframework.dao.DataAccessException;
import org.entityc.tutorial.exception.DaoException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.entityc.tutorial.exception.EntityNotFoundException;
import org.entityc.tutorial.mapper.TutorialMapper;
import org.entityc.tutorial.model.Module;
import org.entityc.tutorial.dto.ModuleDto;
import java.util.HashSet;
import org.entityc.tutorial.service.ModuleService;
import org.entityc.tutorial.util.MarkdownUtils;
import org.entityc.tutorial.util.MarkdownBuilder;
import java.util.Set;
import org.entityc.tutorial.model.Role;

@Service
@Transactional
public class TutorialService {

    private final TutorialRepository tutorialRepository;
    @Autowired SecurityService securityService;
    @Autowired PersistentUserDetailsService userDetailsService;
    private final TutorialMapper tutorialMapper;
    private final ModuleService moduleService;



    @Autowired
    public TutorialService(
        TutorialRepository tutorialRepository,
        TutorialMapper tutorialMapper,
        ModuleService moduleService
    ) {
        this.tutorialRepository = tutorialRepository;
        this.tutorialMapper = tutorialMapper;
        this.moduleService = moduleService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tutorial createTutorial(Tutorial object) throws ServiceException {
        object.setId(UUID.randomUUID());
        User user = userDetailsService.findByEmail(securityService.findLoggedInUsername());
        object.setCreatedUserId(user.getId());
        Tutorial savedObject = this.saveTutorial(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Tutorial updateTutorial(Tutorial object) throws ServiceException {
        return saveTutorial(object);
    }

    private Tutorial saveTutorial(Tutorial object) throws ServiceException {
        try {
            Tutorial savedObject = tutorialRepository.saveTutorial(object);
            return savedObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }
    public Tutorial getTutorialById(UUID id) throws ServiceException {
        try {
            Tutorial responseObject = null;
            if (responseObject == null) {
                Optional<Tutorial> modelObjectOptional = tutorialRepository.getById(id);
                if (!modelObjectOptional.isPresent()) {
                    throw new EntityNotFoundException("Tutorial not found.");
                }

                Tutorial modelObject = modelObjectOptional.get();
                responseObject = modelObject;
            }
            return responseObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public TutorialDto getTutorialDtoById(UUID id) throws ServiceException {
        Tutorial object = getTutorialById(id);
        return dtoFromModel(object);
    }

    public List<Tutorial> getTutorialList(int start, int limit) throws ServiceException {
        try {
            return tutorialRepository.getTutorialList(start, limit);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<TutorialDto> getTutorialDtoList(int start, int limit, boolean hierarchical) throws ServiceException {
        try {
            List<TutorialDto> dtoList = new ArrayList<>();
            List<Tutorial> list = tutorialRepository.getTutorialList(start, limit);
            for(Tutorial modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public void deleteTutorialById(UUID id) throws ServiceException {
        try {
            tutorialRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public TutorialDto dtoFromModel(Tutorial modelObject, boolean hierarchical) throws ServiceException {
        TutorialDto dtoObject = tutorialMapper.toDtoFromModel(modelObject);
        if (hierarchical) {
            dtoObject.setModules(new HashSet<>(moduleService.getModuleDtoListByTutorial(modelObject.getId(), 0, 10000, true)));
        }
        return dtoObject;
    }
    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public TutorialDto dtoFromModel(Tutorial modelObject) throws ServiceException {
        return dtoFromModel(modelObject, true);
    }
    /**
        Builds a markdown document for a specified object that represents a level in the document.
        [Template] ServicePublisher:methods < DocumentBuilderAuthor
     */
    public String buildMarkdownDocSection(final int level, final TutorialDto object, final long count) throws ServiceException {
        MarkdownBuilder sectionBuilder = new MarkdownBuilder();
        sectionBuilder.appendHeading(level, object.getTitle());

        sectionBuilder.appendPara(object.getSummary());
        sectionBuilder.appendHeading(level+1, "Overview");
        sectionBuilder.appendPara(object.getOverview());
        List<ModuleDto> list = moduleService.getModuleDtoListByTutorial(object.getId(), 0, 1000, true);
        for( ModuleDto subObj : list) {
             sectionBuilder.append(moduleService.buildMarkdownDocSection(level + 1, subObj, list.size()));
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

        if (roles.contains(Role.INSTRUCTOR)) {
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

        if (roles.contains(Role.ADMINISTRATOR)) {
            return true;
        }
        return false;
    }

}
