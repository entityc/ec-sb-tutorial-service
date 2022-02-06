//
// This Mapper class is responsible for mapping between various representations of the following entity:
//
//   Name:        User
//   Description: Represents a user in the system.
//
// THIS FILE IS GENERATED. DO NOT EDIT!!
//
package org.entityc.tutorial.mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

import org.entityc.tutorial.model.Role;
import org.entityc.tutorial.dto.UserDto;
import org.entityc.tutorial.model.User;

@Component
public class UserMapper
{
    @Autowired ModelMapper mapper;


    public UserDto toDtoFromModel(User modelObject) {
        UserDto dtoObject = new UserDto();
        // primary key
        dtoObject.setId(modelObject != null ? modelObject.getId() : null);

        // attributes
        if (modelObject != null && modelObject.getCreatedOn() != null) {
            dtoObject.setCreatedOn((modelObject != null ? modelObject.getCreatedOn() : null));
        }
        if (modelObject != null && modelObject.getEmail() != null) {
            dtoObject.setEmail((modelObject != null ? modelObject.getEmail() : null));
        }
        if (modelObject != null && modelObject.getEnabled() != null) {
            dtoObject.setEnabled((modelObject != null ? modelObject.getEnabled() : null));
        }
        if (modelObject != null && modelObject.getFirstName() != null) {
            dtoObject.setFirstName((modelObject != null ? modelObject.getFirstName() : null));
        }
        if (modelObject != null && modelObject.getLastName() != null) {
            dtoObject.setLastName((modelObject != null ? modelObject.getLastName() : null));
        }
        if (modelObject != null && modelObject.getModifiedOn() != null) {
            dtoObject.setModifiedOn((modelObject != null ? modelObject.getModifiedOn() : null));
        }
        if (modelObject != null && modelObject.getRoles() != null) {
            dtoObject.setRoles(modelObject.getRoles().stream().map(s -> Role.valueOf(s.toString())).collect(Collectors.toSet()));
        }
        return dtoObject;
    }

    public User toModelFromDto(UserDto dtoObject) {
        // for now do this, but since names can be quite different potentially we should
        // really do attribute by attribute code like the others.
        return mapper.map(dtoObject, User.class);
    }
}

