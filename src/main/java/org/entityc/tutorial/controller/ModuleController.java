//
// This Controller class is responsible for implementing the API endpoint for entity:
//
//   Name:        Module
//   Description: Represents a module within the tutorial. A module is a big partition of
//                the tutorial.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//
package org.entityc.tutorial.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.entityc.tutorial.util.ResourceUtils;
import org.entityc.tutorial.exception.ServiceException;
import org.entityc.tutorial.exception.EntityNotFoundException;
import org.entityc.tutorial.exception.ValidationException;
import org.entityc.tutorial.security.UserDetailsMapper;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.entityc.tutorial.model.Module;
import org.entityc.tutorial.service.ModuleService;
import org.entityc.tutorial.mapper.ModuleMapper;
import org.entityc.tutorial.dto.ModuleDto;
import org.entityc.tutorial.service.ModuleService;
import org.entityc.tutorial.mapper.ModuleMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping(value = "/api/ectutorials/module")
public class ModuleController {

    @Autowired
    public ModelMapper mapper;

    @Autowired
    public UserDetailsMapper userDetailsMapper;

    @PostConstruct
    private void postConstruct() {
        mapper.getConfiguration().setAmbiguityIgnored(true);
    }

    private final ModuleService moduleService;
    private final ModuleMapper moduleMapper;

    @Autowired
    public ModuleController(
        ModuleService moduleService,
        ModuleMapper moduleMapper
    ) {
        this.moduleService = moduleService;
        this.moduleMapper = moduleMapper;
    }


    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModuleDto createModule(@RequestBody ModuleDto requestDto) throws ServiceException {
        Module requestObject = mapper.map(requestDto, Module.class);
        Module resultObject = moduleService.createModule(requestObject);
        return mapper.map(resultObject, ModuleDto.class);
    }

    @RequestMapping(
        path = "/tutorial/{id}",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModuleDto createModuleWithTutorial(@PathVariable("id") UUID id, @RequestBody ModuleDto requestDto) throws ServiceException {
        Module requestObject = mapper.map(requestDto, Module.class);
        Module resultObject = moduleService.createModuleWithTutorial(id, requestObject);
        return mapper.map(resultObject, ModuleDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.PUT,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ModuleDto updateModule(@PathVariable("id") UUID id, @RequestBody ModuleDto requestDto, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        requestDto.adjustUpdateForRoles(userDetailsMapper.rolesForUserDetails(userDetails));
        Module requestObject = mapper.map(requestDto, Module.class);
        requestObject.setId(id);
        Module resultObject = moduleService.updateModule(requestObject);
        return mapper.map(resultObject, ModuleDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ModuleDto getModuleById(@PathVariable("id") UUID id) throws ServiceException {
        return moduleService.getModuleDtoById(id);
    }


    @RequestMapping(
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<List<ModuleDto>> getModuleList(
        @RequestParam("tutorialId") String tutorialId,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit)
        throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<ModuleDto> results = null;
        if (tutorialId != null) {
            results = moduleService.getModuleDtoListByTutorial(UUID.fromString(tutorialId), queryStart, queryLimit, false);
        } else {
            results = moduleService.getModuleDtoList(queryStart, queryLimit, false);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @RequestMapping(
        path = "/tutorial/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<List<ModuleDto>> getModuleListByTutorial(
        @PathVariable("id") UUID tutorialId,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit) throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<ModuleDto> results = moduleService.getModuleDtoListByTutorial(tutorialId, queryStart, queryLimit, true);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @DeleteMapping("/tutorial/{id}/batch")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void deleteModuleByTutorialId(@PathVariable("id") UUID id) throws ServiceException {
        moduleService.deleteModuleByTutorialId(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void deleteModuleById(@PathVariable("id") UUID id) throws ServiceException {
        moduleService.deleteModuleById(id);
    }

}
