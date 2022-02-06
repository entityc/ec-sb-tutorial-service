//
// This Controller class is responsible for implementing the API endpoint for entity:
//
//   Name:        Session
//   Description: Represents a session within a module. A session typically tries to
//                teach a single concept.
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

import org.entityc.tutorial.model.Session;
import org.entityc.tutorial.service.SessionService;
import org.entityc.tutorial.mapper.SessionMapper;
import org.entityc.tutorial.dto.SessionDto;
import org.entityc.tutorial.service.SessionService;
import org.entityc.tutorial.mapper.SessionMapper;
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
@RequestMapping(value = "/api/ectutorials/session")
public class SessionController {

    @Autowired
    public ModelMapper mapper;

    @Autowired
    public UserDetailsMapper userDetailsMapper;

    @PostConstruct
    private void postConstruct() {
        mapper.getConfiguration().setAmbiguityIgnored(true);
    }

    private final SessionService sessionService;
    private final SessionMapper sessionMapper;

    @Autowired
    public SessionController(
        SessionService sessionService,
        SessionMapper sessionMapper
    ) {
        this.sessionService = sessionService;
        this.sessionMapper = sessionMapper;
    }


    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public SessionDto createSession(@RequestBody SessionDto requestDto) throws ServiceException {
        Session requestObject = mapper.map(requestDto, Session.class);
        Session resultObject = sessionService.createSession(requestObject);
        return mapper.map(resultObject, SessionDto.class);
    }

    @RequestMapping(
        path = "/module/{id}",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public SessionDto createSessionWithModule(@PathVariable("id") UUID id, @RequestBody SessionDto requestDto) throws ServiceException {
        Session requestObject = mapper.map(requestDto, Session.class);
        Session resultObject = sessionService.createSessionWithModule(id, requestObject);
        return mapper.map(resultObject, SessionDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.PUT,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public SessionDto updateSession(@PathVariable("id") UUID id, @RequestBody SessionDto requestDto, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        requestDto.adjustUpdateForRoles(userDetailsMapper.rolesForUserDetails(userDetails));
        Session requestObject = mapper.map(requestDto, Session.class);
        requestObject.setId(id);
        Session resultObject = sessionService.updateSession(requestObject);
        return mapper.map(resultObject, SessionDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public SessionDto getSessionById(@PathVariable("id") UUID id) throws ServiceException {
        return sessionService.getSessionDtoById(id);
    }


    @RequestMapping(
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<List<SessionDto>> getSessionList(
        @RequestParam("moduleId") String moduleId,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit)
        throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<SessionDto> results = null;
        if (moduleId != null) {
            results = sessionService.getSessionDtoListByModule(UUID.fromString(moduleId), queryStart, queryLimit, false);
        } else {
            results = sessionService.getSessionDtoList(queryStart, queryLimit, false);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @RequestMapping(
        path = "/module/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<List<SessionDto>> getSessionListByModule(
        @PathVariable("id") UUID moduleId,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit) throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<SessionDto> results = sessionService.getSessionDtoListByModule(moduleId, queryStart, queryLimit, true);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @DeleteMapping("/module/{id}/batch")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void deleteSessionByModuleId(@PathVariable("id") UUID id) throws ServiceException {
        sessionService.deleteSessionByModuleId(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void deleteSessionById(@PathVariable("id") UUID id) throws ServiceException {
        sessionService.deleteSessionById(id);
    }

}
