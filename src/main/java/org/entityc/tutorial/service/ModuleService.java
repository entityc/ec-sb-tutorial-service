//
// This Service class is responsible for higher level functions for entity:
//
//   Name:        Module
//   Description: Represents a module within the tutorial. A module is a big partition of
//                the tutorial.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//

package org.entityc.tutorial.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.entityc.tutorial.model.Module;
import org.entityc.tutorial.dto.ModuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.entityc.tutorial.repository.ModuleRepository;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.entityc.tutorial.exception.ServiceException;
import org.springframework.dao.DataAccessException;
import org.entityc.tutorial.exception.DaoException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
import org.entityc.tutorial.exception.EntityNotFoundException;
import org.entityc.tutorial.mapper.ModuleMapper;
import org.entityc.tutorial.model.Session;
import org.entityc.tutorial.dto.SessionDto;
import java.util.HashSet;
import org.entityc.tutorial.service.SessionService;
import org.entityc.tutorial.model.Tutorial;
import org.entityc.tutorial.dto.TutorialDto;
import org.entityc.tutorial.util.MarkdownUtils;
import org.entityc.tutorial.util.MarkdownBuilder;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.model.User;
import java.util.Set;
import org.entityc.tutorial.model.Role;

@Service
@Transactional
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final SessionService sessionService;
    @Autowired SecurityService securityService;
    @Autowired PersistentUserDetailsService userDetailsService;



    @Autowired
    public ModuleService(
        ModuleRepository moduleRepository,
        ModuleMapper moduleMapper,
        SessionService sessionService
    ) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
        this.sessionService = sessionService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Module createModule(Module object) throws ServiceException {
        object.setId(UUID.randomUUID());
        Module savedObject = this.saveModule(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Module createModuleWithTutorial(UUID tutorialId, Module object) throws ServiceException {
        object.setId(UUID.randomUUID());
        object.setTutorialId(tutorialId);
        Module savedObject = this.saveModule(object);
        return savedObject;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Module updateModule(Module object) throws ServiceException {
        return saveModule(object);
    }

    private Module saveModule(Module object) throws ServiceException {
        try {
            Module savedObject = moduleRepository.saveModule(object);
            return savedObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }
    public Module getModuleById(UUID id) throws ServiceException {
        try {
            Module responseObject = null;
            if (responseObject == null) {
                Optional<Module> modelObjectOptional = moduleRepository.getById(id);
                if (!modelObjectOptional.isPresent()) {
                    throw new EntityNotFoundException("Module not found.");
                }

                Module modelObject = modelObjectOptional.get();
                responseObject = modelObject;
            }
            return responseObject;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public ModuleDto getModuleDtoById(UUID id) throws ServiceException {
        Module object = getModuleById(id);
        return dtoFromModel(object);
    }

    public List<Module> getModuleList(int start, int limit) throws ServiceException {
        try {
            return moduleRepository.getModuleList(start, limit);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<ModuleDto> getModuleDtoList(int start, int limit, boolean hierarchical) throws ServiceException {
        try {
            List<ModuleDto> dtoList = new ArrayList<>();
            List<Module> list = moduleRepository.getModuleList(start, limit);
            for(Module modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<Module> getModuleListByTutorial(
        UUID tutorialId,
        int start,
        int limit,
        boolean hierarchical) throws ServiceException {

        try {
            List<Module> list = moduleRepository.getModuleListByTutorial(tutorialId, start, limit);
            return list;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<ModuleDto> getModuleDtoListByTutorial(
        UUID tutorialId,
        int start,
        int limit,
        boolean hierarchical) throws ServiceException {

        try {
            List<ModuleDto> dtoList = new ArrayList<>();
            List<Module> list = moduleRepository.getModuleListByTutorial(tutorialId, start, limit);
            for(Module modelObject : list) {
                dtoList.add(dtoFromModel(modelObject, hierarchical));
            }
            return dtoList;
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public UUID getParentId(UUID objId) {
        Optional<Module> objOptional = moduleRepository.getById(objId);
        if (objOptional.isPresent()) {
            Module obj = objOptional.get();
            UUID parentId = obj.getTutorialId();
            return parentId;
        }
        return null;
    }

    public void deleteModuleByTutorialId(UUID tutorialId) throws ServiceException {
        try {
            moduleRepository.deleteModuleByTutorialId(tutorialId);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public void deleteModuleById(UUID id) throws ServiceException {
        try {
            moduleRepository.deleteById(id);
        } catch (DataAccessException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public ModuleDto dtoFromModel(Module modelObject, boolean hierarchical) throws ServiceException {
        ModuleDto dtoObject = moduleMapper.toDtoFromModel(modelObject);
        if (hierarchical) {
            dtoObject.setSessions(new HashSet<>(sessionService.getSessionDtoListByModule(modelObject.getId(), 0, 10000, true)));
        }
        return dtoObject;
    }
    /**
     * Creates a DTO object from a Model object.
     * [Template] ServicePublisher:methods < ServiceMappingAuthor
     */
    public ModuleDto dtoFromModel(Module modelObject) throws ServiceException {
        return dtoFromModel(modelObject, true);
    }
    /**
        Builds a markdown document for a specified object that represents a level in the document.
        [Template] ServicePublisher:methods < DocumentBuilderAuthor
     */
    public String buildMarkdownDocSection(final int level, final ModuleDto object, final long count) throws ServiceException {
        MarkdownBuilder sectionBuilder = new MarkdownBuilder();
        sectionBuilder.appendHeading(level, "Module", object.getNumber(), ":", object.getTitle());

        sectionBuilder.appendPara(object.getSummary());
        sectionBuilder.appendHeading(level+1, "Overview");
        sectionBuilder.appendPara(object.getOverview());
        List<SessionDto> list = sessionService.getSessionDtoListByModule(object.getId(), 0, 1000, true);
        for( SessionDto subObj : list) {
             sectionBuilder.append(sessionService.buildMarkdownDocSection(level + 1, subObj, list.size()));
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
