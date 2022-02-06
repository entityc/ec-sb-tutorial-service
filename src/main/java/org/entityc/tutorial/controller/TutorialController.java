//
// This Controller class is responsible for implementing the API endpoint for entity:
//
//   Name:        Tutorial
//   Description: Represents an entire tutorial with modules and sessions.
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

import org.entityc.tutorial.model.Tutorial;
import org.entityc.tutorial.service.TutorialService;
import org.entityc.tutorial.mapper.TutorialMapper;
import org.entityc.tutorial.dto.TutorialDto;
import org.entityc.tutorial.service.TutorialService;
import org.entityc.tutorial.mapper.TutorialMapper;
import org.entityc.tutorial.util.MarkdownImporter;
import org.entityc.tutorial.security.SecurityService;
import org.entityc.tutorial.security.PersistentUserDetailsService;
import org.entityc.tutorial.model.User;
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
@RequestMapping(value = "/api/ectutorials/tutorial")
public class TutorialController {

    @Autowired
    public ModelMapper mapper;

    @Autowired
    public UserDetailsMapper userDetailsMapper;

    @PostConstruct
    private void postConstruct() {
        mapper.getConfiguration().setAmbiguityIgnored(true);
    }

    private final TutorialService tutorialService;
    private final TutorialMapper tutorialMapper;
    private final MarkdownImporter markdownImporter;
    private final SecurityService securityService;
    private final PersistentUserDetailsService persistentUserDetailsService;

    @Autowired
    public TutorialController(
        TutorialService tutorialService,
        TutorialMapper tutorialMapper,
        MarkdownImporter markdownImporter,
        SecurityService securityService,
        PersistentUserDetailsService persistentUserDetailsService
    ) {
        this.tutorialService = tutorialService;
        this.tutorialMapper = tutorialMapper;
        this.markdownImporter = markdownImporter;
        this.securityService = securityService;
        this.persistentUserDetailsService = persistentUserDetailsService;
    }


    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public TutorialDto createTutorial(@RequestBody TutorialDto requestDto) throws ServiceException {
        Tutorial requestObject = mapper.map(requestDto, Tutorial.class);
        Tutorial resultObject = tutorialService.createTutorial(requestObject);
        return mapper.map(resultObject, TutorialDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.PUT,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public TutorialDto updateTutorial(@PathVariable("id") UUID id, @RequestBody TutorialDto requestDto, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        requestDto.adjustUpdateForRoles(userDetailsMapper.rolesForUserDetails(userDetails));
        Tutorial requestObject = mapper.map(requestDto, Tutorial.class);
        requestObject.setId(id);
        Tutorial resultObject = tutorialService.updateTutorial(requestObject);
        return mapper.map(resultObject, TutorialDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public TutorialDto getTutorialById(@PathVariable("id") UUID id) throws ServiceException {
        return tutorialService.getTutorialDtoById(id);
    }


    @RequestMapping(
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ResponseEntity<List<TutorialDto>> getTutorialList(
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit)
        throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<TutorialDto> results = null;
        results = tutorialService.getTutorialDtoList(queryStart, queryLimit, false);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public void deleteTutorialById(@PathVariable("id") UUID id) throws ServiceException {
        tutorialService.deleteTutorialById(id);
    }
    @RequestMapping(
        path = "/{id}/import/markdown",
        method = RequestMethod.POST,
        consumes = "text/markdown"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void importMarkdown(@PathVariable("id") UUID id, @RequestBody String markdownText) throws ServiceException {
        if (!markdownImporter.validateModuleMarkdown(id, markdownText)) {
            throw new ValidationException("Markdown for import not valid.");
        }
        if (!markdownImporter.importModuleMarkdown(id, markdownText)) {
            throw new ValidationException("Error occurred while trying to import this markdown.");
        }
    }

    @RequestMapping(
        path = "/{id}/import/markdown/validate",
        method = RequestMethod.POST,
        consumes = "text/markdown"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void validateMarkdown(@PathVariable("id") UUID id, @RequestBody String markdownText) throws ServiceException {
        if (!markdownImporter.validateModuleMarkdown(id, markdownText)) {
            throw new ValidationException("Markdown for import not valid.");
        }
    }

}
