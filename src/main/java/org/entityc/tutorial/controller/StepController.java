//
// This Controller class is responsible for implementing the API endpoint for entity:
//
//   Name:        Step
//   Description: An exercise is broken down into smaller steps where a single step has the
//                user perform a small task as part of the exercise.
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

import org.entityc.tutorial.model.Step;
import org.entityc.tutorial.service.StepService;
import org.entityc.tutorial.mapper.StepMapper;
import org.entityc.tutorial.dto.StepDto;
import org.entityc.tutorial.service.StepService;
import org.entityc.tutorial.mapper.StepMapper;
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
@RequestMapping(value = "/api/ectutorials/step")
public class StepController {

    @Autowired
    public ModelMapper mapper;

    @Autowired
    public UserDetailsMapper userDetailsMapper;

    @PostConstruct
    private void postConstruct() {
        mapper.getConfiguration().setAmbiguityIgnored(true);
    }

    private final StepService stepService;
    private final StepMapper stepMapper;

    @Autowired
    public StepController(
        StepService stepService,
        StepMapper stepMapper
    ) {
        this.stepService = stepService;
        this.stepMapper = stepMapper;
    }


    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public StepDto createStep(@RequestBody StepDto requestDto) throws ServiceException {
        Step requestObject = mapper.map(requestDto, Step.class);
        Step resultObject = stepService.createStep(requestObject);
        return mapper.map(resultObject, StepDto.class);
    }

    @RequestMapping(
        path = "/exercise/{id}",
        method = RequestMethod.POST,
        consumes = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public StepDto createStepWithExercise(@PathVariable("id") UUID id, @RequestBody StepDto requestDto) throws ServiceException {
        Step requestObject = mapper.map(requestDto, Step.class);
        Step resultObject = stepService.createStepWithExercise(id, requestObject);
        return mapper.map(resultObject, StepDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.PUT,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public StepDto updateStep(@PathVariable("id") UUID id, @RequestBody StepDto requestDto, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        requestDto.adjustUpdateForRoles(userDetailsMapper.rolesForUserDetails(userDetails));
        Step requestObject = mapper.map(requestDto, Step.class);
        requestObject.setId(id);
        Step resultObject = stepService.updateStep(requestObject);
        return mapper.map(resultObject, StepDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public StepDto getStepById(@PathVariable("id") UUID id) throws ServiceException {
        return stepService.getStepDtoById(id);
    }


    @RequestMapping(
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<List<StepDto>> getStepList(
        @RequestParam("exerciseId") String exerciseId,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit)
        throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<StepDto> results = null;
        if (exerciseId != null) {
            results = stepService.getStepDtoListByExercise(UUID.fromString(exerciseId), queryStart, queryLimit, false);
        } else {
            results = stepService.getStepDtoList(queryStart, queryLimit, false);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @RequestMapping(
        path = "/exercise/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('INSTRUCTOR', 'STUDENT')")
    public ResponseEntity<List<StepDto>> getStepListByExercise(
        @PathVariable("id") UUID exerciseId,
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit) throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<StepDto> results = stepService.getStepDtoListByExercise(exerciseId, queryStart, queryLimit, true);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @DeleteMapping("/exercise/{id}/batch")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void deleteStepByExerciseId(@PathVariable("id") UUID id) throws ServiceException {
        stepService.deleteStepByExerciseId(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    public void deleteStepById(@PathVariable("id") UUID id) throws ServiceException {
        stepService.deleteStepById(id);
    }

}
