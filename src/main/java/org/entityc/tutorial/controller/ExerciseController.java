//
// This Controller class is responsible for implementing the API endpoint for entity:
//
//   Name:        Exercise
//   Description: Represents an exercise within a session. A session typically only has
//                one exercise but it can have more than one if the session is big. An
//                exercise gives the student some hands on experience with the material
//                covered by its session.
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

import org.entityc.tutorial.model.Exercise;
import org.entityc.tutorial.service.ExerciseService;
import org.entityc.tutorial.mapper.ExerciseMapper;
import org.entityc.tutorial.dto.ExerciseDto;
import org.entityc.tutorial.service.ExerciseService;
import org.entityc.tutorial.mapper.ExerciseMapper;
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
@RequestMapping(value = "/api/ectutorials/exercise")
public class ExerciseController {

    @Autowired
    public ModelMapper mapper;

    @Autowired
    public UserDetailsMapper userDetailsMapper;

    @PostConstruct
    private void postConstruct() {
        mapper.getConfiguration().setAmbiguityIgnored(true);
    }

    private final ExerciseService exerciseService;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseController(
        ExerciseService exerciseService,
        ExerciseMapper exerciseMapper
    ) {
        this.exerciseService = exerciseService;
        this.exerciseMapper = exerciseMapper;
    }


    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ExerciseDto createExercise(@RequestBody ExerciseDto requestDto) throws ServiceException {
        Exercise requestObject = mapper.map(requestDto, Exercise.class);
        Exercise resultObject = exerciseService.createExercise(requestObject);
        return mapper.map(resultObject, ExerciseDto.class);
    }

    @RequestMapping(
        path = "/session/{id}",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ExerciseDto createExerciseWithSession(@PathVariable("id") UUID id, @RequestBody ExerciseDto requestDto) throws ServiceException {
        Exercise requestObject = mapper.map(requestDto, Exercise.class);
        Exercise resultObject = exerciseService.createExerciseWithSession(id, requestObject);
        return mapper.map(resultObject, ExerciseDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.PUT,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public ExerciseDto updateExercise(@PathVariable("id") UUID id, @RequestBody ExerciseDto requestDto, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        requestDto.adjustUpdateForRoles(userDetailsMapper.rolesForUserDetails(userDetails));
        Exercise requestObject = mapper.map(requestDto, Exercise.class);
        requestObject.setId(id);
        Exercise resultObject = exerciseService.updateExercise(requestObject);
        return mapper.map(resultObject, ExerciseDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ExerciseDto getExerciseById(@PathVariable("id") UUID id) throws ServiceException {
        return exerciseService.getExerciseDtoById(id);
    }


    @RequestMapping(
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<List<ExerciseDto>> getExerciseList(
        @RequestParam("sessionId") String sessionId,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit)
        throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<ExerciseDto> results = null;
        if (sessionId != null) {
            results = exerciseService.getExerciseDtoListBySession(UUID.fromString(sessionId), queryStart, queryLimit, false);
        } else {
            results = exerciseService.getExerciseDtoList(queryStart, queryLimit, false);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @RequestMapping(
        path = "/session/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<List<ExerciseDto>> getExerciseListBySession(
        @PathVariable("id") UUID sessionId,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit) throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<ExerciseDto> results = exerciseService.getExerciseDtoListBySession(sessionId, queryStart, queryLimit, true);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @DeleteMapping("/session/{id}/batch")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void deleteExerciseBySessionId(@PathVariable("id") UUID id) throws ServiceException {
        exerciseService.deleteExerciseBySessionId(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void deleteExerciseById(@PathVariable("id") UUID id) throws ServiceException {
        exerciseService.deleteExerciseById(id);
    }

}
