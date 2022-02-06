//
// This Controller class is responsible for implementing the API endpoint for entity:
//
//   Name:        User
//   Description: Represents a user in the system.
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

import org.entityc.tutorial.model.User;
import org.entityc.tutorial.service.UserService;
import org.entityc.tutorial.mapper.UserMapper;
import org.entityc.tutorial.dto.UserDto;
import org.entityc.tutorial.service.UserService;
import org.entityc.tutorial.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.entityc.tutorial.model.Role;
import java.util.Set;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping(value = "/api/ectutorials/user")
public class UserController {

    @Autowired
    public ModelMapper mapper;

    @Autowired
    public UserDetailsMapper userDetailsMapper;

    @PostConstruct
    private void postConstruct() {
        mapper.getConfiguration().setAmbiguityIgnored(true);
    }

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(
        UserService userService,
        UserMapper userMapper
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @RequestMapping(
        method = RequestMethod.POST,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public UserDto createUser(@RequestBody UserDto requestDto) throws ServiceException {
        User requestObject = mapper.map(requestDto, User.class);
        User resultObject = userService.createUser(requestObject);
        return mapper.map(resultObject, UserDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.PUT,
        consumes = "application/json",
        produces = "application/json"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public UserDto updateUser(@PathVariable("id") UUID id, @RequestBody UserDto requestDto, @AuthenticationPrincipal UserDetails userDetails) throws ServiceException {
        User user = userService.getByEmail(userDetails.getUsername());
        Set<Role> roles = user.getRoles();
        requestDto.adjustUpdateForRoles(user, roles);
        User requestObject = mapper.map(requestDto, User.class);
        requestObject.setId(id);
        User resultObject = userService.updateUser(requestObject);
        return mapper.map(resultObject, UserDto.class);
    }

    @RequestMapping(
        path = "/{id}",
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public UserDto getUserById(@PathVariable("id") UUID id) throws ServiceException {
        return userService.getUserDtoById(id);
    }


    @RequestMapping(
        method = RequestMethod.GET,
        produces = "application/json"
    )
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public ResponseEntity<List<UserDto>> getUserList(
        @RequestParam("start") Integer start,
        @RequestParam("limit") Integer limit)
        throws ServiceException {
        final int queryStart = ResourceUtils.defaultTo(start, 0);
        final int queryLimit = ResourceUtils.defaultTo(limit, 10);
        List<UserDto> results = null;
        results = userService.getUserDtoList(queryStart, queryLimit, false);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + results.size());
        headers.add("Access-Control-Expose-Headers", "X-Total-Count");
        return new ResponseEntity(results, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    public void deleteUserById(@PathVariable("id") UUID id) throws ServiceException {
        userService.deleteUserById(id);
    }

}
